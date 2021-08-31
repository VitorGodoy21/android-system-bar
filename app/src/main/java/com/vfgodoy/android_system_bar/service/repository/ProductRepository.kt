package com.vfgodoy.android_system_bar.service.repository

import android.content.Context
import android.net.Uri
import com.google.firebase.firestore.*
import com.vfgodoy.android_system_bar.R
import com.vfgodoy.android_system_bar.service.constants.Product
import com.vfgodoy.android_system_bar.service.listener.FirebaseListener
import com.vfgodoy.android_system_bar.service.listener.ValidationListener
import com.vfgodoy.android_system_bar.service.model.ProductModel
import com.vfgodoy.android_system_bar.service.model.ProductModelRequest
import com.vfgodoy.android_system_bar.service.repository.remote.FirebaseStorage
import com.vfgodoy.android_system_bar.service.repository.remote.FirestoreDatabaseClient
import com.vfgodoy.android_system_bar.util.Util

class ProductRepository(val context: Context) : BaseRepository() {

    private val mCollectionReference = FirestoreDatabaseClient.createFirebaseReference(Product.TABLE.NAME)
    private val mStorageReference = FirebaseStorage.storageReference()

    fun getAllProducts(listener : FirebaseListener<List<ProductModel>>){

        if(!Util.isConnectionAvailable(context)){
            listener.onFailure(context.getString(R.string.error_no_internet_connection))
            return
        }

        mCollectionReference!!.addSnapshotListener{ documents, error ->
            if(error != null){
               listener.onFailure(error.message.toString())
            }else if(documents != null){
                listener.onSuccess(documents.toObjects(ProductModel::class.java))
            }else{
                listener.onFailure("Empty Table")
            }
        }
    }

    fun save(product : ProductModel, listener: FirebaseListener<Boolean>){

        if(product.imageUri != null){
            var url = ""
            val reference = mStorageReference?.child("${Product.FOLDER.NAME}${product.name}")
            var uploadTask = reference?.putFile(product.imageUri!!)
            uploadTask?.continueWithTask {
                if (!it.isSuccessful) {
                    it.exception?.let {
                        throw it!!
                    }
                }
                reference?.downloadUrl
            }?.addOnFailureListener {
                listener.onFailure(it.message.toString())
            }?.addOnSuccessListener {
                url = it.toString()
                val product = ProductModelRequest(product.name, product.price, url)
                saveProductOnFirestore(product, listener)
            }
        }else{
            val product = ProductModelRequest(product.name, product.price, "")
            saveProductOnFirestore(product, listener)
        }
    }

    private fun saveProductOnFirestore(product : ProductModelRequest, listener : FirebaseListener<Boolean>){
        mCollectionReference?.document()?.set(product)?.addOnSuccessListener {
            listener.onSuccess(true)
        }?.addOnFailureListener{
            listener.onFailure(it.message.toString())
        }
    }

}