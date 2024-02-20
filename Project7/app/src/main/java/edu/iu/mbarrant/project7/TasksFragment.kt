package edu.iu.mbarrant.project7

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.ValueEventListener
import edu.iu.mbarrant.project7.databinding.TasksFragmentBinding


/**
 * A simple [Fragment] subclass.
 * Use the [TasksFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TasksFragment : Fragment()   {
    val TAG = "TasksFragment"
    private var _binding: TasksFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = TasksFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        val viewModel : TasksViewModel by activityViewModels()
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner


        fun taskClicked (task : Task) {
            viewModel.onTaskClicked(task)
        }
        fun yesPressed(taskId : String) {
            Log.d(TAG, "in yesPressed(): taskId = $taskId")
            binding.viewModel?.deleteTask(taskId)
        }
        fun deleteClicked (taskId : String) {
            ConfirmDeleteDialogFragment(taskId,::yesPressed).show(childFragmentManager,
                ConfirmDeleteDialogFragment.TAG)
        }
        val adapter = TaskItemAdapter(::taskClicked,::deleteClicked)


        binding.tasksList.adapter = adapter

        viewModel.tasks.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        viewModel.navigateToTask.observe(viewLifecycleOwner, Observer { taskId ->
            taskId?.let {
                val action = TasksFragmentDirections
                    .actionTasksFragmentToEditTaskFragment(taskId)
                this.findNavController().navigate(action)
                viewModel.onTaskNavigated()
            }
        })
        viewModel.navigateToSignIn.observe(viewLifecycleOwner, Observer { navigate ->
            if(navigate) {
                this.findNavController().navigate(R.id.action_tasksFragment_to_signInFragment)
                viewModel.onNavigatedToSignIn()
            }
        })

        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}