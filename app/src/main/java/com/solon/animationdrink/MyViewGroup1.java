package com.solon.animationdrink;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationSet;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.Interpolator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by xumin on 16/5/22.
 */
public class MyViewGroup1 extends ViewGroup {

    private static final int TOP_LEFT = 0;
    private static final int TOP_RIGHT = 1;
    private static final int BOTTOM_RIGHT = 2;
    private static final int BOTTOM_LEFT = 3;

    private Random mRandom = new Random();

    //画四个大圆
    private int count = 4;
    //当前位于中心的圆的编号
    private int currIndex = 0;
    //没有大圆的方向
    private int zeroIndex = TOP_LEFT;

    private int curr = 0;

    private boolean isInit = false;

    private Rect2[] rect2s = new Rect2[5];

    private BaseView[] baseViews = new BaseView[count];

    private BaseView[] otherViews = new BaseView[count];

    private BaseView[] smallViews = new BaseView[count];
    private boolean isTouch;

    public MyViewGroup1(Context context) {
        super(context);
    }

    public MyViewGroup1(Context context, AttributeSet attrs) {
        super(context, attrs);
        for (int i = 0; i < count; i++) {

//            rect2s[0] = new Rect2();
            BigView view = new BigView(getContext());

            int r = (int) (Math.random() * 255);
            int g = (int) (Math.random() * 255);
            int b = (int) (Math.random() * 255);
            view.setColor(Color.rgb(r, g, b));
            view.setLocation(i);
            baseViews[i] = view;
            addView(view);
        }
        for (int n = 0; n < count; n++) {
            BigView view = new BigView(getContext());

            int r = (int) (Math.random() * 255);
            int g = (int) (Math.random() * 255);
            int b = (int) (Math.random() * 255);
            view.setColor(Color.rgb(r, g, b));
            view.setLocation(n);
            otherViews[n] = view;
            addView(view);
        }

        for (int m = 0; m < count; m++) {
            SmallView view = new SmallView(getContext());

            int r = (int) (Math.random() * 255);
            int g = (int) (Math.random() * 255);
            int b = (int) (Math.random() * 255);
            view.setColor(Color.rgb(r, g, b));
            view.setLocation(m);
            smallViews[m] = view;
            addView(view);
        }
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                /**
                 * 初始化各个控件的位置
                 * */
                int width = getMeasuredWidth();
                int height = getMeasuredHeight();


//                rect2s[0].x = width / 2;
//                rect2s[0].x = height / 2;
//
//                rect2s[1].x = -width;
//                rect2s[1].x = height / 2;

                //只初始化一次，完成之后就移除掉
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int size = getChildCount();
        float width = getWidth() / 2.0f;
        float height = getHeight() / 2.0f;

        if (!isInit) {
            isInit = true;
            int offset = 200;//(int) (baseViews[0].getMyWidth() * 1.0f * 0.25f);

            rect2s[0] = new Rect2();
            rect2s[0].x = (int) width;
            rect2s[0].y = (int) height;

            rect2s[1] = new Rect2();
            rect2s[1].x = -offset;
            rect2s[1].y = -offset;

            rect2s[2] = new Rect2();
            rect2s[2].x = getWidth() + offset;
            rect2s[2].y = -offset;

            rect2s[3] = new Rect2();
            rect2s[3].x = -offset;
            rect2s[3].y = getHeight() + offset;

            rect2s[4] = new Rect2();
            rect2s[4].x = getWidth() + offset;
            rect2s[4].y = getHeight() + offset;

            for (int i = 0; i < count; i++) {

                BaseView view = otherViews[i];
                view.setPositionX(-1000);
                view.setPositionY(-1000);
                view.setWidth(getMeasuredHeight());
                view.setHeight(getMeasuredHeight());

                view = smallViews[i];
                view.setPositionX(-1000);
                view.setPositionY(-1000);
                view.setWidth(getMeasuredHeight());
                view.setHeight(getMeasuredHeight());

                view = baseViews[i];
                view.setWidth(getMeasuredHeight());
                view.setHeight(getMeasuredHeight());

                if (view.getLocation() == 0) {
                    view.setPositionX((int) width);
                    view.setPositionY((int) height);
                    center = view;
                } else if (view.getLocation() == 1) {
                    view.setPositionX(getX(i));
                    view.setPositionY(getY(i));
                    right = view;
                    rightTop.add(view);

                } else if (view.getLocation() == 2) {
                    view.setPositionX(-1000);
                    view.setPositionY(-1000);

                } else if (view.getLocation() == 3) {
                    view.setPositionX(getX(i));
                    view.setPositionY(getY(i));
                    left = view;
                    leftBottom.add(view);

                } else if (view.getLocation() == 4) {

                }
            }

            smallViews[0].setPositionX(getRandom());
            smallViews[0].setPositionY(getRandom());
            smallViews[1].setPositionX(getRandom());
            smallViews[1].setPositionY(getRandom() + smallViews[0].getMyWidth() + 50);
            leftTop.add(smallViews[0]);
            leftTop.add(smallViews[1]);

            smallViews[2].setPositionX(getWidth() - getRandom());
            smallViews[2].setPositionY(getHeight() - getRandom());
            smallViews[3].setPositionX(getWidth() - getRandom());
            smallViews[3].setPositionY(getHeight() - getRandom() - smallViews[2].getMyWidth() - 50);
            rightBottom.add(smallViews[2]);
            rightBottom.add(smallViews[3]);

            centerToLeft();
            rightToCenter();
        }

        for (int i = 0; i < size; i++) {
            View view = getChildAt(i);
            if (view instanceof BaseView) {
                BaseView v = (BaseView) view;

                int left = (int) (l + (v.getPositionX() - (v.getWidth() * 1.0f * v.getAnchorX())));
                int top = (int) (t + (v.getPositionY() - (v.getHeight() * 1.0f * v.getAnchorY())));
                int right = left + v.getMyWidth();
                int bottom = top + v.getMyHeight();

                v.layout(left, top, right, bottom);
            }

        }
    }

