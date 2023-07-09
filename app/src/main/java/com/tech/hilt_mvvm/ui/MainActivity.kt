package com.tech.hilt_mvvm.ui

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.dev.nestedrecyclerviewexample.model.BottomNavigationColorModel
import com.dev.nestedrecyclerviewexample.model.BottomNavigationModel
import com.google.android.material.navigation.NavigationBarView
import com.tech.hilt_mvvm.R
import com.tech.hilt_mvvm.base.BaseActivity
import com.tech.hilt_mvvm.databinding.ActivityMainBinding
import com.tech.hilt_mvvm.ui.news.NewsFragment
import com.tech.hilt_mvvm.ui.notification.NotificationFragment
import com.tech.hilt_mvvm.ui.weather.WeatherFragment


class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private val bottomNavigationList:ArrayList<BottomNavigationModel> = ArrayList()
    private val bottomNavigationColorList:ArrayList<BottomNavigationColorModel> = ArrayList()
    private lateinit var newsFragment: NewsFragment
    private lateinit var weatherFragment: WeatherFragment
    private lateinit var notificationFragment: NotificationFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initUI()
    }

    private fun initUI() {
        newsFragment = NewsFragment()
        weatherFragment = WeatherFragment()
        notificationFragment = NotificationFragment()

        setCurrentFragment(newsFragment)
        setBottomNavigationItemIconAndTitle()

        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                0 -> {
                    setCurrentFragment(newsFragment)
                }
                1 -> {
                    setCurrentFragment(weatherFragment)
                }
                2 -> {
                    setCurrentFragment(notificationFragment)
                }
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container, fragment)
            commit()
        }
    }


    private fun setBottomNavigationItemIconAndTitle() {
        // bottom navigation add dynamic  title and icon
        if(bottomNavigationList.size == 0) {
            bottomNavigationList.add(BottomNavigationModel(0, "Home", R.drawable.ic_baseline_home_24))
            bottomNavigationList.add(BottomNavigationModel(1, "Live Tv", R.drawable.ic_baseline_live_tv_24))
            bottomNavigationList.add(BottomNavigationModel(2, "Profile", R.drawable.ic_baseline_profile_24))
        }
        for (i in 0 until bottomNavigationList.size) {
            binding.bottomNavigation.menu.add(Menu.NONE, bottomNavigationList[i].id, Menu.NONE, bottomNavigationList[i].title).setIcon(bottomNavigationList[i].icon)
        }
        setBottomNavigationIconAndTextColorChange()
    }

    @SuppressLint("ResourceAsColor")
    private fun setBottomNavigationIconAndTextColorChange() {
        if (bottomNavigationColorList.size == 0){
            bottomNavigationColorList.add(BottomNavigationColorModel("#000000", "#ffffff"))
        }
        val bottomNavigationItemCheckUncheckedCallBack = arrayOf(intArrayOf(android.R.attr.state_checked), intArrayOf(-android.R.attr.state_checked))
        val checkUncheckedColorList = intArrayOf(Color.parseColor(bottomNavigationColorList[0].selectedColor), Color.parseColor(bottomNavigationColorList[0].unSelectedColor))
        val colorStateList = ColorStateList(bottomNavigationItemCheckUncheckedCallBack, checkUncheckedColorList)
        binding.bottomNavigation.itemTextColor = colorStateList
        binding.bottomNavigation.itemIconTintList = colorStateList
        binding.bottomNavigation.labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_LABELED
    }
}