package com.example.cs496_week1.fragments.adapters

import android.database.Cursor
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cs496_week1.fragments.AlbumFragment
import com.example.cs496_week1.fragments.ContactFragment
import com.example.cs496_week1.fragments.SettingsFragment

class ViewPageAdpater(fragmentActivity: FragmentActivity, cursor: ArrayList<ArrayList<String>>) :
    FragmentStateAdapter(fragmentActivity) {
    val fragment1 = ContactFragment(cursor)
    val fragment2 = AlbumFragment()
    val fragment3 = SettingsFragment()

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