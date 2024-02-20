package edu.iu.mbarrant.project9

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import edu.iu.mbarrant.project9.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {
    val TAG = "ProfileFragment"
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        val viewModel: PostsViewModel by activityViewModels()
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.inflateMenu(R.menu.menu_profile)
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_logout -> {
                    // logout and Navigate to login screen.

                    binding.viewModel?.signOut()
                    //view.findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
                    true
                }
                else -> false
            }
        }
    }

}