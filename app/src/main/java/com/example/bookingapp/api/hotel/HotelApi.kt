package com.example.bookingapp.api.hotel

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface HotelApi {
    @Headers(
        "Accept: application/json",
        "Content-type: application/json"
    )
    @GET("/v3/d144777c-a67f-4e35-867a-cacc3b827473")

    fun getHotelInfo(): Call<HotelInfo>
}