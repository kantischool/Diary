package com.exmple.diary

import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

open class Validation {
    private var mAuth : FirebaseAuth? = null

     fun validateData(data: Array<String>, dataField: Array<TextInputEditText>) {
        for(i in data.indices){
            if (data[i].isEmpty()){
                dataField[i].requestFocus()
                dataField[i].error = "This Field Required"
                return
            }
        }
    }
    fun authInstance(): FirebaseAuth {
        if (mAuth == null){
            mAuth = FirebaseAuth.getInstance()
        }
        return mAuth!!
    }

}