package com.vfgodoy.android_system_bar.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.vfgodoy.android_system_bar.R
import com.vfgodoy.android_system_bar.extension.toRequest
import com.vfgodoy.android_system_bar.service.listener.FirebaseListener
import com.vfgodoy.android_system_bar.service.listener.ValidationListener
import com.vfgodoy.android_system_bar.service.model.OrderModel
import com.vfgodoy.android_system_bar.service.model.OrderProductModel
import com.vfgodoy.android_system_bar.service.model.ProductModel
import com.vfgodoy.android_system_bar.service.model.request.OrderModelRequest
import com.vfgodoy.android_system_bar.service.model.request.OrderProductModelRequest
import com.vfgodoy.android_system_bar.service.model.request.ProductModelRequest
import com.vfgodoy.android_system_bar.service.repository.OrderRepository
import com.vfgodoy.android_system_bar.service.repository.ProductRepository

class OrderViewModel(application: Application) : AndroidViewModel(application) {

    private val mOrderRepository = OrderRepository(application)
    private val mProductRepository = ProductRepository(application)

    private val mSaveStarterResult = MutableLiveData<ValidationListener>()
    var saveStarterResult: LiveData<ValidationListener> = mSaveStarterResult

    private val mValidation = MutableLiveData<ValidationListener>()
    var validation: LiveData<ValidationListener> = mValidation

    private val mList = MutableLiveData<List<OrderModelRequest>>()
    var orders: LiveData<List<OrderModelRequest>> = mList

    private val mLoadOrder = MutableLiveData<OrderModel?>()
    var loadOrder: LiveData<OrderModel?> = mLoadOrder

    var finish = MutableLiveData<List<OrderProductModel>>()
    var finishObserver : Observer<List<OrderProductModel>>? = null

    fun saveStarter(order: OrderModelRequest) {

        if (order.number == null || order.description.isNullOrEmpty()) {
            mSaveStarterResult.value =
                ValidationListener((getApplication() as Context).getString(R.string.error_missing_save_order_starter_values))
            return
        }

        mOrderRepository.createStarter(order, object : FirebaseListener<Boolean> {
            override fun onSuccess(model: Boolean) {
                mSaveStarterResult.value = ValidationListener()
            }

            override fun onFailure(str: String) {
                mSaveStarterResult.value = ValidationListener(str)
            }
        })

    }

    fun all() {
        val listener = object : FirebaseListener<List<OrderModelRequest>> {
            override fun onSuccess(model: List<OrderModelRequest>) {
                mList.value = model
            }

            override fun onFailure(str: String) {
                mList.value = listOf()
                mValidation.value = ValidationListener(str)
            }
        }

        mOrderRepository.getAllOrders(listener)

    }

    fun load(id: String) {
        mOrderRepository.get(id, object : FirebaseListener<OrderModelRequest?> {

            override fun onSuccess(model: OrderModelRequest?) {
                val productsModel: MutableList<OrderProductModel> = arrayListOf()
                model?.let { orderModelRequest ->
                    orderModelRequest.products?.let { list ->
                        val productsSize = list.size - 1
                        for (i in 0..productsSize) {
                            val productRequest = list[i]
                            mProductRepository.get(
                                productRequest.productId,
                                object : FirebaseListener<ProductModelRequest?> {
                                    override fun onSuccess(model: ProductModelRequest?) {

                                        model?.let {
                                            val productModel = ProductModel(
                                                productRequest.productId,
                                                it.name ?: "",
                                                it.price,
                                                null,
                                                it.imageUrl ?: ""
                                            )
                                            val productOrderModel = OrderProductModel(
                                                id,
                                                productModel,
                                                productRequest.amount ?: 0
                                            )
                                            productsModel.add(productOrderModel)

                                            if (i == productsSize) {
                                                finish.postValue(productsModel)

                                            }
                                        }

                                    }

                                    override fun onFailure(str: String) {
                                        mValidation.value = ValidationListener(str)
                                        finishObserver?.let { finish.removeObserver(it) }
                                    }

                                })
                        }
                    }
                }

                finishObserver = Observer<List<OrderProductModel>> {
                    if (model != null) {
                        var order = OrderModel(
                            model.id,
                            model.description,
                            model.date,
                            model.number,
                            model.total,
                            it
                        )
                        mLoadOrder.value = order
                    }

                }

                finish.observeForever(finishObserver!!)

            }

            override fun onFailure(str: String) {
                mValidation.value = ValidationListener(str)
            }

        })
    }

    fun onAmountChanged(productOrderModel : OrderProductModel, order : OrderModel?, amount:Int, listener : FirebaseListener<Boolean>){
        updateProductOrder(productOrderModel, order)?.apply {
            mOrderRepository.onChangeProductAmount(this.toRequest(), listener)
        }
    }

    fun updateProductOrder(productOrderModel : OrderProductModel, order : OrderModel?) : OrderModel?{

        order?.let{
            var orderModel = it
            var productList :  MutableList<OrderProductModel> = arrayListOf()
            orderModel.products?.let{
                it.forEach{ orderProduct ->
                    if(orderProduct.product?.id == productOrderModel.id){
                        productList.add(productOrderModel)
                    }else{
                        productList.add(orderProduct)
                    }
                }
            }

            orderModel.products = productList
            return orderModel
        }

        return order

    }

}