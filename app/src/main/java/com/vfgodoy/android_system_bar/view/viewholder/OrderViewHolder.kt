package com.vfgodoy.android_system_bar.view.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vfgodoy.android_system_bar.R
import com.vfgodoy.android_system_bar.extension.toMoneyFormat
import com.vfgodoy.android_system_bar.service.listener.OrderListener
import com.vfgodoy.android_system_bar.service.model.request.OrderModelRequest

class OrderViewHolder(itemView: View, val listener: OrderListener) : RecyclerView.ViewHolder(itemView)  {

    private var mNumber : TextView = itemView.findViewById(R.id.tv_order_number)
    private var mDescription : TextView = itemView.findViewById(R.id.tv_order_description)
    private var mTotal : TextView = itemView.findViewById(R.id.tv_order_total)

    fun bindData(order : OrderModelRequest){
        this.mNumber.text = order.number.toString()
        this.mDescription.text = order.description
        this.mTotal.text = order.total.toString().toMoneyFormat()

        itemView.setOnClickListener{listener.onListClick(order.id)}
    }

}