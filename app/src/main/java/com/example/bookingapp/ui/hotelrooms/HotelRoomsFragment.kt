package com.example.bookingapp.ui.hotelrooms

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookingapp.R
import com.example.bookingapp.api.hotel.HotelRepository
import com.example.bookingapp.api.hotelrooms.HotelRoomsRepository
import com.example.bookingapp.api.hotelrooms.Room
import com.example.bookingapp.databinding.FragmentHotelRoomsBinding
import com.example.bookingapp.databinding.RoomRecyclerViewItemBinding
import com.example.bookingapp.ui.main.MainViewModel

class HotelRoomsFragment : Fragment() {
    private lateinit var viewModel: HotelRoomsViewModel
    private lateinit var recyclerAdapter: RecyclerViewHotelRoomsAdapter

    private var _binding: FragmentHotelRoomsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHotelRoomsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initRecyclerView()
        binding.backButtonToHotel.setOnClickListener {
            findNavController().navigate(R.id.action_hotelRoomsFragment_to_mainFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecyclerView() {
        binding.roomRecyclerView.layoutManager = LinearLayoutManager(this.requireContext())
        recyclerAdapter = RecyclerViewHotelRoomsAdapter { onClick() }
        binding.roomRecyclerView.adapter = recyclerAdapter
    }

    private fun onClick() {
        findNavController().navigate(
            R.id.action_hotelRoomsFragment_to_bookingFragment
        )
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[HotelRoomsViewModel::class.java]
        viewModel.hotelRoomsLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                recyclerAdapter.setData(it)
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

}