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
import android.support.v7.app.AppCompatActivity
import com.pickth.hammer.R
import kotlinx.android.synthetic.main.activity_signin.*
import org.jetbrains.anko.startActivity

/**
 * Created by yonghoon on 2018-02-22
 * Blog   : http://blog.pickth.com
 */

class SignInActivity : AppCompatActivity() {

  val TAG = javaClass.simpleName
//  private lateinit var mAuth: FirebaseAuth
//  private lateinit var mAuthListener: FirebaseAuth.AuthStateListener

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_signin)

//    mAuth = FirebaseAuth.getInstance()
//    mAuthListener = FirebaseAuth.AuthStateListener {
//      var user = it.currentUser
//      if(user != null) {
//        Log.d(TAG, "onAuthStateChanged:signed_in")
//
//        Log.d(TAG, "user info: ${UserInfoManager.getUser(this).toString()}")
//
//        user.getIdToken(true)
//            .addOnCompleteListener {
//              val userToken = it.result.token
//              Log.d(TAG, "user token: ${userToken}")
//              UserInfoManager.firebaseUserToken = it.result.token.toString()
////              getUid(userToken)
//              startToMainActivity()
//            }
//
//      } else {
//        Log.d(TAG, "onAuthStateChanged:signed_out")
//      }
//    }

    fl_login_with_email.setOnClickListener {
      startToSignUpWithEmailActivity()
    }

    tv_signup.setOnClickListener {
      startActivity<SignUpActivity>()
      finish()
    }

//    tv_skip.setOnClickListener {
//      startActivity<MainActivity>()
//      finish()
//    }

  }

  private fun startToMainActivity() {
    startActivity<MainActivity>()
    finish()
  }

  private fun startToSignUpWithEmailActivity() {
    startActivity<SignInWithEmailActivity>()
    finish()
  }

//  override fun onStart() {
//    super.onStart()
//    mAuth.addAuthStateListener(mAuthListener)
//  }
//
//  override fun onStop() {
//    super.onStop()
//    if(mAuthListener != null) mAuth.removeAuthStateListener(mAuthListener)
//  }
}