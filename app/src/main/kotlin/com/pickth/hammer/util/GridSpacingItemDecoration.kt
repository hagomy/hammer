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

package com.pickth.hammer.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import com.pickth.commons.extensions.convertDpToPixel
import com.pickth.hammer.R

/**
 * Created by yonghoon on 2018-01-30
 * Blog   : http://blog.pickth.com
 */

class GridSpacingItemDecoration(context: Context, private val spanCount: Int, private var spacing: Int, private val includeEdge: Boolean, private val useDivider: Boolean = false, private val includeEdgeDivider: Boolean = false) :
    RecyclerView.ItemDecoration() {
  init {
    spacing = context.convertDpToPixel(spacing)
  }

  private val divider = ContextCompat.getDrawable(context, R.drawable.line_divider)

  override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {

    val position = parent.getChildAdapterPosition(view) // item position
    val column = position % spanCount // item column

    with(outRect) {
      if(includeEdge) {
        left = spacing - column * spacing / spanCount // spacing - column * ((1f / spanCount) * spacing)
        right = (column + 1) * spacing / spanCount // (column + 1) * ((1f / spanCount) * spacing)
        if(position < spanCount) { // top edge
          top = spacing
        }
        bottom = spacing // item bottom
      } else {
        left = column * spacing / spanCount // column * ((1f / spanCount) * spacing)
        right = spacing - (column + 1) * spacing / spanCount // spacing - (column + 1) * ((1f /    spanCount) * spacing)
        if(position >= spanCount) {
          top = spacing // item top
        }
      }
    }
  }

  override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
    val childCount = parent.childCount

    for(i in 0 until childCount - 1) {
      val child = parent.getChildAt(i)

      val params = child.layoutParams as RecyclerView.LayoutParams

      if(useDivider && divider != null && includeEdgeDivider) {
        // 처음 아이템들 위에 가로방향 그려주기, 마지막 아이템의 세로방향 그려주기
        if(childCount > i && i in 0..spanCount) {
          val left = params.marginStart
          val right = child.right
          val top = child.top - params.topMargin - (spacing / 2)
          val bottom = top + divider.intrinsicHeight

          divider.setBounds(left, top, right, bottom)
          divider.draw(c)
        }
      }

      // 아래에서 오른쪽 그리기, 오른쪽에서 아래로 그리기, 끝에서 2번째 까지
      if(useDivider && divider != null) {
        // 오른쪽으로 그리기
        var left = params.marginStart
        var right = child.right
        var top = child.bottom + params.bottomMargin + (spacing / 2)
        var bottom = top + divider.intrinsicHeight

        divider.setBounds(left, top, right, bottom)
        divider.draw(c)

        if(spanCount > 1) {
          // 아래로 그리기
          left = child.right + params.rightMargin + (spacing / 2)
          right = left + divider.intrinsicWidth
          top = params.topMargin
          bottom = child.bottom

          // 좌표값 설정
          divider.setBounds(left, top, right, bottom)
          divider.draw(c)
        }
      }

    }

    if(childCount > spanCount && childCount % spanCount != 0 && useDivider && divider != null && includeEdgeDivider) {
      for(i in childCount - (childCount % spanCount) until childCount) {
        val child = parent.getChildAt(i)
        val params = child.layoutParams as RecyclerView.LayoutParams

        val left = child.right + params.rightMargin + (spacing / 2)
        val right = left + divider.intrinsicWidth
        val top = params.topMargin
        val bottom = child.bottom

        // 좌표값 설정
        divider.setBounds(left, top, right, bottom)
        divider.draw(c)
      }
    }

  }
}