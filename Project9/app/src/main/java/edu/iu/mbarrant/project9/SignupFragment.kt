package edu.iu.mbarrant.project9

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth

class SignupFragment : Fragment() {
    private val TAG = "SignupFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_signup, container, false)

        val btnSignUp = view.findViewById<Button>(R.id.signUpbtn)
        val login = view.findViewById<Button>(R.id.LoginInBtn)

        btnSignUp.setOnClickListener {
            signUp()
        }

        login.setOnClickListener {
            ToSignInScreen()
        }

        return view
    }

    private fun signUp() {
        val etEmail = view?.findViewById<EditText>(R.id.ET_Email)
        val etPassword = view?.findViewById<EditText>(R.id.ET_Password)
        val etRepeatPassword = view?.findViewById<EditText>(R.id.etRepeatPassword)

        val email = etEmail?.text.toString()
        val password = etPassword?.text.toString()
        val repeatPassword = etRepeatPassword?.text.toString()

        if (email.isBlank() || password.isBlank() || repeatPassword.isBlank()) {
            Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        if (password != repeatPassword) {
            Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return
        }

        val auth = FirebaseAuth.getInstance()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")
                    Toast.makeText(context, "Sign-up successful", Toast.LENGTH_SHORT).show()
                    // Navigate to the desired destination after successful sign-up
                    findNavController().navigate(R.id.action_signupFragment_to_postsFragment)
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun ToSignInScreen() {
        this.findNavController().navigate(R.id.action_signupFragment_to_loginInFragment)
    }
}