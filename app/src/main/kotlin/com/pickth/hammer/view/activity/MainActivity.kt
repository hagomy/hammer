package com.pickth.hammer.view.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.ActionBarDrawerToggle
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
            R.id.navigation_home -> {
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
        supportActionBar?.run {
            setDisplayShowTitleEnabled(true)

            // icon
            setHomeAsUpIndicator(R.drawable.ic_myinfo)
            setDisplayHomeAsUpEnabled(true)
        }
        title = getString(R.string.app_name)

        // side navigation drawer
        val mDrawerToggle = ActionBarDrawerToggle(this,
                dl_main,
                main_toolbar,
                R.string.nav_open,
                R.string.nav_close)
        dl_main.addDrawerListener(mDrawerToggle)
        mDrawerToggle.syncState()

        // bottom navigation
        mNavigation = main_navigation
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        mMainPagerAdapter = MainPagerAdapter(supportFragmentManager).apply {
            for(i in 0..3)
                setListItem(i)
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

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item?.itemId) {
            android.R.id.home -> {
                dl_main.openDrawer(GravityCompat.START)
            }
        }

        return super.onOptionsItemSelected(item)
    }
}
