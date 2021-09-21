package com.vfgodoy.android_system_bar.service.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.vfgodoy.android_system_bar.service.model.request.OrderProductModelRequest

class OrderModel (
    @DocumentId
    val id: String = "",
    val description : String? = null,
    val date : Timestamp? = null,
    val number : Int? = null,
    val total : Float? = null,
    var products : List<OrderProductModel>? = null
)