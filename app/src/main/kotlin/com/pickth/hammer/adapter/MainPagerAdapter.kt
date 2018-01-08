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

package com.pickth.hammer.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.pickth.hammer.view.fragment.Test2Fragment
import com.pickth.hammer.view.fragment.Test3Fragment
import com.pickth.hammer.view.fragment.Test4Fragment
import com.pickth.hammer.view.fragment.TestFragment

/**
 * Created by yonghoon on 2018-01-09
 * Blog   : http://blog.pickth.com
 */

class MainPagerAdapter(val fragmentManager: FragmentManager): FragmentStatePagerAdapter(fragmentManager) {
    private val mTesetFragment = TestFragment.getInstance()
    private val mTeset2Fragment = Test2Fragment.getInstance()
    private val mTeset3Fragment = Test3Fragment.getInstance()
    private val mTeset4Fragment = Test4Fragment.getInstance()

    private val itemList = ArrayList<Int>()

    override fun getItem(position: Int): Fragment = when(position) {
//        1 -> mGachiFragment
        0 -> mTesetFragment
        1 -> mTeset2Fragment
        2 -> mTeset3Fragment
        3 -> mTeset4Fragment
       else -> {
           mTeset4Fragment
       }
    }

    fun setListItem(position: Int) {
        itemList.add(position)
    }

    fun getListItem(position: Int): Int = itemList[position]

    override fun getCount(): Int = itemList.size
}