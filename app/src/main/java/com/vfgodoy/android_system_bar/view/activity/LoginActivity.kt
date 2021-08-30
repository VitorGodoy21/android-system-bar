package com.vfgodoy.android_system_bar.view.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.vfgodoy.android_system_bar.databinding.ActivityLoginBinding
import com.vfgodoy.android_system_bar.util.Util
import com.vfgodoy.android_system_bar.viewmodel.UserViewModel

class LoginActivity : AppCompatActivity(), View.OnClickListener{

    private lateinit var binding: ActivityLoginBinding
    private lateinit var mUserViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        observer()
        setListeners()

        mUserViewModel.verifyUserLogged()

    }

    private fun login(activity : Activity){
        val email : String = binding.etUser.text.toString()
        val password : String = binding.etPassword.text.toString()

        mUserViewModel.doLogin(email, password, activity)

    }

    private fun observer(){
        mUserViewModel.userLogged.observe(this, Observer {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        })

        mUserViewModel.login.observe(this, Observer {
            if(it.success()){
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }else{
                Util.makeToast(this, it.failure())
            }
        })

    }

    private fun setListeners(){
        binding.btLogin.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            binding.btLogin.id -> login(this)
        }
    }

}