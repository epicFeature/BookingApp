package com.example.bookingapp.api.hotelrooms

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HotelRoomsInfo(
    val rooms: List<Room>
)
