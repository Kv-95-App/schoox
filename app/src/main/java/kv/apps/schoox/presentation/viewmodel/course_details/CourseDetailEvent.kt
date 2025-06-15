package kv.apps.schoox.presentation.viewmodel.course_details

sealed class CourseDetailEvent {
    data class Error(val message: String) : CourseDetailEvent()
    object ProgressSaved : CourseDetailEvent()
    object NavigateBack : CourseDetailEvent()
}