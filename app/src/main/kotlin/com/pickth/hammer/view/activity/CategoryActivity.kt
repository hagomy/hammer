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
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.MenuItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.pickth.hammer.R
import com.pickth.hammer.adapter.GoodsItemAdapter
import com.pickth.hammer.item.Goods
import com.pickth.hammer.item.User
import com.pickth.hammer.listener.GoodsItemTouchListener
import kotlinx.android.synthetic.main.activity_category.fab
import kotlinx.android.synthetic.main.activity_category.recyclerview_category
import kotlinx.android.synthetic.main.activity_category.toolbar_category
import kotlinx.android.synthetic.main.activity_detail.detail_toolbar
import kotlinx.android.synthetic.main.activity_write_goods.tv_write_goods_category_title
import org.jetbrains.anko.startActivity
import java.util.ArrayList

/**
 * Created by yonghoon on 2018-02-26
 * Blog   : http://blog.pickth.com
 */

class CategoryActivity: AppCompatActivity() {

  val TAG = javaClass.simpleName
  private var mCategoryCode = ""
  private var mCategoryName = ""

  // adapter
  private lateinit var mGoodsItemAdapter: GoodsItemAdapter

  // database
  private lateinit var mItemReference: DatabaseReference
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
        Log.e(TAG, p0?.value.toString())

        val items = p0?.value as ArrayList<String>
        mCategoryName = items[mCategoryCode.toInt()%100]
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

    fab.setOnClickListener { startActivity<WriteGoodsActivity>("code" to mCategoryCode, "name" to mCategoryName) }

    mGoodsItemAdapter = GoodsItemAdapter().apply {
      setItemTouchListener(object : GoodsItemTouchListener {
        override fun onClick(position: Int) {

        }

      })
    }

    recyclerview_category.run {
      adapter = mGoodsItemAdapter
      layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }


    getItemList(mCategoryCode)
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
    mItemReference = FirebaseDatabase.getInstance().reference
        .child("goods")
        .child(categoryCode)

    mItemReference.addListenerForSingleValueEvent(object: ValueEventListener {
      override fun onDataChange(p0: DataSnapshot?) {
//        val goods = p0?.getValue(ArrayList<Any>::javaClass)
        if(p0 != null) {
          for(itemSnapshot in p0.children) {
            itemSnapshot.let {
              val id = it.child("id").value as String
              val name = it.child("name").value as String
              val explanation = it.child("explanation").value as String
              val price = it.child("price").value as Long
              val isHot = it.child("isHot").value as Boolean
              val userMap = it.child("user").value as HashMap<String, String>
              val images = it.child("images").value as ArrayList<String>?

              val user = User(userMap["uid"]!!, userMap["email"]!!)

              val goods: Goods

              if(images != null) {
                goods = Goods(id, name, explanation, price.toInt(), isHot, user, images)
              } else {
                goods = Goods(id, name, explanation, price.toInt(), isHot, user)
              }
              mGoodsItemAdapter.addItem(goods)
            }
//            Log.e(TAG, itemSnapshot.value.toString())
//            itemSnapshot.getValue(Goods::class.java)
//            {
//              Log.e(TAG, it.toString())
//              val item = it.getValue(Goods::class.java)
//              if(item != null) {
//                mGoodsItemAdapter.addItem(item)
//              }
//            }
//            Log.e(TAG, itemSnapshot.children.toString())
//            val goods = itemSnapshot.children.iterator().next().getValue(Goods::class.java)
////            val goods = itemSnapshot.getValue(Goods::class.java)
//            if(goods != null) {
//              mGoodsItemAdapter.addItem(goods)
//            }
          }
          mGoodsItemAdapter.notifyDataSetChanged()
        }

      }
      override fun onCancelled(p0: DatabaseError?) {
      }
    })

  }
}