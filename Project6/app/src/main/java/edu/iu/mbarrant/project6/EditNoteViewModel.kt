package edu.iu.mbarrant.project6

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class EditNoteViewModel(taskId: Long, val dao: NoteDao) : ViewModel() {
    val note = dao.get(taskId)
    var newNoteName = ""
    var newNoteDesc = ""
    private val _navigateToList = MutableLiveData<Boolean>(false)
    val navigateToList: LiveData<Boolean>
        get() = _navigateToList

    // Function to save the note to the database
    fun saveNote() {
        val noteToSave = note.value
        if (noteToSave != null) {
            viewModelScope.launch {
                dao.insert(noteToSave)
            }
        }
        navigateToList
    }

    fun addNote() {
        viewModelScope.launch {
            val note = Note()
            note.noteName = newNoteName
            dao.insert(note)
        }
    }

    fun onNavigatedToList() {
        _navigateToList.value = false
    }
}