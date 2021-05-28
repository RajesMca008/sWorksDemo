package com.raj.mymapdemo.model

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson

class MapsActivityModel : ViewModel() {
    private val url = "http://demo8360259.mockable.io/clients"
    val orders: MutableLiveData<List<Order>> by lazy {
        MutableLiveData<List<Order>>()
    }

    fun getOrdersList(context: Context) {
        val requestQueue = Volley.newRequestQueue(context)

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->

                val ordersModel = Gson().fromJson(response, OrdersModel::class.java)
                ordersModel.getOrders()
                ordersModel.getOrders()?.let {
                    orders.value = it as List<Order>
                }
            },
            { error ->
                error.printStackTrace()
            })
        requestQueue.add(stringRequest)
    }

}