package com.vfgodoy.android_system_bar.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import com.vfgodoy.android_system_bar.R

class CustomDialogClass(context: Context) : Dialog(context) {

    init {
        setCancelable(false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_order_form)

    }
}