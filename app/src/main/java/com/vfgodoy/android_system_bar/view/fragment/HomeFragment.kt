package com.vfgodoy.android_system_bar.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.vfgodoy.android_system_bar.R
import com.vfgodoy.android_system_bar.databinding.FragmentHomeBinding
import com.vfgodoy.android_system_bar.view.activity.FloatingActionButtonController
import com.vfgodoy.android_system_bar.view.activity.ProductFormActivity
import com.vfgodoy.android_system_bar.viewmodel.HomeViewModel

class HomeFragment : BaseFragment(), View.OnClickListener {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setListeners()

        return root
    }

    override fun onResume() {
        super.onResume()
        setFabImageResource(R.drawable.ic_fab_add)
        setFabVisibility(View.INVISIBLE)
        setFabAction {
            //No Action
        }
    }

    private fun setListeners() {
        binding.btAddProduct.setOnClickListener(this)
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

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.btAddProduct.id -> {
                startActivity(Intent(context, ProductFormActivity::class.java))
            }
        }
    }
}