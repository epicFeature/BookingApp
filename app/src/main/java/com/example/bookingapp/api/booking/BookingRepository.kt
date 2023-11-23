package com.example.bookingapp.api.booking

import com.example.bookingapp.api.common.RetrofitInstance
import com.example.bookingapp.api.hotel.HotelApi
import com.example.bookingapp.api.hotel.HotelRepository

object BookingRepository {
    private val retrofit = RetrofitInstance.instance
    fun getBookingData() =
        BookingRepository
            .retrofit
            .create(BookingApi::class.java)
            .getBookingInfo()
}