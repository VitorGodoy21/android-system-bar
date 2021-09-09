package com.vfgodoy.android_system_bar.service.model

import android.net.Uri
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import java.util.*

class OrderModel (
    @DocumentId
    val id: String = "",
    val description : String? = null,
    val date : Timestamp? = null,
    val number : Int? = null,
    val total : Float? = null,
    val products : List<OrderProductModel>? = null
){
    companion object{
        fun createStarterOrder(number : Int?, description : String) : OrderModel{
            val products = listOf<OrderProductModel>()
            return OrderModel("", description, Timestamp(Date()), number, 0f, products)
        }
    }
}