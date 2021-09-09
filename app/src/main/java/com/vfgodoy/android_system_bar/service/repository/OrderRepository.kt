package com.vfgodoy.android_system_bar.service.repository

import android.content.Context
import com.vfgodoy.android_system_bar.R
import com.vfgodoy.android_system_bar.service.constants.OrderConstants
import com.vfgodoy.android_system_bar.service.constants.ProductConstants
import com.vfgodoy.android_system_bar.service.listener.FirebaseListener
import com.vfgodoy.android_system_bar.service.model.OrderModel
import com.vfgodoy.android_system_bar.service.model.ProductModelRequest
import com.vfgodoy.android_system_bar.service.repository.remote.FirestoreDatabaseClient
import com.vfgodoy.android_system_bar.util.Util

class OrderRepository(val context: Context) : BaseRepository(){

    private val mCollectionReference = FirestoreDatabaseClient.createFirebaseReference(OrderConstants.TABLE.NAME)

    fun createStarter(orderStarter : OrderModel, listener : FirebaseListener<Boolean>){
        mCollectionReference?.document()?.set(orderStarter)?.addOnSuccessListener {
            listener.onSuccess(true)
        }?.addOnFailureListener{
            listener.onFailure(it.message.toString())
        }
    }

    fun getAllOrders(listener : FirebaseListener<List<OrderModel>>){

        if(!Util.isConnectionAvailable(context)){
            listener.onFailure(context.getString(R.string.error_no_internet_connection))
            return
        }

        mCollectionReference!!.addSnapshotListener{ documents, error ->
            if(error != null){
                listener.onFailure(error.message.toString())
            }else if(documents != null){
                listener.onSuccess(documents.toObjects(OrderModel::class.java))
            }else{
                listener.onFailure("Empty Table")
            }

        }

    }

    fun get(id:String, listener: FirebaseListener<OrderModel?>){
        mCollectionReference?.document(id)?.get()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                val order =document?.toObject(OrderModel::class.java)
                listener.onSuccess(order)
            } else {
                listener.onFailure(task.exception?.message.toString())
            }
        }
    }


}