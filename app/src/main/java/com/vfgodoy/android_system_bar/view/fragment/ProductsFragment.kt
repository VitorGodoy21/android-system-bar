package com.vfgodoy.android_system_bar.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.vfgodoy.android_system_bar.databinding.FragmentProductsBinding
import com.vfgodoy.android_system_bar.viewmodel.ProductViewModel

class ProductsFragment : Fragment() {

    private lateinit var mProductViewModel: ProductViewModel
    private var _binding: FragmentProductsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        mProductViewModel =
                ViewModelProvider(this).get(ProductViewModel::class.java)

        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        observer()
        mProductViewModel.all()

        return root
    }

    private fun observer(){
        mProductViewModel.list.observe(viewLifecycleOwner, Observer {
            if(it.size > 0){
                Log.d("wwwe", it.toString())
            }
        })

        mProductViewModel.validation.observe(viewLifecycleOwner, Observer {
            if(!it.success()){
                Log.d("wwwe", it.failure())
            }
        })


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}