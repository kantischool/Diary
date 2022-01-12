package com.exmple.diary.ui.auth.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.exmple.diary.ui.main.MainActivity
import com.exmple.diary.R
import com.exmple.diary.UserViewModel
import com.exmple.diary.UserViewModelFactory
import com.exmple.diary.Validation
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_login.*


class Login : Fragment() {

    private lateinit var navController: NavController
    private lateinit var userViewModel: UserViewModel
    private lateinit var db: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_login, container, false)
        db= Firebase.database.getReference("Users")
        userViewModel = ViewModelProvider(this, UserViewModelFactory(Validation().authInstance(), requireActivity(), requireContext(), db)).get(UserViewModel::class.java)
        navController = findNavController()
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        login_btn.setOnClickListener {
            val emailId = email.text.toString().trim()
            val passId = pass.text.toString().trim()
            if (!Patterns.EMAIL_ADDRESS.matcher(emailId).matches()){
                email.requestFocus()
                email.error = "Enter valid email"
            }
            Validation().validateData(arrayOf(emailId, passId), arrayOf(email, pass))
            l_pro.visibility = View.VISIBLE
            userViewModel.loginUser(emailId, passId, l_pro)
        }
        click_l.setOnClickListener {
            navController.navigate(LoginDirections.actionLoginToRegister())
        }

    }

    override fun onStart() {
        super.onStart()
        if (Validation().authInstance().currentUser != null){
            startActivity(Intent(requireActivity(), MainActivity::class.java))
            requireActivity().finish()
        }
    }

}