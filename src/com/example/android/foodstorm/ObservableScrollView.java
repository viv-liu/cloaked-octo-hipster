package com.example.android.foodstorm;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Override ScrollView class to allow callback when
 * scroll position is changed. Adapted from
 * http://stackoverflow.com/questions/3948934/synchronise-scrollview-scroll-positions-android/3952629#3952629
 */
public class ObservableScrollView extends ScrollView {
    // Java has no function pointers. Really?
    private ScrollViewListener scrollViewListener = null;

    public ObservableScrollView(Context context) {
        super(context);
    }

    public ObservableScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if(scrollViewListener != null) {
            scrollViewListener.onScrollChanged(x, y, oldx, oldy);
        }
    }

}