package com.example.bookingapp.ui.hotelrooms

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bookingapp.api.hotel.HotelInfo
import com.example.bookingapp.api.hotelrooms.HotelRoomsInfo
import com.example.bookingapp.api.hotelrooms.HotelRoomsRepository
import com.example.bookingapp.api.hotelrooms.Room
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HotelRoomsViewModel : ViewModel() {
    var hotelRoomsLiveData = MutableLiveData<List<Room>>()

    fun emergeHotelRoomsInfo(dataList: MutableLiveData<List<Room>>, response: Call<HotelRoomsInfo>) {
        response.enqueue(
            object : Callback<HotelRoomsInfo> {
                override fun onResponse(
                    call: Call<HotelRoomsInfo>,
                    response: Response<HotelRoomsInfo>
                ) {
                    dataList.postValue(response.body()?.rooms)
                }

                override fun onFailure(call: Call<HotelRoomsInfo>, t: Throwable) {
                    Log.e("httpCall", t.message ?:"Unknown Error")
                    dataList.postValue(null)
                }

            }
        )
    }

    fun makeApiCall(){
        emergeHotelRoomsInfo(
            hotelRoomsLiveData, HotelRoomsRepository.getHotelRoomsData()
        )
    }
}