package com.raj.mymapdemo.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class OrdersModel {
    @SerializedName("orders")
    @Expose
    private var orders: List<Order?>? = null

    fun getOrders(): List<Order?>? {
        return orders
    }

    fun setOrders(orders: List<Order?>?) {
        this.orders = orders
    }
}

class Order {
    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("location")
    @Expose
    var location: Location? = null

    @SerializedName("phone")
    @Expose
    var phone: Int? = null

    @SerializedName("address")
    @Expose
    var address: String? = null
}

class Location {
    @SerializedName("lat")
    @Expose
    var lat: Double? = null

    @SerializedName("long")
    @Expose
    var long: Double? = null
}