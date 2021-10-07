package com.vfgodoy.android_system_bar.view.activity

import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.vfgodoy.android_system_bar.R
import com.vfgodoy.android_system_bar.databinding.ActivityFormProductBinding
import com.vfgodoy.android_system_bar.service.constants.ProductConstants
import com.vfgodoy.android_system_bar.service.model.ProductModel
import com.vfgodoy.android_system_bar.util.Util
import com.vfgodoy.android_system_bar.viewmodel.ProductViewModel

class FormProductActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mProductViewModel: ProductViewModel
    private lateinit var binding: ActivityFormProductBinding
    private var uriImage : Uri? = null
    private var urlImage : String = ""
    private var mProductId = ""

    //TODO: MOSTRAR BOTÃO VOLTAR
    //TODO: MASCARA NA INSERÇÃO DO PREÇO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFormProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mProductViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)

        setListeners()
        observer()
        loadDataFromActivity()

    }

    private fun handleSave(){
        //TODO: BUG: APP ENVIA IMAGEM VAZIA PRO FIREBASE QUANDO ATUALIZO O PRODUTO SEM ALTERAR A IMAGEM
        val name = binding.etName.text.toString()
        val price = binding.etPrice.text.toString()
        val product = ProductModel(mProductId, name, price.toFloatOrNull(), uriImage, urlImage)
        mProductViewModel.save(product)
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

        mProductViewModel.loadProduct.observe(this,{ product ->
            product?.let {
                binding.etName.setText(it.name)
                binding.etPrice.setText(it.price.toString())
                if(!product.imageUrl.isNullOrEmpty()){
                    urlImage = product.imageUrl
                    Glide.with(this).asBitmap().load(product.imageUrl).listener(object :
                        RequestListener<Bitmap> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Bitmap>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            binding.ivProduct.setPadding(40,40,40,40)
                            binding.ivProduct.setImageResource(R.drawable.ic_empty_image)
                            binding.pbImage.visibility = View.INVISIBLE
                            return true
                        }

                        override fun onResourceReady(
                            resource: Bitmap?,
                            model: Any?,
                            target: Target<Bitmap>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            binding.ivProduct.setPadding(0,0,0,0)
                            binding.pbImage.visibility = View.INVISIBLE
                            return false
                        }

                    }).into(binding.ivProduct)
                }else{
                    binding.ivProduct.setPadding(0,0,0,0)
                    binding.pbImage.visibility = View.INVISIBLE
                }
            }
        })


    }

    private fun loadDataFromActivity(){
        val bundle = intent.extras
        if(bundle != null){
            mProductId = bundle.getString(ProductConstants.BUNDLE.PRODUCTID).toString()
            mProductViewModel.load(mProductId)
            binding.pbImage.visibility = View.VISIBLE
        }
    }

    private val selectImageFromGalleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                uriImage = uri
                binding.ivProduct.setImageURI(uriImage)
            }
        }
}