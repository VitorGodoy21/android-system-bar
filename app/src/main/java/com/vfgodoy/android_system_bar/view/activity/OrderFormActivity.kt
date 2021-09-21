package com.vfgodoy.android_system_bar.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.vfgodoy.android_system_bar.R
import com.vfgodoy.android_system_bar.databinding.ActivityOrderFormBinding
import com.vfgodoy.android_system_bar.service.model.request.OrderModelRequest
import com.vfgodoy.android_system_bar.util.Util
import com.vfgodoy.android_system_bar.viewmodel.OrderViewModel

class OrderFormActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mOrderViewModel: OrderViewModel
    private lateinit var binding: ActivityOrderFormBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mOrderViewModel = ViewModelProvider(this).get(OrderViewModel::class.java)
        binding = ActivityOrderFormBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()
        setObservers()
    }

    private fun setObservers() {
        mOrderViewModel.saveStarterResult.observe(this, {
            if(it.success()){
                Util.makeToast(this, getString(R.string.order_starter_save_with_success))
                finish()
            }else{
                Util.makeToast(this, it.failure())
            }
        })
    }

    private fun setListeners() {
        binding.btSaveOrder.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            binding.btSaveOrder.id -> {handleSave()}
        }
    }

    private fun handleSave() {
        val number : String = binding.etNumber.text.toString()
        val description : String = binding.etDescription.text.toString()

        val order = OrderModelRequest.createStarterOrder(number.toIntOrNull(), description)
        mOrderViewModel.saveStarter(order)
    }
}