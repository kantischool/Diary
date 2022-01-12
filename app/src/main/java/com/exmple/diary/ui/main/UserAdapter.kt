package com.exmple.diary.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.exmple.diary.Common
import com.exmple.diary.R
import com.exmple.diary.model.User
import com.exmple.diary.ui.main.fragment.UsersDirections
import kotlinx.android.synthetic.main.user_item.view.*

class UserAdapter(val context: Context, private val list : ArrayList<User>, val navController: NavController) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    inner class UserViewHolder(item : View) : RecyclerView.ViewHolder(item){
        val name = item.name!!
        val age = item._age!!
        private val card = item.card!!
        init {
            card.setOnClickListener {
                val i = list[adapterPosition]
                Common.tasks = i.tasks
                Common.uid = null
                navController.navigate(UsersDirections.actionUserToTaskList())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.user_item, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
       val user = list[position]
        holder.name.text = user.name
        holder.age.text = "${user.age} years"
    }

    override fun getItemCount(): Int {
       return list.size
    }
}