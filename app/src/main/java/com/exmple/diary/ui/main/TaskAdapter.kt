package com.exmple.diary.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.exmple.diary.Common
import com.exmple.diary.R
import com.exmple.diary.model.Task
import com.exmple.diary.ui.main.fragment.TaskList
import kotlinx.android.synthetic.main.task_item.view.*

class TaskAdapter(val context: Context, val tasks : ArrayList<Task>, val fra : TaskList, private val uid : String) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>(){
    inner class TaskViewHolder(item : View) : RecyclerView.ViewHolder(item){
        val tsk = item.task!!
        internal val del = item.delete!!
        init {

            del.setOnClickListener {
                val i = tasks[adapterPosition]
                tasks.remove(i)
                notifyDataSetChanged()
                fra.deleteTask(i.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.tsk.text = tasks[position].task
       if (Common.uid != null){
           holder.del.visibility = View.VISIBLE
       }else{
           holder.del.visibility = View.GONE
       }

    }

    override fun getItemCount(): Int {
        return tasks.size
    }
}