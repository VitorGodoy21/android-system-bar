package com.vfgodoy.android_system_bar.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vfgodoy.android_system_bar.R
import com.vfgodoy.android_system_bar.service.listener.OrderListener
import com.vfgodoy.android_system_bar.service.model.request.OrderModelRequest
import com.vfgodoy.android_system_bar.view.viewholder.OrderViewHolder

class OrderAdapter : RecyclerView.Adapter<OrderViewHolder>() {

    private var mList : List<OrderModelRequest> = arrayListOf()
    private lateinit var mListener: OrderListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.item_order_list, parent, false)
        return OrderViewHolder(item, mListener)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bindData(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.count()
    }

    fun attachListener(listener: OrderListener) {
        mListener = listener
    }

    fun updateList(list : List<OrderModelRequest>){
        mList = list
        notifyDataSetChanged()
    }
}