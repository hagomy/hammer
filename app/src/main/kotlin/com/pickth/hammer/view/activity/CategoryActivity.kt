/*
 * Copyright 2017 Yonghoon Kim
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pickth.hammer.view.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.google.firebase.database.*
import com.pickth.hammer.R
import com.pickth.hammer.adapter.GoodsItemAdapter
import com.pickth.hammer.extensions.getCategoryName
import com.pickth.hammer.extensions.getGoods
import com.pickth.hammer.listener.GoodsItemTouchListener
import kotlinx.android.synthetic.main.activity_category.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast

/**
 * Created by yonghoon on 2018-02-26
 * Blog   : http://blog.pickth.com
 */

class CategoryActivity: AppCompatActivity() {

  val TAG = javaClass.simpleName
  val WRITE_GOODS = 78

  private var mCategoryCode = ""
  private var mCategoryName = ""

  // adapter
  private lateinit var mGoodsItemAdapter: GoodsItemAdapter

  // database
  private lateinit var mCategoryReference: DatabaseReference

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_category)

    mCategoryCode = intent.getStringExtra("code")

    mCategoryReference = FirebaseDatabase.getInstance()
        .reference
        .child("category")
        .child(((mCategoryCode.toInt()+1)/100).toString())

    mCategoryReference.addListenerForSingleValueEvent(object: ValueEventListener {
      override fun onDataChange(p0: DataSnapshot?) {
        Log.d(TAG, p0?.value.toString())
        if(p0 == null) {
          toast("잘못된 접근입니다.")
          finish()
          return
        }
        mCategoryName = p0.getCategoryName(mCategoryCode)
        Log.d(TAG, "category code: ${mCategoryCode}, category name : ${mCategoryName}")
        if(mCategoryName == "") {
          toast("잘못된 접근입니다.")
          finish()
          return
        }

        tv_category_name.text = mCategoryName
      }
      override fun onCancelled(p0: DatabaseError?) {
      }
    })

    // actionbar
    setSupportActionBar(toolbar_category)
    supportActionBar?.run {
      setDisplayShowTitleEnabled(false)

      // icon
      setHomeAsUpIndicator(R.drawable.ic_back)
      setDisplayHomeAsUpEnabled(true)
    }

    fab.setOnClickListener {
      startActivityForResult<WriteGoodsActivity>(WRITE_GOODS,"code" to mCategoryCode, "name" to mCategoryName)
    }

    mGoodsItemAdapter = GoodsItemAdapter().apply {
      setItemTouchListener(object : GoodsItemTouchListener {
        override fun onClick(position: Int) {
          startActivity<DetailActivity>("id" to mGoodsItemAdapter.getItem(position).id, "code" to mCategoryCode)
        }

      })
    }

    recyclerview_category.run {
      adapter = mGoodsItemAdapter
      layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }


    getItemList(mCategoryCode)
  }

  private fun init() {
    linear_not_exist_item.visibility = View.GONE
    mGoodsItemAdapter.clearItems()
    mGoodsItemAdapter.notifyDataSetChanged()
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)

    when(requestCode) {
      WRITE_GOODS -> {
        Log.d(TAG, "resultcode : $resultCode")

        if(resultCode == Activity.RESULT_OK) {
          init()
          getItemList(mCategoryCode)
        }
      }
    }
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      android.R.id.home -> {
        finish()
      }
    }
    return super.onOptionsItemSelected(item)
  }

  private fun getItemList(categoryCode: String) {
    val mItemReference = FirebaseDatabase.getInstance().reference
        .child("goods")
        .child(categoryCode)
        .orderByChild("regDate")
        .addListenerForSingleValueEvent(object: ValueEventListener {
          override fun onDataChange(p0: DataSnapshot?) {
            Log.d(TAG, "get item lis : ${p0?.toString()}")
            if(p0 != null) {
              if(p0.value != null) {
                for(itemSnapshot in p0.children) {
                  itemSnapshot.getGoods()?.let {
                    mGoodsItemAdapter.addItemAtFirst(it)
                    mGoodsItemAdapter.notifyItemInserted(0)
                  }
                }
              } else {
                linear_not_exist_item.visibility = View.VISIBLE
              }

            }

          }
          override fun onCancelled(p0: DatabaseError?) {
            p0?.toException()?.printStackTrace()
          }
        })


  }
}