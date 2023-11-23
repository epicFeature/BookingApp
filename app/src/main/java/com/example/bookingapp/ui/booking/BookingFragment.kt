package com.example.bookingapp.ui.booking

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.bookingapp.R
import com.example.bookingapp.databinding.FragmentBookingBinding
import com.google.android.material.textfield.TextInputEditText

class BookingFragment : Fragment() {
    private lateinit var blankLayout: LinearLayout
    private lateinit var bookingMapper: BookingMapper
    private lateinit var viewModel: BookingFragmentViewModel
    private var _binding: FragmentBookingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookingBinding.inflate(inflater, container, false)
        bookingMapper = BookingMapper(binding)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        val emailIsValid = emailIsValid()
        val phoneIsValid = phoneIsValid()
        binding.payButton.setOnClickListener {
            allNewTouristsFieldsFilled()
            if (allNewTouristsFieldsFilled() && emailIsValid && phoneIsValid) {
                findNavController().navigate(R.id.action_bookingFragment_to_paidFragment)
            }
        }
        binding.backButtonToHotelRooms.setOnClickListener {
            findNavController().navigate(R.id.action_bookingFragment_to_hotelRoomsFragment)
        }
        binding.hideShowTouristButton.setOnClickListener {
            hideShowTourist()
        }
        binding.addTouristButton.setOnClickListener {
            addTourist()
        }
        hideShowNewTourists()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[BookingFragmentViewModel::class.java]
        viewModel.bookingLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                bookingMapper.setBookingFragmentStaticData(it)
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

    private fun hideShowTourist() {
        val touristUnfold = binding.tourist1Unfold
        val hideShowTouristButton = binding.hideShowTouristButton
        if (touristUnfold.visibility == View.VISIBLE) {
            touristUnfold.visibility = View.GONE
            hideShowTouristButton.setImageResource(R.drawable.blue_arrow_down_button)
        } else {
            touristUnfold.visibility = View.VISIBLE
            hideShowTouristButton.setImageResource(R.drawable.blue_arrow_up_button)
        }
    }

    private fun hideShowNewTourists() {
        val count = binding.blankLayoutForNewTourist.childCount
        var v: View?
        var hideShowTouristButton: ImageView
        var touristUnfold: LinearLayout
        for (i in 0 until count) {
            v = binding.blankLayoutForNewTourist.getChildAt(i)
            hideShowTouristButton = v.findViewById(R.id.hide_show_tourist_button)
            touristUnfold = v.findViewById(R.id.tourist_sample_unfold)
            hideShowTouristButton.setOnClickListener {
                if (touristUnfold.visibility == View.VISIBLE) {
                    touristUnfold.visibility = View.GONE
                    hideShowTouristButton.setImageResource(R.drawable.blue_arrow_down_button)
                } else {
                    touristUnfold.visibility = View.VISIBLE
                    hideShowTouristButton.setImageResource(R.drawable.blue_arrow_up_button)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun addTourist() {
        val numberNames = listOf<String>("Первый", "Второй", "Третий", "Четвёртый", "Пятый")
        val inflater =
            LayoutInflater.from(this.requireContext()).inflate(R.layout.new_tourist_layout, null)

        binding.blankLayoutForNewTourist.addView(
            inflater,
            binding.blankLayoutForNewTourist.childCount
        )
        val childCount = binding.blankLayoutForNewTourist.childCount

        val view = binding.blankLayoutForNewTourist.getChildAt(childCount - 1)

        view.findViewById<TextView>(R.id.tourist_sample_info_title).text =
            "${numberNames[childCount]} турист"
    }

    private fun allNewTouristsFieldsFilled(): Boolean {
        return true
        /*val count = binding.blankLayoutForNewTourist.childCount
        val v: View?
        var vName: TextInputEditText
        var vSurname: TextInputEditText
        va
        for (i in 0 until count) {
            v = binding.blankLayoutForNewTourist.getChildAt(i)

            return when {
                TextUtils.isEmpty(v.findViewById<TextInputEditText>(R.id.text_input_layout_name)
                    .text.toString()) -> .setBackgroundResource(R.drawable.edit_text_mistake)
            }
        }*/
    }

    private fun showErrors(editText: TextInputEditText) {
        editText.setBackgroundResource(R.drawable.edit_text_mistake)
        Toast.makeText(
            this.requireContext(),
            "Пожалуйста, заполните все поля.",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun phoneIsValid(): Boolean {
        var valid = false
        binding.phoneTextInput.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val phoneText = binding.phoneTextInput.text.toString()
                valid = if (!phoneText.matches(".*[0-9]*".toRegex()) || phoneText.length != 10) {
                    binding.phoneTextInput.setBackgroundResource(R.drawable.edit_text_mistake)
                    binding.phoneTextInputContainer.helperText = "Некоректный номер телефона"
                    false
                } else {
                    binding.phoneTextInput.setBackgroundResource(R.drawable.edit_text_background)
                    true
                }
            } else {
                binding.phoneTextInput.setBackgroundResource(R.drawable.edit_text_background)
            }
        }
        return valid
    }

    private fun emailIsValid(): Boolean {
        var valid = false
        binding.emailTextInput.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val emailText = binding.emailTextInput.text.toString()
                valid = if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
                    binding.emailTextInput.setBackgroundResource(R.drawable.edit_text_mistake)
                    binding.emailTextInputContainer.helperText =
                        "Некорректный адрес электронной почты"
                    false
                } else {
                    binding.emailTextInput.setBackgroundResource(R.drawable.edit_text_background)
                    true
                }
            } else {
                binding.emailTextInput.setBackgroundResource(R.drawable.edit_text_background)
            }
        }
        return valid
    }
}