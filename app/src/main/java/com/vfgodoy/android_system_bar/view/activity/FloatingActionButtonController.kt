package com.vfgodoy.android_system_bar.view.activity

import android.view.View

interface FloatingActionButtonController {

    fun setFabImageResource(resourceId : Int)
    fun setFabAction(listener: (View) -> Unit)
    fun setFabVisibility(visibility: Int)

}