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

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.pickth.hammer.R
import com.pickth.hammer.extensions.getGoods
import com.pickth.hammer.item.Goods
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.toast

/**
 * Created by yonghoon on 2018-01-29
 * Blog   : http://blog.pickth.com
 */

class DetailActivity : AppCompatActivity() {

  val TAG = javaClass.simpleName
  private var mId = ""
  private var mCategoryCode = ""
  private lateinit var mGoodsInfoReference: DatabaseReference

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_detail)

    // actionbar
    setSupportActionBar(detail_toolbar)
    supportActionBar?.run {
      setDisplayShowTitleEnabled(false)

      // icon
      setHomeAsUpIndicator(R.drawable.ic_back_primary)
      setDisplayHomeAsUpEnabled(true)
    }

    mId = intent.getStringExtra("id")
    mCategoryCode = intent.getStringExtra("code")

    getGoodsInfo(mId)

    is_detail.addItems(
        ArrayList<Int>().apply {
          add(R.drawable.e_0)
          add(R.drawable.e_1)
          add(R.drawable.e_2)
          add(R.drawable.e_3)
          add(R.drawable.e_4)
          add(R.drawable.e_5)
          add(R.drawable.e_6)
        }
    )
  }

  private fun getGoodsInfo(id: String) {
    mGoodsInfoReference = FirebaseDatabase.getInstance()
        .reference
        .child("goods")
        .child(mCategoryCode)
        .child(id)

    mGoodsInfoReference.addListenerForSingleValueEvent(object : ValueEventListener {
      override fun onDataChange(p0: DataSnapshot?) {
        bindGoodsInfo(p0?.getGoods())
      }

      override fun onCancelled(p0: DatabaseError?) {
      }

    })
  }

  private fun bindGoodsInfo(goods: Goods?) {
    if(goods == null) {
      toast("잘못된 상품입니다.")
      return
    }

    tv_detail_goods_name.text = goods.name
    tv_detail_goods_title.text = goods.name
    tv_detail_goods_price.text = goods.price.toString()
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {

    when (item?.itemId) {
      android.R.id.home -> {
        finish()
      }
    }

    return super.onOptionsItemSelected(item)
  }
}