    private int getX(int count) {
        return rect2s[count].x + getRandom();
    }

    private int getY(int count) {
        return rect2s[count].y + getRandom();
    }

    public int getRandom() {
        return (int) (-20 + (Math.random() * 40));
    }

    private double getRaduis(BigView view1, BigView view2) {

        double x = Math.sqrt((double) ((view1.getPositionX() - view2.getPositionX()) ^ 2 + (view1.getPositionY() + view2.getPositionY()) ^ 2));
        return x;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
//                BaseView baseView = baseViews[2];
//                AnimatorSet anim1 = getAnim(baseView, rect2s[0]);
//
//                baseView = baseViews[0];
//                AnimatorSet anim2 = getAnim(baseView, rect2s[1]);
//
//                baseView = baseViews[3];
//                AnimatorSet anim3 = getAnim(baseView, -1000, 1000);
//
//                AnimatorSet sAnim1 = getAnim(smallViews[0], -1000, -1000);
//                AnimatorSet sAnim2 = getAnim(smallViews[1], -1000, -1000);
//                AnimatorSet sAnim3 = getAnim(smallViews[2], 1000, 1000);
//                AnimatorSet sAnim4 = getAnim(smallViews[3], 1000, 1000);
//
//                AnimatorSet sSet = new AnimatorSet();
//                sSet.play(sAnim1).with(sAnim2).with(sAnim3).with(sAnim4);
//                /**---------------------------华丽丽的分割线------------------------------------*/
//                baseView = otherViews[0];
//                AnimatorSet anim4 = getAnim(baseView, getWidth() + baseView.getMyWidth() + 200, getHeight() + baseView.getMyHeight() + 200, getX(4), getY(4));
//
//                AnimatorSet sAnim5 = getAnim(smallViews[2], - 400, getHeight() + baseView.getMyHeight() ,getRandom(),getHeight() - getRandom());
//                AnimatorSet sAnim6 = getAnim(smallViews[3], - 400, getHeight() + baseView.getMyHeight() ,getRandom(),getHeight() - getRandom() - smallViews[2].getMyWidth() - 50 );
//
//                baseView = otherViews[1];
//                AnimatorSet anim5 = getAnim(baseView, getWidth() + baseView.getMyWidth() + 200, - 200, getX(2), getY(2));
//                anim5.setStartDelay(300);
//                anim5.play(sAnim5).with(sAnim6);
//
//                AnimatorSet set = new AnimatorSet();
//                set.play(anim1).with(anim2).with(anim3).with(anim4).with(anim5).with(sSet);
//                set.addListener(new Animator.AnimatorListener() {
//                    @Override
//                    public void onAnimationStart(Animator animation) {
//                        int size = getChildCount();
//                        for (int i = 0 ; i < size ; i++ ){
//                            ((BaseView)getChildAt(i)).startAnim();
//                        }
//                    }
//
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//
//                    }
//
//                    @Override
//                    public void onAnimationCancel(Animator animation) {
//
//                    }
//
//                    @Override
//                    public void onAnimationRepeat(Animator animation) {
//
//                    }
//                });
//                set.start();
                isTouch = true;
                break;
            case MotionEvent.ACTION_MOVE:
                break;

        }

