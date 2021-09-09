package com.vfgodoy.android_system_bar.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.ktx.Firebase
import com.vfgodoy.android_system_bar.R
import com.vfgodoy.android_system_bar.service.listener.FirebaseListener
import com.vfgodoy.android_system_bar.service.listener.ValidationListener
import com.vfgodoy.android_system_bar.service.model.OrderModel
import com.vfgodoy.android_system_bar.service.model.ProductModel
import com.vfgodoy.android_system_bar.service.model.ProductModelRequest
import com.vfgodoy.android_system_bar.service.repository.OrderRepository

class OrderViewModel(application: Application) : AndroidViewModel(application) {

    private val mOrderRepository = OrderRepository(application)

    private val mSaveStarterResult = MutableLiveData<ValidationListener>()
    var saveStarterResult : LiveData<ValidationListener> = mSaveStarterResult

    private val mValidation = MutableLiveData<ValidationListener>()
    var validation: LiveData<ValidationListener> = mValidation

    private val mList = MutableLiveData<List<OrderModel>>()
    var orders : LiveData<List<OrderModel>> = mList

    private val mLoadOrder = MutableLiveData<OrderModel?>()
    var loadOrder : LiveData<OrderModel?> = mLoadOrder

    fun saveStarter(order : OrderModel){

        if(order.number == null || order.description.isNullOrEmpty()){
            mSaveStarterResult.value = ValidationListener((getApplication() as Context).getString(R.string.error_missing_save_order_starter_values))
            return
        }

        mOrderRepository.createStarter(order, object : FirebaseListener<Boolean>{
            override fun onSuccess(model: Boolean) {
                mSaveStarterResult.value = ValidationListener()
            }

            override fun onFailure(str: String) {
                mSaveStarterResult.value = ValidationListener(str)
            }
        })

    }

    fun all(){
        val listener = object : FirebaseListener<List<OrderModel>>{
            override fun onSuccess(model: List<OrderModel>) {
                mList.value = model
            }

            override fun onFailure(str: String) {
                mList.value = listOf()
                mValidation.value = ValidationListener(str)
            }
        }

        mOrderRepository.getAllOrders(listener)

    }

    fun load(id : String){
        mOrderRepository.get(id, object : FirebaseListener<OrderModel?>{
            override fun onSuccess(model: OrderModel?) {
                mLoadOrder.value = model
            }

            override fun onFailure(str: String) {
            }

        })

    }

}