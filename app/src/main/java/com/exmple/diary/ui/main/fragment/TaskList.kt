package com.exmple.diary.ui.main.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.exmple.diary.*
import com.exmple.diary.model.Task
import com.exmple.diary.ui.GridSpacingItemDecoration
import com.exmple.diary.ui.main.MainActivity
import com.exmple.diary.ui.main.TaskAdapter
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_task_list.*


class TaskList : Fragment() {

    private lateinit var adapter : TaskAdapter
    private val list : ArrayList<Task> = ArrayList()
    private lateinit var userViewModel: UserViewModel
    private lateinit var db: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_task_list, container, false)
        val activity = activity as MainActivity
        val bottomBar = activity.findViewById<BottomAppBar>(R.id.bottom)
        val toolbar = activity.findViewById<Toolbar>(R.id.main_toolbar)
        toolbar.visibility = View.GONE
        bottomBar.visibility = View.GONE
        val fab = activity.findViewById<FloatingActionButton>(R.id.add_task)
        fab.visibility = View.INVISIBLE
        fab.isEnabled = false
        db= Firebase.database.getReference("Users")
        userViewModel = ViewModelProvider(this, UserViewModelFactory(Validation().authInstance(), requireActivity(), requireContext(), db)).get(UserViewModel::class.java)
        adapter = TaskAdapter(requireContext(), list, this, Validation().authInstance().currentUser!!.uid)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        task_recycler.adapter = adapter
        task_recycler.addItemDecoration(GridSpacingItemDecoration(2, 15, true))
        val tasks = Common.tasks
        if (tasks == null){
            msg.visibility = View.VISIBLE
        }else{
            list.clear()
//            val sortedList = tasks.values.sorted()
           for (i in tasks.keys){
               list.add(Task(i, tasks.getValue(i)))
           }
        }
    }
    fun deleteTask(id : String){
        userViewModel.deleteTask(id)
    }
}