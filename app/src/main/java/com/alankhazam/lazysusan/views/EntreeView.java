package com.alankhazam.lazysusan.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.alankhazam.lazysusan.R;
import com.alankhazam.lazysusan.data.Entree;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.squareup.picasso.Picasso;

/**
 * Entree view
 * Created by Alan on 11/24/2015.
 */
public class EntreeView extends RelativeLayout {

    private GestureDetector mHeaderGestureDetector;
    private SlidingUpPanelLayout mSlideLayout;
    private SlidingUpPanelLayout.PanelSlideListener mPanelSlideListener;

    private Entree mEntree;

    public EntreeView(Context context) {
        this(context, null, 0);
    }

    public EntreeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EntreeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inflate(getContext(), R.layout.view_entree, this);
        mSlideLayout = (SlidingUpPanelLayout) findViewById(R.id.entree_sliding_layout);

        // Load default image
        Picasso.with(getContext()).load(R.drawable.bg_splash).into((ImageView) findViewById(R.id.entree_image));

        // Detect gestures on Header
        mHeaderGestureDetector = new GestureDetector(getContext(),
                new HeaderGestureListener());
        findViewById(R.id.entree_header).setOnTouchListener(
                new OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent event) {
                        view.performClick();
                        return mHeaderGestureDetector.onTouchEvent(event);
                    }
                });

        // Set PanelSlideListener to handle pull text
        mSlideLayout.setPanelSlideListener(new SlidingUpPanelLayout.SimplePanelSlideListener());
    }

    public boolean setEntree(Entree entree) {
        mEntree = entree;

        return true;
    }

    private class HeaderGestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            // Check if vertical swipe passes threshold
            float diffY = e2.getY() - e1.getY();
            if (Math.abs(diffY) > SWIPE_THRESHOLD
                    && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffY > 0) {
                    // Slide panel down
                    mSlideLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                } else {
                    // Slide panel up
                    mSlideLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                }

                return true;
            }

            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            togglePanel();
            super.onLongPress(e);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            togglePanel();
            return super.onSingleTapConfirmed(e);
        }

        private void togglePanel() {
            if (mSlideLayout.getPanelState() != SlidingUpPanelLayout.PanelState.EXPANDED) {
                mSlideLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        }
    }
}
