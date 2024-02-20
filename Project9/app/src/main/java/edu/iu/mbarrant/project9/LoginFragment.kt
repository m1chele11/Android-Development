package edu.iu.mbarrant.project9

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth


class LoginFragment : Fragment() {
    private val TAG = "LoginFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            goToPostsScreen()
        }

        val btnSignUp = view.findViewById<Button>(R.id.btnSignUp)
        btnSignUp.setOnClickListener {
            navigateToSignUpScreen()
        }



        val btnLogin = view.findViewById<Button>(R.id.btnLogin)
        btnLogin.setOnClickListener {
            btnLogin.isEnabled = false
            val etEmail = view.findViewById<TextView>(R.id.ET_Email)
            val etPassword = view.findViewById<TextView>(R.id.ET_Password)
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this.context, "Email/password cannot be empty", Toast.LENGTH_SHORT).show()
                btnLogin.isEnabled = true
                return@setOnClickListener
            }
            // Firebase authentication check
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                btnLogin.isEnabled = true
                if (task.isSuccessful) {
                    Toast.makeText(this.context, "Success!", Toast.LENGTH_SHORT).show()
                    goToPostsScreen()
                } else {
                    Log.e(TAG, "signInWithEmail failed", task.exception)
                    Toast.makeText(this.context, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
        return view
    }

    private fun goToPostsScreen() {
        this.findNavController().navigate(R.id.action_loginFragment_to_postsFragment)
    }

    private fun navigateToSignUpScreen() {
        this.findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
    }
}