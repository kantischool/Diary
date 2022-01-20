package com.exmple.diary;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class JValidation {

    private FirebaseAuth mAuth;

   public void validation(String[] data, TextInputEditText[] dataField){
        for(int i=0; i<data.length; i++){
            if(data[i].isEmpty()){
                dataField[i].requestFocus();
                dataField[i].setError("This field is required");
                return;
            }
        }
    }
    public FirebaseAuth authInstance(){
        if(mAuth == null){
            mAuth = FirebaseAuth.getInstance();
        }
        return mAuth;
    }
}
