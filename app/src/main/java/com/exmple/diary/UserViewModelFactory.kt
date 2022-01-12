package com.exmple.diary

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

class UserViewModelFactory(private val mAuth: FirebaseAuth, private val activity: Activity, val context: Context, private val db: DatabaseReference): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserViewModel(mAuth, activity, context, db) as T
    }
}