package com.raj.mymapdemo

import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_CALL
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.raj.mymapdemo.model.Order


class OrderAdapter(val orders: List<Order>, val context: Context) :
    RecyclerView.Adapter<OrderAdapter.MyViewHolder>() {
    var selectedPostion =0;
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mainLayout = itemView.findViewById<View>(R.id.main_layout)
        val titleText = itemView.findViewById<TextView>(R.id.text_id)
        val subText = itemView.findViewById<TextView>(R.id.sub_text)
        val phoneIcon = itemView.findViewById<ImageView>(R.id.img_id)
        val direfctionIcon = itemView.findViewById<ImageView>(R.id.direction_id)
        val deliveryBtn= itemView.findViewById<Button>(R.id.delivery_btn)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val orderItem = orders[position]
        holder.titleText.text = orderItem.name
        holder.subText.text = orderItem.address

        holder.phoneIcon.setOnClickListener {

            if (MapsActivity.isPhonePermissionGranted) {
                val intent = Intent(ACTION_CALL, Uri.parse("tel:" + orderItem.phone))
                intent.flags = FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            } else {
                Toast.makeText(
                    context,
                    context.getString(R.string.phone_permission),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        holder.direfctionIcon.setOnClickListener {
            val gmmIntentUri =
                Uri.parse("geo:${orderItem.location?.lat},${orderItem.location?.long}")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            mapIntent.flags = FLAG_ACTIVITY_NEW_TASK
            mapIntent.resolveActivity(context.packageManager)?.let {
                context.startActivity(mapIntent)
            }
        }

        holder.mainLayout.setOnClickListener {
            selectedPostion = position;
            notifyDataSetChanged()
        }
        if (selectedPostion == position)
        {
            holder.deliveryBtn.visibility = View.VISIBLE
        }else
        {
            holder.deliveryBtn.visibility = View.GONE
        }

    }

    override fun getItemCount(): Int {
        return orders.size
    }

}
