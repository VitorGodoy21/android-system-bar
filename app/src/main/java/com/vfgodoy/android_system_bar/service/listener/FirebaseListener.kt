package com.vfgodoy.android_system_bar.service.listener

interface FirebaseListener<T> {

    fun onSuccess(model : T)

    fun onFailure(str: String)

}