package com.vfgodoy.android_system_bar.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.vfgodoy.android_system_bar.R
import com.vfgodoy.android_system_bar.service.model.request.OrderModelRequest
import com.vfgodoy.android_system_bar.util.Util
import com.vfgodoy.android_system_bar.viewmodel.OrderViewModel

class FormOrderDialog : DialogFragment(), View.OnClickListener {

    private lateinit var mOrderViewModel: OrderViewModel
    private var saveButton : Button? = null
    private var descriptionTextView : TextView? = null
    private var numberTextView : TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.activity_order_form, container)

        mOrderViewModel = ViewModelProvider(this).get(OrderViewModel::class.java)

        saveButton = rootView.findViewById(R.id.bt_save_order)
        descriptionTextView = rootView.findViewById(R.id.et_description)
        numberTextView = rootView.findViewById(R.id.et_number)

        setListeners()
        setObservers()
        return rootView
    }


    override fun onStart() {
        super.onStart()
        dialog!!.window?.setLayout(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    private fun setObservers() {
        mOrderViewModel.saveStarterResult.observe(this, {
            if (it.success()) {
                Util.makeToast(context, getString(R.string.order_starter_save_with_success))
                dismiss()
            } else {
                Util.makeToast(context, it.failure())
            }
        })
    }

    private fun setListeners() {
        saveButton?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.bt_save_order -> {
                handleSave()
            }
        }
    }

    private fun handleSave() {
        val number: String = numberTextView?.text.toString()
        val description: String = descriptionTextView?.text.toString()

        val order = OrderModelRequest.createStarterOrder(number.toIntOrNull(), description)
        mOrderViewModel.saveStarter(order)
    }
}