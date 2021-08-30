package com.vfgodoy.android_system_bar.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vfgodoy.android_system_bar.service.listener.FirebaseListener
import com.vfgodoy.android_system_bar.service.listener.ValidationListener
import com.vfgodoy.android_system_bar.service.model.ProductModel
import com.vfgodoy.android_system_bar.service.repository.ProductRepository

class ProductViewModel(application: Application) : AndroidViewModel(application) {

    private val mProductRepository = ProductRepository(application)

    private val mValidation = MutableLiveData<ValidationListener>()
    var validation: LiveData<ValidationListener> = mValidation

    private val mList = MutableLiveData<List<ProductModel>>()
    var list : LiveData<List<ProductModel>> = mList

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


}