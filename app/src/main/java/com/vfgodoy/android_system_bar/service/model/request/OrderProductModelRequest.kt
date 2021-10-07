package com.vfgodoy.android_system_bar.service.model.request

import com.google.firebase.firestore.DocumentId

class OrderProductModelRequest (
    @DocumentId
    val id: String = "",
    val productId : String = "",
    val amount : Int? = null
)