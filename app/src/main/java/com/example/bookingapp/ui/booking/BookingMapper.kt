package com.example.bookingapp.ui.booking

import android.annotation.SuppressLint
import com.example.bookingapp.api.booking.BookingInfo
import com.example.bookingapp.databinding.FragmentBookingBinding

class BookingMapper(
    private val binding: FragmentBookingBinding
) {
    @SuppressLint("SetTextI18n")
    fun setBookingFragmentStaticData(bookingInfo: BookingInfo){
        with(binding){
            bookingHotelName.text = bookingInfo.hotelName
            bookingHotelAddress.text=bookingInfo.hotelAddress
            rating.text = "${bookingInfo.rating} ${bookingInfo.ratingName}"
            flightFromInfo.text = bookingInfo.departure
            countryCityToInfo.text=bookingInfo.arrivalCountry
            dateInfo.text="${bookingInfo.tourDateStart} - ${bookingInfo.tourDateStop}"
            numberNightsInfo.text="${bookingInfo.numberOfNights} ночей"
            bookingHotelNameInfo.text=bookingInfo.hotelName
            hotelRoomInfo.text=bookingInfo.room
            mealOptionInfo.text=bookingInfo.nutrition
            tourPrice.text="${bookingInfo.tourPrice} ₽"
            fuelSurchargePrice.text="${bookingInfo.fuelCharge} ₽"
            serviceChargePrice.text="${bookingInfo.serviceCharge} ₽"
            toBePaidPrice.text="${totalSumCount(bookingInfo)} ₽"
            payButton.text="Оплатить ${totalSumCount(bookingInfo)} ₽"
        }
    }

    private fun totalSumCount(bookingInfo: BookingInfo):Int{
        return bookingInfo.tourPrice+bookingInfo.fuelCharge+bookingInfo.serviceCharge
    }
}