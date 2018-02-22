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
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.util.AttributeSet

/**
 * Created by yonghoon on 2018-01-08
 * Blog   : http://blog.pickth.com
 */

class MyBottomNavigationView(context: Context, attrs: AttributeSet) : BottomNavigationView(context, attrs) {
  init {
    centerMenuIcon()
  }

  private fun centerMenuIcon() {

    val menuView: BottomNavigationMenuView = getChildAt(0) as BottomNavigationMenuView

    menuView::class.java.getDeclaredField("mShiftingMode").apply {
      isAccessible = true
      setBoolean(menuView, false)
      isAccessible = false
    }

  }
}