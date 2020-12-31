package com.example.cs496_week1.fragments.album_fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class FullViewPagerAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager) {
    private val items = ArrayList<Fragment>()
    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): Fragment {
        return items[position]
    }

    fun updateFragment(items: List<Fragment>) {
        this.items.addAll(items)
    }
}