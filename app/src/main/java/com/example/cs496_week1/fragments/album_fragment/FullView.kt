package com.example.cs496_week1.fragments.album_fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.cs496_week1.R

class FullView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_view)

        val position = intent.getIntExtra("position", 0)
        val group = intent.getStringArrayListExtra("uri_group")
        val fragments = ArrayList<Fragment>()
        if (group != null) {
            for (i in group) {
                fragments.add(FullViewFragment.newInstance(i))
            }
        }

        val viewPager: CustomViewPager = findViewById(R.id.full_pager)
        val adapter = FullViewPagerAdapter(supportFragmentManager)
        adapter.updateFragment(fragments)
        viewPager.adapter = adapter
        viewPager.currentItem = position
    }
}