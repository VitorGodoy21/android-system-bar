package com.vfgodoy.android_system_bar.extension

import com.vfgodoy.android_system_bar.service.model.OrderModel
import com.vfgodoy.android_system_bar.service.model.OrderProductModel
import com.vfgodoy.android_system_bar.service.model.ProductModel
import com.vfgodoy.android_system_bar.service.model.request.OrderModelRequest
import com.vfgodoy.android_system_bar.service.model.request.OrderProductModelRequest
import com.vfgodoy.android_system_bar.service.model.request.ProductModelRequest

fun OrderModel.toRequest(): OrderModelRequest {

    val products: MutableList<OrderProductModelRequest> = arrayListOf()

    this.products?.let { list ->
        list.forEach {
            products.add(it.toRequest())
        }
    }

    return OrderModelRequest(this.id, this.description, this.date, this.number, this.total, products)

}

fun ProductModel.toRequest() : ProductModelRequest{
    return ProductModelRequest(this.id,this.name, this.price, this.imageUrl)
}

fun OrderProductModel.toRequest() : OrderProductModelRequest{
    return OrderProductModelRequest(this.id, this.product?.id?:"", this.amount)
}

fun ProductModelRequest.toModel() : ProductModel {
    return ProductModel(this.id, this.name, this.price, null, this.imageUrl)
}