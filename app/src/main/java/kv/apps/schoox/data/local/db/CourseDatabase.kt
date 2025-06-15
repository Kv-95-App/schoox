package kv.apps.schoox.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import kv.apps.schoox.data.local.dao.CourseDao
import kv.apps.schoox.data.local.entity.CourseEntity

@Database(
    entities = [CourseEntity::class],
    version = 1
)
abstract class CourseDatabase : RoomDatabase() {
    abstract fun courseDao(): CourseDao
}