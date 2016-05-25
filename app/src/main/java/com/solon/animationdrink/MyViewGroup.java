package com.solon.animationdrink;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 豪杰 on 2016/5/20.
 */
public class MyViewGroup extends ViewGroup {

    private int count = 3;

    private float currOffset = 1.0f;

    private int curr = 1;


    public MyViewGroup(Context context) {
        this(context, null);
    }


    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);

        for (int i = 0 ; i < count ; i++) {
            BigView view = new BigView(getContext());
            int r = (int) (Math.random() * 255);
            int g = (int) (Math.random() * 255);
            int b = (int) (Math.random() * 255);
            view.setColor(Color.rgb(r, g, b));
            view.setLocation(i);
            addView(view);
        }
//        for (int i = 0 ; i < 3 ; i++){
//            SmallView smallView = new SmallView(context);
//            r = (int) (Math.random() * 255);
//            g = (int) (Math.random() * 255);
//            b = (int) (Math.random() * 255);
//            smallView.setColor(Color.rgb(r, g, b));
//            smallView.setLocation(i);
//            addView(smallView);
//        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = getChildCount();
        float width = getWidth() / 2.0f;
        float height = getHeight() / 2.0f;
        for (int i = 0 ; i < size ; i++){
            View child = getChildAt(i);
            if (child instanceof  BigView){
                BigView view = (BigView) child;
                view.setWidth(getMeasuredHeight());
                view.setHeight(getMeasuredHeight());

                if (view.getLocation() == curr) {
                    float offset = (currOffset < 1 ? 1 - currOffset + 1 : currOffset);
                    view.setPositionX((int) (width * currOffset));
                    view.setPositionY((int) (height * offset));

                }else if(view.getLocation() == curr - 1){

                    float offset = (currOffset < 1 ? 1 - currOffset + 1.2f : currOffset);
                    view.setPositionX((int) (width * (-1 + currOffset)));
                    view.setPositionY((int) (height * offset));

                }else if(view.getLocation() == curr + 1){

                    view.setPositionX((int) (width * (1 + currOffset)));
                    view.setPositionY((int) (height * (-1 + currOffset)));

                }
//                view.setPositionY((int) (height * 0.5f));
            }else if(child instanceof SmallView){

                SmallView view = (SmallView) child;
                view.setWidth(getMeasuredHeight());
                view.setHeight(getMeasuredHeight());

                float offset = 0.03f + (float) (Math.random() * 0.04f);
                int offsetX = (int) (getMeasuredWidth() *1.0f * offset);
                view.setPositionX(view.getLocation() / 2 == 0 ? offsetX : getMeasuredWidth() - offsetX );

                int offsetY = (int) (getMeasuredHeight() *1.0f * offset);
                view.setPositionY(view.getLocation() / 2 == 0 ? offsetY : getMeasuredHeight() - offsetY);
            }
        }
    }

    private float downX;
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){

            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                float offset = (event.getX() - downX) / 500.0f;
                currOffset = curr + offset;
                Log.e("TAG","currOffset = " + currOffset);
                break;

        }

        requestLayout();
        return true;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int size = getChildCount();

        for (int i = 0 ; i < size ; i++){
            View view = getChildAt(i);
            if (view instanceof  BaseView){
                BaseView v = (BaseView) view;
                int left = l + (v.getPositionX() - (v.getWidth() / 2));
                int top = t + (v.getPositionY() - (v.getHeight() / 2));
                int right = left + v.getMyWidth();
                int bottom = top +  v.getMyHeight();

                v.layout(left,top ,right ,bottom);
            }

        }
    }
}
