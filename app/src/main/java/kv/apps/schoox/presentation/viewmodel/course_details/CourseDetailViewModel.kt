package kv.apps.schoox.presentation.viewmodel.course_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kv.apps.schoox.domain.repository.CourseRepository
import javax.inject.Inject

@HiltViewModel
class CourseDetailViewModel @Inject constructor(
    private val repository: CourseRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CourseDetailUiState())
    val uiState: StateFlow<CourseDetailUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<CourseDetailEvent>()
    val events: SharedFlow<CourseDetailEvent> = _events.asSharedFlow()

    private var currentCourseId: String? = null

    fun loadCourse(courseId: String) {
        if (courseId == currentCourseId) return

        currentCourseId = courseId
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val course = repository.getCourseById(courseId)
                    ?: throw IllegalArgumentException("Course $courseId not found")

                _uiState.update {
                    it.copy(
                        course = course,
                        currentProgress = course.progress,
                        isLoading = false,
                        isProgressChanged = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        course = null
                    )
                }
                _events.emit(CourseDetailEvent.Error("Failed to load course: ${e.message}"))
            }
        }
    }

    fun updateProgress(progress: Int) {
        _uiState.update {
            it.copy(
                currentProgress = progress.coerceIn(0, 100),
                isProgressChanged = true
            )
        }
    }

    fun saveProgress() {
        viewModelScope.launch {
            val courseId = uiState.value.course?.id ?: run {
                _events.emit(CourseDetailEvent.Error("No course loaded to save progress"))
                return@launch
            }

            _uiState.update { it.copy(isSaving = true) }
            try {
                repository.updateCourseProgress(
                    courseId,
                    uiState.value.currentProgress
                )
                _events.emit(CourseDetailEvent.ProgressSaved)
                _uiState.update { state ->
                    state.copy(
                        isSaving = false,
                        isProgressChanged = false,
                        course = state.course?.copy(progress = state.currentProgress)
                    )
                }
                _events.emit(CourseDetailEvent.NavigateBack)
            } catch (e: Exception) {
                _events.emit(CourseDetailEvent.Error("Failed to save progress: ${e.message}"))
                _uiState.update { it.copy(isSaving = false) }
            }
        }
    }
}