package com.solon.animationdrink;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 豪杰 on 2016/5/20.
 */
public abstract class BaseView extends View {

    private Rect2 mPosition = new Rect2();

    private int width,height;

    private Paint paint;

    private int location;

    private float anchorX = 0.5f;

    private float anchorY = 0.5f;

    private float scaleX = 1,scaleY = 1;

    public float getAnchorX() {
        return anchorX;
    }

    /**
     * 设置X锚点
     * @param anchorX 0.0f - 1.0f
     */
    public void setAnchorX(float anchorX) {
        this.anchorX = anchorX;
    }

    public float getAnchorY() {
        return anchorY;
    }
    /**
     * 设置Y锚点
     * @param anchorY 0.0f - 1.0f
     */
    public void setAnchorY(float anchorY) {
        this.anchorY = anchorY;
    }

    public BaseView(Context context) {
        this(context, null);
    }

    public BaseView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint();
        paint.setAntiAlias(true);
        startAnim();

    }


    public void setColor(int color){
        paint.setColor(color);
    }

    protected void setMyWidth(int width){
        this.width = width;
    }

    protected void setMyHeight(int height){
        this.height = height;
    }

    public abstract void setWidth(int width);

    public abstract void setHeight(int width);

    public int getPositionX() {
        return mPosition.x;
    }

    public int getMyWidth() {
        return (int) (width * scaleX);
    }

    public int getMyHeight() {
        return (int) (height * scaleX);
    }

    @Override
    public float getScaleX() {
        return scaleX;
    }

    @Override
    public void setScaleX(float scaleX) {
        this.scaleX = scaleX;
    }

    @Override
    public float getScaleY() {
        return scaleY;
    }

    @Override
    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;
    }

    public void setScale(float v) {
        setScaleX(v);
        setScaleY(v);
        getParent().requestLayout();
    }

    public void setPositionX(int PositionX) {
        mPosition.x = PositionX;
        getParent().requestLayout();
    }

    public int getPositionY() {
        return mPosition.y;
    }

    public void setPositionY(int PositionY) {
        this.mPosition.y = PositionY;
        getParent().requestLayout();
    }

    public void setPosition(Rect2 position){
        mPosition = position;
        getParent().requestLayout();
    }

    public Rect2 getPosition(){
        return mPosition;
    }

    public void setLocation(int i){
        location = i;
    }
    public int getLocation(){return location;}

    public void startAnim(){
        floatAnim(this, 0);
    }
    private void floatAnim(View view,int delay){

        List<Animator> animators = new ArrayList<>();

        float offsetX = (float) (4 + (Math.random() * 8));
        ObjectAnimator translationXAnim = ObjectAnimator.ofFloat(view, "translationX", -offsetX,offsetX - 1,-offsetX - 1,offsetX - 2,-offsetX - 2,offsetX - 3,-offsetX - 3,offsetX - 4,-offsetX - 4);
        translationXAnim.setDuration(2500 + (long) (Math.random() * 1000));
        translationXAnim.setRepeatCount(-1);//无限循环
        translationXAnim.setRepeatMode(ValueAnimator.INFINITE);//

        DecelerateInterpolator overshootInterpolator = new DecelerateInterpolator(1);
//        translationXAnim.setInterpolator(overshootInterpolator);

        translationXAnim.start();
        animators.add(translationXAnim);
        float offsetY = (float) (3 + (Math.random() * 6));
        ObjectAnimator translationYAnim = ObjectAnimator.ofFloat(view, "translationY", -offsetY,offsetY - 1,-offsetY - 1,offsetY - 2,-offsetY - 2,offsetY - 3,-offsetY - 3,offsetY - 4,-offsetY - 4);
        translationYAnim.setDuration(2500 + (long) (Math.random() * 1000));
        translationYAnim.setRepeatCount(-1);
        translationYAnim.setRepeatMode(ValueAnimator.INFINITE);
//        translationYAnim.setInterpolator(overshootInterpolator);
        translationYAnim.start();
        animators.add(translationYAnim);

        AnimatorSet btnSexAnimatorSet = new AnimatorSet();
        btnSexAnimatorSet.playTogether(animators);
        btnSexAnimatorSet.setInterpolator(overshootInterpolator);
        btnSexAnimatorSet.setStartDelay(delay);
        btnSexAnimatorSet.start();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        int radius = getMyWidth() / 2;
        canvas.drawCircle(radius,radius,radius,paint);
    }


}
