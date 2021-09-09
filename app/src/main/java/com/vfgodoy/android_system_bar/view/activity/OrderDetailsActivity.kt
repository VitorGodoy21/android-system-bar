package com.vfgodoy.android_system_bar.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.vfgodoy.android_system_bar.databinding.ActivityOrderDetailsBinding
import com.vfgodoy.android_system_bar.extension.toMoneyFormat
import com.vfgodoy.android_system_bar.service.constants.OrderConstants
import com.vfgodoy.android_system_bar.viewmodel.OrderViewModel

class OrderDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderDetailsBinding
    private var mOrderId = ""
    private lateinit var mOrderViewModel: OrderViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOrderDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mOrderViewModel = ViewModelProvider(this).get(OrderViewModel::class.java)

        observers()
        loadDataFromActivity()
    }

    private fun observers(){
        mOrderViewModel.loadOrder.observe(this, Observer {
            it?.let {
                binding.tvOrderNumber.text = it.number.toString()
                binding.tvOrderDescription.text = it.description
                binding.tvOrderTotal.text = it.total.toString().toMoneyFormat()
            }
        })
    }

    private fun loadDataFromActivity(){
        val bundle = intent.extras
        if(bundle != null){
            mOrderId = bundle.getString(OrderConstants.BUNDLE.ORDERID).toString()
            mOrderViewModel.load(mOrderId)
        }
    }

}