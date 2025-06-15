package kv.apps.schoox.presentation.viewmodel.course_details

import kv.apps.schoox.domain.model.Course

data class CourseDetailUiState(
    val course: Course? = null,
    val currentProgress: Int = 0,
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val isProgressChanged: Boolean = false
)