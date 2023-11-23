package com.example.bookingapp.ui.main

import android.annotation.SuppressLint
import com.example.bookingapp.api.hotel.HotelInfo
import com.example.bookingapp.databinding.FragmentMainBinding
import com.example.bookingapp.ui.viewpager.ViewPagerAdapter

class HotelMapper(
    private val binding: FragmentMainBinding,
    private val mainFragment: MainFragment
) {

    @SuppressLint("SetTextI18n")
    fun setMainFragmentData(hotelInfo: HotelInfo){
        with(binding) {
            rating.text = "${hotelInfo.rating} ${hotelInfo.ratingName}"
            hotelName.text = hotelInfo.name
            hotelAddress.text=hotelInfo.address
            hotelSum.text = "от ${hotelInfo.minimalPrice.toString()} ₽"
            //переделать на нормальный перебор
            peculiarItemFirst.text = hotelInfo.aboutTheHotel.peculiarities[0]
            peculiarItemSecond.text = hotelInfo.aboutTheHotel.peculiarities[1]
            peculiarItemThird.text = hotelInfo.aboutTheHotel.peculiarities[2]
            peculiarItemForth.text=hotelInfo.aboutTheHotel.peculiarities[3]
            hotelDesription.text = hotelInfo.aboutTheHotel.description

            /*titleRu.text = cinemaData.name
            titleEng.text = cinemaData.alternativeName
            rating.text = cinemaData.rating?.kp.toString()
            genresMapping(cinemaData)
            discription.text = cinemaData.description*/
        }
        imageSlider(hotelInfo)
    }

    private fun imageSlider(hotelInfo: HotelInfo) {
        val hotelImages = hotelInfo.imageUrls
        val dotsIndicator = binding.dotsIndicatorHotel
        val viewPager = binding.viewPagerHotel
        val viewPagerAdapter = ViewPagerAdapter(mainFragment.requireContext(), hotelImages)
        viewPager.adapter = viewPagerAdapter
        dotsIndicator.attachTo(viewPager)
    }
}