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
import android.util.Log
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.pickth.hammer.R
import com.pickth.hammer.item.User
import com.pickth.hammer.util.UserInfoManager
import kotlinx.android.synthetic.main.activity_signup.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * Created by yonghoon on 2018-02-22
 * Blog   : http://blog.pickth.com
 */

class SignUpActivity : AppCompatActivity() {

  val TAG = javaClass.simpleName
  private lateinit var mAuth: FirebaseAuth
  private lateinit var mAuthListener: FirebaseAuth.AuthStateListener

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_signup)

    // actionbar
    setSupportActionBar(signup_toolbar)
    supportActionBar?.run {
      setHomeAsUpIndicator(R.drawable.ic_back_primary)
      setDisplayShowTitleEnabled(false)
      setDisplayHomeAsUpEnabled(true)
    }

    // firebase
    mAuth = FirebaseAuth.getInstance()
    mAuthListener = FirebaseAuth.AuthStateListener {
      var user = it.currentUser
      if(user != null) {
        Log.d(TAG, "onAuthStateChanged:signed_in")

        Log.d(TAG, "user info: ${UserInfoManager.getUser(this).toString()}")

        user.getIdToken(true).addOnCompleteListener {
          val token = it.result.token
          Log.d(TAG, "user token: $token")
          UserInfoManager.firebaseUserToken = token.toString()
//              getUid(token)
          startToMainActivity()
        }

      } else {
        Log.d(TAG, "onAuthStateChanged:signed_out")
      }
    }


    tv_signup_submit.setOnClickListener {
      val email = et_signup_email.text.toString().trim()
      if(!isEmailValid(email)) {
        toast("invalid email")
        return@setOnClickListener
      }

      val password = et_signup_password.text.toString().trim()
      if(!isPasswordValid(password)) {
        toast("invalid password")
        return@setOnClickListener
      }

      mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
        Log.d(TAG, "createUserWithEmail:onComplete:" + it.isSuccessful);

        if(!it.isSuccessful) {
          Log.d(TAG, "createUserWithEmailAndPassword: ${it.exception}")
          Log.d(TAG, "add user not successful " + it.isSuccessful);
        } else {
          if(it.isSuccessful) {
            val user = UserInfoManager.setUser(this, User(it.result.user.uid, email))
            startToMainActivity()
          }
        }
      }
    }
  }

  private fun isEmailValid(email: String): Boolean {
    return email.contains("@")
  }

  private fun isPasswordValid(password: String): Boolean {
    return password.length > 5
  }

  override fun onStart() {
    super.onStart()
    mAuth.addAuthStateListener(mAuthListener)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when(item?.itemId) {
      android.R.id.home -> { startToSignInActivity() }
    }

    return super.onOptionsItemSelected(item)
  }

  private fun startToMainActivity() {
    startActivity<MainActivity>()
    finish()
  }

  private fun startToSignInActivity() {
    startActivity<SignInActivity>()
    finish()
  }

  override fun onBackPressed() {
    startToSignInActivity()
  }

  override fun onStop() {
    super.onStop()
    if(mAuthListener != null) mAuth.removeAuthStateListener(mAuthListener)
  }

}