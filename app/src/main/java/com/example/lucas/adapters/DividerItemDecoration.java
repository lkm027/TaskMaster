package com.example.lucas.adapters;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.lucas.tasks.R;

/**
 * Created by lkm02 on 3/16/2017.
 */

public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable mDivider;

    public DividerItemDecoration(Context context) {
        mDivider =  context.getResources().getDrawable(R.drawable.line_divider);
    }

    //provides offsets between our list items
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        if (parent.getChildAdapterPosition(view) % 2 == 1) {
            outRect.top = mDivider.getIntrinsicHeight();
            outRect.right = mDivider.getIntrinsicWidth();
        } else {
            outRect.top = mDivider.getIntrinsicHeight();
            outRect.right = mDivider.getIntrinsicWidth();
            outRect.left = mDivider.getIntrinsicWidth();
        }


    }

//    @Override
//    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
//        int dividerLeft = parent.getPaddingLeft();
//        int dividerRight = parent.getWidth();
//
//        int childCount = parent.getChildCount();
//        for (int i = 0; i < childCount - 1; i++) {
//            View child = parent.getChildAt(i);
//
//            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
//
//            int dividerTop = child.getBottom() + params.bottomMargin;
//            int dividerBottom = dividerTop + mDivider.getIntrinsicHeight();
////            dividerLeft = parent.getPaddingLeft() - params.leftMargin;
//            mDivider.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom);
//            mDivider.draw(canvas);
//        }
//    }
}
