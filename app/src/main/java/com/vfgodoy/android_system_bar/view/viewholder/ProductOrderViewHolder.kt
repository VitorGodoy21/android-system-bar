package com.vfgodoy.android_system_bar.view.viewholder

import android.graphics.Bitmap
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.vfgodoy.android_system_bar.R
import com.vfgodoy.android_system_bar.service.listener.ProductListener
import com.vfgodoy.android_system_bar.service.listener.ProductOrderListener
import com.vfgodoy.android_system_bar.service.model.OrderProductModel

class ProductOrderViewHolder (itemView: View, val listener: ProductOrderListener) : RecyclerView.ViewHolder(itemView) {

    private var mTextName : TextView = itemView.findViewById(R.id.tv_product_order_name)
    private var mTextPrice : TextView = itemView.findViewById(R.id.tv_product_order_price)
    private var mImageProduct : ImageView = itemView.findViewById(R.id.iv_product_order)
    private var mProgressBar : ProgressBar = itemView.findViewById(R.id.pb_image_product_order)
    private var mButtonMinus : Button =  itemView.findViewById(R.id.bt_product_order_minus)
    private var mButtonPlus : Button =  itemView.findViewById(R.id.bt_product_order_plus)
    private var mEditTextAmount : EditText = itemView.findViewById(R.id.et_product_order_amount)



    fun bindData(productOrderModel: OrderProductModel){
        this.mTextName.text = productOrderModel.product?.name
        this.mTextPrice.text = productOrderModel.product?.price?.toString()
        this.mEditTextAmount.setText(productOrderModel.amount.toString())
        this.mProgressBar.visibility = View.VISIBLE

        mButtonMinus.setOnClickListener{
            var amount : Int = mEditTextAmount.text.toString().toInt()
            if(amount> 0){
                amount--
                mEditTextAmount.setText(amount.toString())
                productOrderModel.amount = amount
                listener.onAmountChange(productOrderModel)
            }
        }

        mButtonPlus.setOnClickListener{
            var amount : Int = mEditTextAmount.text.toString().toInt()
            amount++
            mEditTextAmount.setText(amount.toString())
            productOrderModel.amount = amount
            listener.onAmountChange(productOrderModel)
        }

        if(!productOrderModel.product?.imageUrl.isNullOrEmpty()){
            Glide.with(itemView.context).asBitmap().load(productOrderModel.product?.imageUrl).listener(object :
                RequestListener<Bitmap> {
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
    }

}