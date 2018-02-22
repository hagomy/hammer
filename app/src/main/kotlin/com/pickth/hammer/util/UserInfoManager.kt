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
import android.content.Context.MODE_PRIVATE
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by yonghoon on 2018-02-22
 * Blog   : http://blog.pickth.com
 */

object UserInfoManager {
  var firebaseUserToken = ""
  private var mUser: User? = null

  fun getUser(context: Context): User? {
    if(mUser == null) {
      val json = context
          .getSharedPreferences("hammer", MODE_PRIVATE)
          .getString("user", "")

      if(json == "") return null

      val type = object: TypeToken<User>(){}.type
      mUser = Gson().fromJson<User>(json, type)
    }

    return mUser
  }

  fun setUser(context: Context, user: User) {
    mUser = user
    notifyDataSetChanged(context)
  }

  fun clearUserInfo(context: Context) {
    mUser = null
    context.getSharedPreferences("hammer", MODE_PRIVATE)
        .edit()
        .putString("user", "")
        .apply()
  }

  fun notifyDataSetChanged(context: Context) {
    context.getSharedPreferences("hammer", MODE_PRIVATE)
        .edit()
        .putString("user", Gson().toJson(mUser).toString())
        .apply()
  }

  data class User(var uid: String)
}