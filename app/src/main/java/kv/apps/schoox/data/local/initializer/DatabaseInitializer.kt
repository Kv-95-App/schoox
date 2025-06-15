package kv.apps.schoox.data.local.initializer

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kv.apps.schoox.R
import kv.apps.schoox.data.local.dao.CourseDao
import kv.apps.schoox.data.local.entity.CourseEntity
import javax.inject.Inject

class DatabaseInitializer @Inject constructor(
    private val dao: CourseDao
) {
    suspend fun initialize() = withContext(Dispatchers.IO) {
        dao.insertAll(createSampleCourses())
    }


    private fun createSampleCourses(): List<CourseEntity> {
        return listOf(
            CourseEntity(
                id = "1",
                title = "Welcome to Schoox",
                description = "Introduction to the Schoox platform",
                imageRes = R.drawable.schoox_image,
                progress = 50
            ),
            CourseEntity(
                id = "2",
                title = "Using Courses",
                description = "Learn how to navigate and complete courses.",
                imageRes = R.drawable.schoox_image,
                progress = 100
            ),
            CourseEntity(
                id = "3",
                title = "Using Learning Paths",
                description = "This course will guide you through the structure and functionality of learning paths within the Schoox platform.",
                imageRes = R.drawable.schoox_image,
                progress = 0
            )
        )
    }
}
