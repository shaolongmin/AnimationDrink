package com.solon.animationdrink;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by 豪杰 on 2016/5/20.
 */
public class BigView extends BaseView {


    public BigView(Context context) {
        super(context);
    }

    public BigView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setWidth(int width) {
        setMyWidth((int) (width * 1.0f * 1.3f));
    }

    @Override
    public void setHeight(int height) {
        setMyHeight((int) (height * 1.0f * 1.3f));
    }

    public void toSmall(){

    }
}
