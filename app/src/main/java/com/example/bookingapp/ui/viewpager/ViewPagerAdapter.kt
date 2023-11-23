package com.example.bookingapp.ui.viewpager

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.example.bookingapp.R

class ViewPagerAdapter(
    private val context: Context,
    private val imageList: List<String>,
) : PagerAdapter() {
    override fun getCount(): Int = imageList.size

    override fun isViewFromObject(view: View, `object`: Any) =
        view === `object` as ConstraintLayout

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view: View =
            (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
                R.layout.view_pager_item,
                null
            )
        val ivImages = view.findViewById<ImageView>(R.id.view_pager_image)

        imageList[position].let {
            Glide.with(context)
                .load(it)
                .centerCrop()
                .placeholder(R.drawable.image_placeholder)
                .into(ivImages)
        }

        val vp = container as ViewPager
        vp.addView(view,0)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val vp = container as ViewPager
        val view =`object` as View
        vp.removeView(view)
    }
}