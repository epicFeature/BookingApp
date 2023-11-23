package com.example.bookingapp.api.booking

import com.example.bookingapp.api.hotel.HotelInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface BookingApi {
    @Headers(
        "Accept: application/json",
        "Content-type: application/json"
    )
    @GET("/v3/63866c74-d593-432c-af8e-f279d1a8d2ff")

    fun getBookingInfo(): Call<BookingInfo>
}