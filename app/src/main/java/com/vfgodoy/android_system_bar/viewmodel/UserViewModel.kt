package com.vfgodoy.android_system_bar.viewmodel

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.vfgodoy.android_system_bar.service.listener.AuthListener
import com.vfgodoy.android_system_bar.service.listener.FirebaseListener
import com.vfgodoy.android_system_bar.service.listener.ValidationListener
import com.vfgodoy.android_system_bar.service.repository.UserRepository

class UserViewModel (application: Application) : AndroidViewModel(application)  {

    private val mUserRepository = UserRepository(application)

    private val mUserLogged = MutableLiveData<Boolean>()
    var userLogged : LiveData<Boolean> = mUserLogged

    private val mLogin = MutableLiveData<ValidationListener>()
    var login : LiveData<ValidationListener> = mLogin

    private val mLogout = MutableLiveData<Boolean>()
    var logout: LiveData<Boolean> = mLogout

    fun verifyUserLogged(){
        val listener = object : AuthListener{
            override fun onComplete(result: Boolean) {
                if(result){
                    mUserLogged.value = result
                }
            }

        }

        mUserRepository.verifyUserLogged(listener)
    }

    fun doLogin(email : String, password : String, activity: Activity){
        val listener = object : FirebaseListener<FirebaseUser?>{
            override fun onSuccess(model: FirebaseUser?) {
                mLogin.value = ValidationListener()
            }

            override fun onFailure(str: String) {
                mLogin.value = ValidationListener(str)
            }
        }

        mUserRepository.doLogin(email, password, listener, activity)

    }

    fun doLogout(){
        Firebase.auth.signOut()
        mLogout.value = true
    }


}