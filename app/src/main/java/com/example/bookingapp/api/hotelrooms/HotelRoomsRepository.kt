package com.example.bookingapp.api.hotelrooms

import com.example.bookingapp.api.common.RetrofitInstance

object HotelRoomsRepository {
    private val retrofit = RetrofitInstance.instance
    fun getHotelRoomsData() = retrofit.create(HotelRoomsApi::class.java).getHotelRoomsInfo()
}