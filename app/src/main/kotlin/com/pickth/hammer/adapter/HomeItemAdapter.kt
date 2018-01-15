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

import android.view.LayoutInflater
import android.view.ViewGroup
import com.pickth.commons.recyclerview.BaseRecyclerView
import com.pickth.hammer.R
import com.pickth.hammer.item.Goods
import com.pickth.hammer.view.viewholder.HomeItemViewHolder

/**
 * Created by yonghoon on 2018-01-15
 * Blog   : http://blog.pickth.com
 */

class HomeItemAdapter : BaseRecyclerView.BaseAdapter<Goods>() {
    override fun onBindViewHolder(holder: BaseRecyclerView.BaseViewHolder<Goods>, position: Int) {
        holder.onBind(arrItems[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerView.BaseViewHolder<Goods> {
        val itemView = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_goods, parent, false)

        return HomeItemViewHolder(itemView)
    }

    override fun getItemCount(): Int = arrItems.size
}