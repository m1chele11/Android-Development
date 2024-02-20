package edu.iu.mbarrant.project7

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController


/**
 * A simple [Fragment] subclass.
 * Use the [SplashFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SplashFragment : Fragment() {

    val viewModel : TasksViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.splash_fragment, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onStart() {
        super.onStart()
        val currentUser = viewModel.getCurrentUser()

        val handler = Handler(Looper.myLooper()!!)
        handler.postDelayed({
            if (currentUser != null) {
                this.findNavController().navigate(R.id.action_splashFragment_to_tasksFragment)
            }
            else {
                this.findNavController().navigate(R.id.action_splashFragment_to_signInFragment)

            }

        }, 2000)
    }


}