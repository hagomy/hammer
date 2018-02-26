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

package com.pickth.hammer.item

/**
 * Created by yonghoon on 2018-01-15
 * Blog   : http://blog.pickth.com
 */

data class Goods(var id: String, var name: String, var explanation: String, var price: Int, var isHot: Boolean = false, var user: User, var images: ArrayList<String> = ArrayList()) {
  constructor(id: String, name: String, explanation: String, price: Int, isHot: Boolean, user: User) :this(id, name, explanation, price, isHot, user, ArrayList())
  fun toMap(): HashMap<String, Any> {
    val result = HashMap<String, Any>()
    result.put("id", id)
    result.put("name", name)
    result.put("explanation", explanation)
    result.put("price", price)
    result.put("isHot", isHot)
    result.put("user", user)
    result.put("images", images)

    return result
  }
}