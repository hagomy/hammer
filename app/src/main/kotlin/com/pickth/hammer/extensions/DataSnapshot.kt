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

package com.pickth.hammer.extensions

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.pickth.hammer.item.Goods
import com.pickth.hammer.item.User
import java.util.ArrayList

/**
 * Created by yonghoon on 2018-02-27
 * Blog   : http://blog.pickth.com
 */

/**
 * Get goods info.
 * Use in reference of goods.
 */
fun DataSnapshot.getGoods(): Goods? {
  var goods: Goods? = null

  try {
    val id = child("id").value as String
    val name = child("name").value as String
    val explanation = child("explanation").value as String
    val price = child("price").value as Long
    val isHot = child("isHot").value as Boolean
    val userMap = child("user").value as HashMap<String, String>
    val images = child("images").value as java.util.ArrayList<String>?
    val category = child("category").value as String
    val regDate = child("regDate").value as String

    val user = User(userMap["uid"]!!, userMap["email"]!!)
    if(images != null) {
      goods = Goods(id, name, explanation, price.toInt(), isHot, user, category, regDate, images)
    } else {
      goods = Goods(id, name, explanation, price.toInt(), isHot, user, category, regDate)
    }
  } catch (e: Exception) {
    e.printStackTrace()
    return null
  }

  return goods
}

/**
 * Use in root reference
 */
fun DataSnapshot.getCategoryName(categoryCode: String): String {
  return try {
    if(value is ArrayList<*>) {
      (value as ArrayList<String>)[categoryCode.toInt()%100]
    } else {
      (value as HashMap<String, String>)[categoryCode]?:""
    }

  } catch (e: Exception) {
    e.printStackTrace()
    ""
  }

}