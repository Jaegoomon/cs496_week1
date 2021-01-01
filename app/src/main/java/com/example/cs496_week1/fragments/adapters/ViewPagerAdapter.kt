package com.example.cs496_week1.fragments.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cs496_week1.fragments.contact_fragment.ContactInfo
import com.example.cs496_week1.fragments.album_fragment.AlbumFragment
import com.example.cs496_week1.fragments.contact_fragment.ContactFragment
import com.example.cs496_week1.fragments.clip_fragment.ClipFragment

class ViewPageAdpater(fragmentActivity: FragmentActivity, cursor1: ArrayList<ContactInfo>, cursor2: ArrayList<String>) :
    FragmentStateAdapter(fragmentActivity) {
    val fragment1 = ContactFragment(cursor1)
    val fragment2 = AlbumFragment(cursor2)
    val fragment3 = ClipFragment()

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> fragment1
            1 -> fragment2
            2 -> fragment3
            else -> fragment1
        }
    }
}