package com.exmple.diary.model

 class User{
     var name: String? = null
     var age: String? = null
     var dob: String? = null
     var uid : String? = null
     var tasks : Map<String, String>? = null

     constructor()
     constructor(name: String?, age: String?, dob: String?, uid: String?, tasks : Map<String, String>?) {
         this.name = name
         this.age = age
         this.dob = dob
         this.uid = uid
         this.tasks= tasks
     }
 }