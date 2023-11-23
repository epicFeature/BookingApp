package com.example.bookingapp.ui.paid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.bookingapp.R
import com.example.bookingapp.databinding.FragmentPaidBinding
import kotlin.random.Random

class PaidFragment : Fragment() {

    private var _binding: FragmentPaidBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPaidBinding.inflate(inflater, container, false)
        setUniqueId()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.finishButton.setOnClickListener{
            findNavController().navigate(R.id.action_paidFragment_to_mainFragment)
        }
        binding.backButtonToBooking.setOnClickListener {
            findNavController().navigate(R.id.action_paidFragment_to_bookingFragment)
        }
    }

    private fun setUniqueId(){
        var newOrderNumber: Int = Random.nextInt(1000000)
        var orderNumberwithText = binding.orderNumberWithText.text.toString()
        var newTextWithOrderNumber = orderNumberwithText.replace("order_number", "$newOrderNumber")
        binding.orderNumberWithText.text = newTextWithOrderNumber
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}