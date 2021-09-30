package com.vfgodoy.android_system_bar.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vfgodoy.android_system_bar.R
import com.vfgodoy.android_system_bar.databinding.FragmentOrdersBinding
import com.vfgodoy.android_system_bar.service.constants.OrderConstants
import com.vfgodoy.android_system_bar.service.listener.OrderListener
import com.vfgodoy.android_system_bar.util.Util
import com.vfgodoy.android_system_bar.view.activity.FloatingActionButtonController
import com.vfgodoy.android_system_bar.view.activity.OrderDetailsActivity
import com.vfgodoy.android_system_bar.view.adapter.OrderAdapter
import com.vfgodoy.android_system_bar.view.dialog.FormOrderDialog
import com.vfgodoy.android_system_bar.viewmodel.OrderViewModel

class OrdersFragment : BaseFragment() {

    private lateinit var mOrdersViewModel: OrderViewModel
    private var _binding: FragmentOrdersBinding? = null

    private val binding get() = _binding!!

    private lateinit var mListener : OrderListener
    private val mAdapter = OrderAdapter()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        mOrdersViewModel =
                ViewModelProvider(this).get(OrderViewModel::class.java)

        _binding = FragmentOrdersBinding.inflate(inflater, container, false)
        val root: View = binding.root

        root.findViewById<RecyclerView>(R.id.rv_orders).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }

        mListener = object : OrderListener{
            override fun onListClick(id: String) {
                val intent = Intent(context, OrderDetailsActivity::class.java)
                val bundle = Bundle()
                bundle.putString(OrderConstants.BUNDLE.ORDERID, id)
                intent.putExtras(bundle)
                startActivity(intent)
            }
        }

        observer()
        mOrdersViewModel.all()
        mAdapter.attachListener(mListener)

        return root
    }

    private fun observer(){

        mOrdersViewModel.orders.observe(viewLifecycleOwner, {
            if(it.isNotEmpty()){
                mAdapter.updateList(it)
            }
        })

        mOrdersViewModel.validation.observe(viewLifecycleOwner, {
            if(!it.success()){
                Util.makeToast(context, it.failure())
            }
        })

    }

    override fun onResume() {
        super.onResume()

        mAdapter.attachListener(mListener)
        mOrdersViewModel.all()
        setFabImageResource(R.drawable.ic_fab_add)
        setFabVisibility(View.VISIBLE)
        setFabAction {
            FormOrderDialog().show(parentFragmentManager, "FormOrderDialog")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun setFabImageResource(resourceId: Int) {
        (activity as FloatingActionButtonController?)?.setFabImageResource(R.drawable.ic_fab_add)
    }

    override fun setFabAction(listener: (View) -> Unit) {
        (activity as FloatingActionButtonController?)?.setFabAction(listener)
    }

    override fun setFabVisibility(visibility: Int) {
        (activity as FloatingActionButtonController?)?.setFabVisibility(visibility)
    }

}