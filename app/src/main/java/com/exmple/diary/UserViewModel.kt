package com.exmple.diary

import android.app.Activity
import android.content.Context
import android.widget.ProgressBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.exmple.diary.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

class UserViewModel( mAuth : FirebaseAuth, activity: Activity, context: Context,  db: DatabaseReference): ViewModel() {
    private val repo = Repository(mAuth, activity, context, db)
    fun loginUser(email: String, pass:String, pro:ProgressBar){
        repo.loginUser(email, pass, pro)
    }
    fun signInUser(email: String, pass: String, name:String, age:String, dob:String, pro: ProgressBar){
        repo.signInUser(email, pass, name, age, dob, pro)
    }
    fun getUser(): LiveData<ArrayList<User>> {
        return repo.getUsers()
    }
    fun addTask(task : String, navController: NavController){
        return repo.addTask(task, navController)
    }
    fun deleteTask(id :String){
        repo.deleteTask(id)
    }
}