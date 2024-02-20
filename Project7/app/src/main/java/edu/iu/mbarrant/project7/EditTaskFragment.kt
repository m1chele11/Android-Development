package edu.iu.mbarrant.project7

import  android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import edu.iu.mbarrant.project7.databinding.EditTaskFragmentBinding


/**
 * A simple [Fragment] subclass.
 * Use the [EditTaskFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditTaskFragment : Fragment() {
    private var _binding: EditTaskFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = EditTaskFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        val taskId = EditTaskFragmentArgs.fromBundle(requireArguments()).taskId

        val viewModel : TasksViewModel by activityViewModels()
        viewModel.taskId = taskId
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.navigateToList.observe(viewLifecycleOwner, Observer { navigate ->
            if (navigate) {
                view.findNavController()
                    .navigate(R.id.action_editTaskFragment_to_tasksFragment)
                viewModel.onNavigatedToList()
            }
        })
        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    //to make changes
}