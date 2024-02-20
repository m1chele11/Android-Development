package edu.iu.mbarrant.project6

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "task_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    var noteId: Long = 0L,
    @ColumnInfo(name = "note_name")
    var noteName: String = "",
    @ColumnInfo(name = "note_done")
    var noteDone: Boolean = false,
    @ColumnInfo(name = "note_description")
    var noteDescription: String = ""
)