        return true;
    }

    private AnimatorSet getAnim(BaseView view, Rect2 rect2) {
        return getAnim(view, rect2.x, rect2.y);
    }

    private AnimatorSet getAnim(BaseView view, int x, int y) {
        return getAnim(view, view.getPositionX(), view.getPositionY(), x, y);
    }

    private AnimatorSet getAnim(BaseView view, int sx, int sy, int x, int y) {
        ObjectAnimator anim1 = ObjectAnimator.ofInt(view, "PositionX", sx, x);
        ObjectAnimator anim2 = ObjectAnimator.ofInt(view, "PositionY", sy, y);
        AnimatorSet set = new AnimatorSet();
//        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.setDuration(400);
        set.play(anim1).with(anim2);
        return set;
    }


    private BaseView left, center, right;

    private List<BaseView> leftTop = new ArrayList<>();
    private List<BaseView> leftBottom = new ArrayList<>();
    private List<BaseView> rightTop = new ArrayList<>();
    private List<BaseView> rightBottom = new ArrayList<>();


    private void centerToLeft() {
        AnimatorSet anim = new AnimatorSet();
        if (getRandomBoolean()) { //true 往上
            //移动到左上

            AnimatorSet.Builder play = anim.play(getAnim(center, rect2s[1]));
            //判断左上 是否有view  有 就移动到其他地方

            for (BaseView view : leftTop) {
                play.with(getAnim(view, -1000, -1000));
            }
            leftTop.clear();
            leftTop.add(center);
            //将下面的一起移走
            for (BaseView view : leftBottom) {
                play.with(getAnim(view, -1000, 1000));
            }
            AnimatorSet set1 = getAnim(smallViews[0], - smallViews[0].getMyWidth() - 60,getHeight() + smallViews[0].getMyHeight() - 60 ,getRandom(), getHeight() - getRandom());
            AnimatorSet set2 = getAnim(smallViews[1], - smallViews[0].getMyWidth() - 60,getHeight() + smallViews[0].getMyHeight() - 60 ,getRandom(), getHeight() - (getRandom() + smallViews[0].getMyWidth() + 50));
            set1.setStartDelay(600);
            set2.setStartDelay(600);
            play.with(set1);
            play.with(set2);
            leftTop.add(smallViews[0]);
            leftTop.add(smallViews[1]);

            leftBottom.clear();
        } else {
            AnimatorSet.Builder play = anim.play(getAnim(center, rect2s[3]));

            for (BaseView view : leftBottom) {
                play.with(getAnim(view, -1000, 1000));
            }
            leftBottom.clear();
            leftBottom.add(center);

            //将上面的一起移走
            for (BaseView view : leftTop) {
                play.with(getAnim(view, -1000, -1000));

            }
            AnimatorSet set1 = getAnim(smallViews[0], - smallViews[0].getMyWidth() - 60,- smallViews[0].getMyHeight() - 60 , getRandom(), getRandom());
            AnimatorSet set2 = getAnim(smallViews[1], - smallViews[0].getMyWidth() - 60,- smallViews[0].getMyHeight() - 60 , getRandom(), getRandom() + smallViews[0].getMyWidth() + 50);
            set1.setStartDelay(600);
            set2.setStartDelay(600);
            play.with(set1);
            play.with(set2);
            leftBottom.add(smallViews[0]);
            leftBottom.add(smallViews[1]);
            leftTop.clear();
        }

        anim.setInterpolator(new AccelerateDecelerateInterpolator() );
        anim.setDuration(400).start();
        left = center;

    }

    private boolean isTop = true;

    private void rightToCenter() {
        //移动到中心
        final AnimatorSet anim = new AnimatorSet();
        AnimatorSet.Builder play = anim.play(getAnim(right, rect2s[0]));
        center = right;
        if (isTop) {
            for (BaseView view : rightBottom) {
                play.with(getAnim(view, getWidth() / 2, getHeight() + view.getMyHeight() + 200));
                view.startAnim();
            }
            rightBottom.clear();
        } else {
            for (BaseView view : rightTop) {
                play.with(getAnim(view, getWidth() / 2, -(view.getMyHeight() + 200)));
                view.startAnim();
            }

            rightTop.clear();
        }
        right = getNextView();
        if (getRandomBoolean()) {
            isTop = true;
            play.with(getAnim(right, getWidth() + right.getMyWidth() + 200, -(right.getMyHeight() + 200), getX(2), getY(2)));
            AnimatorSet set1 = getAnim(smallViews[2], getWidth() + smallViews[0].getMyWidth() - 60,getHeight() + smallViews[0].getMyHeight() + 60 , getWidth() - getRandom(), getHeight() - getRandom());
            AnimatorSet set2 = getAnim(smallViews[3], getWidth() + smallViews[0].getMyWidth() - 60,getHeight() + smallViews[0].getMyHeight() + 60 , getWidth() - getRandom(), getHeight() - (getRandom() + smallViews[0].getMyWidth() + 50));
            set1.setStartDelay(600);
            set2.setStartDelay(600);
            play.with(set1);
            play.with(set2);
            rightBottom.add(smallViews[2]);
            rightBottom.add(smallViews[3]);
        } else {
            isTop = false;
            play.with(getAnim(right, getWidth() + right.getMyWidth() + 200, getHeight() + right.getMyHeight() + 200, getX(4), getY(4)));
            AnimatorSet set1 = getAnim(smallViews[2], getWidth() + smallViews[0].getMyWidth() - 60,- smallViews[0].getMyHeight() - 60 , getWidth() - getRandom(), getRandom());
            AnimatorSet set2 = getAnim(smallViews[3], getWidth() +  smallViews[0].getMyWidth() - 60,- smallViews[0].getMyHeight() - 60 , getWidth() - getRandom(), getRandom() + smallViews[0].getMyWidth() + 50);
            set1.setStartDelay(600);
            set2.setStartDelay(600);
            play.with(set1);
            play.with(set2);
            rightTop.add(smallViews[2]);
            rightTop.add(smallViews[3]);
        }
        anim.setStartDelay(150);
        anim.setDuration(400).start();
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (isTouch){
                            for (BaseView view: baseViews) {
                                ObjectAnimator.ofFloat(view,"scale",1,0.4f).setDuration(400).start();
                            }
                            for (BaseView view:smallViews) {
                                ObjectAnimator.ofFloat(view,"scale",1,0.0f).setDuration(400).start();
                            }
                            float padding = getWidth() * 1.0f * 0.1f;
                            float offset = (getWidth() - (padding * 4) ) / 4f;
                            AnimatorSet set = getAnim(baseViews[0], (int) (padding), (int) (getHeight() * 1.0f * 0.33f));
                            AnimatorSet.Builder builder = set.play(getAnim(baseViews[1], (int) ((padding * 2) + offset), (int) (getHeight() * 1.0f * 0.63f)));
                            builder.with(getAnim(baseViews[2], (int) (((padding * 3) + (2 * offset))), (int) (getHeight() * 1.0f * 0.33f)));
                            builder.with(getAnim(baseViews[3], (int) (((padding * 4) + (3 * offset))), (int) (getHeight() * 1.0f * 0.63f)));
                            set.setInterpolator(new AnticipateInterpolator(4));
                            set.setDuration(400).start();

                            return;
                        }
                        centerToLeft();
                        rightToCenter();
                    }
                },700);
                anim.removeAllListeners();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    Handler handler = new Handler();

    private BaseView getNextView() {
        return baseViews[++curr > count - 1 ? curr = 0 : curr];
    }

    private boolean getRandomBoolean() {
        return Math.random() > 0.5 ? true : false;
    }

}
