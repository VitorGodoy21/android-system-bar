package com.vfgodoy.android_system_bar.view.viewholder

import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.vfgodoy.android_system_bar.R
import com.vfgodoy.android_system_bar.service.listener.ProductListener
import com.vfgodoy.android_system_bar.service.model.ProductModel
import org.w3c.dom.Text

class ProductViewHolder(itemView: View, val listener: ProductListener) : RecyclerView.ViewHolder(itemView) {


    private var mTextName : TextView = itemView.findViewById(R.id.tv_product_name)
    private var mTextPrice : TextView = itemView.findViewById(R.id.tv_product_price)
    private var mImageProduct : ImageView = itemView.findViewById(R.id.iv_product)

    fun bindData(product : ProductModel){
        this.mTextName.text = product.name
        this.mTextPrice.text = product.price.toString()
        Glide.with(itemView.context).asBitmap().load(product.imageUrl).into(mImageProduct)
        this.mTextPrice.text = product.price.toString().toMoneyFormat()

        itemView.setOnClickListener{ listener.onListClick(product.id) }

    }


}