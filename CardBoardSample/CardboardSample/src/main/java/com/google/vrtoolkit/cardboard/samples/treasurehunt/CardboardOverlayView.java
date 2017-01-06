package com.google.vrtoolkit.cardboard.samples.treasurehunt;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CardboardOverlayView extends LinearLayout {

    private final CardboardOverlayEyeView leftView;
    private final CardboardOverlayEyeView rightView;

    public CardboardOverlayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(HORIZONTAL);

        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f);
        params.setMargins(0, 0, 0, 0);

        leftView = new CardboardOverlayEyeView(context, attrs);
        leftView.setLayoutParams(params);
        addView(leftView);

        rightView = new CardboardOverlayEyeView(context, attrs);
        rightView.setLayoutParams(params);
        addView(rightView);

        setDepthOffset(0.016f);
        setVisibility(View.VISIBLE);

    }

    private void setDepthOffset(float offset) {
        leftView.setOffset(offset);
        rightView.setOffset(-offset);
    }

    public void setText(String text) {
        leftView.setText(text);
        rightView.setText(text);
    }

    private class CardboardOverlayEyeView extends ViewGroup {

        private float offset;
        private TextView textView;

        public CardboardOverlayEyeView(Context context, AttributeSet attrs) {

            super(context, attrs);

            textView = new TextView(context, attrs);
            textView.setTextColor(Color.YELLOW);

            addView(textView);
        }

        public void setOffset(float offset) {
            this.offset = offset;
        }

        public void setText(String text) {
            textView.setText(text);
        }


        @Override
        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

            final int width = right - left;
            final int height = bottom - top;
            final float imageSize = 0.1f;
            final float verticalImageOffset = -0.07f;
            final float verticalTextPos = 0.52f;
            float adjustedOffset = offset;

            float imageMargin = (1.0f - imageSize) / 2.0f;
            float leftMargin = (int) (width * (imageMargin + adjustedOffset));
            float topMargin = (int) (height * (imageMargin + verticalImageOffset));

            leftMargin = adjustedOffset * width;
            topMargin = height * verticalTextPos;

            textView.layout(
                    (int) leftMargin + 200, (int) topMargin,
                    (int) (leftMargin + width), (int) ((topMargin + height)));
        }
    }
}