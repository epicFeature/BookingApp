package com.example.bookingapp.ui.сustomview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.bookingapp.databinding.NewTouristLayoutBinding

class TouristInfoCustom(context: Context, attributeSet: AttributeSet) :
    LinearLayout(context, attributeSet) {
    private var binding: NewTouristLayoutBinding =
        NewTouristLayoutBinding.inflate(LayoutInflater.from(context), this, false)

    // init реализация при создании

            // добавить функции на обработку edit text
    //изучить кастомизацию edittext из кода

    //продумать связь с recycler view
}