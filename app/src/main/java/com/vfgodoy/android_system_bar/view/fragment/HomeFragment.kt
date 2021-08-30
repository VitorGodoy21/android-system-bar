package com.vfgodoy.android_system_bar.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.vfgodoy.android_system_bar.databinding.FragmentHomeBinding
import com.vfgodoy.android_system_bar.view.activity.ProductActivity
import com.vfgodoy.android_system_bar.viewmodel.HomeViewModel

class HomeFragment : Fragment(), View.OnClickListener {

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

    private fun setListeners() {
        binding.btAddProduct.setOnClickListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.btAddProduct.id -> {
                startActivity(Intent(context, ProductActivity::class.java))
            }
        }
    }
}