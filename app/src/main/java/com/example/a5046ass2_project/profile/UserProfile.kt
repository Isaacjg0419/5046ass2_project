package com.example.a5046ass2_project.profile

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.util.Date


@Entity
data class UserProfile(
    @PrimaryKey
    var email: String = "",
    var gender: String? = null,
    var dateOfBirth: Date? = null
)

object CurrentUser {
    var profile: UserProfile? = null
}

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}