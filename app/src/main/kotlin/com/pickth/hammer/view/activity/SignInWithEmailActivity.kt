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
import android.os.UserManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.pickth.commons.extensions.hideKeyboard
import com.pickth.hammer.R
import com.pickth.hammer.item.User
import com.pickth.hammer.util.UserInfoManager
import kotlinx.android.synthetic.main.activity_signup.et_signup_email
import kotlinx.android.synthetic.main.activity_signup.et_signup_password
import kotlinx.android.synthetic.main.activity_signup.signup_toolbar
import kotlinx.android.synthetic.main.activity_signup.tv_signup_submit
import kotlinx.android.synthetic.main.activity_signup.tv_signup_title
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.jetbrains.anko.userManager

/**
 * Created by yonghoon on 2018-02-22
 * Blog   : http://blog.pickth.com
 */

class SignInWithEmailActivity: AppCompatActivity() {

  val TAG = javaClass.simpleName
  private lateinit var mAuth: FirebaseAuth

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_signup)

    // actionbar
    setSupportActionBar(signup_toolbar)
    supportActionBar?.run {
      setHomeAsUpIndicator(R.drawable.ic_back)
      setDisplayShowTitleEnabled(false)
      setDisplayHomeAsUpEnabled(true)
    }

    // firebase
    mAuth = FirebaseAuth.getInstance()

    tv_signup_title.text = "Sign in with email"
    tv_signup_submit.text = "sign in"


    tv_signup_submit.setOnClickListener {
      hideKeyboard()

      val email = et_signup_email.text.toString().trim()
      if(email.length == 0 ) {
        toast("이메일을 입력해주세요")
        return@setOnClickListener
      }

      val password = et_signup_password.text.toString().trim()
      if(password.length == 0 ) {
        toast("비밀번호를 입력해주세요")
        return@setOnClickListener
      }

      mAuth.signInWithEmailAndPassword(email, password)
          .addOnCompleteListener {
            Log.d(TAG, "signInWithEmailAndPassword:onComplete:" + it.isSuccessful);

            if (!it.isSuccessful) {
              Log.d(TAG, "signInWithEmailAndPassword, : ${it.exception}")
              toast("유효하지 않은 정보입니다.")
            } else {
              val uid = it.result.user.uid
              it.result.user.getIdToken(true)
                  .addOnCompleteListener {
                    val token = it.result.token
                    Log.d(TAG, "user token: $token")
                    UserInfoManager.firebaseUserToken = token.toString()
                    UserInfoManager.setUser(this,User(uid, email))
                    startToMainActivity()
                  }


            }
          }
    }
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when(item?.itemId) {
      android.R.id.home -> { startToSignInActivity() }
    }

    return super.onOptionsItemSelected(item)
  }

  override fun onBackPressed() {
    startToSignInActivity()
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