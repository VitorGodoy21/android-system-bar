package com.vfgodoy.android_system_bar.service.listener

import com.vfgodoy.android_system_bar.service.model.OrderProductModel

interface ProductOrderListener {
    fun onAmountChange(productOrderModel: OrderProductModel)
}