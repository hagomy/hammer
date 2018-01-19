package com.example.kounchoi.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by kounchoi on 2018. 1. 10..
 */

public class MyFragment extends Fragment {

    private ViewPager mViewPager;
    private SlidingTabLayout mSlidingTabLayout;

    public MyFragment() {
        // TODO Auto-generated constructor stub
    }

    //Fragment의 lifecycle 메소드 중에서 Fragment가 보여줄 View를 설정하는 메소드
    //MainActivity.java 에서 onCreate() 메소드 안에 setContentView()하듯이
    //Fragment에서는 이 메소드에서 Fragment가 보여줄 View 객체를 생성해서 return 시켜줘야 함.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = null; //Fragment가 보여줄 View 객체를 참조할 참조변수
        //매개변수로 전달된 LayoutInflater객체를 통해 fragment_test.xml 레이아웃 파일을
        //View 객체로 생성
        view = inflater.inflate(R.layout.fragment1, container, false);
        //생성된 View 객체를 리턴
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mViewPager.setAdapter(new MyPagerAdapter());

        mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setViewPager(mViewPager);
    }

    class MyPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return 6;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "tab " + (position + 1);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = null;
            String fragmentContentName = "pager_item";
            int layoutResId = (int) getActivity().getResources().getIdentifier(
                    fragmentContentName, "id", getActivity().getPackageName());
//            view = getActivity().getLayoutInflater().inflate(Integer.valueOf(layoutResId),container,false);

//            view = getActivity().getLayoutInflater().inflate(R.layout.pager_item, container, false);
//            container.addView(view);
//            container.removeView(container.getRootView());
//            view = getActivity().getLayoutInflater().inflate(R.layout.fragment_content1, container, false);
//            container.addView(view);

            switch (position) {
                case 0:
                    container.removeView(container.getRootView());
                    view = getActivity().getLayoutInflater().inflate(R.layout.fragment_content1, container, false);
                    container.addView(view);
                case 1:
                    container.removeView(container.getRootView());
                    view = getActivity().getLayoutInflater().inflate(R.layout.fragment_content2, container, false);
                    container.addView(view);
                case 2:
                    container.removeView(container.getRootView());
                    view = getActivity().getLayoutInflater().inflate(R.layout.fragment_content3, container, false);
                    container.addView(view);
                case 3:
                    container.removeView(container.getRootView());
                    view = getActivity().getLayoutInflater().inflate(R.layout.fragment_content4, container, false);
                    container.addView(view);
                case 4:
                    container.removeView(container.getRootView());
                    view = getActivity().getLayoutInflater().inflate(R.layout.fragment_content5, container, false);
                    container.addView(view);
                case 5:
                    container.removeView(container.getRootView());
                    view = getActivity().getLayoutInflater().inflate(R.layout.fragment_content6, container, false);
                    container.addView(view);

            }


//            TextView title = (TextView) view.findViewById(R.id.item_title);
//            title.setText(String.valueOf(position));

//            view = getActivity().getLayoutInflater().inflate(R.layout.pager_item, container, false);
//            container.addView(view);
            return view;

        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return o == view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }


}
