package com.artifex.sonui.editor;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.apps.e;
import kankan.wheel.widget.d;

public class InkLineWidthDialog {
    private static final float[] a = new float[]{0.25F, 0.5F, 1.0F, 1.5F, 3.0F, 4.5F, 6.0F, 8.0F, 12.0F, 18.0F, 24.0F};

    public InkLineWidthDialog() {
    }

    public static void show(Context var0, View var1, float var2, final WidthChangedListener var3) {
        View var4 = View.inflate(var0, com.artifex.sonui.editor.R.layout.sodk_editor_line_width_dialog, (ViewGroup)null);
        final WheelView var5 = (WheelView)var4.findViewById(com.artifex.sonui.editor.R.id.wheel);
        var5.setViewAdapter(new LineWidthAdapter(var0, a));
        var5.setVisibleItems(5);
        int var6 = 0;
        var5.setCurrentItem(0);



        while(true) {
            float[] var7 = a;
            if (var6 >= var7.length) {
                var5.a(new d() {
                    public void a(WheelView var1) {
                    }

                    public void b(WheelView var1) {
                        var3.onWidthChanged(InkLineWidthDialog.a[var1.getCurrentItem()]);
                    }
                });
                NUIPopupWindow var8 = new NUIPopupWindow(var4, -2, -2);
                var8.setFocusable(true);
                var8.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    public void onDismiss() {
                        var5.a();
                    }
                });
                int screen = Utilities.getScreenSize(var0).x;
                int[] location = new int[2];
                var1.getLocationOnScreen(location);
                int x = location[0] - var8.getWidth()/2 +  var8.getWidth()/2;
                int y = location[1] - var8.getHeight();
                var8.showAtLocation(var1, Gravity.NO_GRAVITY, x -screen/2 , y-screen/2);

//                var8.showAsDropDown(var1, 30, 30);
                return;
            }

            if (var7[var6] == var2) {
                var5.setCurrentItem(var6);
            }

            ++var6;
        }
    }

    public static class LineWidthAdapter implements e {
        private Context a;
        private float[] b;

        public LineWidthAdapter(Context var1, float[] var2) {
            this.b = var2;
            this.a = var1;
        }

        public View getEmptyItem(View var1, ViewGroup var2) {
            return null;
        }

        public View getItem(int var1, View var2, ViewGroup var3) {
            View var4 = var2;
            if (var2 == null) {
                var4 = ((LayoutInflater)this.a.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(com.artifex.sonui.editor.R.layout.sodk_editor_line_width_item, var3, false);
            }

            float var5 = InkLineWidthDialog.a[var1];
            SOTextView var7 = (SOTextView)var4.findViewById(com.artifex.sonui.editor.R.id.value);
            StringBuilder var8 = new StringBuilder();
            var8.append(Utilities.formatFloat(var5));
            var8.append(" pt");
            var7.setText(var8.toString());
            int var6 = (int)(var5 * 3.0F / 2.0F);
            var1 = var6;
            if (var6 < 1) {
                var1 = 1;
            }

            var2 = var4.findViewById(com.artifex.sonui.editor.R.id.bar);
            var2.getLayoutParams().height = var1;
            var2.setLayoutParams(var2.getLayoutParams());
            return var4;
        }

        public int getItemsCount() {
            return this.b.length;
        }

        public void registerDataSetObserver(DataSetObserver var1) {
        }

        public void unregisterDataSetObserver(DataSetObserver var1) {
        }
    }

    public interface WidthChangedListener {
        void onWidthChanged(float var1);
    }
}
