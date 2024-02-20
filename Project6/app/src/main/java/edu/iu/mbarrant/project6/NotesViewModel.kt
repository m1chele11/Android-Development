package edu.iu.mbarrant.project6


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Insert
import kotlinx.coroutines.launch



class NotesViewModel(val dao: NoteDao) : ViewModel() {
    var newNoteName = ""
    val tasks = dao.getAll()
    private val _navigateToNote = MutableLiveData<Long?>()
    val navigateToNote: LiveData<Long?>
        get() = _navigateToNote

    // Function to insert a new note into the database and return its ID
//    @Insert
//    suspend fun insertNote(note: Note) {
//        return dao.insert(note)
//    }

    fun addNote() {
        Log.d("NotesViewModel", "addNote function called")
        viewModelScope.launch {
            val note = Note()
            note.noteName = newNoteName
            val id=dao.insert(note)
            _navigateToNote.value=id

        }
    }

    fun onNoteClicked(taskId: Long) {
        _navigateToNote.value = taskId
    }

    fun onNoteNavigated() {
        _navigateToNote.value = null
    }

}
