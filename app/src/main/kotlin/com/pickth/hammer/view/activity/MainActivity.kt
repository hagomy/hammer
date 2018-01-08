package com.pickth.hammer.view.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.view.ViewPager
import android.view.MenuItem
import com.pickth.hammer.R
import com.pickth.hammer.adapter.MainPagerAdapter
import com.pickth.hammer.view.custom.MyBottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {
    private var mMainPagerAdapter: MainPagerAdapter? = null
    private lateinit var mNavigation: MyBottomNavigationView
    private lateinit var mMenuItem: MenuItem
    private lateinit var prevBottomNavigation: MenuItem
    private var mViewPager: ViewPager? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_festival -> {
                mViewPager?.currentItem = 0
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_chat -> {
                mViewPager?.currentItem = 1
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_alarm -> {
                mViewPager?.currentItem = 2
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_myinfo -> {
                mViewPager!!.currentItem = 3
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // actionbar
        setSupportActionBar(main_toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        title = "GACHI"

        mNavigation = main_navigation
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        mMainPagerAdapter = MainPagerAdapter(supportFragmentManager).apply {
            setListItem(0)
            setListItem(1)
            setListItem(2)
            setListItem(3)
            notifyDataSetChanged()
        }

        mViewPager = view_pager.apply {
            adapter = mMainPagerAdapter
            currentItem = 0
            offscreenPageLimit = 4
            addOnPageChangeListener(this@MainActivity)
        }

        prevBottomNavigation = mNavigation.menu.getItem(0)
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        prevBottomNavigation.isChecked = false
        prevBottomNavigation = mNavigation.menu.getItem(position)
        prevBottomNavigation.isChecked = true
    }

}
