package kv.apps.schoox.presentation.viewmodel.course_list

import kv.apps.schoox.data.local.entity.CourseEntity

data class CourseListUiState(
    val courses: List<CourseEntity> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)