package com.artifex.sonui.editor;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;


import com.artifex.solib.ConfigOptions;
import com.artifex.solib.SOSelectionLimits;
import com.artifex.sonui.editor.R.string;
import com.artifex.sonui.editor.SODocSession.SODocSessionLoadListenerCustom;
import com.document.docease.utils.AdUnits;
import com.document.docease.utils.Constant;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

public class NUIActivity extends BaseActivity {
    private static SODocSession a;
    private Intent b = null;
    private long c = 0L;
    private int d = -1;
    protected NUIPreview mNUIPreview;

    AdView adView;

    public NUIActivity() {
    }

    //changed NUIPreview to NUIPreview and also in the layout of this file
    private void a() {
        Intent var1 = this.getIntent();
        Bundle var2 = null;
        try {
            var2 = var1.getExtras();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Bundle var2 = var1.getExtras();
        boolean var3;
        if (var2 != null) {
            var3 = var2.getBoolean("SESSION", false);
            this.a(var1);
        } else {
            var3 = false;
        }

        if (var3 && a == null) {
            super.finish();
        } else {
            this.a(var1, false);
        }
    }

    private void a(Intent var1) {
        try {
            ConfigOptions var2 = ConfigOptions.a();
            Bundle var3 = var1.getExtras();
            if (var1.hasExtra("ENABLE_SAVE")) {
                var2.n(var3.getBoolean("ENABLE_SAVE", true));
            }

            if (var1.hasExtra("ENABLE_SAVEAS")) {
                var2.b(var3.getBoolean("ENABLE_SAVEAS", true));
            }

            if (var1.hasExtra("ENABLE_SAVEAS_PDF")) {
                var2.c(var3.getBoolean("ENABLE_SAVEAS_PDF", true));
            }

            if (var1.hasExtra("ENABLE_CUSTOM_SAVE")) {
                var2.o(var3.getBoolean("ENABLE_CUSTOM_SAVE", true));
            }

            if (var1.hasExtra("ALLOW_AUTO_OPEN")) {
                var2.q(var3.getBoolean("ALLOW_AUTO_OPEN", true));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void a(Intent intent, boolean z) {
        SOFileState fromString;
        String string;
        String str;
        boolean z2;
        int i;
        NUIActivity nUIActivity = this;
        Bundle extras = null;
        try {
            extras = intent.getExtras();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Bundle extras = intent.getExtras();
        boolean z3 = false;
        boolean z4 = extras != null && extras.getBoolean("SESSION", false);
        SODocSession sODocSession = a;
        setContentView(R.layout.sodk_editor_doc_view_activity);
        adView = findViewById(MainR.getMainAppInt("ad_view"));
        NUIPreview nuiPreview = (NUIPreview) findViewById(R.id.doc_view);
        nUIActivity.mNUIPreview = nuiPreview;
        nuiPreview.setOnDoneListener(new NUIView.OnDoneListener() {
            @Override
            public void done() {
                NUIActivity.super.finish();
            }
        });
        if (extras != null) {
            int i2 = extras.getInt(Constant.START_PAGE);
            fromString = SOFileState.fromString(extras.getString("STATE"), SOFileDatabase.getDatabase());
            string = extras.getString("FOREIGN_DATA", null);
            boolean z5 = extras.getBoolean("IS_TEMPLATE", true);
            String string2 = extras.getString("CUSTOM_DOC_DATA");
            if (fromString == null && !z) {
                fromString = SOFileState.getAutoOpen(this);
                if (fromString != null) {
                    str = string2;
                    z4 = z3;
                    z2 = z5;
                    i = i2;
                }
            }
            z3 = z4;
            str = string2;
            z4 = z3;
            z2 = z5;
            i = i2;
        } else {
            fromString = null;
            string = null;
            str = string;
            z2 = true;
            i = 1;
        }
        if (str == null) {
            Utilities.setSessionLoadListener(null);
        }
        if (z4) {
            nUIActivity.mNUIPreview.start(sODocSession, i, string, intent);
        } else if (fromString != null) {
            nUIActivity.mNUIPreview.start(fromString, i, intent);
        } else {
            //pass intent with nuipreview for previewing
            nUIActivity.mNUIPreview.start(intent.getData(), z2, i, str, intent.getType(), intent);
        }

        loadBannerAd();
    }

    private void loadBannerAd() {
        if (Constant.INSTANCE.getShowAdsState().getValue()) {
            adView.setVisibility(View.VISIBLE);
            AdRequest adRequest = new AdRequest.Builder().build();
            adView.loadAd(adRequest);
        } else {
            adView.setVisibility(View.GONE);
        }
    }

    public static void setSession(SODocSession var0) {
        a = var0;
    }

    public Intent childIntent() {
        return this.b;
    }

    protected void doResumeActions() {
        NUIPreview var1 = this.mNUIPreview;
        if (var1 != null) {
            var1.onResume();
        }

    }

    public void finish() {
        if (this.mNUIPreview == null) {
            super.finish();
        } else {
            Utilities.dismissCurrentAlert();
            this.onBackPressed();
        }
    }

    protected void initialise() {
        this.a();
    }

    public boolean isDocModified() {
        NUIPreview var1 = this.mNUIPreview;
        return var1 != null && var1.isDocModified();
    }

    public SOSelectionLimits getSelectionLimits() {
        SOSelectionLimits selectionLimits = null;
        if (this.mNUIPreview != null && this.mNUIPreview.getNUIDocView() != null && this.mNUIPreview.getNUIDocView().getDocView() != null) {
            selectionLimits = this.mNUIPreview.getNUIDocView().getDocView().getSelectionLimits();
        }
        return selectionLimits;
    }

    protected void onActivityResult(int var1, int var2, Intent var3) {
        super.onActivityResult(var1, var2, var3);
        NUIPreview var4 = this.mNUIPreview;
        if (var4 != null) {
            var4.onActivityResult(var1, var2, var3);
        }

    }

    public void onBackPressed() {
        NUIPreview var1 = this.mNUIPreview;
        if (var1 != null) {
            var1.onBackPressed();
        }

    }

    public void onConfigurationChanged(Configuration var1) {
        super.onConfigurationChanged(var1);
        this.mNUIPreview.onConfigurationChange(var1);
    }

    protected void onCreate(Bundle var1) {
        super.onCreate(var1);
        this.initialise();
    }

    protected void onDestroy() {
        super.onDestroy();
        NUIPreview var1 = this.mNUIPreview;
        if (var1 != null) {
            var1.onDestroy();
        }

    }

    public boolean onKeyDown(int var1, KeyEvent var2) {
        long var3 = var2.getEventTime();
        if (this.d == var1 && var3 - this.c <= 100L) {
            return true;
        } else {
            this.c = var3;
            this.d = var1;
            return this.mNUIPreview.doKeyDown(var1, var2);
        }
    }

    public void onNewIntent(final Intent var1) {
        super.onNewIntent(var1);
        ConfigOptions.a();
        if (this.isDocModified()) {
            Utilities.yesNoMessage(this, this.getString(string.sodk_editor_new_intent_title), this.getString(string.sodk_editor_new_intent_body), this.getString(string.sodk_editor_new_intent_yes_button), this.getString(string.sodk_editor_new_intent_no_button), new Runnable() {
                public void run() {
                    if (NUIActivity.this.mNUIPreview != null) {
                        NUIActivity.this.mNUIPreview.endDocSession(true);
                    }

                    NUIActivity.this.a(var1);
                    NUIActivity.this.a(var1, true);
                }
            }, new Runnable() {
                public void run() {
                    SODocSessionLoadListenerCustom var1 = Utilities.getSessionLoadListener();
                    if (var1 != null) {
                        var1.onSessionReject();
                    }

                    Utilities.setSessionLoadListener((SODocSessionLoadListenerCustom) null);
                }
            });
        } else {
            NUIPreview var2 = this.mNUIPreview;
            if (var2 != null) {
                var2.endDocSession(true);
            }

            this.a(var1);
            this.a(var1, true);
        }

    }

    public void onPause() {
        NUIPreview var1 = this.mNUIPreview;
        if (var1 != null) {
            var1.onPause();
            this.mNUIPreview.releaseBitmaps();
        }

        if (Utilities.isChromebook(this)) {
            PrintHelperPdf.setPrinting(false);
        }

        super.onPause();
    }

    protected void onResume() {
        this.b = null;
        super.onResume();
        this.doResumeActions();
    }

    protected void setConfigurableButtons() {
        NUIPreview var1 = this.mNUIPreview;
        if (var1 != null) {
            var1.setConfigurableButtons();
        }

    }

    public void startActivity(Intent var1) {
        try {
            this.b = var1;
            super.startActivity(var1, (Bundle) null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startActivityForResult(Intent var1, int var2) {
        try {
            this.b = var1;
            super.startActivityForResult(var1, var2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startActivityForResult(Intent var1, int var2, Bundle var3) {
        try {
            this.b = var1;
            super.startActivityForResult(var1, var2, var3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
