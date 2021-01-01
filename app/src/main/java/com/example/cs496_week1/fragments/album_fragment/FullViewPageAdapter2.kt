package com.example.cs496_week1.fragments.album_fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class FullViewPageAdapter2(fragmentActivity: FragmentActivity, fragments: ArrayList<Fragment>) :
    FragmentStateAdapter(fragmentActivity) {
    val fragments = fragments

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}