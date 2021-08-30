package com.vfgodoy.android_system_bar.service.repository

import android.content.Context
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.vfgodoy.android_system_bar.R
import com.vfgodoy.android_system_bar.service.constants.Product
import com.vfgodoy.android_system_bar.service.listener.FirebaseListener
import com.vfgodoy.android_system_bar.service.listener.ValidationListener
import com.vfgodoy.android_system_bar.service.model.ProductModel
import com.vfgodoy.android_system_bar.service.repository.remote.FirestoreDatabaseClient
import com.vfgodoy.android_system_bar.util.Util

class ProductRepository(val context: Context) : BaseRepository() {

    private val mReference = FirestoreDatabaseClient.createFirebaseReference(Product.TABLE.NAME)

    fun getAllProducts(listener : FirebaseListener<List<ProductModel>>){

        if(!Util.isConnectionAvailable(context)){
            listener.onFailure(context.getString(R.string.error_no_internet_connection))
            return
        }

        mReference!!.addSnapshotListener{ documents, error ->
            if(error != null){
               listener.onFailure(error.message.toString())
            }else if(documents != null){
                listener.onSuccess(documents.toObjects(ProductModel::class.java))
            }else{
                listener.onFailure("Empty Table")
            }
        }
    }

}