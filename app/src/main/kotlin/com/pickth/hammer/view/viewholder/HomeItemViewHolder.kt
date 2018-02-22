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

package com.pickth.hammer.view.viewholder

import android.view.View
import com.bumptech.glide.Glide
import com.pickth.commons.extensions.beVisible
import com.pickth.commons.recyclerview.BaseRecyclerView
import com.pickth.hammer.item.Goods
import com.pickth.hammer.listener.ItemTouchListener
import kotlinx.android.synthetic.main.item_goods.view.*

/**
 * Created by yonghoon on 2018-01-15
 * Blog   : http://blog.pickth.com
 */

class HomeItemViewHolder(view: View, val itemTouchListener: ItemTouchListener) : BaseRecyclerView.BaseViewHolder<Goods>(view) {
  override fun onBind(item: Goods) {
    with(itemView) {
      tv_home_goods_title.text = item.name
      tv_home_goods_explanation.text = item.explanation
      tv_home_item_price.text = "${item.price} 원"
      tv_home_goods_seller_nickname.text = item.user.nickname
      if(item.isHot) iv_home_goods_is_hot.beVisible()

      Glide.with(context).load(item.images[0]).into(iv_home_goods_img)

      setOnClickListener {
        itemTouchListener.onClick(0)
      }
    }
  }
}