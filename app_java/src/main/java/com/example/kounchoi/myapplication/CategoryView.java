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

package com.example.kounchoi.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by yonghoon on 2018-01-31
 * Blog   : http://blog.pickth.com
 */

class CategoryView extends NestedScrollView {
    private CategoryListener mCategoryListener;
    private ArrayList<String> mTitles = new ArrayList();
    private int mItemViewId = 0;
    private LinearLayout mRootLinearLayout;

    public CategoryView(Context context) {
        super(context);
        initializeView();
    }

    public CategoryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeView();
    }

    public CategoryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeView();
    }

    private void initializeView() {
        mRootLinearLayout = new LinearLayout(getContext());
        mRootLinearLayout.setOrientation(LinearLayout.VERTICAL);
        mRootLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        setOverScrollMode(View.OVER_SCROLL_NEVER);
        addView(mRootLinearLayout);
    }

    public void addCategory(String title, ArrayList<String> items) {
        addDivider(8);
        addTitle(title);
        addRecyclerView(items);
    }

    private void addDivider(int margin) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, convertDpToPixel(1));
        params.setMargins(0, convertDpToPixel(margin), 0, 0);

        View divider = new View(getContext());
        divider.setLayoutParams(params);
        divider.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorBlack));
        mRootLinearLayout.addView(divider);
    }


    private void addTitle(String title) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, convertDpToPixel(36));
        mTitles.add(title);
        TextView textView = new TextView(getContext());
        textView.setLayoutParams(params);
        textView.setPadding(convertDpToPixel(8), 0, 0, 0);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorWhite));
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorBlack));
        textView.setText(title);
        textView.setTextSize(16);
        mRootLinearLayout.addView(textView);
    }


    private void addRecyclerView(ArrayList<String> items) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        CategoryAdapter adapter = new CategoryAdapter(mItemViewId, items, mTitles.size() - 1, mCategoryListener);
        RecyclerView recyclerView = new RecyclerView(getContext());
        recyclerView.setLayoutParams(params);
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
        recyclerView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorWhite));
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.addItemDecoration(new GridItemDecoration(getContext(), 2, 0, false, true, true));
        mRootLinearLayout.addView(recyclerView);
    }

    public void setCategoryListener(CategoryListener categoryListener) {
        mCategoryListener = categoryListener;
    }

    public void setItemViewId(int itemViewId) {
        mItemViewId = itemViewId;
    }

    private int convertDpToPixel(int dp) {
        return (int) getContext().getResources().getDisplayMetrics().density * dp;
    }

    private class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
        int mItemViewId;
        ArrayList<String> mItemNames;
        int mCategoryPosition;
        CategoryListener mCategoryListener;

        CategoryAdapter(int itemViewId, ArrayList<String> itemNames, int categoryPosition, CategoryListener categoryListener) {
            mItemViewId = itemViewId;
            mItemNames = itemNames;
            mCategoryPosition = categoryPosition;
            mCategoryListener = categoryListener;
        }

        @Override
        public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.item_category, parent, false);
            return new CategoryViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(CategoryViewHolder holder, int position) {
            holder.onBind(mCategoryPosition, position, mItemNames.get(position));
        }

        @Override
        public int getItemCount() {
            return mItemNames.size();
        }

        class CategoryViewHolder extends RecyclerView.ViewHolder {
            CategoryViewHolder(View itemView) {
                super(itemView);
            }

            public void onBind(final int categoryPosition, final int itemPosition, String item) {
                ((TextView) itemView.findViewById(R.id.tv_category)).setText(item);
                itemView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mCategoryListener.onClickItem(categoryPosition, itemPosition);
                    }
                });
            }
        }

    }


    private class GridItemDecoration extends RecyclerView.ItemDecoration {
        int mSpacing;
        int mCount;
        boolean isIncludeEdge;
        boolean isUseDivider;
        boolean isIncludeEdgeDivider;
        Drawable divider;

        GridItemDecoration(Context context, int spanCount, int spacing, boolean includeEdge, boolean useDivider, boolean includeEdgeDivider) {
            mSpacing = (int) context.getResources().getDisplayMetrics().density * spacing;
            mCount = spanCount;
            isIncludeEdge = includeEdge;
            isUseDivider = useDivider;
            isIncludeEdgeDivider = includeEdgeDivider;
            divider = ContextCompat.getDrawable(context, R.drawable.line_divider);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);
            int column = position % mCount;

            if (isIncludeEdge) {
                int left = mSpacing - column * mSpacing / mCount;
                int right = (column + 1) * mSpacing / mCount;
                int top = outRect.top;
                if (position < mCount) {
                    top = mSpacing;
                }
                int bottom = mSpacing;
                outRect.set(left, top, right, bottom);
            } else {
                int left = column * mSpacing / mCount;
                int right = mSpacing - (column + 1) * mSpacing / mCount;
                int top = outRect.top;
                if (position >= mCount) {
                    top = mSpacing;
                }
                int bottom = outRect.bottom;
                outRect.set(left, top, right, bottom);
            }
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            int childCount = parent.getChildCount();

            for (int i = 0; i < childCount - 1; i++) {
                View child = parent.getChildAt(i);
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                if (isUseDivider && divider != null && isIncludeEdgeDivider) {
                    if (childCount > i && i >= 0 && i < mCount) {
                        int left = params.leftMargin;
                        int right = child.getRight();
                        int top = child.getTop() - params.topMargin - (mSpacing / 2);
                        int bottom = top + divider.getIntrinsicHeight();
                        divider.setBounds(left, top, right, bottom);
                        divider.draw(c);
                    }
                }

                if (isUseDivider && divider != null) {
                    int left = params.leftMargin;
                    int right = child.getRight();
                    int top = child.getBottom() + params.bottomMargin + (mSpacing / 2);
                    int bottom = top + divider.getIntrinsicHeight();
                    divider.setBounds(left, top, right, bottom);
                    divider.draw(c);

                    if (mCount > 1) {
                        left = child.getRight() + params.rightMargin + (mSpacing / 2);
                        right = left + divider.getIntrinsicWidth();
                        top = params.topMargin;
                        bottom = child.getBottom();
                        divider.setBounds(left, top, right, bottom);
                        divider.draw(c);
                    }
                }

            }

            if (childCount > mCount && childCount % mCount != 0 && isUseDivider && divider != null && isIncludeEdgeDivider) {
                for (int j = childCount - (childCount % mCount); j < childCount; j++) {
                    View child = parent.getChildAt(j);
                    RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                    int left = child.getRight() + params.rightMargin + (mSpacing / 2);
                    int right = left + divider.getIntrinsicWidth();
                    int top = params.topMargin;
                    int bottom = child.getBottom();
                    divider.setBounds(left, top, right, bottom);
                    divider.draw(c);
                }
            }
        }
    }
}