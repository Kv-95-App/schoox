package kv.apps.schoox.presentation.viewmodel.course_list

sealed class CourseListEvent {
    data class Error(val message: String) : CourseListEvent()
}