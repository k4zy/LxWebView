package com.kazy.lx.sample;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by DSK02 on 2/19/2016.
 */
public class ScrollingFABBehavior extends CoordinatorLayout.Behavior<LinearLayout> {
    private int toolbarHeight;

    public ScrollingFABBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.toolbarHeight = context.getResources().getDimensionPixelOffset(R.dimen.toolbar_height);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, LinearLayout child, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, LinearLayout child, View dependency) {
        if (dependency instanceof AppBarLayout) {
            CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
            int fabBottomMargin = lp.bottomMargin;
            int distanceToScroll = child.getHeight() + fabBottomMargin;
            float ratio = (float) dependency.getY() / (float) toolbarHeight;
            child.setTranslationY(-distanceToScroll * ratio);
        }
        return true;
    }
}
