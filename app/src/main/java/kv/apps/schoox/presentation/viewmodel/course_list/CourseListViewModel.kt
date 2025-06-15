package kv.apps.schoox.presentation.viewmodel.course_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kv.apps.schoox.data.local.initializer.DatabaseInitializer
import kv.apps.schoox.domain.repository.CourseRepository
import javax.inject.Inject

@HiltViewModel
class CourseListViewModel @Inject constructor(
    private val repository: CourseRepository,
    private val dbInitializer: DatabaseInitializer
) : ViewModel() {

    private val _uiState = MutableStateFlow(CourseListUiState())
    val uiState: StateFlow<CourseListUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<CourseListEvent>()

    init {
        loadCourses()
    }

    fun loadCourses() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                dbInitializer.initialize()

                val courses = repository.getDefaultCoursesInOrder()

                _uiState.update {
                    it.copy(
                        courses = courses,
                        isLoading = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Failed to load courses"
                    )
                }
                emitError("Failed to load courses: ${e.message}")
            }
        }
    }

    private suspend fun emitError(message: String) {
        _events.emit(CourseListEvent.Error(message))
    }

}