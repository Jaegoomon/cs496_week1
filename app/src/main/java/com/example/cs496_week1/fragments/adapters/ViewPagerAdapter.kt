package com.example.cs496_week1.fragments.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cs496_week1.fragments.AlbumFragment
import com.example.cs496_week1.fragments.ContactFragment
import com.example.cs496_week1.fragments.SettingsFragment

class ViewPageAdpater(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ContactFragment()
            1 -> AlbumFragment()
            2 -> SettingsFragment()
            else -> ContactFragment()
        }
    }
}