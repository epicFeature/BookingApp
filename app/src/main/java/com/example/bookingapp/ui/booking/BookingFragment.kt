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
import androidx.core.view.children
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.bookingapp.R
import com.example.bookingapp.databinding.FragmentBookingBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

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
        emailIsValid()
        phoneIsValid()
        binding.payButton.setOnClickListener {
            val allNewFieldsFilled = if (binding.blankLayoutForNewTourist.childCount > 0) {
                allNewTouristsFieldsFilled()
            } else {
                true
            }
            val firstTFilled = firstTouristFieldsFilled()
            if (allNewFieldsFilled && emailIsValid() && phoneIsValid() && firstTFilled) {
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
        binding.blankLayoutForNewTourist.children.forEach { v ->
             v.findViewById<ImageView>(R.id.hide_show_tourist_button)
                .setOnClickListener {
                    val touristUnfold = it.findViewById<LinearLayout>(R.id.tourist_sample_unfold)
                    if (touristUnfold.visibility == View.VISIBLE) {
                        touristUnfold.visibility = View.GONE
                        v.findViewById<ImageView>(R.id.hide_show_tourist_button).setImageResource(R.drawable.blue_arrow_down_button)
                    } else {
                        touristUnfold.visibility = View.VISIBLE
                        v.findViewById<ImageView>(R.id.hide_show_tourist_button).setImageResource(R.drawable.blue_arrow_up_button)
                    }
                }
        }
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

    private fun firstTouristFieldsFilled(): Boolean {
        val allFilled = mutableListOf<Boolean>()
        if (TextUtils.isEmpty(binding.textInputTourist1Name.text.toString())) {
            binding.textInputTourist1Name.setBackgroundResource(R.drawable.edit_text_mistake)
            allFilled.add(false)
        } else binding.textInputTourist1Name.setBackgroundResource(R.drawable.edit_text_background)
        if (TextUtils.isEmpty(binding.textInputTourist1Surname.text.toString())) {
            binding.textInputTourist1Surname.setBackgroundResource(R.drawable.edit_text_mistake)
            allFilled.add(false)
        } else binding.textInputTourist1Surname.setBackgroundResource(R.drawable.edit_text_background)
        if (TextUtils.isEmpty(binding.textInputTourist1BirthDate.text.toString())) {
            binding.textInputTourist1BirthDate.setBackgroundResource(R.drawable.edit_text_mistake)
            allFilled.add(false)
        } else binding.textInputTourist1BirthDate.setBackgroundResource(R.drawable.edit_text_background)
        if (TextUtils.isEmpty(binding.textInputTourist1Citizenship.text.toString())) {
            binding.textInputTourist1Citizenship.setBackgroundResource(R.drawable.edit_text_mistake)
            allFilled.add(false)
        } else binding.textInputTourist1Citizenship.setBackgroundResource(R.drawable.edit_text_background)
        if (TextUtils.isEmpty(binding.textInputTourist1Passport.text.toString())) {
            binding.textInputTourist1Passport.setBackgroundResource(R.drawable.edit_text_mistake)
            allFilled.add(false)
        } else binding.textInputTourist1Passport.setBackgroundResource(R.drawable.edit_text_background)
        if (TextUtils.isEmpty(binding.textInputTourist1PassportValidity.text.toString())) {
            binding.textInputTourist1PassportValidity.setBackgroundResource(R.drawable.edit_text_mistake)
            allFilled.add(false)
        } else binding.textInputTourist1PassportValidity.setBackgroundResource(R.drawable.edit_text_background)
        return if (allFilled.contains(false)) {
            Toast.makeText(
                this.requireContext(),
                "Пожалуйста, заполните все поля.",
                Toast.LENGTH_SHORT
            ).show()
            false
        } else true
    }

    private fun allNewTouristsFieldsFilled(): Boolean {
        val allFilled = mutableListOf<Boolean>()
        val count = binding.blankLayoutForNewTourist.childCount
        var v: View?
        var vName: TextInputEditText
        var vSurname: TextInputEditText
        var vBd: TextInputEditText
        var vCitizen: TextInputEditText
        var vPassport: TextInputEditText
        var vPasValidity: TextInputEditText
        for (i in 0 until count) {
            v = binding.blankLayoutForNewTourist.getChildAt(i)
            vName = v.findViewById<TextInputEditText>(R.id.text_input_layout_name)
            vSurname = v.findViewById<TextInputEditText>(R.id.text_input_layout_surname)
            vBd = v.findViewById<TextInputEditText>(R.id.text_input_layout_birth_date)
            vCitizen = v.findViewById<TextInputEditText>(R.id.text_input_layout_citizenship)
            vPassport = v.findViewById<TextInputEditText>(R.id.text_input_layout_passport)
            vPasValidity =
                v.findViewById<TextInputEditText>(R.id.text_input_layout_passport_validity)
            if (TextUtils.isEmpty(vName.text.toString())) {
                vName.setBackgroundResource(R.drawable.edit_text_mistake)
                allFilled.add(false)
            } else vName.setBackgroundResource(R.drawable.edit_text_background)
            if (TextUtils.isEmpty(vSurname.text.toString())) {
                vSurname.setBackgroundResource(R.drawable.edit_text_mistake)
                allFilled.add(false)
            } else vSurname.setBackgroundResource(R.drawable.edit_text_background)
            if (TextUtils.isEmpty(vBd.text.toString())) {
                vBd.setBackgroundResource(R.drawable.edit_text_mistake)
                allFilled.add(false)
            } else vBd.setBackgroundResource(R.drawable.edit_text_background)
            if (TextUtils.isEmpty(vCitizen.text.toString())) {
                vCitizen.setBackgroundResource(R.drawable.edit_text_mistake)
                allFilled.add(false)
            } else vCitizen.setBackgroundResource(R.drawable.edit_text_background)
            if (TextUtils.isEmpty(vPassport.text.toString())) {
                vPassport.setBackgroundResource(R.drawable.edit_text_mistake)
                allFilled.add(false)
            } else vPassport.setBackgroundResource(R.drawable.edit_text_background)
            if (TextUtils.isEmpty(vPasValidity.text.toString())) {
                vPasValidity.setBackgroundResource(R.drawable.edit_text_mistake)
                allFilled.add(false)
            } else vPasValidity.setBackgroundResource(R.drawable.edit_text_background)
        }
        return if (allFilled.contains(false)) {
            Toast.makeText(
                this.requireContext(),
                "Пожалуйста, заполните все поля.",
                Toast.LENGTH_SHORT
            ).show()
            false
        } else true
    }

    private fun phoneIsValid(): Boolean {
        var valid = true
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
                binding.phoneTextInputContainer.helperText = ""
            }
        }
        return valid
    }

    private fun emailIsValid(): Boolean {
        var valid = true
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
                binding.emailTextInputContainer.helperText = ""
            }
        }
        return valid
    }
}