package com.example.kounchoi.myapplication;

import android.app.ActionBar;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private ActionBar actionBar;
    private Toolbar mToolbar;
    private DrawerLayout mDlMain;
    private CategoryView mCvMain;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // actionbar
        mToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Hammer" + ".");
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        // drawer
        mDlMain = findViewById(R.id.dl_main);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDlMain, mToolbar, R.string.nav_open, R.string.nav_open);
        mDlMain.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        // fragment
        if(savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            MyFragment fragment = new MyFragment();
            fragmentTransaction.replace(R.id.main_fragment, fragment);
            fragmentTransaction.commit();
        }

        mTextMessage = findViewById(R.id.message);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        LinearLayout llNavItem = findViewById(R.id.ll_nav_item);
        llNavItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        // category
        mCvMain = findViewById(R.id.cv_main);
        mCvMain.setItemViewId(R.layout.item_category);
        mCvMain.setCategoryListener(new CategoryListener() {
            @Override
            public void onClickItem(int categoryPosition, int itemPosition) {
                // do something
            }
        });

        addCategoryItem();
    }

    public void addCategoryItem() {
        ArrayList<String> titles = new ArrayList<String>();
        titles.add("패션의류/잡화");
        titles.add("뷰티");
        titles.add("식품");

        ArrayList<String> item0 = new ArrayList<String>();
        item0.add("여성패션");
        item0.add("남성패션");
        item0.add("베이비여아");
        item0.add("베이비남아");
        item0.add("키즈여아");
        item0.add("키즈남아");
        item0.add("주닝너여아");
        item0.add("주니어남아");

        ArrayList<String> item1 = new ArrayList<String>();
        item1.add("유기농/친환경 전문관");
        item1.add("스킨케어");
        item1.add("메이크업");
        item1.add("향수");
        item1.add("헤어");
        item1.add("바디");
        item1.add("네일");
        item1.add("뷰티소품");
        item1.add("남성화장품");
        item1.add("명품화장품");
        item1.add("더모코스메틱");
        item1.add("선물세트/키트");
        item1.add("로드샵");

        ArrayList<String> item2 = new ArrayList<String>();
        item2.add("선물세트관");
        item2.add("유기농/친환경 전문관");
        item2.add("과일");
        item2.add("견과/건과");
        item2.add("채소");
        item2.add("쌀/잡곡");
        item2.add("축산/계란");
        item2.add("수산물/건어물");
        item2.add("생수/음료");
        item2.add("커피/원두/차");
        item2.add("과자/간식");
        item2.add("면/통조림/가공식품");
        item2.add("가루/조미료/오일");
        item2.add("장/소스/드레싱/식초");
        item2.add("유제품/아이스크림");
        item2.add("냉장/냉동/간편식");
        item2.add("건강식품");
        item2.add("분유/어린이식품");
        item2.add("수입식품 전문관");
        item2.add("대용량 전문관");

        mCvMain.addCategory(titles.get(0), item0);
        mCvMain.addCategory(titles.get(1), item1);
        mCvMain.addCategory(titles.get(2), item2);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            mDlMain.openDrawer(GravityCompat.START);
        }

        return super.onOptionsItemSelected(item);
    }
}
