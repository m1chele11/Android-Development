package edu.iu.mbarrant.project6

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class EditNoteViewModelFactory(private val taskId: Long,
                               private val dao: NoteDao)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditNoteViewModel::class.java)) {
            return EditNoteViewModel(taskId, dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }

}