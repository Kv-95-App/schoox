package kv.apps.schoox.domain.repository

import kv.apps.schoox.data.local.entity.CourseEntity
import kv.apps.schoox.domain.model.Course

interface CourseRepository {
    suspend fun getCourses(): List<Course>
    suspend fun getCourseById(id: String): Course?
    suspend fun updateCourseProgress(id: String, progress: Int): Boolean
    suspend fun getCourseCount(): Int
    suspend fun getDefaultCoursesInOrder(): List<CourseEntity>
}