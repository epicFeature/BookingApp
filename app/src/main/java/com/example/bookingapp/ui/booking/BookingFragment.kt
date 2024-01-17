package com.example.bookingapp.ui.booking

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.bookingapp.R
import com.example.bookingapp.databinding.FragmentBookingBinding
import com.santalu.maskedittext.MaskEditText
import java.util.Calendar

class BookingFragment : Fragment() {
    private lateinit var bookingMapper: BookingMapper
    private lateinit var viewModel: BookingFragmentViewModel
    private var _binding: FragmentBookingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookingBinding.inflate(inflater, container, false)
        bookingMapper = BookingMapper(binding)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()

        binding.backButtonToHotelRooms.setOnClickListener {
            findNavController().navigate(R.id.action_bookingFragment_to_hotelRoomsFragment)
        }

        binding.hideShowTouristButton.setOnClickListener {
            setTouristFieldsVisibility()
        }

        binding.phoneTextInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                payButtonEnableIfAllFieldsNotEmpty()
            }
        })

        binding.emailTextInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                payButtonEnableIfAllFieldsNotEmpty()
            }
        })

        binding.textInputTouristName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                payButtonEnableIfAllFieldsNotEmpty()
            }
        })

        binding.textInputTouristSurname.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                payButtonEnableIfAllFieldsNotEmpty()
            }
        })

        binding.textInputTouristBirthDate.setOnClickListener {
            addPopUpCalendar(binding.textInputTouristBirthDate)
        }

        binding.textInputTouristBirthDate.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                payButtonEnableIfAllFieldsNotEmpty()
            }
        })

        binding.textInputTouristCitizenship.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                payButtonEnableIfAllFieldsNotEmpty()
            }
        })

        binding.textInputTouristPassport.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                payButtonEnableIfAllFieldsNotEmpty()
            }
        })

        binding.textInputTouristPassportExpiration.setOnClickListener {
            addPopUpCalendar(binding.textInputTouristPassportExpiration)
        }

        binding.textInputTouristPassportExpiration.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                payButtonEnableIfAllFieldsNotEmpty()
            }
        })

        binding.payButton.isEnabled = false
        binding.payButton.setOnClickListener {
            onPayButtonClickCheck()
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

    //show hide tourist info
    private fun setTouristFieldsVisibility() {
        if (binding.touristUnfold.visibility == View.VISIBLE) hideTouristFields()
        else showTouristFields()
    }

    private fun showTouristFields() {
        binding.touristUnfold.visibility = View.VISIBLE
        binding.hideShowTouristButton.setImageResource(R.drawable.blue_arrow_up_button)
    }

    private fun hideTouristFields() {
        binding.touristUnfold.visibility = View.GONE
        binding.hideShowTouristButton.setImageResource(R.drawable.blue_arrow_down_button)
    }

    // customer info validation

    private fun emailValidation(): Boolean {
        return if (android.util.Patterns.EMAIL_ADDRESS.matcher(binding.emailTextInput.text.toString())
                .matches()
        ) {
            true
        } else {
            binding.emailTextInput.error = resources.getText(R.string.email_incorrect)
            false
        }
    }

    //tourist validation

    private fun addPopUpCalendar(editText: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(
            this.requireContext(),
            R.style.MyDatePickerStyle,
            { _, year, monthOfYear, dayOfMonth ->
                var _month = if (monthOfYear < 10) {
                    "0" + (monthOfYear + 1).toString()
                } else (monthOfYear + 1).toString()
                var _day = if (dayOfMonth < 10) {
                    "0$dayOfMonth"
                } else dayOfMonth.toString()

                val date = ("$_day.$_month.$year")
                editText.setText(date)
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    private fun allFieldsValid(): Boolean {
        val phoneValid = textFieldMaskLengthValidation(
            binding.phoneTextInput,
            10,
            R.string.phone_number_incorrect
        )
        val emailValid = emailValidation()
        val nameValid = textFieldBasicValidation(
            binding.textInputTouristName,
            R.string.write_name
        )
        val surnameValid =
            textFieldBasicValidation(
                binding.textInputTouristSurname,
                R.string.write_surname
            )
        val birthDateValid =
            textFieldBasicValidation(
                binding.textInputTouristBirthDate,
                R.string.write_birth_date
            )
        val citizenshipValid = textFieldBasicValidation(
            binding.textInputTouristCitizenship,
            R.string.write_citizenship
        )
        val passportValid = textFieldMaskLengthValidation(
            binding.textInputTouristPassport,
            9,
            R.string.write_passport_number
        )
        val passportExpireDateValid = textFieldBasicValidation(
            binding.textInputTouristPassportExpiration,
            R.string.write_passport_expire_date
        )
        return (phoneValid
                && emailValid
                && nameValid
                && surnameValid
                && birthDateValid
                && citizenshipValid
                && passportValid
                && passportExpireDateValid)
    }

    private fun allFieldsNotEmpty(): Boolean {
        return !(binding.phoneTextInput.text.isNullOrBlank()
                || binding.emailTextInput.text.isNullOrBlank()
                || binding.textInputTouristName.text.isNullOrBlank()
                || binding.textInputTouristSurname.text.isNullOrBlank()
                || binding.textInputTouristBirthDate.text.isNullOrBlank()
                || binding.textInputTouristCitizenship.text.isNullOrBlank()
                || binding.textInputTouristPassport.text.isNullOrBlank()
                || binding.textInputTouristPassportExpiration.text.isNullOrBlank())
    }

    private fun payButtonEnableIfAllFieldsNotEmpty() {
        binding.payButton.isEnabled = allFieldsNotEmpty()
    }

    private fun onPayButtonClickCheck() {
        if (allFieldsValid()) {
            findNavController().navigate(R.id.action_bookingFragment_to_paidFragment)
        }
    }

    private fun textFieldBasicValidation(editText: EditText, @StringRes errorText: Int): Boolean {
        return if (!editText.text.isNullOrBlank()) {
            true
        } else {
            editText.error = resources.getText(errorText)
            false
        }
    }

    private fun textFieldMaskLengthValidation(
        maskEditText: MaskEditText,
        lengthRequired: Int,
        @StringRes errorText: Int
    ): Boolean {
        return if (
            (maskEditText.rawText.length == lengthRequired)
        ) {
            true
        } else {
            maskEditText.error = resources.getText(errorText)
            false
        }

    }

}