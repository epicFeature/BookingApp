package com.example.bookingapp.api.hotel

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HotelInfo(
    val id: Int,
    val name: String,
    @Json(name = "adress")
    val address: String,
    @Json(name = "minimal_price")
    val minimalPrice: Int,
    val rating: Int,
    @Json(name = "rating_name")
    val ratingName: String,
    @Json(name = "image_urls")
    val imageUrls: List<String>,
    @Json(name = "about_the_hotel")
    val aboutTheHotel: AboutTheHotel
)
