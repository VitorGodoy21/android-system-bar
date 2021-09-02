package com.vfgodoy.android_system_bar.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vfgodoy.android_system_bar.R
import com.vfgodoy.android_system_bar.databinding.FragmentProductsBinding
import com.vfgodoy.android_system_bar.service.constants.ProductConstants
import com.vfgodoy.android_system_bar.service.listener.ProductListener
import com.vfgodoy.android_system_bar.util.Util
import com.vfgodoy.android_system_bar.view.activity.ProductFormActivity
import com.vfgodoy.android_system_bar.view.adapter.ProductAdapter
import com.vfgodoy.android_system_bar.viewmodel.ProductViewModel
import com.vfgodoy.android_system_bar.view.activity.FloatingActionButtonController


class ProductsFragment : BaseFragment() {

    private lateinit var mProductViewModel: ProductViewModel
    private var _binding: FragmentProductsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var mListener: ProductListener
    private val mAdapter = ProductAdapter()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        mProductViewModel =
                ViewModelProvider(this).get(ProductViewModel::class.java)

        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        root.findViewById<RecyclerView>(R.id.rv_products).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }

        mListener = object : ProductListener{
            override fun onListClick(id: String) {
                val intent = Intent(context, ProductFormActivity::class.java)
                val bundle = Bundle()
                bundle.putString(ProductConstants.BUNDLE.PRODUCTID, id)
                intent.putExtras(bundle)
                startActivity(intent)
            }
        }

        observer()
        mProductViewModel.all()
        mAdapter.attachListener(mListener)

        return root
    }

    private fun observer(){
        mProductViewModel.products.observe(viewLifecycleOwner, Observer {
            if(it.isNotEmpty()){
                mAdapter.updateList(it)
            }
        })

        mProductViewModel.validation.observe(viewLifecycleOwner, Observer {
            if(!it.success()){
                Util.makeToast(context, it.failure())
            }
        })
    }

    override fun onResume() {
        super.onResume()
        mAdapter.attachListener(mListener)
        mProductViewModel.all()
        setFabImageResource(R.drawable.ic_fab_add)
        setFabVisibility(View.VISIBLE)
        setFabAction {
            startActivity(Intent(activity, ProductFormActivity::class.java))
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun setFabImageResource(resourceId: Int) {
        (activity as FloatingActionButtonController?)?.setFabImageResource(resourceId)
    }

    override fun setFabAction(listener: (View) -> Unit) {
        (activity as FloatingActionButtonController?)?.setFabAction(listener)
    }

    override fun setFabVisibility(visibility: Int) {
        (activity as FloatingActionButtonController?)?.setFabVisibility(visibility)
    }
}