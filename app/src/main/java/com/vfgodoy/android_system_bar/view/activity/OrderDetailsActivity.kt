package com.vfgodoy.android_system_bar.view.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.vfgodoy.android_system_bar.databinding.ActivityOrderDetailsBinding
import com.vfgodoy.android_system_bar.extension.toModel
import com.vfgodoy.android_system_bar.extension.toMoneyFormat
import com.vfgodoy.android_system_bar.service.constants.OrderConstants
import com.vfgodoy.android_system_bar.service.listener.FirebaseListener
import com.vfgodoy.android_system_bar.service.listener.ProductListener
import com.vfgodoy.android_system_bar.service.listener.ProductOrderListener
import com.vfgodoy.android_system_bar.service.model.OrderModel
import com.vfgodoy.android_system_bar.service.model.OrderProductModel
import com.vfgodoy.android_system_bar.util.Util
import com.vfgodoy.android_system_bar.view.adapter.ProductOrderAdapter
import com.vfgodoy.android_system_bar.view.dialog.AddOrderProductDialog
import com.vfgodoy.android_system_bar.view.dialog.FormOrderDialog
import com.vfgodoy.android_system_bar.viewmodel.OrderViewModel
import com.vfgodoy.android_system_bar.viewmodel.ProductViewModel

class OrderDetailsActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityOrderDetailsBinding
    private var mOrderId = String()
    private var mOrder : OrderModel? = null

    private lateinit var mOrderViewModel: OrderViewModel
    private lateinit var mProductViewModel: ProductViewModel

    private val mAdapter = ProductOrderAdapter()
    private lateinit var mListener: ProductOrderListener

    private val addOrderProductDialog = AddOrderProductDialog()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mOrderViewModel = ViewModelProvider(this).get(OrderViewModel::class.java)
        mProductViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)

        binding = ActivityOrderDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvOrderProducts.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }

        mListener = object : ProductOrderListener{

            override fun onAmountChange(productOrderModel: OrderProductModel) {
                mOrderViewModel.onAmountChanged(productOrderModel, mOrder, productOrderModel.amount, object : FirebaseListener<OrderModel>{
                    override fun onSuccess(model: OrderModel) {
                        model.products?.let { products ->
                            mAdapter.updateList(products)
                        }
                    }

                    override fun onFailure(str: String) {
                        Util.makeToast(applicationContext, str)
                    }

                })
            }

        }

        observers()
        setListeners()
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

        addOrderProductDialog.productSelected.observe(this, {
            mProductViewModel.load(it)
        })

        mProductViewModel.loadProduct.observe(this, {
            it?.let {
                mOrderViewModel.onAddProduct(it, mOrder, object : FirebaseListener<OrderModel> {
                    override fun onSuccess(model: OrderModel) {
                        model.products?.let { products ->
                            mAdapter.updateList(products)
                        }
                    }

                    override fun onFailure(str: String) {
                        Util.makeToast(applicationContext, str)
                    }
                })
            }
        })
    }

    private fun setListeners(){
        binding.btAddProduct.setOnClickListener(this)
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

    override fun onClick(v: View?) {
        when(v?.id){
            binding.btAddProduct.id -> { addOrderProductDialog.show(this.supportFragmentManager, "AddOrderProduct") }
        }
    }

}