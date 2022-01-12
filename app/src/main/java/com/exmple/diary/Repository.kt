package com.exmple.diary

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.exmple.diary.model.User
import com.exmple.diary.ui.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class Repository(private val mAuth : FirebaseAuth, private val activity: Activity, val context: Context, private val db: DatabaseReference) {
    fun loginUser(email: String, pass: String, pro: ProgressBar){
        mAuth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    pro.visibility = View.GONE
                    activity.startActivity(Intent(activity, MainActivity::class.java))
                    activity.finish()
                }else{
                    pro.visibility = View.GONE
                    Toast.makeText(context, it.exception?.message, Toast.LENGTH_LONG).show()
                }
            }
    }
    fun signInUser(email: String, pass: String, name:String, age:String, dob:String, pro: ProgressBar){
        mAuth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    db.child(mAuth.currentUser!!.uid).setValue(User(name, age, dob,mAuth.currentUser!!.uid, null))
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful){
                                pro.visibility = View.GONE
                                activity.startActivity(Intent(activity, MainActivity::class.java))
                                activity.finish()
                            }else{
                                pro.visibility = View.GONE
                                Toast.makeText(context, task.exception?.message, Toast.LENGTH_LONG).show()
                            }
                        }
                }else{
                    pro.visibility = View.GONE
                    Toast.makeText(context, it.exception?.message, Toast.LENGTH_LONG).show()
                }
            }
    }
    val user : MutableLiveData<ArrayList<User>> = MutableLiveData()
    fun getUsers(): LiveData<ArrayList<User>> {
        db.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val users : ArrayList<User> = ArrayList()
                if (snapshot.exists()){
                    for (i in snapshot.children){
                            i.getValue(User::class.java)?.let { users.add(it) }
                    }
                    user.value = users
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, error.message, Toast.LENGTH_LONG).show()
            }
        })
        return user
    }
    fun addTask(task : String, navController: NavController){
        db.child(mAuth.currentUser!!.uid).child("tasks").push().setValue(task)
            .addOnCompleteListener {
            if (it.isSuccessful){
                navController.popBackStack()
            }else{
                Toast.makeText(context, it.exception?.message, Toast.LENGTH_LONG).show()
            }
            }
    }
    fun deleteTask(id : String){
        db.child(mAuth.currentUser!!.uid).child("tasks").child(id).removeValue()
            .addOnCompleteListener {
                if (it.isSuccessful){
                    Toast.makeText(context, "Task Deleted Successfully", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(context, it.exception?.message, Toast.LENGTH_LONG).show()
                }
            }
    }

}