package com.vfgodoy.android_system_bar.service.repository.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FirebaseAuthentication {

    companion object{

        fun currentUser() : FirebaseAuth?{
            return Firebase.auth
        }

    }


}