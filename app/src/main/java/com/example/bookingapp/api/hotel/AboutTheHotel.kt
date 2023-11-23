package com.example.bookingapp.api.hotel

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AboutTheHotel(
    val description: String,
    val peculiarities: List<String>
)
