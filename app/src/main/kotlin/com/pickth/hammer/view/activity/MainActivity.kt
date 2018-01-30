package com.pickth.hammer.view.activity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.pickth.hammer.R
import com.pickth.hammer.adapter.MainPagerAdapter
import com.pickth.hammer.listener.CategoryListener
import com.pickth.hammer.view.custom.MyBottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_view.*
import org.jetbrains.anko.toast

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

        initLayout()

        mMainPagerAdapter = MainPagerAdapter(supportFragmentManager).apply {
            for (i in 0..3)
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

        ll_nav_item.setOnClickListener {

        }
    }

    private fun initLayout() {
        // actionbar
        setSupportActionBar(main_toolbar)
        supportActionBar?.run {
            setDisplayShowTitleEnabled(true)
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
        mNavigation = main_navigation.apply {
            setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        }

        var titles = ArrayList<String>().apply {
            add("패션의류/잡화")
            add("뷰티")
            add("식품")
        }

        var item0 = ArrayList<String>().apply {
            add("여성패션")
            add("남성패션")
            add("베이비여아")
            add("베이비남아")
            add("키즈여아")
            add("키즈남아")
            add("주닝너여아")
            add("주니어남아")
        }
        var item1 = ArrayList<String>().apply {
            add("유기농/친환경 전문관")
            add("스킨케어")
            add("메이크업")
            add("향수")
            add("헤어")
            add("바디")
            add("네일")
            add("뷰티소품")
            add("남성화장품")
            add("명품화장품")
            add("더모코스메틱")
            add("선물세트/키트")
            add("로드샵")
        }
        var item2 = ArrayList<String>().apply {
            add("선물세트관")
            add("유기농/친환경 전문관")
            add("과일")
            add("견과/건과")
            add("채소")
            add("쌀/잡곡")
            add("축산/계란")
            add("수산물/건어물")
            add("생수/음료")
            add("커피/원두/차")
            add("과자/간식")
            add("면/통조림/가공식품")
            add("가루/조미료/오일")
            add("장/소스/드레싱/식초")
            add("유제품/아이스크림")
            add("냉장/냉동/간편식")
            add("건강식품")
            add("분유/어린이식품")
            add("수입식품 전문관")
            add("대용량 전문관")
        }
        rv_category.run {
            setItemViewId(R.layout.category_item)
            setCategoryListener(object: CategoryListener {
                override fun onClickItem(categoryPosition: Int, itemPosition: Int) {
//                    toast("${titles[categoryPosition]} ${item0[itemPosition]}을 누르셨습니다")
                }
            })

            addCategory(titles[0], item0)
            addCategory(titles[1], item1)
            addCategory(titles[2], item2)
        }
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

        when (item?.itemId) {
            android.R.id.home -> {
                dl_main.openDrawer(GravityCompat.START)
            }
        }

        return super.onOptionsItemSelected(item)
    }
}
