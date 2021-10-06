package com.vfgodoy.android_system_bar.service.repository

import android.content.Context
import com.vfgodoy.android_system_bar.R
import com.vfgodoy.android_system_bar.extension.toRequest
import com.vfgodoy.android_system_bar.service.constants.OrderConstants
import com.vfgodoy.android_system_bar.service.listener.FirebaseListener
import com.vfgodoy.android_system_bar.service.model.OrderModel
import com.vfgodoy.android_system_bar.service.model.OrderProductModel
import com.vfgodoy.android_system_bar.service.model.request.OrderModelRequest
import com.vfgodoy.android_system_bar.service.model.request.OrderProductModelRequest
import com.vfgodoy.android_system_bar.service.repository.remote.FirestoreDatabaseClient
import com.vfgodoy.android_system_bar.util.Util

class OrderRepository(val context: Context) : BaseRepository(){

    private val mCollectionReference = FirestoreDatabaseClient.createFirebaseReference(OrderConstants.TABLE.NAME)

    fun createStarter(orderStarter : OrderModelRequest, listener : FirebaseListener<Boolean>){
        mCollectionReference?.document()?.set(orderStarter)?.addOnSuccessListener {
            listener.onSuccess(true)
        }?.addOnFailureListener{
            listener.onFailure(it.message.toString())
        }
    }

    fun getAllOrders(listener : FirebaseListener<List<OrderModelRequest>>){

        if(!Util.isConnectionAvailable(context)){
            listener.onFailure(context.getString(R.string.error_no_internet_connection))
            return
        }

        mCollectionReference!!.orderBy(OrderConstants.TABLE.NUMBER).addSnapshotListener{ documents, error ->
            if(error != null){
                listener.onFailure(error.message.toString())
            }else if(documents != null){
                listener.onSuccess(documents.toObjects(OrderModelRequest::class.java))
            }else{
                listener.onFailure(OrderConstants.ERROR.EMPTY_TABLE)
            }

        }

    }

    fun get(id:String, listener: FirebaseListener<OrderModelRequest?>){
        mCollectionReference?.document(id)?.get()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                val order =document?.toObject(OrderModelRequest::class.java)
                listener.onSuccess(order)
            } else {
                listener.onFailure(task.exception?.message.toString())
            }
        }
    }

    fun onChangeProduct(order : OrderModel, listener : FirebaseListener<OrderModel>){
        val orderRequest : OrderModelRequest = order.toRequest()
        mCollectionReference?.document(orderRequest.id)?.set(orderRequest)?.addOnSuccessListener {
            listener.onSuccess(order)
        }?.addOnFailureListener{
            listener.onFailure(it.message.toString())
        }
    }


}