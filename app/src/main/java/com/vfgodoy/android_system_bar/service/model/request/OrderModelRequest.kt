package com.vfgodoy.android_system_bar.service.model.request

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import java.util.*

class OrderModelRequest (
    @DocumentId
    val id: String = "",
    val description : String? = null,
    val date : Timestamp? = null,
    val number : Int? = null,
    val total : Float? = null,
    var products : List<OrderProductModelRequest>? = null
){
    companion object{
        fun createStarterOrder(number : Int?, description : String) : OrderModelRequest {
            val products = listOf<OrderProductModelRequest>()
            return OrderModelRequest("", description, Timestamp(Date()), number, 0f, products)
        }
    }
}