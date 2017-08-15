package com.anddle.music.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by HUAHUA on 2017/8/14.
 * 自定义recyclerview 分割线
 *
 * ItemDecoration 允许应用给具体的View添加具体的图画或者layout的偏移，对于绘制View之间的分割线
 */

public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;//水平分割线
    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;//垂直分割线
    private static final int[] ATTRS = new int[]{
      android.R.attr.listDivider
    };
    private Drawable mDivider;
    private int mOrientation;

    public DividerItemDecoration(Context context, int orientation){
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
        setOrientation(orientation);//设置绘制方向
    }

    //判断绘制方向
    public void setOrientation(int orientation){
        if (orientation !=HORIZONTAL_LIST && orientation != VERTICAL_LIST){
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }

    /**
     * 通过一系列c.drawXXX()方法在绘制itemView之前定制需要绘制的内容
     * @param c
     * @param parent
     */
    @Override
    public void onDraw(Canvas c, RecyclerView parent){

        if (mOrientation == VERTICAL_LIST){
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }

    //垂直-画水平线
    public void drawVertical(Canvas c,RecyclerView parent){
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();
        for(int i = 0; i < childCount; i++){
            final View child = parent.getChildAt(i);//在集合中返回指定位置的recyclerview
            RecyclerView v = new RecyclerView(parent.getContext());//获取的是当前对象所在的Context
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)child.getLayoutParams();//实例化子控件的布局对象
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();//取得Drawable（绘制的线）的固有高度，单位是dp
            mDivider.setBounds(left,top,right,bottom);//设置画线位置和长度 （x,y,w,h)
            mDivider.draw(c);//画线
        }
    }

    //水平-画垂直线
    public void drawHorizontal(Canvas c,RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }

    }

    /**
     * getItemOffests可以通过outRect.set(l,t,r,b)设置指定itemview的paddingLeft，paddingTop， paddingRight， paddingBottom
     * @param outRect
     * @param itemPosition
     * @param parent
     */
    @Override
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        if (mOrientation == VERTICAL_LIST) {
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        } else {
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
        }

    }
}
