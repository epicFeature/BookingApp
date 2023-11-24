package com.example.bookingapp.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bookingapp.api.hotel.HotelInfo
import com.example.bookingapp.api.hotel.HotelRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    var hotelLiveData = MutableLiveData<HotelInfo>()

    private fun emergeHotelInfo(data: MutableLiveData<HotelInfo>, response: Call<HotelInfo>) {
        response.enqueue(
            object : Callback<HotelInfo> {
                override fun onResponse(call: Call<HotelInfo>, response: Response<HotelInfo>) {
                   data.postValue(response.body())
                }

                override fun onFailure(call: Call<HotelInfo>, t: Throwable) {
                    Log.e("httpCall", t.message ?: "UnknownError")
                    data.postValue(null)
                }

            }
        )
    }

    fun makeApiCall(){
        emergeHotelInfo(hotelLiveData, HotelRepository.getHotelData())
    }
}