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
import com.pickth.hammer.adapter.HomeItemAdapter
import com.pickth.hammer.item.Goods
import com.pickth.hammer.item.User
import com.pickth.hammer.listener.ItemTouchListener
import com.pickth.hammer.view.activity.DetailActivity
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.jetbrains.anko.startActivity
import java.util.UUID

/**
 * Created by yonghoon on 2018-01-09
 * Blog   : http://blog.pickth.com
 */

class HomeFragment : Fragment(), ItemTouchListener {

  private lateinit var mAdapter: HomeItemAdapter

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

    mAdapter = HomeItemAdapter().apply {
      setItemTouchListener(this@HomeFragment)
    }

    rootView.rv_home.apply {
      adapter = mAdapter
      layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    addItem()

    mAdapter.notifyDataSetChanged()
    return rootView
  }

  fun addItem() {
    // http://www.ikea.com/kr/ko/catalog/categories/departments/ikea_family_products/27821/
    mAdapter.addItem(Goods(UUID.randomUUID().toString(), "FJARMA", "바깥쪽 주머니에 열쇠와 휴대폰 등을 넣어두면 보다 편리하게 사용할 수 있어요. 가방 아래쪽 칸에는 신발 등을 넣을 수 있습니다.", 39900, true, User("2","user"), ArrayList<Int>().apply {
      add(R.drawable.a_0)
      add(R.drawable.a_1)
      add(R.drawable.a_2)
      add(R.drawable.a_3)
    }
    )
    )
    mAdapter.addItem(Goods(UUID.randomUUID().toString(), "FJARMA", "바깥쪽 주머니에 열쇠와 휴대폰 등을 넣어두면 보다 편리하게 사용할 수 있어요. 가방 아래쪽 칸에는 신발 등을 넣을 수 있습니다.", 49900, false, User("2","user"), ArrayList<Int>().apply {
      add(R.drawable.b_0)
      add(R.drawable.b_1)
      add(R.drawable.b_2)
      add(R.drawable.b_3)
    }
    )
    )
    mAdapter.addItem(Goods(UUID.randomUUID().toString(), "KNALLA", "세탁물과 식료품, 재활용품까지 무엇이든 담을 수 있습니다. 지퍼식 제품으로 가방을 떨어뜨려도 내용물이 쏟아지지 않습니다.", 2900, false, User("2","user"), ArrayList<Int>().apply {
      add(R.drawable.c_0)
      add(R.drawable.c_1)
    }
    )
    )
    mAdapter.addItem(Goods(UUID.randomUUID().toString(), "FÖRENKLA", "바깥쪽 주머니에 열쇠와 휴대폰 등을 넣어두면 보다 편리하게 사용할 수 있어요. 슈트케이스의 손잡이에 쉽게 걸어둘 수 있어서 여행을 다닐 때 특히 더 편리합니다.", 29900, true, User("2","user"), ArrayList<Int>().apply {
      add(R.drawable.d_0)
      add(R.drawable.d_1)
      add(R.drawable.d_2)
      add(R.drawable.d_3)
      add(R.drawable.d_4)
    }
    )
    )
    mAdapter.addItem(Goods(UUID.randomUUID().toString(), "FÖRENKLA", "바깥쪽 주머니에 열쇠와 휴대폰 등을 넣어두면 보다 편리하게 사용할 수 있어요. 슈트케이스의 손잡이에 쉽게 걸어둘 수 있어서 여행을 다닐 때 특히 더 편리합니다.", 29900, false, User("2","user"), ArrayList<Int>().apply {
      add(R.drawable.e_0)
      add(R.drawable.e_1)
      add(R.drawable.e_2)
      add(R.drawable.e_3)
      add(R.drawable.e_4)
      add(R.drawable.e_5)
      add(R.drawable.e_6)
    }
    )
    )
  }
}