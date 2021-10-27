package com.vfgodoy.android_system_bar.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vfgodoy.android_system_bar.R
import com.vfgodoy.android_system_bar.service.listener.ProductListener
import com.vfgodoy.android_system_bar.service.listener.ProductOrderListener
import com.vfgodoy.android_system_bar.service.model.OrderProductModel
import com.vfgodoy.android_system_bar.view.viewholder.ProductOrderViewHolder

class ProductOrderAdapter : RecyclerView.Adapter<ProductOrderViewHolder>(){

    private var mList : List<OrderProductModel> = arrayListOf()
    private lateinit var mListener: ProductOrderListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductOrderViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.item_product_order_list, parent, false)
        return ProductOrderViewHolder(item,mListener)
    }

    override fun onBindViewHolder(holder: ProductOrderViewHolder, position: Int) {
        holder.bindData(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.count()
    }

    fun updateList(list : List<OrderProductModel>){
        mList = list.sortedBy { it.product?.name }
        notifyDataSetChanged()
    }

    fun attachListener(listener: ProductOrderListener) {
        mListener = listener
    }
}