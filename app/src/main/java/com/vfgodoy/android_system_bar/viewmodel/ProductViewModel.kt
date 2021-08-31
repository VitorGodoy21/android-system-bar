package com.vfgodoy.android_system_bar.viewmodel

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.vfgodoy.android_system_bar.R
import com.vfgodoy.android_system_bar.service.listener.FirebaseListener
import com.vfgodoy.android_system_bar.service.listener.ValidationListener
import com.vfgodoy.android_system_bar.service.model.ProductModel
import com.vfgodoy.android_system_bar.service.repository.ProductRepository

class ProductViewModel(application: Application) : AndroidViewModel(application) {

    private val mProductRepository = ProductRepository(application)

    private val mValidation = MutableLiveData<ValidationListener>()
    var validation: LiveData<ValidationListener> = mValidation

    private val mList = MutableLiveData<List<ProductModel>>()
    var products : LiveData<List<ProductModel>> = mList

    private val mSaveResult = MutableLiveData<ValidationListener>()
    var saveResult : LiveData<ValidationListener> = mSaveResult

    fun all(){
        val listener = object : FirebaseListener<List<ProductModel>>{
            override fun onSuccess(model: List<ProductModel>) {
                mList.value = model
            }

            override fun onFailure(str: String) {
                mList.value = listOf()
                mValidation.value = ValidationListener(str)
            }

        }

        mProductRepository.getAllProducts(listener)
    }

    fun save(name : String, price : String, imageUri : Uri?){

        if(name.isEmpty() || (price.isEmpty() || price.toFloatOrNull() == null )){
            mSaveResult.value = ValidationListener((getApplication() as Context).getString(R.string.error_missing_save_product_values))
            return
        }

        val product = ProductModel(null,name, price.toFloat(), imageUri)
        mProductRepository.save(product, object : FirebaseListener<Boolean>{
            override fun onSuccess(model: Boolean) {
                mSaveResult.value = ValidationListener()
            }

            override fun onFailure(str: String) {
                mSaveResult.value = ValidationListener(str)
            }

        })

    }


}