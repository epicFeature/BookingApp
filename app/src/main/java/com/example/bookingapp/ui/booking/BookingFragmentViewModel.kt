package com.example.bookingapp.ui.booking

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bookingapp.api.booking.BookingInfo
import com.example.bookingapp.api.booking.BookingRepository
import com.example.bookingapp.api.hotel.HotelRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookingFragmentViewModel: ViewModel() {
    var bookingLiveData=MutableLiveData<BookingInfo>()

    private fun emergeBookingInfo(data: MutableLiveData<BookingInfo>, response: Call<BookingInfo>){
        response.enqueue(
            object: Callback<BookingInfo> {
                override fun onResponse(call: Call<BookingInfo>, response: Response<BookingInfo>) {
                    data.postValue(response.body())
                }
                override fun onFailure(call: Call<BookingInfo>, t: Throwable) {
                    Log.e("httpCall", t.message ?: "UnknownError")
                    data.postValue(null)
                }

            }
        )
    }

    fun makeApiCall(){
        emergeBookingInfo(bookingLiveData, BookingRepository.getBookingData())
    }
}