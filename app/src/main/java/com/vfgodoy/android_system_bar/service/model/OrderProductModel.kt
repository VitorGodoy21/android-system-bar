package com.vfgodoy.android_system_bar.service.model

import com.google.firebase.firestore.DocumentId

class OrderProductModel (
    @DocumentId
    val id: String = "",
    val productId : String = "",
    val amount : Int? = null
)