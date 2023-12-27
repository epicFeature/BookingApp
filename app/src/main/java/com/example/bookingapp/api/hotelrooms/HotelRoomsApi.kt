package com.example.bookingapp.api.hotelrooms

import com.example.bookingapp.api.hotel.HotelInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface HotelRoomsApi {
    @Headers(
        "Accept: application/json",
        "Content-type: application/json"
    )
    @GET("/v3/8b532701-709e-4194-a41c-1a903af00195")

    fun getHotelRoomsInfo( ): Call<HotelRoomsInfo>
}