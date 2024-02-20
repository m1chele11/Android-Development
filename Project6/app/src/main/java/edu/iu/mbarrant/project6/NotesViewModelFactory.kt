package edu.iu.mbarrant.project6

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class NotesViewModelFactory(private val dao: NoteDao)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesViewModel::class.java)) {
            return NotesViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}