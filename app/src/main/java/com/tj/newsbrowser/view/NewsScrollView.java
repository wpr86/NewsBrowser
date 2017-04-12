package com.tj.newsbrowser.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.ScrollView;


public class NewsScrollView extends ScrollView {

    private static final String TAG = NewsScrollView.class.getSimpleName();

    private int mActivePointerId = -1;

    private int mLastMotionY;

    private ScrollPositionCallback mScrollPositionCallback;

    private ScrollInfoProvider mScrollInfoProvider;

    public NewsScrollView(Context context) {
        super(context);
    }

    public NewsScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NewsScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setScrollInfoProvider(ScrollInfoProvider scrollInfoProvider) {
        mScrollInfoProvider = scrollInfoProvider;
    }

    public void setScrollPositionCallback(ScrollPositionCallback scrollPositionCallback) {
        mScrollPositionCallback = scrollPositionCallback;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mScrollPositionCallback != null) {
            mScrollPositionCallback.onScrollPosition(t);
        }
    }

    @Override
    public void requestChildFocus(View child, View focused) {
        //解决因为ListView持有焦点导致自动滚动到ListView的问题
        if (!(focused instanceof ListView)) {
            super.requestChildFocus(child, focused);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        try {
            final int action = ev.getAction() & MotionEvent.ACTION_MASK;
            if (!canScrollToBottom() && (MotionEvent.ACTION_UP == action || MotionEvent.ACTION_CANCEL == action)) {
                post(new Runnable() {
                    @Override
                    public void run() {
                        if (scrollHalf()) {
                            fullScroll(ScrollView.FOCUS_DOWN);
                        } else {
                            fullScroll(ScrollView.FOCUS_UP);
                        }
                    }
                });
            }
            return super.dispatchTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            //
        }

        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = ev.getAction() & MotionEvent.ACTION_MASK;
        if (canScrollToBottom()) {
            switch (action) {
                case MotionEvent.ACTION_MOVE: {
                    final int activePointerId = mActivePointerId;
                    if (activePointerId == -1) {
                        break;
                    }

                    final int pointerIndex = ev.findPointerIndex(activePointerId);
                    if (pointerIndex == -1) {
                        break;
                    }

                    final int y = (int) ev.getY(pointerIndex);
                    final int yDiff = y - mLastMotionY;
                    if (yDiff > 0 && mScrollInfoProvider != null && mScrollInfoProvider.canScrollUp()) {
                        return super.onInterceptTouchEvent(ev);
                    }
                    break;
                }

                case MotionEvent.ACTION_DOWN: {
                    mLastMotionY = (int) ev.getY();
                    mActivePointerId = ev.getPointerId(0);
                    return super.onInterceptTouchEvent(ev);
                }
            }
            return false;
        }
        if (getScrollY() == 0 && mScrollInfoProvider != null && !mScrollInfoProvider.canScrollUp()) {
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }

    private boolean canScrollToBottom() {
        return getScrollY() >= getMaxScroll();
    }

    private boolean scrollHalf() {
        return getScrollY() >= getMaxScroll() * 0.2f;
    }

    int maxScroll = -1;

    private int getMaxScroll() {
        if (maxScroll == -1) {
            maxScroll = computeVerticalScrollRange() - getHeight();
        }
        return maxScroll;
    }

    public interface ScrollPositionCallback {
        void onScrollPosition(int position);
    }

    public interface ScrollInfoProvider {
        boolean canScrollUp();
    }
}
