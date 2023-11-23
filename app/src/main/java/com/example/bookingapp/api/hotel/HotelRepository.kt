package com.example.bookingapp.api.hotel

import com.example.bookingapp.api.common.RetrofitInstance

object HotelRepository {
    private val retrofit = RetrofitInstance.instance
    fun getHotelData() = retrofit.create(HotelApi::class.java).getHotelInfo()

}