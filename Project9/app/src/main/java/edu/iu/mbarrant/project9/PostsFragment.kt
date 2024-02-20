package edu.iu.mbarrant.project9

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import edu.iu.mbarrant.project9.databinding.FragmentPostsBinding


class PostsFragment : Fragment() {

    private lateinit var shakeDetector: ShakeFragment
    private lateinit var viewModel: PostsViewModel

    val TAG = "PostsFragment"
    private var _binding: FragmentPostsBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val navController = findNavController()

        shakeDetector = ShakeFragment(requireContext()) {
            Log.d(TAG, "Shake detected! Navigating to TakePhotoFragment.")
            navController.navigate(R.id.action_postsFragment_to_takePhotoFragment)
        }

        _binding = FragmentPostsBinding.inflate(inflater, container, false)
        val view = binding.root
        // ViewModel initialization
        viewModel = ViewModelProvider(this).get(PostsViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // Access the shared PostsViewModel
        val postsViewModel: PostsViewModel by activityViewModels()

        // Observe changes in the ViewModel and update the RecyclerView
        postsViewModel.posts.observe(viewLifecycleOwner, Observer { posts ->
            // Update your RecyclerView adapter with the new list of posts
            // ...
        })

        val adapter = PostsAdapter(this.requireContext())
        binding.rvPosts.adapter = adapter
        viewModel.posts.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
//                adapter.notifyDataSetChanged()
            }
        })
        // Set click listener for sign out FAB
        binding.signOut.setOnClickListener {
            viewModel.signOut()
            // Navigate back to sign-in screen after signing out
            binding.signOut
            navController.navigate(R.id.action_postsFragment_to_login)

        }


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.inflateMenu(R.menu.menu_posts)
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_profile -> {
                    // Navigate to profile screen.
                    //view.findNavController().navigate(R.id.action_postsFragment_to_profileFragment)
                    true
                }
                else -> false
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        shakeDetector.unregisterSensor()

    }
}