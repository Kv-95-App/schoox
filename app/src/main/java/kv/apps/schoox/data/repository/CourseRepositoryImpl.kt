package kv.apps.schoox.data.repository

import kv.apps.schoox.data.local.dao.CourseDao
import kv.apps.schoox.data.local.entity.CourseEntity
import kv.apps.schoox.domain.model.Course
import kv.apps.schoox.domain.repository.CourseRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CourseRepositoryImpl @Inject constructor(
    private val dao: CourseDao
) : CourseRepository {

    override suspend fun getCourses(): List<Course> {
        return dao.getAll().map { it.toDomain() }
    }

    override suspend fun getCourseById(id: String): Course? {
        return dao.getById(id)?.toDomain()
    }

    override suspend fun updateCourseProgress(id: String, progress: Int): Boolean {
        return dao.getById(id)?.let { existingCourse ->
            val updatedCourse = existingCourse.copy(
                progress = progress.coerceIn(0, 100)
            )
            dao.update(updatedCourse) > 0
        } == true
    }

    override suspend fun getCourseCount(): Int {
        return dao.getCount()
    }

    override suspend fun getDefaultCoursesInOrder(): List<CourseEntity> {
        return dao.getDefaultCoursesInOrder()
    }

    private fun CourseEntity.toDomain(): Course {
        return Course(
            id = id,
            title = title,
            description = description,
            imageRes = imageRes,
            progress = progress
        )
    }
}