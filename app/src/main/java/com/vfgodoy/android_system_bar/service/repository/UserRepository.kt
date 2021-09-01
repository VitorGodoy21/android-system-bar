package com.vfgodoy.android_system_bar.service.repository

import android.app.Activity
import android.app.Application
import android.content.Context
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseUser
import com.vfgodoy.android_system_bar.R
import com.vfgodoy.android_system_bar.service.listener.AuthListener
import com.vfgodoy.android_system_bar.service.listener.FirebaseListener
import com.vfgodoy.android_system_bar.service.listener.ValidationListener
import com.vfgodoy.android_system_bar.service.repository.remote.FirebaseAuthentication
import com.vfgodoy.android_system_bar.util.Util

class UserRepository(val context: Context) : BaseRepository() {

    private val mAuth = FirebaseAuthentication.currentUser()

    fun verifyUserLogged(listener : AuthListener){
        if(mAuth?.currentUser != null){
            listener.onComplete(true)
            return
        }

        listener.onComplete(false)

    }

    fun doLogin(email : String, password : String, listener : FirebaseListener<FirebaseUser?>, activity: Activity){

        if(email.isEmpty() || password.isEmpty()){
            listener.onFailure(context.getString(R.string.error_missing_login_values))
            return
        }

        if(!Util.isConnectionAvailable(context)){
            listener.onFailure(context.getString(R.string.error_no_internet_connection))
            return
        }

        mAuth?.signInWithEmailAndPassword(email, password)
        ?.addOnCompleteListener(activity) { task ->
            if (task.isSuccessful) {
                listener.onSuccess(mAuth.currentUser)
            } else {
                listener.onFailure(task.exception?.message.toString())
            }
        }


    }



}