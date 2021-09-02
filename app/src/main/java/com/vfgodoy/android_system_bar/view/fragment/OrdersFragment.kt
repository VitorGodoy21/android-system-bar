package com.vfgodoy.android_system_bar.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.vfgodoy.android_system_bar.R
import com.vfgodoy.android_system_bar.databinding.FragmentOrdersBinding
import com.vfgodoy.android_system_bar.view.activity.FloatingActionButtonController
import com.vfgodoy.android_system_bar.view.activity.ProductFormActivity
import com.vfgodoy.android_system_bar.viewmodel.OrdersViewModel

class OrdersFragment : BaseFragment() {

    private lateinit var ordersViewModel: OrdersViewModel
    private var _binding: FragmentOrdersBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        ordersViewModel =
                ViewModelProvider(this).get(OrdersViewModel::class.java)

        _binding = FragmentOrdersBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textOrders
        ordersViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        return root
    }

    override fun onResume() {
        super.onResume()
        setFabImageResource(R.drawable.ic_fab_add)
        setFabVisibility(View.VISIBLE)
        setFabAction {
            //NoAction
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