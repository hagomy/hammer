package com.pickth.hammer.view.activity

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.pickth.hammer.R
import com.pickth.hammer.item.Goods
import com.pickth.hammer.util.UserInfoManager
import kotlinx.android.synthetic.main.activity_write_goods.*
import org.jetbrains.anko.toast
import java.util.UUID
import kotlin.collections.HashMap

/**
 * Created by yonghoon on 2018-02-23
 * Blog   : http://blog.pickth.com
 */
class WriteGoodsActivity : AppCompatActivity() {
  val TAG = javaClass.simpleName

  private var mCategoryname = ""

  // database
  private lateinit var mDatabase: DatabaseReference
  private var mCategoryCode = "0"

  // storage
  private lateinit var mFirebaseStorage: FirebaseStorage
  private lateinit var mStorageRef: StorageReference
  private val GALLERY_CODE = 10
  private val PERMISSIONS_READ_STORAGE = 0
  private var imageUrl: Uri? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_write_goods)

    // actionbar
    setSupportActionBar(tb_write_goods)
    supportActionBar?.run {
      setHomeAsUpIndicator(R.drawable.ic_back)
      setDisplayShowTitleEnabled(false)
      setDisplayHomeAsUpEnabled(true)
    }

    intent.run {
      mCategoryCode = getStringExtra("code")
      mCategoryname = getStringExtra("name")
    }

    tv_write_goods_category_title.text = mCategoryname

    btn_write_goods_upload_image.setOnClickListener {
      checkPermission()
      checkPermission()
    }
  }

  private fun uploadImage(goodsId: String, image: Uri?) {
    if(image != null) {
      mFirebaseStorage = FirebaseStorage.getInstance()
      mStorageRef = mFirebaseStorage.reference

      // 마지막 child가 파일 이름이 됨
      mStorageRef.child("images").child("goods").child(goodsId).child("0").putFile(image).addOnSuccessListener {
        if(it.task.isSuccessful) {
          Log.d(TAG, "download image url = ${it.downloadUrl}")
        }
      }
    }
  }

  private fun getGallery() {
    val itGallery = Intent(Intent.ACTION_PICK).apply {
      type = MediaStore.Images.Media.CONTENT_TYPE
      data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    }

    startActivityForResult(itGallery, GALLERY_CODE)
  }

  private fun postGoods(categoryCode: String, goods: Goods) {
    mDatabase = FirebaseDatabase.getInstance().reference

    val childUpdates = HashMap<String, Any>()
    childUpdates.put("/goods/$categoryCode/${goods.id}", goods.toMap())
    mDatabase.updateChildren(childUpdates)

    toast("등록되었습니다.")
    finish()
  }

  private fun checkPermission() {
    ActivityCompat.requestPermissions(this, Array(1, { android.Manifest.permission.READ_EXTERNAL_STORAGE }), PERMISSIONS_READ_STORAGE)
  }

  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    when (requestCode) {
      PERMISSIONS_READ_STORAGE -> {
        if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          // permission ok
          getGallery()
        } else {
          toast("사진을 등록하려면 권한이 필요합니다.")
        }
      }
    }
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)

    when (requestCode) {
      GALLERY_CODE -> {
        if(resultCode == Activity.RESULT_OK) {
          imageUrl = data?.data

          Glide.with(this).load(imageUrl).into(iv_write_goods_image)
        }
      }
    }
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      android.R.id.home -> {
        finish()
      }

      R.id.menu_check -> {
        // post server

        if(et_write_goods_name.text.toString().isEmpty()) {
          toast("이름을 입력해 주세요")
          return false
        }

        if(et_write_goods_explanation.text.toString().isEmpty()) {
          toast("설명을 입력해 주세요")
          return false
        }

        if(et_write_goods_price.text.toString().isEmpty()) {
          toast("가격을 입력해 주세요")
          return false
        }

        val name = et_write_goods_name.text.toString().trim()
        val explanation = et_write_goods_explanation.text.toString().trim()
        val price = et_write_goods_price.text.toString().trim().toInt()

        val user = UserInfoManager.getUser(this)

        if(user == null) {
          toast("잘못된 접근입니다")
          return false
        }

        val goodsId = UUID.randomUUID().toString()

        uploadImage(goodsId, imageUrl)

        val goods = Goods(goodsId, name, explanation, price, false, user, mCategoryCode)

        postGoods(mCategoryCode, goods)
      }
    }

    return super.onOptionsItemSelected(item)
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_check, menu)
    return super.onCreateOptionsMenu(menu)
  }
}