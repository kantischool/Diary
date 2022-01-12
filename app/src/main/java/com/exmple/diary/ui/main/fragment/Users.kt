package com.exmple.diary.ui.main.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.exmple.diary.*
import com.exmple.diary.model.User
import com.exmple.diary.ui.main.MainActivity
import com.exmple.diary.ui.main.UserAdapter
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_users.*
import kotlinx.android.synthetic.main.user_item.*


class Users : Fragment() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var db: DatabaseReference
    private lateinit var adapter: UserAdapter
    private lateinit var navController: NavController
    private val list : ArrayList<User> = ArrayList()
    private lateinit var uid : String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_users, container, false)
        db= Firebase.database.getReference("Users")
        navController = findNavController()
        adapter = UserAdapter(requireContext(), list, navController)
         uid = Validation().authInstance().currentUser!!.uid
        userViewModel = ViewModelProvider(this, UserViewModelFactory(Validation().authInstance(), requireActivity(), requireContext(), db)).get(UserViewModel::class.java)

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        user_recycler.adapter = adapter
        user_pro.visibility = View.VISIBLE
        userViewModel.getUser().observe(viewLifecycleOwner, {
            list.clear()
            for (i in it){
                if (i.uid == uid){
                    Common.uid = i.uid
                    Common.tasks = i.tasks
                    s_name.text = i.name
                    s_age.text = "${i.age} years"
                    s_dob.text = i.dob
                }else{
                    list.add(i)
                }
            }
            user_pro.visibility = View.GONE
            adapter.notifyDataSetChanged()
        })
        self_user.setOnClickListener {
            navController.navigate(UsersDirections.actionUserToTaskList())
        }
        mode.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val activity = activity as MainActivity
        val fab = activity.findViewById<FloatingActionButton>(R.id.add_task)
        val toolbar = activity.findViewById<Toolbar>(R.id.main_toolbar)
        toolbar.visibility = View.VISIBLE
        fab.visibility = View.VISIBLE
        fab.isEnabled = true
        val bottomBar = activity.findViewById<BottomAppBar>(R.id.bottom)
        bottomBar.visibility = View.VISIBLE
    }
}