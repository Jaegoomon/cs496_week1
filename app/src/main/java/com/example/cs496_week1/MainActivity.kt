package com.example.cs496_week1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.cs496_week1.fragments.adapters.ViewPageAdpater
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private val tabTextList = arrayListOf("연락처", "사진", "몰라")
    private val tabIconList = arrayListOf(
        R.drawable.ic_baseline_phone_24,
        R.drawable.ic_baseline_photo_24,
        R.drawable.ic_baseline_question_answer_24
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpTabs()
    }

    private fun setUpTabs() {
        val pager2: ViewPager2 = findViewById(R.id.viewPager2)
        val tabs: TabLayout = findViewById(R.id.tabs)

        pager2.adapter = ViewPageAdpater(this@MainActivity)
        TabLayoutMediator(tabs, pager2) { tab, position ->
            tab.setIcon(tabIconList[position])
            tab.text = tabTextList[position]
        }.attach()
    }
}