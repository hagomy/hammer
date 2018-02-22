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

package com.pickth.hammer.view.activity

import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.pickth.commons.view.activitys.BaseActivity
import com.pickth.hammer.util.UserInfoManager
import com.pickth.hammer.view.activity.MainActivity
import org.jetbrains.anko.startActivity

/**
 * Created by yonghoon on 2018-01-15
 * Blog   : http://blog.pickth.com
 */

class SplashActivity : BaseActivity() {
  val TAG = javaClass.simpleName

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val user = FirebaseAuth.getInstance().currentUser
    if(user != null) {
      Log.d(TAG, "onAuthStateChanged:signed_in: ${user.photoUrl}")
      user.getIdToken(true)
          .addOnCompleteListener {
            if(it.isSuccessful) {
              Log.d(TAG, "user token: ${it.result.token.toString()}")
              UserInfoManager.firebaseUserToken = it.result.token.toString()
              Log.d(TAG, "user info: ${UserInfoManager.getUser(this).toString()}")
              val user = UserInfoManager.getUser(this)
              startToMainActivity()
            }
          }

    } else {
      Log.d(TAG, "onAuthStateChanged:signed_out")
      startToSignInActivity()
    }

  }

  private fun startToMainActivity() {
    startActivity<MainActivity>()
    finish()
  }

  private fun startToSignInActivity() {
    startActivity<SignInActivity>()
    finish()
  }
}