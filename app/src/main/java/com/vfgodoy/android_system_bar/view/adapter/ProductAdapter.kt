package com.vfgodoy.android_system_bar.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vfgodoy.android_system_bar.R
import com.vfgodoy.android_system_bar.service.listener.ProductListener
import com.vfgodoy.android_system_bar.service.model.ProductModel
import com.vfgodoy.android_system_bar.view.viewholder.ProductViewHolder

class ProductAdapter : RecyclerView.Adapter<ProductViewHolder>() {

    private var mList : List<ProductModel> = arrayListOf()
    private lateinit var mListener: ProductListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.item_product_list, parent, false)
        return ProductViewHolder(item, mListener)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bindData(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.count()
    }

    fun attachListener(listener: ProductListener) {
        mListener = listener
    }

    fun updateList(list : List<ProductModel>){
        mList = list
        notifyDataSetChanged()
    }
}