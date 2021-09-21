package com.vfgodoy.android_system_bar.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.vfgodoy.android_system_bar.databinding.ActivityOrderDetailsBinding
import com.vfgodoy.android_system_bar.extension.toMoneyFormat
import com.vfgodoy.android_system_bar.service.constants.OrderConstants
import com.vfgodoy.android_system_bar.service.listener.FirebaseListener
import com.vfgodoy.android_system_bar.service.listener.ProductListener
import com.vfgodoy.android_system_bar.service.listener.ProductOrderListener
import com.vfgodoy.android_system_bar.service.model.OrderModel
import com.vfgodoy.android_system_bar.service.model.OrderProductModel
import com.vfgodoy.android_system_bar.util.Util
import com.vfgodoy.android_system_bar.view.adapter.ProductOrderAdapter
import com.vfgodoy.android_system_bar.viewmodel.OrderViewModel

class OrderDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderDetailsBinding
    private var mOrderId = ""
    private var mOrder : OrderModel? = null
    private lateinit var mOrderViewModel: OrderViewModel

    private val mAdapter = ProductOrderAdapter()
    private lateinit var mListener: ProductOrderListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mOrderViewModel = ViewModelProvider(this).get(OrderViewModel::class.java)

        binding = ActivityOrderDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvOrderProducts.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }

        mListener = object : ProductOrderListener{

            override fun onAmountChange(productOrderModel: OrderProductModel) {
                mOrderViewModel.onAmountChanged(productOrderModel, mOrder, productOrderModel.amount, object : FirebaseListener<Boolean>{
                    override fun onSuccess(model: Boolean) {
                        Util.makeToast(applicationContext, "SUCESSO")
                    }

                    override fun onFailure(str: String) {
                        Util.makeToast(applicationContext, str)
                    }

                })
            }

        }

        observers()
        loadDataFromActivity()
        mAdapter.attachListener(mListener)
    }

    private fun observers(){
        mOrderViewModel.loadOrder.observe(this, Observer {
            it?.let {
                mOrder = it
                binding.tvOrderNumber.text = it.number.toString()
                binding.tvOrderDescription.text = it.description
                binding.tvOrderTotal.text = it.total.toString().toMoneyFormat()
                if(!it.products.isNullOrEmpty()){
                    mAdapter.updateList(it.products!!)
                }
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

    override fun onResume() {
        super.onResume()
        mAdapter.attachListener(mListener)
    }

}