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

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.pickth.hammer.R
import com.pickth.hammer.item.Goods
import com.pickth.hammer.listener.GoodsItemTouchListener
import com.pickth.hammer.view.viewholder.GoodsItemViewHolder
import java.util.ArrayList

/**
 * Created by yonghoon on 2018-01-15
 * Blog   : http://blog.pickth.com
 */

class GoodsItemAdapter : RecyclerView.Adapter<GoodsItemViewHolder>() {
  private var arrItems = ArrayList<Goods>()
  private lateinit var mGoodsItemTouchListener: GoodsItemTouchListener

  override fun onBindViewHolder(holder: GoodsItemViewHolder, position: Int) {
    holder.onBind(arrItems[position])
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoodsItemViewHolder {
    val itemView = LayoutInflater
        .from(parent.context)
        .inflate(R.layout.item_goods, parent, false)

    return GoodsItemViewHolder(itemView, mGoodsItemTouchListener)
  }

  override fun getItemCount(): Int = arrItems.size

  fun setItemTouchListener(goodsItemTouchListener: GoodsItemTouchListener) {
    mGoodsItemTouchListener = goodsItemTouchListener
  }

  fun getItems(): ArrayList<Goods> = arrItems

  fun getItem(position: Int) = arrItems[position]

  fun addItem(item: Goods) {
    arrItems.add(item)
  }

  fun addItemAtLast(item: Goods) {
    arrItems.add(itemCount, item)
  }
}