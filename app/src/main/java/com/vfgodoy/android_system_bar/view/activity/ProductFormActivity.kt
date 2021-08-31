package com.vfgodoy.android_system_bar.view.activity

import android.Manifest
import android.app.PendingIntent.getActivity
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.vfgodoy.android_system_bar.R
import com.vfgodoy.android_system_bar.databinding.ActivityFormProductBinding
import com.vfgodoy.android_system_bar.service.model.ProductModel
import com.vfgodoy.android_system_bar.util.Util
import com.vfgodoy.android_system_bar.viewmodel.ProductViewModel
import com.vfgodoy.android_system_bar.viewmodel.UserViewModel

class ProductFormActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mProductViewModel: ProductViewModel
    private lateinit var binding: ActivityFormProductBinding
    private var uriImage : Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFormProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mProductViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)

        setListeners()
        observer()

    }

    private fun handleSave(){

        val name = binding.etName.text.toString()
        val price = binding.etPrice.text.toString()

        mProductViewModel.save(name, price, uriImage)

    }

    private fun setListeners(){
        binding.btSave.setOnClickListener(this)
        binding.btUpload.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            binding.btSave.id -> handleSave()
            binding.btUpload.id -> selectImageFromGalleryResult.launch("image/*")
        }
    }

    private fun observer(){
        mProductViewModel.saveResult.observe(this, {
            if(it.success()){
                Util.makeToast(this, getString(R.string.product_save_with_success))
                finish()
            }else{
                Util.makeToast(this, it.failure())
            }
        })

    }

    private val selectImageFromGalleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                uriImage = uri
                binding.ivProduct.setImageURI(uriImage)
            }
        }

}