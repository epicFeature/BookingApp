package com.example.bookingapp.api.hotelrooms

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Room(
    val id: Int,
    val name: String,
    val price: Int,
    @Json(name = "price_per")
    val pricePer: String,
    val peculiarities: List<String?>,
    @Json(name = "image_urls")
    val imageUrls: List<String>
)
