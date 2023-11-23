package com.example.bookingapp.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.bookingapp.R
import com.example.bookingapp.api.hotel.HotelRepository
import com.example.bookingapp.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private lateinit var hotelMapper:HotelMapper
    private lateinit var viewModel: MainViewModel
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        hotelMapper=HotelMapper(binding, this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        binding.toChooseRoomScreenButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_hotelRoomsFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.hotelLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                hotelMapper.setMainFragmentData(it)
            } else {
                Toast.makeText(
                    this.requireContext(),
                    "Ой, что-то пошло не так, попробуйте позже.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        viewModel.makeApiCall()
    }

    /*private fun imageSlider() {
        val tempImages = listOf(
            "https://cdn.iportal.ru/news/2020/99/preview/d23ee289e327bed84b1abd98e409696b0f1d05b9_1920_1280_c.jpg",
            "https://q.bstatic.com/xdata/images/hotel/max1024x768/267647265.jpg?k=c8233ff42c39f9bac99e703900a866dfbad8bcdd6740ba4e594659564e67f191&o=",
            "https://worlds-trip.ru/wp-content/uploads/2022/10/white-hills-resort-5.jpeg"
        )
        val dotsIndicator = binding.dotsIndicatorHotel
        val viewPager = binding.viewPagerHotel
        val viewPagerAdapter = ViewPagerHotelAdapter(this.requireContext(), tempImages)
        viewPager.adapter = viewPagerAdapter
        dotsIndicator.attachTo(viewPager)
    }
*/

}