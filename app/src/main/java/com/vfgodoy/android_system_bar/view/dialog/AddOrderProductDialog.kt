package com.vfgodoy.android_system_bar.view.dialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.storage.ktx.storageMetadata
import com.vfgodoy.android_system_bar.databinding.DialogAddOrderProductDialogBinding
import com.vfgodoy.android_system_bar.service.listener.ProductListener
import com.vfgodoy.android_system_bar.util.Util
import com.vfgodoy.android_system_bar.view.adapter.ProductAdapter
import com.vfgodoy.android_system_bar.viewmodel.ProductViewModel

class AddOrderProductDialog : DialogFragment() {

    private lateinit var mProductViewModel: ProductViewModel

    private var _binding: DialogAddOrderProductDialogBinding? = null
    private val binding get() = _binding!!

    private val mProductSelected = MutableLiveData<String>()
    var productSelected : LiveData<String> = mProductSelected

    private lateinit var mListener: ProductListener
    private val mAdapter = ProductAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        mProductViewModel =
            ViewModelProvider(this).get(ProductViewModel::class.java)

        _binding = DialogAddOrderProductDialogBinding.inflate(inflater, container, false)

        val root = binding.root

        binding.rvProducts.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }

        mListener = object : ProductListener {
            override fun onListClick(id: String) {
                mProductSelected.value = id
                dismiss()
            }

        }

        observer()
        onSearchListener()
        mProductViewModel.all()
        mAdapter.attachListener(mListener)

        return root
    }

    override fun onResume() {
        super.onResume()
        mAdapter.attachListener(mListener)
        mProductViewModel.all()
    }

    private fun onSearchListener(){

        binding.etSearch.doOnTextChanged() { term, start, count, after ->
            Log.d("SEARCHTERM", term.toString())
            term?.let {
                mAdapter.filter(it.toString())
            }
        }

    }

    private fun observer() {
        mProductViewModel.products.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                mAdapter.updateList(it)
            }
        })

        mProductViewModel.validation.observe(viewLifecycleOwner, Observer {
            if (!it.success()) {

                Util.makeToast(context, it.failure())
            }
        })
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        dialog!!.window?.setLayout(
            width,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

}