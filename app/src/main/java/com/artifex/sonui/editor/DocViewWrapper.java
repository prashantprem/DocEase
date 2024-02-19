package com.artifex.sonui.editor;

import android.content.Context;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.artifex.solib.SOSelectionLimits;

public class DocViewWrapper extends DocView {

    float posX = 0;
    float posY = 0;

    public DocViewWrapper(Context context) {
        super(context);
    }

    public DocViewWrapper(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public DocViewWrapper(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void onSelectionChanged() {
        super.onSelectionChanged();
        if (NUIDocView.currentNUIDocView() != null) {
            NUIDocView.currentNUIDocView().handleSelectionPopup(posX, posY);
        }
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
        posX = motionEvent.getX();
        posY = motionEvent.getY();
        this.doDoubleTap(motionEvent.getX(), motionEvent.getY());
    }

    protected void doDoubleTap2(float var1, float var2) {
        try {
            if (!(this.mHostActivity instanceof NUIDocViewOther)) {
                Point var3 = this.eventToScreen(var1, var2);
                DocPageView var4 = this.findPageViewContainingPoint(var3.x, var3.y, false);
                if (var4 != null && var4.canDoubleTap(var3.x, var3.y)) {
                    var4.onDoubleTap(var3.x, var3.y);
                    this.focusInputView();
                    this.resetInputView();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
