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

package com.pickth.hammer.view.custom

import android.content.Context
import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.pickth.commons.extensions.convertDpToPixel
import com.pickth.hammer.R
import com.pickth.hammer.listener.CategoryListener
import com.pickth.hammer.util.GridSpacingItemDecoration
import kotlinx.android.synthetic.main.item_category.view.*
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.leftPadding
import org.jetbrains.anko.textColor

/**
 * Created by yonghoon on 2018-01-30
 * Blog   : http://blog.pickth.com
 */

class CategoryView : NestedScrollView {
    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initializeView()
    }

    private lateinit var mCategoryListener: CategoryListener
    private var mTitles = ArrayList<String>()
    private var mItemViewId = 0
    private var mRootLinearLayout = LinearLayout(context).apply {
        orientation = LinearLayout.VERTICAL
        layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
    }


    fun setCategoryListener(categoryListener: CategoryListener) {
        mCategoryListener = categoryListener
    }

    fun setItemViewId(itemViewId: Int) {
        mItemViewId = itemViewId
    }

    private fun initializeView() {
        overScrollMode = View.OVER_SCROLL_NEVER
        addView(mRootLinearLayout)
    }

    fun addCategory(title: String, items: ArrayList<String>) {
        addDivider(8)
        addTitle(title)
        addRecyclerView(items)
    }

    private fun addDivider(marginSize: Int = 0) {
        val divider = View(context).apply {
            layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, context.convertDpToPixel(1)).apply {
                setMargins(0, context.convertDpToPixel(marginSize), 0, 0)
            }
            backgroundColor = ContextCompat.getColor(context, R.color.colorBlack)
        }

        mRootLinearLayout.addView(divider)
    }

    private fun addTitle(title: String) {
        var titleParam = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, context.convertDpToPixel(36))

        mTitles.add(title)

        mRootLinearLayout.addView(TextView(context).apply {
            layoutParams = titleParam
            leftPadding = context.convertDpToPixel(8)
            gravity = Gravity.CENTER_VERTICAL
            backgroundColor = ContextCompat.getColor(context, R.color.colorWhite)
            typeface = Typeface.DEFAULT_BOLD
            textColor = ContextCompat.getColor(context, R.color.colorBlack)
            text = title
            textSize = 16f
        })
    }

    private fun addRecyclerView(items: ArrayList<String>) {
        var itemsParam = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT).apply {
            //            setMargins(0, context.convertDpToPixel(8), 0, 0)
        }

        val myAdapter = CategoryAdapter(mItemViewId, items, mTitles.size - 1, mCategoryListener)

        var recyclerView = RecyclerView(context).apply {
            layoutParams = itemsParam
            overScrollMode = View.OVER_SCROLL_NEVER
            isNestedScrollingEnabled = false
            adapter = myAdapter
            backgroundColor = ContextCompat.getColor(context, R.color.colorWhite)
            layoutManager = GridLayoutManager(context, 2)
            addItemDecoration(GridSpacingItemDecoration(context, 2, 0, false, true, true))
        }


        mRootLinearLayout.addView(recyclerView)
    }


    private inner class CategoryAdapter(val itemViewId: Int, val itemNames: ArrayList<String>, val categoryPosition: Int, val categoryListener: CategoryListener) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

        override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
            holder.onBind(categoryPosition, position, itemNames[position])
        }

        override fun getItemCount(): Int = itemNames.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
            val itemView = LayoutInflater
                    .from(parent.context)
                    .inflate(itemViewId, parent, false)
            return CategoryViewHolder(itemView)
        }

        private inner class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            fun onBind(categoryPosition: Int, itemPosition: Int, item: String) {
                with(itemView) {
                    tv_category.text = item

                    setOnClickListener {
                        Log.i("ssssss", "categoryPosition ${categoryPosition} itemPosition ${itemPosition}")
                        categoryListener.onClickItem(categoryPosition, itemPosition)
                    }
                }
            }
        }
    }

}