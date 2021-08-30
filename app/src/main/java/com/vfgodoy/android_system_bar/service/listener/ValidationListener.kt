package com.vfgodoy.android_system_bar.service.listener

class ValidationListener(str: String = "") {

    private var mStatus : Boolean = true
    private var mMessage : String = ""

    init {
        if(str != ""){
            mStatus = false
            mMessage = str
        }
    }

    fun success() = mStatus
    fun failure() = mMessage


}