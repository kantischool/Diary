package com.exmple.diary.ui.main.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.exmple.diary.R
import com.exmple.diary.UserViewModel
import com.exmple.diary.UserViewModelFactory
import com.exmple.diary.Validation
import com.exmple.diary.ui.main.MainActivity
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_add_task.*


class AddTask : Fragment() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var navController: NavController
    private lateinit var db: DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_add_task, container, false)
        val activity = activity as MainActivity
       val bottomBar = activity.findViewById<BottomAppBar>(R.id.bottom)
       bottomBar.visibility = View.GONE
        val fab = activity.findViewById<FloatingActionButton>(R.id.add_task)
       fab.visibility = View.INVISIBLE
       fab.isEnabled = false
        db= Firebase.database.getReference("Users")
        userViewModel = ViewModelProvider(this, UserViewModelFactory(Validation().authInstance(), requireActivity(), requireContext(), db)).get(
            UserViewModel::class.java)
        navController = findNavController()
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        save_task.setOnClickListener {
            val tsk = tasks.text.toString().trim()
            if (tsk.isEmpty()){
                Toast.makeText(requireContext(), "Add task", Toast.LENGTH_LONG).show()
            }else{
                userViewModel.addTask(tsk, navController)
            }
        }
    }
    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        return if (enter) {
            AnimationUtils.loadAnimation(context, R.anim.slide_in_right)
        } else {
            AnimationUtils.loadAnimation(context, R.anim.slide_out_left)
        }
    }
}