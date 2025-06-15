package kv.apps.schoox.domain.model

data class Course(
    val id: String,
    val title: String,
    val description: String,
    val imageRes: Int,
    val progress: Int
)