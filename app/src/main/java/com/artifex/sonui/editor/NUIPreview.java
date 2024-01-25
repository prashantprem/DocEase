//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.artifex.sonui.editor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.FrameLayout;

import com.artifex.solib.k;
import com.document.docease.utils.Constant;

public class NUIPreview extends FrameLayout {
    private NUIDocView a;
    private NUIView.OnDoneListener b = null;
    private Intent intent;

    public NUIPreview(Context var1) {
        super(var1);
        this.a(var1);
    }

    public NUIPreview(Context var1, AttributeSet var2) {
        super(var1, var2);
        this.a(var1);
    }

    public NUIPreview(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
        this.a(var1);
    }

    private void a(Context var1) {
    }

    public NUIDocView getNUIDocView() {
        return this.a;
    }

    private void a(Uri var1, String var2) {
        String var3 = com.artifex.solib.a.a(this.getContext(), var1, var2);
        StringBuilder var4 = new StringBuilder();
        var4.append("SomeFileName.");
        var4.append(var3);
        this.a(var4.toString());
    }

    private void a(String var1) {
        Object var2;
        Bundle bundle = new Bundle();
        bundle = intent.getExtras();
        Log.d("testingPreview", "called in NUIPreview");
        if (intent != null && !intent.hasExtra(Constant.EDITOR)) {
            intent.putExtra(Constant.PREVIEW, true);
            if (com.artifex.solib.a.c(var1, "pdf")) {
                var2 = new NUIDocViewPdf(this.getContext());
            } else {
                var2 = new NUIDocViewPreview(this.getContext());
            }
//        }
//        if (bundle != null && bundle.containsKey(Constant.PREVIEW)) {
//            if (com.artifex.solib.a.c(var1, "pdf")) {
//                var2 = new NUIDocViewPdf(this.getContext());
//            } else {
//                var2 = new NUIDocViewPreview(this.getContext());
//            }
        } else if (com.artifex.solib.a.c(var1, "pdf")) {
            var2 = new NUIDocViewPdf(this.getContext());
        } else if (com.artifex.solib.a.c(var1, "svg")) {
            var2 = new NUIDocViewMuPdf(this.getContext());
        } else if (com.artifex.solib.a.c(var1, "epub")) {
            var2 = new NUIDocViewMuPdf(this.getContext());
        } else if (com.artifex.solib.a.c(var1, "xps")) {
            var2 = new NUIDocViewMuPdf(this.getContext());
        } else if (com.artifex.solib.a.c(var1, "fb2")) {
            var2 = new NUIDocViewMuPdf(this.getContext());
        } else if (com.artifex.solib.a.c(var1, "xhtml")) {
            var2 = new NUIDocViewMuPdf(this.getContext());
        } else if (com.artifex.solib.a.c(var1, "cbz")) {
            var2 = new NUIDocViewMuPdf(this.getContext());
        } else if (k.c((Activity) this.getContext(), var1)) {
            var2 = new NUIDocViewXls(this.getContext());
        } else if (k.d((Activity) this.getContext(), var1)) {
            var2 = new NUIDocViewPpt(this.getContext());

        } else if (k.e((Activity) this.getContext(), var1)) {
            var2 = new NUIDocViewPdf(this.getContext());
        } else if (k.f((Activity) this.getContext(), var1)) {
            var2 = new NUIDocViewDoc(this.getContext());
        } else {
            var2 = new NUIDocViewOther(this.getContext());
        }

        this.a = (NUIDocView) var2;
    }

    public boolean doKeyDown(int var1, KeyEvent var2) {
        NUIDocView var3 = this.a;
        return var3 != null ? var3.doKeyDown(var1, var2) : false;
    }

    public void endDocSession(boolean var1) {
        NUIDocView var2 = this.a;
        if (var2 != null) {
            var2.endDocSession(var1);
        }

    }

    public boolean isDocModified() {
        NUIDocView var1 = this.a;
        return var1 != null ? var1.documentHasBeenModified() : false;
    }

    public void onActivityResult(int var1, int var2, Intent var3) {
        NUIDocView var4 = this.a;
        if (var4 != null) {
            var4.onActivityResult(var1, var2, var3);
        }

    }

    public void onBackPressed() {
        NUIDocView var1 = this.a;
        if (var1 != null) {
            var1.onBackPressed();
        }

    }

    public void onConfigurationChange(Configuration var1) {
        this.a.onConfigurationChange(var1);
    }

    public void onDestroy() {
        this.a.onDestroy();
    }

    public void onPause() {
        NUIDocView var1 = this.a;
        if (var1 != null) {
            var1.onPause();
        }

        Utilities.hideKeyboard(this.getContext());
    }

    public void onResume() {
        NUIDocView var1 = this.a;
        if (var1 != null) {
            var1.onResume();
        }

    }

    public void releaseBitmaps() {
        NUIDocView var1 = this.a;
        if (var1 != null) {
            var1.releaseBitmaps();
        }

    }

    public void setConfigurableButtons() {
        NUIDocView var1 = this.a;
        if (var1 != null) {
            var1.setConfigurableButtons();
        }

    }

    public void setOnDoneListener(NUIView.OnDoneListener var1) {
        this.b = var1;
    }

    public void start(Uri var1, boolean var2, int var3, String var4, String var5, Intent intent) {
        this.intent = intent;
        this.a(var1, var5);
        this.addView(this.a);
        this.a.start(var1, var2, var3, var4, this.b);
    }

    public void start(SODocSession var1, int var2, String var3, Intent intent) {
        this.intent = intent;
        this.a(var1.getUserPath());
        this.addView(this.a);
        this.a.start(var1, var2, var3, this.b);
    }

    public void start(SOFileState var1, int var2, Intent intent) {
        this.intent = intent;
        this.a(var1.getOpenedPath());
        this.addView(this.a);
        this.a.start(var1, var2, this.b);
    }

    public interface OnDoneListener {
        void done();
    }

}
