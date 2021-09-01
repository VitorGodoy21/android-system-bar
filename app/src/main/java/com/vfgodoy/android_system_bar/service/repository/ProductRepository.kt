package com.vfgodoy.android_system_bar.service.repository

import android.content.Context
import com.vfgodoy.android_system_bar.R
import com.vfgodoy.android_system_bar.service.constants.ProductConstants
import com.vfgodoy.android_system_bar.service.listener.FirebaseListener
import com.vfgodoy.android_system_bar.service.model.ProductModel
import com.vfgodoy.android_system_bar.service.model.ProductModelRequest
import com.vfgodoy.android_system_bar.service.repository.remote.FirebaseStorage
import com.vfgodoy.android_system_bar.service.repository.remote.FirestoreDatabaseClient
import com.vfgodoy.android_system_bar.util.Util

class ProductRepository(val context: Context) : BaseRepository() {

    private val mCollectionReference = FirestoreDatabaseClient.createFirebaseReference(ProductConstants.TABLE.NAME)
    private val mStorageReference = FirebaseStorage.storageReference()

    //TODO: Create and Update Product -> evaluate refactoring code repeated - Analyze lambda usage

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

    fun update(product : ProductModel, listener: FirebaseListener<Boolean>){

        if(product.imageUri != null){
            var url = ""
            val reference = mStorageReference?.child("${ProductConstants.FOLDER.PRODUCT_PATH}${product.name}")
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
                val request = ProductModelRequest(product.name, product.price, url)
                updateProductOnFirestore(product.id, request, listener)
            }
        }else{
            val request = ProductModelRequest(product.name, product.price, "")
            updateProductOnFirestore(product.id, request, listener)
        }




    }

    fun create(product : ProductModel, listener: FirebaseListener<Boolean>){

        if(product.imageUri != null){
            var url = ""
            val reference = mStorageReference?.child("${ProductConstants.FOLDER.PRODUCT_PATH}${product.name}")
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
                createProductOnFirestore(product, listener)
            }
        }else{
            val product = ProductModelRequest(product.name, product.price, "")
            createProductOnFirestore(product, listener)
        }
    }

    private fun createProductOnFirestore(product : ProductModelRequest, listener : FirebaseListener<Boolean>){
        mCollectionReference?.document()?.set(product)?.addOnSuccessListener {
            listener.onSuccess(true)
        }?.addOnFailureListener{
            listener.onFailure(it.message.toString())
        }
    }

    private fun updateProductOnFirestore(id : String, product : ProductModelRequest, listener : FirebaseListener<Boolean>){
        mCollectionReference?.document(id)?.set(product)?.addOnSuccessListener {
            listener.onSuccess(true)
        }?.addOnFailureListener{
            listener.onFailure(it.message.toString())
        }
    }

    fun get(id:String, listener: FirebaseListener<ProductModelRequest?>){
        mCollectionReference?.document(id)?.get()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                val product =document?.toObject(ProductModelRequest::class.java)
                listener.onSuccess(product)
            } else {
                listener.onFailure(task.exception?.message.toString())
            }
        }
    }



}