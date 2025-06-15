package kv.apps.schoox.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kv.apps.schoox.presentation.screens.course_detail.CourseDetailScreen
import kv.apps.schoox.presentation.screens.course_list.CourseListScreen
import kv.apps.schoox.presentation.viewmodel.course_details.CourseDetailViewModel
import kv.apps.schoox.presentation.viewmodel.course_list.CourseListViewModel

@Composable
fun NavigationGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.CourseList.route
    ) {
        composable(Screen.CourseList.route) {
            val viewModel: CourseListViewModel = hiltViewModel()
            CourseListScreen(
                viewModel = viewModel,
                onCourseClick = { courseId ->
                    navController.navigate(Screen.CourseDetails.createRoute(courseId))
                }
            )
        }

        composable(
            route = Screen.CourseDetails.routeWithArgs,
            arguments = listOf(navArgument(Screen.CourseDetails.argName) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val courseId = backStackEntry.arguments?.getString(Screen.CourseDetails.argName) ?: ""
            val viewModel: CourseDetailViewModel = hiltViewModel()

            LaunchedEffect(courseId) {
                viewModel.loadCourse(courseId)
            }

            CourseDetailScreen(
                onBack = { navController.popBackStack() },
                courseId = courseId,
                viewModel = viewModel,
                onSaveComplete = { navController.popBackStack() }
            )
        }
    }
}