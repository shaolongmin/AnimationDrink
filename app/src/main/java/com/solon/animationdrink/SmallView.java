package com.solon.animationdrink;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by 豪杰 on 2016/5/20.
 */
public class SmallView extends BaseView {


    private float offsetScrol;

    public SmallView(Context context) {
        this(context, null);
    }

    public SmallView(Context context, AttributeSet attrs) {
        super(context, attrs);
        offsetScrol = (float)(0.10f +  (Math.random() * 0.2f));
    }

    @Override
    public void setWidth(int width) {
        setMyWidth((int) (width * 1.0f * offsetScrol));
    }

    @Override
    public void setHeight(int width) {
        setMyHeight((int) (width * 1.0f * offsetScrol));
    }


}
