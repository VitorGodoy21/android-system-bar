package com.vfgodoy.android_system_bar.view.viewholder

import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.vfgodoy.android_system_bar.R
import com.vfgodoy.android_system_bar.extension.toMoneyFormat
import com.vfgodoy.android_system_bar.service.listener.ProductListener
import com.vfgodoy.android_system_bar.service.model.ProductModel

class ProductViewHolder(itemView: View, val listener: ProductListener) : RecyclerView.ViewHolder(itemView) {

    private var mTextName : TextView = itemView.findViewById(R.id.tv_product_name)
    private var mTextPrice : TextView = itemView.findViewById(R.id.tv_product_price)
    private var mImageProduct : ImageView = itemView.findViewById(R.id.iv_product)
    private var mProgressBar : ProgressBar = itemView.findViewById(R.id.pb_image_product)

    fun bindData(product : ProductModel){
        this.mTextName.text = product.name
        this.mTextPrice.text = product.price.toString().toMoneyFormat()
        this.mProgressBar.visibility = View.VISIBLE

        if(!product.imageUrl.isNullOrEmpty()){
            Glide.with(itemView.context).asBitmap().load(product.imageUrl).listener(object : RequestListener<Bitmap>{
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    isFirstResource: Boolean
                ): Boolean {
                    mImageProduct.setPadding(40,40,40,40)
                    mImageProduct.setImageResource(R.drawable.ic_empty_image)
                    mProgressBar.visibility = View.INVISIBLE
                    return true
                }

                override fun onResourceReady(
                    resource: Bitmap?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    mImageProduct.setPadding(0,0,0,0)
                    mProgressBar.visibility = View.INVISIBLE
                    return false
                }

            }).into(mImageProduct)
        }else{
            mImageProduct.setPadding(40,40,40,40)
            mImageProduct.setImageResource(R.drawable.ic_empty_image)
            mProgressBar.visibility = View.INVISIBLE
        }

        itemView.setOnClickListener{ listener.onListClick(product.id) }
    }
}