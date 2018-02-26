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

package com.pickth.hammer.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pickth.hammer.R
import com.pickth.hammer.adapter.GoodsItemAdapter
import com.pickth.hammer.item.Goods
import com.pickth.hammer.item.User
import com.pickth.hammer.listener.GoodsItemTouchListener
import com.pickth.hammer.view.activity.DetailActivity
import com.pickth.imageslider.listener.OnImageTouchListener
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.jetbrains.anko.startActivity
import java.util.UUID

/**
 * Created by yonghoon on 2018-01-09
 * Blog   : http://blog.pickth.com
 */

class HomeFragment : Fragment(), GoodsItemTouchListener {

  private lateinit var mAdapter: GoodsItemAdapter

  companion object {
    private val mInstance = HomeFragment()
    fun getInstance(): HomeFragment = mInstance
  }

  override fun onClick(position: Int) {
//        mAdapter.getItem(position)
    activity?.startActivity<DetailActivity>()
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val rootView = inflater.inflate(R.layout.fragment_home, container, false)

    mAdapter = GoodsItemAdapter().apply {
      setItemTouchListener(this@HomeFragment)
    }

    rootView.rv_home.apply {
      adapter = mAdapter
      layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    val items = ArrayList<Int>().apply {
      add(R.drawable.e_0)
      add(R.drawable.e_0)
      add(R.drawable.e_0)
      add(R.drawable.e_0)
    }

    rootView.imageslider_home_event.run {
      setOnImageTouchListener(object: OnImageTouchListener {
        override fun onClickListener(position: Int) {

        }

        override fun onLongClickListener(position: Int) {
        }

      })

      imageslider_home_event.addItems(items)
    }

    addItem()

    mAdapter.notifyDataSetChanged()
    return rootView
  }

  fun addItem() {

  }
}