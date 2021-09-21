package com.vfgodoy.android_system_bar.service.model

import com.google.firebase.firestore.DocumentId

class OrderProductModel (
    val id: String = "",
    val product : ProductModel? = null,
    var amount : Int = 0
)