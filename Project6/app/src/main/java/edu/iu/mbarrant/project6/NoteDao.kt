package edu.iu.mbarrant.project6


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update



@Dao
interface NoteDao {
    @Insert
    suspend fun insert(task: Note): Long
    @Update
    suspend fun update(task: Note)
    @Delete
    suspend fun delete(task: Note)
    @Query("SELECT * FROM task_table WHERE noteId = :key")
    fun get(key: Long): LiveData<Note>
    @Query("SELECT * FROM task_table ORDER BY noteId DESC")
    fun getAll(): LiveData<List<Note>>
}