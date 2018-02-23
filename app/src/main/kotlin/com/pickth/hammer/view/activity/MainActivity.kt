package com.pickth.hammer.view.activity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.pickth.hammer.R
import com.pickth.hammer.adapter.MainPagerAdapter
import com.pickth.hammer.listener.CategoryListener
import com.pickth.hammer.view.custom.MyBottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_view.*
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import com.pickth.hammer.util.UserInfoManager
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {
  val TAG = javaClass.simpleName
  private var mMainPagerAdapter: MainPagerAdapter? = null
  private lateinit var mNavigation: MyBottomNavigationView
  private lateinit var mMenuItem: MenuItem
  private lateinit var prevBottomNavigation: MenuItem
  private lateinit var mViewPager: ViewPager

  private lateinit var mDatabase: FirebaseDatabase
  private var mCategoryCodes = ArrayList<ArrayList<String>>()

  private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
    when (item.itemId) {
      R.id.navigation_home -> {
        mViewPager.currentItem = 0
        return@OnNavigationItemSelectedListener true
      }
      R.id.navigation_chat -> {
        mViewPager.currentItem = 1
        return@OnNavigationItemSelectedListener true
      }
      R.id.navigation_alarm -> {
        mViewPager.currentItem = 2
        return@OnNavigationItemSelectedListener true
      }
      R.id.navigation_myinfo -> {
        mViewPager.currentItem = 3
        return@OnNavigationItemSelectedListener true
      }
    }
    false
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    mDatabase = FirebaseDatabase.getInstance()
    initLayout()
    getCategory()

    mMainPagerAdapter = MainPagerAdapter(supportFragmentManager).apply {
      for(i in 0..3) setListItem(i)
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

  private fun getCategory() {
    val myRef = mDatabase.getReference("category")
    myRef.addValueEventListener(object : ValueEventListener {
      override fun onDataChange(dataSnapshot: DataSnapshot) {
        rv_category.clearCategory()
        mCategoryCodes.clear()

        for(data: DataSnapshot in dataSnapshot.children) {
          val categories = ArrayList<String>()
          val categoryCodes = ArrayList<String>()

          for(item: DataSnapshot in data.children) {
            categories.add(item.value as String)
            categoryCodes.add(item.key)
          }

          rv_category.addCategory(data.key, categories)
          mCategoryCodes.add(categoryCodes)
        }
      }

      override fun onCancelled(error: DatabaseError) {
        // Failed to read value
        Log.w(TAG, "Failed to read value.", error.toException())
      }
    }
    )
  }

  private fun initLayout() {
    // actionbar
    setSupportActionBar(main_toolbar)
    supportActionBar?.run {
      setDisplayShowTitleEnabled(true)
    }
    title = getString(R.string.app_name)

    // side navigation drawer
    val mDrawerToggle = ActionBarDrawerToggle(this, dl_main, main_toolbar, R.string.nav_open, R.string.nav_close)
    dl_main.addDrawerListener(mDrawerToggle)
    mDrawerToggle.syncState()

    // bottom navigation
    mNavigation = main_navigation.apply {
      setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    txtNavUserNickName.text = FirebaseAuth.getInstance().currentUser?.email

    rv_category.run {
      setItemViewId(R.layout.item_category)
      setCategoryListener(object : CategoryListener {
        override fun onClickItem(categoryPosition: Int, itemPosition: Int) {
//          toast("${mCategoryCodes[categoryPosition][itemPosition]}을 누르셨습니다")
          startActivity<WriteGoodsActivity>()
        }
      }
      )

    }

    tv_sign_out.setOnClickListener {
      UserInfoManager.clearUserInfo(this)
      FirebaseAuth.getInstance().signOut()
      startActivity<SignInActivity>()
      finish()
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

  override fun onBackPressed() {

    if(dl_main.isDrawerOpen(GravityCompat.START)) {
      dl_main.closeDrawer(GravityCompat.START)
      return
    }

    super.onBackPressed()
  }
}
