package edu.iu.mbarrant.project6


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import edu.iu.mbarrant.project6.databinding.NoteFragmentBinding

class EditNoteFragment : Fragment() {
    private var _binding: NoteFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = NoteFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        val taskId = EditNoteFragmentArgs.fromBundle(requireArguments()).taskId

        val application = requireNotNull(this.activity).application
        val dao = NoteDatabase.getInstance(application).NoteDao

        val viewModelFactory = EditNoteViewModelFactory(taskId, dao)
        val viewModel = ViewModelProvider(this, viewModelFactory)
            .get(EditNoteViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.navigateToList.observe(viewLifecycleOwner, Observer { navigate ->
            if (navigate) {
                view.findNavController()
                    .navigate(R.id.action_editNoteFragment_to_notesFragment)
                viewModel.onNavigatedToList()
            }
        })
        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}