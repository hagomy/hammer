package com.pickth.hammer.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pickth.hammer.R
import kotlinx.android.synthetic.main.fragment_test.view.*

/**
 * Created by yonghoon on 2018-01-09
 * Blog   : http://blog.pickth.com
 */

class Test3Fragment : Fragment() {

    companion object {
        private val mInstance = Test3Fragment()
        fun getInstance(): Test3Fragment = mInstance
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_test, container, false)
        rootView.tv_test.text = "세번째 페이지"
        return rootView
    }
}