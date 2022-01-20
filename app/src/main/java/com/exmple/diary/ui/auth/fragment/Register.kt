package com.exmple.diary.ui.auth.fragment

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
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
import com.exmple.diary.model.User
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_register.*
import java.util.*

class Register : Fragment() {

    private lateinit var navController: NavController
    private lateinit var userViewModel: UserViewModel
    private lateinit var db: DatabaseReference
    private val formate = SimpleDateFormat("dd MMM yyyy", Locale.US)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_register, container, false)
        navController = findNavController()
        db= Firebase.database.getReference("Users")
        userViewModel = ViewModelProvider(this, UserViewModelFactory(Validation().authInstance(), requireActivity(), requireContext(), db)).get(
            UserViewModel::class.java)

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val validation = Validation()
        val auth = validation.authInstance()
        register_btn.setOnClickListener {
            val name = name_r.text.toString().trim()
            val uAge = age.text.toString().trim()
            val uDob = dob.text.toString().trim()
            val email = email_id.text.toString().trim()
            val pass = password.text.toString().trim()


            if (name.isEmpty()){
                name_r.requestFocus()
                name_r.error = "Enter your name"
                return@setOnClickListener
            }
            if (uAge.isEmpty()){
                age.requestFocus()
                age.error = "Enter your age"
                return@setOnClickListener
            }
            if (uDob.isEmpty()){
                dob.requestFocus()
                dob.error = "Enter your Date of birth"
                return@setOnClickListener
            }
            if (email.isEmpty()){
                email_id.requestFocus()
                email_id.error = "Enter your Enter your email"
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                email_id.requestFocus()
                email_id.error = "Enter valid email"
                return@setOnClickListener
            }
            if (pass.isEmpty()){
            password.requestFocus()
            password.error = "Enter your password"
            return@setOnClickListener
        }
//            validation.validateData(arrayOf(name, uAge, uDob, email, pass), arrayOf(name_r, age, dob, email_id, password))
            r_pro.visibility = View.VISIBLE
            userViewModel.signInUser(email, pass, name,uAge, uDob,r_pro)

        }
        click_r.setOnClickListener {
            navController.navigate(RegisterDirections.actionRegisterToLogin())
        }
        dob.setOnClickListener {
            val now = Calendar.getInstance()
            val datePicker = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                now.set(android.icu.util.Calendar.YEAR, year)
                now.set(android.icu.util.Calendar.MONTH, month)
                now.set(android.icu.util.Calendar.DAY_OF_MONTH, dayOfMonth)
                val dat = formate.format(now.time)
                dob.setText(dat)
            }, now.get(android.icu.util.Calendar.YEAR), now.get(android.icu.util.Calendar.MONTH), now.get(
                android.icu.util.Calendar.DAY_OF_MONTH))
            datePicker.show()
        }
    }
}