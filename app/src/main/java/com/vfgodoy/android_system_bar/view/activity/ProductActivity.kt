package com.vfgodoy.android_system_bar.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vfgodoy.android_system_bar.databinding.ActivityProductBinding

class ProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}