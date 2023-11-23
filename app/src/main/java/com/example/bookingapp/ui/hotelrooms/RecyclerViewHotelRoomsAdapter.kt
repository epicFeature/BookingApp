package com.example.bookingapp.ui.hotelrooms

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bookingapp.api.hotelrooms.Room
import com.example.bookingapp.databinding.RoomRecyclerViewItemBinding
import com.example.bookingapp.ui.viewpager.ViewPagerAdapter
import java.util.Locale

class RecyclerViewHotelRoomsAdapter(
    val onClick: () -> Unit
) :
    RecyclerView.Adapter<RecyclerViewHotelRoomsAdapter.HotelRoomsViewHolder>() {

    private var _data: List<Room> = emptyList()
    fun setData(data: List<Room>) {
        _data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HotelRoomsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RoomRecyclerViewItemBinding.inflate(inflater, parent, false)
        return HotelRoomsViewHolder(binding)
    }

    override fun getItemCount(): Int = _data.count()

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        holder: HotelRoomsViewHolder,
        position: Int,
    ) {
        val room = _data[position]
        val context = holder.itemView.context

        with(holder.binding) {
            roomName.text = room.name
            roomSum.text = "${room.price} ₽"
            textForTourWithFlight.text = room.pricePer.toString().lowercase(Locale.ROOT)
            peculiarItemFirst.text = room.peculiarities[0]
            peculiarItemSecond.text = room.peculiarities[1]
            chooseRoomButton.setOnClickListener{
                onClick()
            }
        }
        imageSlider(room, holder.binding, context)
    }

    private fun imageSlider(
        roomsInfo: Room,
        binding: RoomRecyclerViewItemBinding,
        context: Context
    ) {
        val hotelImages = roomsInfo.imageUrls
        val dotsIndicator = binding.dotsIndicatorRoom
        val viewPager = binding.viewPagerRoom
        val viewPagerAdapter = ViewPagerAdapter(context, hotelImages)
        viewPager.adapter = viewPagerAdapter
        dotsIndicator.attachTo(viewPager)
    }

    class HotelRoomsViewHolder(
        val binding: RoomRecyclerViewItemBinding
    ) : RecyclerView.ViewHolder(binding.root)
}