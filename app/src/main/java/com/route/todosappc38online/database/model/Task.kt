package com.route.todosappc38online.database.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.sql.Date

@Entity(tableName = "todo_table")

@Parcelize
data class Task(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    val id: Int? = null,
    @ColumnInfo
    val title: String? = null,
    @ColumnInfo
    val description: String? = null,
    @ColumnInfo
    var isDone: Boolean? = false,
    @ColumnInfo
    val date: Long? = null,


    ): Parcelable