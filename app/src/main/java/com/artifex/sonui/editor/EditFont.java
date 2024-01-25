package com.artifex.sonui.editor;

import android.content.Context;
import android.view.View;
import android.widget.PopupWindow;

import com.artifex.solib.SODoc;

import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.apps.c;

public class EditFont {

    private static String[] f1446a;
    private static String[] f1447b = {"6 pt", "8 pt", "9 pt", "10 pt", "12 pt", "14 pt", "16 pt", "18 pt", "20 pt", "24 pt", "30 pt", "36 pt", "48 pt", "60 pt", "72 pt"};
    private static WheelView wheelView;
    private static WheelView wheelView1;
    private static SODoc e;

    private static float a(String str) {
        return (float) Integer.parseInt(str.substring(0, str.length() - 3));
    }

    private static void a(Context context) {
        FontListAdapter fontListAdapter = new FontListAdapter(context);
        fontListAdapter.enumerateFonts(e);
        int count = fontListAdapter.getCount();
        f1446a = new String[count];
        for (int i = 0; i < count; i++) {
            f1446a[i] = fontListAdapter.getItem(i);
        }
    }

    private static void d() {
        String selectionFontName = Utilities.getSelectionFontName(e);
        int i = 0;
        int i2 = 0;
        while (true) {
            String[] strArr = f1446a;
            if (i2 >= strArr.length) {
                break;
            } else if (strArr[i2].equalsIgnoreCase(selectionFontName)) {
                wheelView.setCurrentItem(i2);
                break;
            } else {
                i2++;
            }
        }
        double selectionFontSize = e.getSelectionFontSize();
        while (true) {
            String[] strArr2 = f1447b;
            if (i >= strArr2.length) {
                return;
            }
            if (((double) a(strArr2[i])) >= selectionFontSize) {
                wheelView1.setCurrentItem(i);
                return;
            }
            i++;
        }
    }

    public static void e() {
        e.setSelectionFontName(f1446a[wheelView.getCurrentItem()]);
    }

    public static void f() {
        e.setSelectionFontSize((double) a(f1447b[wheelView1.getCurrentItem()]));
    }

    public static void g() {
        wheelView.a();
        wheelView1.a();
        wheelView = null;
        wheelView1 = null;
        e = null;
        f1446a = null;
    }

    public static void show(Context context, View view, SODoc sODoc) {
        e = sODoc;
        a(context);
        View inflate = View.inflate(context, R.layout.sodk_editor_edit_font, null);
        wheelView = (WheelView) inflate.findViewById(R.id.left_wheel);

        c myAdapter = new c(context, f1446a);

        //cVar.b(18);
        myAdapter.a(context.getResources().getColor(R.color.sodk_editor_palette_white));
        wheelView.setViewAdapter(myAdapter);
        wheelView.setVisibleItems(5);
        wheelView1 = (WheelView) inflate.findViewById(R.id.right_wheel);

        //wheelView1.b(18);
        //wheelView1.setBackgroundColor(context.getResources().getColor(R.color.sodk_editor_palette_white));
        c cVar2 = new c(context, f1447b);
        cVar2.a(context.getResources().getColor(R.color.sodk_editor_palette_white));
        wheelView1.setViewAdapter(cVar2);
        wheelView1.setVisibleItems(5);
        d();
        wheelView.a((kankan.wheel.widget.d) new kankan.wheel.widget.d() {
            public void a(WheelView wheelView) {
            }

            public void b(WheelView wheelView) {
                EditFont.e();
            }
        });
        wheelView1.a((kankan.wheel.widget.d) new kankan.wheel.widget.d() {
            public void a(WheelView wheelView) {
            }

            public void b(WheelView wheelView) {
                EditFont.f();
            }
        });
        NUIPopupWindow nUIPopupWindow = new NUIPopupWindow(inflate, -2, -2);
        nUIPopupWindow.setFocusable(true);
        nUIPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            public void onDismiss() {
                EditFont.g();
            }
        });
        nUIPopupWindow.showAsDropDown(view, 30, 30);

    }

}
