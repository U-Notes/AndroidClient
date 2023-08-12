package com.example.unotes.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;

public class SlidingMenu extends FrameLayout {
    /**
     * 菜单View
     */
    private View vMenuView;
    /**
     * 内容View
     */
    private View vContentView;
    /**
     * 拽托帮助类
     */
    private ViewDragHelper mViewDragHelper;
    /**
     * 菜单状态改变监听
     */
    private OnMenuStateChangeListener mMenuStateChangeListener;
    /**
     * 菜单是否开启
     */
    private boolean isOpenMenu;

    public SlidingMenu(@NonNull Context context) {
        this(context, null);
    }

    public SlidingMenu(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingMenu(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    /**
     * 初始化
     */
    private void init(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        mViewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(@NonNull View child, int pointerId) {
                //菜单和内容都可以拽托
                return child == vMenuView || child == vContentView;
            }

            @Override
            public int getViewHorizontalDragRange(@NonNull View child) {
                //拽托范围，返回非0值即可，某些情况需要该值来确定是否可以拽托
                return vContentView.getWidth();
            }

            @Override
            public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
                //处理横向拽托，获取菜单宽度
                int menuWidth = vMenuView.getWidth();
                //因为拽托菜单和内容传入的对象不同，我们需要处理2种情况
                if (child == vMenuView) {
                    //拽托的是菜单
                    if (left < -menuWidth) {
                        //左边距离，最多只能完全隐藏于屏幕最左侧
                        return -menuWidth;
                    } else if (left > 0) {
                        //左边距离，最多能完全出现在屏幕
                        return 0;
                    } else {
                        return left;
                    }
                } else if (child == vContentView) {
                    //拽托的是内容区域，不能移动超出最左边的屏幕
                    if (left < 0) {
                        return 0;
                    } else if (left > menuWidth) {
                        //最多不能超过菜单的宽度
                        return menuWidth;
                    } else {
                        return left;
                    }
                }
                return 0;
            }

            @Override
            public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);
                //处理联动，拽托菜单布局，让内容布局跟着动
                if (changedView == vMenuView) {
                    int newLeft = vContentView.getLeft() + dx;
                    int right = newLeft + vContentView.getWidth();
                    vContentView.layout(newLeft, top, right, getBottom());
                } else if (changedView == vContentView) {
                    //拽托内容布局，让菜单布局跟着动
                    int newLeft = vMenuView.getLeft() + dx;
                    vMenuView.layout(newLeft, top, left, getBottom());
                }
                //处理滑动中的回调，计算滑动比值
                if (mMenuStateChangeListener != null) {
                    float fraction = (vContentView.getLeft() * 1f) / vMenuView.getWidth();
                    mMenuStateChangeListener.onSliding(fraction);
                }
                //处理开、关状态，由于该方法会不断被回调，所以需要加上状态值，保证只回调一次给监听器
                if ((vMenuView.getLeft() == -vMenuView.getWidth()) && isOpenMenu) {
                    //关
                    isOpenMenu = false;
                    if (mMenuStateChangeListener != null) {
                        mMenuStateChangeListener.onMenuClose();
                    }
                } else if (vMenuView.getLeft() == 0 && !isOpenMenu) {
                    //开
                    isOpenMenu = true;
                    if (mMenuStateChangeListener != null) {
                        mMenuStateChangeListener.onMenuOpen();
                    }
                }
            }

            @Override
            public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
                //fling操作
                if (xvel < 0) {
                    //向左
                    closeMenu();
                    return;
                } else if (xvel > 300) {
                    //向右
                    openMenu();
                    return;
                }
                //松手回弹
                float halfMenuWidth = vMenuView.getWidth() / 2f;
                //如果菜单打开的范围小于菜单的一半，则当为关
                if (vMenuView.getLeft() < -halfMenuWidth) {
                    //关
                    closeMenu();
                } else {
                    //开
                    openMenu();
                }
            }
        });
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //获取所有子View，确保只有菜单和内容这2个控件
        int childCount = getChildCount();
        if (childCount != 2) {
            throw new IllegalStateException("侧滑菜单内只能有2个子View，分别是菜单和内容");
        }
        vMenuView = getChildAt(0);
        vContentView = getChildAt(1);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //布局菜单和内容，菜单在最左边，普通状态是看不到的
        vMenuView.layout(-vMenuView.getMeasuredWidth(), top, left, bottom);
        //内容View铺满整个父控件
        vContentView.layout(left, top, right, bottom);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //将onInterceptTouchEvent委托给ViewDragHelper
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //将onTouchEvent委托给ViewDragHelper
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        //判断是否移动到头了，未到头则继续
        if (mViewDragHelper != null) {
            if (mViewDragHelper.continueSettling(true)) {
                invalidate();
            }
        }
    }

    /**
     * 打开菜单
     */
    public void openMenu() {
        mViewDragHelper.smoothSlideViewTo(vMenuView, 0, vMenuView.getTop());
        ViewCompat.postInvalidateOnAnimation(SlidingMenu.this);
    }

    /**
     * 关闭菜单
     */
    public void closeMenu() {
        mViewDragHelper.smoothSlideViewTo(vMenuView, -vMenuView.getWidth(), vMenuView.getTop());
        ViewCompat.postInvalidateOnAnimation(SlidingMenu.this);
    }

    /**
     * 菜单状态改变监听
     */
    public interface OnMenuStateChangeListener {
        /**
         * 当菜单开启时回调
         */
        void onMenuOpen();

        /**
         * 正在滑动时回调
         *
         * @param fraction 滑动比值
         */
        void onSliding(float fraction);

        /**
         * 当菜单关闭时回调
         */
        void onMenuClose();
    }

    /**
     * 设置状态改变监听
     *
     * @param menuStateChangeListener 监听器
     */
    public void setOnMenuStateChangeListener(OnMenuStateChangeListener menuStateChangeListener) {
        mMenuStateChangeListener = menuStateChangeListener;
    }
}