package com.vfgodoy.android_system_bar.service.model.request

import com.google.firebase.firestore.DocumentId

class ProductModelRequest (
    val name : String? = null,
    val price : Float? = null,
    val imageUrl : String? = null
)