package kv.apps.schoox.presentation.navigation

sealed class Screen(val route: String) {
    object CourseList : Screen("course_list")
    object CourseDetails : Screen("course_details") {
        const val argName = "courseId"
        val routeWithArgs = "$route/{$argName}"
        fun createRoute(courseId: String) = "$route/$courseId"
    }
}