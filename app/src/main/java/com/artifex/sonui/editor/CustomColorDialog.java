package com.artifex.sonui.editor;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

import com.artifex.solib.SODoc;
import com.artifex.sonui.editor.R.id;
import com.artifex.sonui.editor.R.string;

public class CustomColorDialog implements OnTouchListener, OnDismissListener {
    public static final int BG_COLORS = 2;
    public static final int FG_COLORS = 1;
    private static CustomColorDialog f;
    private final Context a;
    private final View b;
    private final ColorChangedListener c;
    private final int d;
    private final SODoc e;
    private PopupWindow g;
    private final String[] h = new String[]{"#000000", "#FFFFFF", "#D8D8D8", "#808080", "#EEECE1", "#1F497D", "#0070C0", "#C0504D", "#9BBB59", "#8064A2", "#4BACC6", "#F79646", "#FF0000", "#FFFF00", "#DBE5F1", "#F2DCDB", "#EBF1DD", "#00B050"};
    private boolean i = true;
    private int[] j;
    private final Point k = new Point();

    public CustomColorDialog(int var1, Context var2, SODoc var3, View var4, ColorChangedListener var5) {
        this.a = var2;
        this.b = var4;
        this.c = var5;
        this.d = var1;
        this.e = var3;
    }

    private void a() {
        this.g.dismiss();
        f = null;
    }

    public void onDismiss() {
        this.a();
    }

    public boolean onTouch(View var1, MotionEvent var2) {
        int var3 = var2.getAction();
        if (var3 != 0) {
            if (var3 == 2) {
                var3 = this.k.x;
                int var4 = (int)var2.getRawX();
                int var5 = this.k.y;
                int var6 = (int)var2.getRawY();
                PopupWindow var7 = this.g;
                int[] var8 = this.j;
                var7.update(var8[0] - (var3 - var4), var8[1] - (var5 - var6), -1, -1, true);
            }
        } else {
            this.j = new int[2];
            this.g.getContentView().getLocationOnScreen(this.j);
            this.k.set((int)var2.getRawX(), (int)var2.getRawY());
        }

        return true;
    }

    public void setShowTitle(boolean var1) {
        this.i = var1;
    }

    public void show() {
        f = this;
        View var1 = LayoutInflater.from(this.a).inflate(com.document.docease.R.layout.sodk_editor_colors, (ViewGroup)null);
        SOTextView var2 = (SOTextView)var1.findViewById(id.color_dialog_title);
        int var4;
        if (this.i) {
            Context var3;
            if (this.d == 2) {
                var3 = this.a;
                var4 = string.sodk_editor_background;
            } else {
                var3 = this.a;
                var4 = string.sodk_editor_color;
            }

            var2.setText(var3.getString(var4));
        } else {
            var2.setVisibility(View.GONE);
        }

        String[] var14;
        if (this.d == 2) {
            var14 = this.e.getBgColorList();
        } else {
            var14 = this.h;
        }

        LinearLayout var5 = (LinearLayout)var1.findViewById(id.fontcolors_row1);
        LinearLayout var6 = (LinearLayout)var1.findViewById(id.fontcolors_row2);
        LinearLayout var7 = (LinearLayout)var1.findViewById(id.fontcolors_row3);
        var4 = 0;

        for(int var8 = 0; var4 < 3; ++var4) {
            LinearLayout var13 = (new LinearLayout[]{var5, var6, var7})[var4];
            int var9 = var13.getChildCount();

            int var12;
            for(int var10 = 0; var10 < var9; var8 = var12) {
                Button var11 = (Button)var13.getChildAt(var10);
                var12 = var8 + 1;
                if (var12 <= var14.length) {
                    var11.setVisibility(View.VISIBLE);
                    var11.setBackgroundColor(Color.parseColor(var14[var8]));
                    var11.setTag(var14[var8]);
                    var11.setOnClickListener(new OnClickListener() {
                        public void onClick(View var1) {
                            CustomColorDialog.this.c.onColorChanged((String)var1.getTag());
                            CustomColorDialog.this.onDismiss();
                        }
                    });
                } else {
                    var11.setVisibility(View.GONE);
                }

                ++var10;
            }
        }

        Button var15 = (Button)var1.findViewById(id.transparent_color_button);
        if (this.d == 2) {
            var15.setVisibility(View.VISIBLE);
            var15.setOnClickListener(new OnClickListener() {
                public void onClick(View var1) {
                    CustomColorDialog.this.c.onColorChanged((String)var1.getTag());
                    CustomColorDialog.this.onDismiss();
                }
            });
        } else {
            var15.setVisibility(View.GONE);
        }

        PopupWindow var16 = new PopupWindow(this.a);
        this.g = var16;
        var16.setContentView(var1);
        this.g.setClippingEnabled(false);
        var1.setOnTouchListener(this);
        this.g.setOnDismissListener(this);
        this.g.setFocusable(true);
        var1.measure(0, 0);
        this.g.setWidth(var1.getMeasuredWidth());
        this.g.setHeight(var1.getMeasuredHeight());
        this.g.showAtLocation(this.b, Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 30);
//        this.g.showAsDropDown(this.b, 0,-32,Gravity.CENTER_HORIZONTAL);
    }
}
