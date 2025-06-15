package kv.apps.schoox.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kv.apps.schoox.data.local.entity.CourseEntity
import javax.inject.Singleton

@Dao
interface CourseDao {
    @Query("SELECT * FROM courses ORDER BY title ASC")
    suspend fun getAll(): List<CourseEntity>

    @Query("SELECT * FROM courses WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): CourseEntity?

    @Update
    suspend fun update(course: CourseEntity): Int

    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(courses: List<CourseEntity>): List<Long>

    @Query("SELECT COUNT(*) FROM courses")
    suspend fun getCount(): Int

    @Query("DELETE FROM courses WHERE id IN ('1','2','3')")
    suspend fun deleteDefaultCourses()

    @Query("""
        SELECT * FROM courses 
        WHERE id IN ('1','2','3')
        ORDER BY CASE id 
            WHEN '1' THEN 1 
            WHEN '2' THEN 2 
            WHEN '3' THEN 3 
            ELSE 4 
        END
    """)
    suspend fun getDefaultCoursesInOrder(): List<CourseEntity>
}
