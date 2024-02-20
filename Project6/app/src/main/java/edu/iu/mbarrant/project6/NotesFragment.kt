package edu.iu.mbarrant.project6

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import edu.iu.mbarrant.project6.databinding.NoteListFragmentBinding
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [TasksFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NotesFragment : Fragment() {
    private var _binding: NoteListFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        _binding = NoteListFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        val application = requireNotNull(this.activity).application
        val dao = NoteDatabase.getInstance(application).NoteDao
        val viewModelFactory = NotesViewModelFactory(dao)

        val viewModel = ViewModelProvider(
            this, viewModelFactory).get(NotesViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        val adapter = TaskItemAdapter{ taskId ->
            viewModel.onNoteClicked(taskId)
        }

        binding.listRecyclerView.adapter = adapter

        viewModel.tasks.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        viewModel.navigateToNote.observe(viewLifecycleOwner, Observer { taskId ->
            taskId?.let {
                val action =NotesFragmentDirections
                    .actionNotesFragmentToEditNoteFragment(taskId)
                this.findNavController().navigate(action)
                viewModel.onNoteNavigated()
            }
        })

//        binding.addNoteButton.setOnClickListener {
//            // Create a new task
//            val newNote = Note(
//                noteName = "", // Replace with the actual task name
//                noteDescription = "" // Replace with the actual task description
//            )
//
//            // Launch a coroutine to insert the note into the database and obtain the ID
//            viewModel.viewModelScope.launch {
//                val newNoteId = viewModel.insertNote(newNote)
//                //Trigger navigation with the obtained note ID
//                viewModel.addNote()
//            }
//        }

        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}