package com.artifex.sonui.editor;

import static android.content.ClipDescription.MIMETYPE_TEXT_PLAIN;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.supportv1.v4.content.ContextCompat;
import android.supportv1.v4.graphics.drawable.DrawableCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListPopupWindow;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.artifex.solib.ConfigOptions;
import com.artifex.solib.SOBitmap;
import com.artifex.solib.SODoc;
import com.artifex.solib.SODocSaveListener;
import com.artifex.solib.SOLib;
import com.artifex.solib.SOSelectionLimits;
import com.artifex.solib.j;
import com.artifex.solib.k;
import com.artifex.solib.p;
import com.artifex.sonui.editor.AuthorDialog.AuthorDialogListener;
import com.artifex.sonui.editor.NUIView.OnDoneListener;
import com.artifex.sonui.editor.R.color;
import com.artifex.sonui.editor.R.dimen;
import com.artifex.sonui.editor.R.id;
import com.artifex.sonui.editor.R.integer;
import com.artifex.sonui.editor.R.layout;
import com.artifex.sonui.editor.R.string;
import com.artifex.sonui.editor.R.style;
import com.artifex.sonui.editor.SODocSession.SODocSessionLoadListener;

import com.document.docease.BuildConfig;
import com.document.docease.ui.module.editors.HomeViewModel;
import com.document.docease.ui.module.editors.ViewEditorActivity;

import com.document.docease.utils.Constant;
import com.document.docease.utils.FileUtil;
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader;
import com.tom_roush.pdfbox.io.MemoryUsageSetting;
import com.tom_roush.pdfbox.pdmodel.PDDocument;
import com.tom_roush.pdfbox.text.PDFTextStripperByArea;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class NUIDocView extends FrameLayout implements OnClickListener, OnTabChangeListener, DocViewHost {
    public static int OVERSIZE_MARGIN;
    public static final int OVERSIZE_PERCENT = 20;
    private static NUIDocView ak;
    private SOTextView A;
    private SOTextView C;
    private ToolbarButton D;
    private ToolbarButton E;
    private SOTextView F;
    private ToolbarButton G;
    private LinearLayout H;
    private LinearLayout I;
    private LinearLayout J;
    private LinearLayout K;
    private SOTextView L;
    private int M = 0;
    private long N = 0L;
    private LinearLayout O;
    private LinearLayout P;
    private LinearLayout Q;
    private int R = 0;
    private SOBitmap[] S = new SOBitmap[]{null, null};
    private boolean T = false;
    private String U = null;
    private int V = -1;
    private boolean W = false;
    OnDoneListener a = null;
    private boolean aa = false;
    private int ab = 0;
    private final int ac = 2;
    private ListPopupWindow ad;
    private ArrayList<String> ae = new ArrayList();
    private TabHost af = null;
    private TabData[] ag = null;
    private k ah;
    private ArrayList<String> ai = new ArrayList();
    private InputView aj = null;
    private View al = null;
    private int am = 0;
    private int an = 0;
    private boolean ao = false;
    private boolean ap = false;
    private String aq = "";
    private long ar = 0L;
    private ProgressDialog as = null;
    private Runnable at = null;
    private boolean au = false;
    private Toast av;
    private boolean aw = false;
    private final String b = "NUIDocView";
    private boolean c = false;
    private boolean d = true;
    private Boolean e;
    private SOFileState f;
    private SOFileDatabase g;
    private ProgressDialog h;
    private DocView i;
    private DocListPagesView j;
    private String k;
    protected boolean keyboardShown = false;
    private String l;
    private p m = null;
    protected PageAdapter mAdapter;
    protected ImageView mBackButton;
    protected ConfigOptions mConfigOptions = null;
    protected ToolbarButton mDecreaseIndentButton;
    protected boolean mFinished = false;
    protected ToolbarButton mIncreaseIndentButton;
    protected LinearLayout mInsertImageButton;
    protected LinearLayout mInsertPhotoButton;
    protected ImageView imgInsertImageButton;
    protected ImageView imgInsertPhotoButton;
    protected TextView tvInsertImageButton;
    protected TextView tvInsertPhotoButton;
    protected boolean mIsSession = false;
    protected ToolbarButton mListBulletsButton;
    protected ToolbarButton mListNumbersButton;
    protected ToolbarButton mOpenInButton;
    protected ToolbarButton mOpenPdfInButton;
    protected int mPageCount;
    protected ToolbarButton mProtectButton;
    protected ImageView mRedoButton;

    protected LinearLayout mSaveAsButton;
    protected LinearLayout mSaveButton;
    protected LinearLayout mSavePdfButton;
    protected LinearLayout mPrintButton;
    protected LinearLayout mShareButton;
    private LinearLayout mPreviewPrint;
    private LinearLayout editorHighlight;
    private TextView editorExpandFontTv;
    private TextView editorExpandFontNameTv;
    private LinearLayout editorFontFamily;
    private ImageView editorExpandFontUp;
    private ImageView editorExpandFontDown;
    private LinearLayout editorExpandFont;
    private ImageView editorExpandInsertImage;
    private ImageView editorExpandInsertPhoto;
    private LinearLayout editorExpandOptions;
    private ImageView editorExpandBulletNumber;
    private ImageView editorExpandBulletDots;


    //toolbar options
    private TextView editorToolbarDone;

    private ImageView editorToolbarShare;
    private ImageView editorToolbarCopy;
    private ImageView editorToolbarUndo;
    private ImageView editorToolbarRedo;
    private ImageView editorToolbarSearch;
    private LinearLayout editorSearchLayout;
    private ImageView editorCloseSearch;
    private LinearLayout previewSearch;
    private TextView tvSave;
    private TextView tvPrint;

    private LinearLayout layoutUndo;
    private LinearLayout layoutRedo;


    protected SODocSession mSession;
    protected Uri mStartUri = null;
    protected SOFileState mState = null;
    protected ToolbarButton mStyleBoldButton;
    protected ToolbarButton mStyleItalicButton;
    protected ToolbarButton mStyleLinethroughButton;
    protected ToolbarButton mStyleUnderlineButton;
    protected ImageView mUndoButton;
    private SODataLeakHandlers n;
    private ToolbarButton o;
    private ToolbarButton p;
    protected ProgressCallback progressCallBack;
    private ToolbarButton q;
    private ToolbarButton r;
    private ToolbarButton s;
    private ToolbarButton t;
    protected Map<String, View> tabMap = new HashMap();
    private Button u;
    private Button v;
    private ImageView w;
    private SOEditText x;
    private LinearLayout y;
    private LinearLayout z;
    private HomeViewModel homeViewModel;
    private NUIDocView context;
    private LinearLayout editorStyle;

    private PDDocument document;
    private Boolean isPdf = false;
    private Boolean isPreview = false;
    private PopupWindow popupWindow;
    private PopupWindow popupWindowPaste;

    private View popupView;
    private TextView copyView;
    private TextView pasteView;
    private View lineView;
    private boolean isBeingSelected = false;


    public NUIDocView(Context var1) {
        super(var1);
        this.a(var1);
    }

    public NUIDocView(Context var1, AttributeSet var2) {
        super(var1, var2);
        this.a(var1);
    }

    public NUIDocView(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
        this.a(var1);
    }

    private int a(ListAdapter var1) {
        int var2 = 0;
        int var3 = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        int var4 = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        int var5 = var1.getCount();
        FrameLayout var6 = null;
        Object var7 = var6;
        int var8 = 0;

        FrameLayout var12;
        for (int var9 = 0; var2 < var5; var6 = var12) {
            int var10 = var1.getItemViewType(var2);
            int var11 = var9;
            if (var10 != var9) {
                var7 = null;
                var11 = var10;
            }

            var12 = var6;
            if (var6 == null) {
                var12 = new FrameLayout(this.getContext());
            }

            var7 = var1.getView(var2, (View) var7, var12);
            ((View) var7).measure(var3, var4);
            var10 = ((View) var7).getMeasuredWidth();
            var9 = var8;
            if (var10 > var8) {
                var9 = var10;
            }

            ++var2;
            var8 = var9;
            var9 = var11;
        }

        return var8;
    }

    private void a() {
        int var1 = this.al.getHeight();
        int var2 = var1 * 15 / 100;
        Rect var3 = new Rect();
        this.al.getWindowVisibleDisplayFrame(var3);
        this.am = var1 - var3.bottom;
        Resources var4 = this.getContext().getResources();
        var1 = var4.getIdentifier("config_showNavigationBar", "bool", "android");
        if (var1 > 0 && var4.getBoolean(var1) || Utilities.isEmulator()) {
            var1 = var4.getIdentifier("navigation_bar_height", "dimen", "android");
            if (var1 > 0) {
                var1 = var4.getDimensionPixelSize(var1);
            } else {
                var1 = 0;
            }

            this.am -= var1;
        }

        if (this.am >= var2) {
            if (!this.keyboardShown) {
                this.onShowKeyboard(true);
            }
        } else {
            this.am = 0;
            if (this.keyboardShown) {
                this.onShowKeyboard(false);
            }
        }

    }

    private void a(Context var1) {
        this.al = ((Activity) this.getContext()).getWindow().getDecorView();
        com.artifex.solib.a.a(var1);
        this.V = var1.getResources().getConfiguration().keyboard;
        this.mConfigOptions = ConfigOptions.a();
    }

    private void a(View var1) {
        if (!this.mFinished) {
            if (this.getDocView() != null) {
                if (!this.isFullScreen()) {
                    if (this.mConfigOptions.A()) {
                        this.aw = true;
                        Utilities.hideKeyboard(this.getContext());
                        this.getDocView().onFullscreen(true);
                        this.onFullScreenHide();
                        if (this.av == null) {
                            String var2 = this.getContext().getString(string.sodk_editor_fullscreen_warning);
                            Toast var3 = Toast.makeText(this.getContext(), var2, Toast.LENGTH_SHORT);
                            this.av = var3;
                            var3.setGravity(53, 0, 0);
                            ((TextView) ((ViewGroup) this.av.getView()).getChildAt(0)).setTextSize(16.0F);
                        }

                        this.av.show();
                    }
                }
            }
        }
    }

    private void a(View var1, int var2, float var3) {
        var1 = var1.findViewById(var2);
        var1.setScaleX(var3);
        var1.setScaleY(var3);
    }

    private void a(View var1, boolean var2) {
        var1.setEnabled(var2);
        if (var1 instanceof ViewGroup) {
            ViewGroup var5 = (ViewGroup) var1;

            for (int var3 = 0; var3 < var5.getChildCount(); ++var3) {
                View var4 = var5.getChildAt(var3);
                if (var4 != this.mBackButton) {
                    this.a(var4, var2);
                }
            }
        }

    }

    private void a(Button var1, boolean var2) {
        var1.setEnabled(var2);
        int var3 = ContextCompat.getColor(this.activity(), color.sodk_editor_header_button_enabled_tint);
        if (!var2) {
            var3 = ContextCompat.getColor(this.activity(), color.sodk_editor_header_button_disabled_tint);
        }

        this.setButtonColor(var1, var3);
    }

    private void a(String var1) {
        this.ah = com.artifex.solib.k.a(this.activity(), var1);
        Point var4 = Utilities.getRealScreenSize(this.activity());
        int var2 = Math.max(var4.x, var4.y);
        int var3 = var2 * 120 / 100;
        OVERSIZE_MARGIN = (var3 - var2) / 2;
        var2 = 0;

        while (true) {
            SOBitmap[] var5 = this.S;
            if (var2 >= var5.length) {
                return;
            }

            var5[var2] = this.ah.a(var3, var3);
            ++var2;
        }
    }

    private void a(final boolean var1) {
        if (this.n != null && this.f != null) {
            this.preSaveQuestion(new Runnable() {
                public void run() {
                    String var1x;
                    boolean var10001;
                    try {
                        var1x = NUIDocView.this.f.getUserPath();
                    } catch (UnsupportedOperationException var7) {
                        var10001 = false;
                        return;
                    }

                    String var2 = var1x;
                    if (var1x == null) {
                        try {
                            var2 = NUIDocView.this.f.getOpenedPath();
                        } catch (UnsupportedOperationException var6) {
                            var10001 = false;
                            return;
                        }
                    }

                    try {
                        String originalFileName = null;
                        Bundle intentData = activity().getIntent().getExtras();
                        if (intentData != null && intentData.containsKey(Constant.FILE_NAME)) {
                            originalFileName = intentData.getString(Constant.FILE_NAME);
                        }
                        File var8 = new File(var2);
                        SODataLeakHandlers var9 = NUIDocView.this.n;
                        var1x = var8.getName();

                        if (originalFileName != null && !var1x.equals(originalFileName)) {
                            var1x = originalFileName;
                        }
                        SODoc var3 = NUIDocView.this.mSession.getDoc();
                        SOSaveAsComplete var4 = new SOSaveAsComplete() {
                            public void onComplete(int var1x, String var2) {
                                if (var1x == 0) {
                                    NUIDocView.this.setFooterText(var2);
                                    NUIDocView.this.f.setUserPath(var2);
                                    if (var1) {
                                        NUIDocView.this.prefinish();
                                    }

                                    if (NUIDocView.this.mFinished) {
                                        return;
                                    }

                                    NUIDocView.this.f.setHasChanges(false);
                                    NUIDocView.this.onSelectionChanged();
                                    NUIDocView.this.reloadFile();
                                    activity().finish();
                                } else {
                                    NUIDocView.this.f.setUserPath((String) null);
                                }

                                NUIDocView.this.T = NUIDocView.this.f.isTemplate();
                            }
                        };
                        var9.saveAsHandler(var1x, var3, var4);
                    } catch (UnsupportedOperationException var5) {
                        var10001 = false;
                    }
                }
            }, new Runnable() {
                public void run() {
                }
            });
        }

//        else {
//            throw new UnsupportedOperationException();
//        }
    }

    private void b() {
        this.a((View) this, false);
    }

    private void b(View var1, boolean var2) {
        //GradientDrawable gradientDrawable = (GradientDrawable) ((LayerDrawable) var1.getBackground()).findDrawableByLayerId(id.tab_bg_color);
        SOTextView tvTitleTab = (SOTextView) var1.findViewById(id.tabText);
        byte var4 = 0;
        int var5 = 0;
        if (var2) {
            //gradientDrawable.setColor(this.getTabSelectedColor());
            //tvTitleTab.setTextColor(this.getTabSelectedTextColor());
            Drawable[] var8 = tvTitleTab.getCompoundDrawables();

            for (int var12 = var8.length; var5 < var12; ++var5) {
                Drawable var10 = var8[var5];
                if (var10 != null) {
                    var10.setColorFilter(this.getTabSelectedTextColor(), Mode.SRC_IN);
                }
            }
        } else {
            //gradientDrawable.setColor(this.getTabUnselectedColor());
            //tvTitleTab.setTextColor(this.getTabUnselectedTextColor());
            Drawable[] var11 = tvTitleTab.getCompoundDrawables();
            int var6 = var11.length;

            for (var5 = var4; var5 < var6; ++var5) {
                Drawable var9 = var11[var5];
                if (var9 != null) {
                    var9.setColorFilter(this.getTabUnselectedTextColor(), Mode.SRC_IN);
                }
            }
        }

    }

    private void c() {
        try {
            this.c = false;
            final ViewGroup var1 = (ViewGroup) ((LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(this.getLayoutId(), (ViewGroup) null);
            this.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                public void onGlobalLayout() {
                    NUIDocView.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    if (!NUIDocView.this.c) {
                        View var1x = var1.findViewById(id.tabhost);
                        byte var2 = 8;
                        byte var3;
                        if (var1x != null) {
                            if (NUIDocView.this.mConfigOptions.b()) {
                                var3 = 0;
                            } else {
                                var3 = 8;
                            }

                            var1x.setVisibility(var3);
                        }

                        var1x = var1.findViewById(id.footer);
                        var3 = var2;
                        if (NUIDocView.this.mConfigOptions.b()) {
                            var3 = 0;
                        }

                        var1x.setVisibility(var3);
                        NUIDocView.this.afterFirstLayoutComplete();
                        NUIDocView.this.c = true;
                    }

                }
            });
            this.addView(var1);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static NUIDocView currentNUIDocView() {
        return ak;
    }

    private void d() {
        label66:
        {
            String var14;
            label55:
            {
                label54:
                {
                    label53:
                    {
                        label52:
                        {
                            j var1;
                            boolean var10001;
                            try {
                                com.artifex.solib.k.b(this.activity());
                                var1 = SOLib.e();
                            } catch (ExceptionInInitializerError var10) {
                                var10001 = false;
                                break label54;
                            } catch (LinkageError var11) {
                                var10001 = false;
                                break label53;
                            } catch (SecurityException var12) {
                                var10001 = false;
                                break label52;
                            } catch (Exception var13) {
                                var10001 = false;
                                break label66;
                            }

                            if (var1 != null) {
                                try {
                                    var1.a(this.activity());
                                    return;
                                } catch (ExceptionInInitializerError var2) {
                                    var10001 = false;
                                    break label54;
                                } catch (LinkageError var3) {
                                    var10001 = false;
                                    break label53;
                                } catch (SecurityException var4) {
                                    var10001 = false;
                                } catch (Exception var5) {
                                    var10001 = false;
                                    break label66;
                                }
                            } else {
                                try {
                                    ClassNotFoundException var15 = new ClassNotFoundException();
                                    throw var15;
                                } catch (ExceptionInInitializerError var6) {
                                    var10001 = false;
                                    break label54;
                                } catch (LinkageError var7) {
                                    var10001 = false;
                                    break label53;
                                } catch (SecurityException var8) {
                                    var10001 = false;
                                } catch (ClassNotFoundException var9) {
                                    var10001 = false;
                                    break label66;
                                }
                            }
                        }

                        var14 = String.format("initClipboardHandler() experienced unexpected exception [%s]", "SecurityException");
                        break label55;
                    }

                    var14 = String.format("initClipboardHandler() experienced unexpected exception [%s]", "LinkageError");
                    break label55;
                }

                var14 = String.format("initClipboardHandler() experienced unexpected exception [%s]", "ExceptionInInitializerError");
            }

            Log.e("NUIDocView", var14);
            return;
        }

        Log.i("NUIDocView", "initClipboardHandler implementation unavailable");
    }

    private void e() {
        String var17;
        label78:
        {
            label67:
            {
                label66:
                {
                    label65:
                    {
                        label64:
                        {
                            label63:
                            {
                                label62:
                                {
                                    SODataLeakHandlers var1;
                                    boolean var10001;
                                    try {
                                        var1 = Utilities.getDataLeakHandlers();
                                        this.n = var1;
                                    } catch (ExceptionInInitializerError var12) {
                                        var10001 = false;
                                        break label66;
                                    } catch (LinkageError var13) {
                                        var10001 = false;
                                        break label65;
                                    } catch (SecurityException var14) {
                                        var10001 = false;
                                        break label64;
                                    } catch (Exception var15) {
                                        var10001 = false;
                                        break label63;
                                    }

                                    if (var1 != null) {
                                        try {
                                            var1.initDataLeakHandlers(this.activity());
                                            return;
                                        } catch (ExceptionInInitializerError var2) {
                                            var10001 = false;
                                            break label66;
                                        } catch (LinkageError var3) {
                                            var10001 = false;
                                            break label65;
                                        } catch (SecurityException var4) {
                                            var10001 = false;
                                            break label64;
                                        } catch (IOException var6) {
                                            var10001 = false;
                                        }
                                    } else {
                                        try {
                                            ClassNotFoundException var18 = new ClassNotFoundException();
                                            throw var18;
                                        } catch (ExceptionInInitializerError var7) {
                                            var10001 = false;
                                            break label66;
                                        } catch (LinkageError var8) {
                                            var10001 = false;
                                            break label65;
                                        } catch (SecurityException var9) {
                                            var10001 = false;
                                            break label64;
                                        } catch (ClassNotFoundException var10) {
                                            var10001 = false;
                                            break label63;
                                        } catch (Exception var11) {
                                            var10001 = false;
                                        }
                                    }
                                }

                                var17 = "DataLeakHandlers IOException";
                                break label78;
                            }

                            var17 = "DataLeakHandlers implementation unavailable";
                            break label78;
                        }

                        var17 = String.format("setDataLeakHandlers() experienced unexpected exception [%s]", "SecurityException");
                        break label67;
                    }

                    var17 = String.format("setDataLeakHandlers() experienced unexpected exception [%s]", "LinkageError");
                    break label67;
                }

                var17 = String.format("setDataLeakHandlers() experienced unexpected exception [%s]", "ExceptionInInitializerError");
            }

            Log.e("NUIDocView", var17);
            return;
        }

        Log.i("NUIDocView", var17);
    }

    private void f() {
        Point var1 = Utilities.getRealScreenSize(this.activity());
        byte var2;
        if (var1.x > var1.y) {
            var2 = 2;
        } else {
            var2 = 1;
        }

        label18:
        {
            if (!this.ao) {
                int var3 = this.an;
                if (var2 == var3 || var3 == 0) {
                    break label18;
                }
            }

            this.onOrientationChange();
        }

        this.ao = false;
        this.an = var2;
    }

    private void g() {
        this.i.requestLayout();
        if (this.usePagesView() && this.isPageListVisible()) {
            this.j.requestLayout();
        }

    }

    private View getSingleTabView() {
        int var1 = this.af.getTabWidget().getTabCount();
        return this.af.getTabWidget().getChildTabViewAt(var1 - 1);
    }

    private void h() {
        Utilities.hideKeyboard(this.getContext());
    }

    private void i() {
        ProgressDialog var1 = new ProgressDialog(this.getContext(), style.sodk_editor_alert_dialog_style);
        this.h = var1;
        var1.setMessage(this.getContext().getString(string.sodk_editor_loading_please_wait));
        this.h.setCancelable(false);
        this.h.setIndeterminate(true);
        Window var2 = this.h.getWindow();
        var2.setFlags(8, 8);
        var2.clearFlags(2);
        this.h.show();
    }

    private void j() {
        ProgressDialog var1 = this.h;
        if (var1 != null && activity() != null && !activity().isFinishing() && !activity().isDestroyed()) {
            var1.dismiss();
            this.h = null;
        }

    }

    private void k() {
        if (this.m == null) {
            this.m = new p() {
                public void a() {
                    NUIDocView.this.v();
                    Utilities.yesNoMessage((Activity) NUIDocView.this.getContext(), NUIDocView.this.getResources().getString(string.sodk_editor_no_more_found), NUIDocView.this.getResources().getString(string.sodk_editor_keep_searching), NUIDocView.this.getResources().getString(string.sodk_editor_str_continue), NUIDocView.this.getResources().getString(string.sodk_editor_stop), new Runnable() {
                        public void run() {
                            (new Handler()).post(new Runnable() {
                                public void run() {
                                    NUIDocView.this.t();
                                }
                            });
                        }
                    }, new Runnable() {
                        public void run() {
                        }
                    });
                }

                public void a(int var1) {
                }

                public void a(int var1, RectF var2) {
                    NUIDocView.this.v();
                    NUIDocView.this.getDocView().onFoundText(var1, var2);
                }

                public boolean b() {
                    NUIDocView.this.t();
                    return true;
                }

                public boolean c() {
                    NUIDocView.this.t();
                    return true;
                }

                public void d() {
                    NUIDocView.this.v();
                }

                public void e() {
                    NUIDocView.this.v();
                }
            };
            this.mSession.getDoc().a(this.m);
        }

        this.mSession.getDoc().c(false);
    }

    private void l() {
        int var1 = this.af.getTabWidget().getTabCount();

        for (int var2 = 1; var2 < var1 - 1; ++var2) {
            this.af.getTabWidget().getChildAt(var2).setVisibility(GONE);
        }

    }

    private void m() {
        int var1 = this.af.getTabWidget().getTabCount();

        for (int var2 = 1; var2 < var1 - 1; ++var2) {
            this.af.getTabWidget().getChildAt(var2).setVisibility(VISIBLE);
        }

    }

    private void n() {
        if (this.mListNumbersButton.isSelected()) {
            this.mSession.getDoc().B();
        } else if (this.mListBulletsButton.isSelected()) {
            this.mSession.getDoc().C();
        } else {
            this.mSession.getDoc().A();
        }

    }

    private boolean o() {
        int[] var1 = this.mSession.getDoc().getIndentationLevel();
        return var1 != null && var1[0] < var1[1];
    }

    private boolean p() {
        int[] var1 = this.mSession.getDoc().getIndentationLevel();
        return var1 != null && var1[0] > 0;
    }

    private void q() {
        DocView var1 = this.i;
        if (var1 != null) {
            var1.releaseBitmaps();
        }

        if (this.usePagesView()) {
            DocListPagesView var3 = this.j;
            if (var3 != null) {
                var3.releaseBitmaps();
            }
        }

        int var2 = 0;

        while (true) {
            SOBitmap[] var4 = this.S;
            if (var2 >= var4.length) {
                return;
            }

            if (var4[var2] != null) {
                var4[var2].a().recycle();
                this.S[var2] = null;
            }

            ++var2;
        }
    }

    private void r() {
        if (this.ag != null) {
            TabData[] var1 = this.getTabData();
            ListPopupWindow var2;
            if (!this.mConfigOptions.k() && !this.mConfigOptions.l()) {
                if (this.aq.equals(this.ag[2].name)) {
                    String var3 = this.ag[this.getInitialTab()].name;
                    this.changeTab(var3);
                    this.setSingleTabTitle(var3);
                    this.af.setCurrentTabByTag(var3);
                    this.b(this.getSingleTabView(), true);
                }

                var2 = this.ad;
                if (var2 != null && var2.isShowing() && var1[2].visibility != 8) {
                    this.ad.dismiss();
                }

                var1[2].visibility = 8;
            } else {
                var2 = this.ad;
                if (var2 != null && var2.isShowing() && var1[2].visibility != 0) {
                    this.ad.dismiss();
                }

                var1[2].visibility = 0;
            }

        }
    }

    private void s() {
        DocView var1 = this.i;
        if (var1 != null) {
            var1.setBitmaps(this.S);
        }

        if (this.usePagesView()) {
            DocListPagesView var2 = this.j;
            if (var2 != null) {
                var2.setBitmaps(this.S);
            }
        }

    }

    private void setFooterText(String var1) {
        if (var1 != null && !var1.isEmpty()) {
            String var2 = (new File(var1)).getName();
            if (var2 != null && !var2.isEmpty()) {
                this.L.setText(var2);
            } else {
                this.L.setText(var1);
            }
        }

    }

    private void setSingleTabTitle(String var1) {
        if (!var1.equalsIgnoreCase(this.getContext().getString(string.sodk_editor_tab_hidden))) {
            int var2 = this.af.getTabWidget().getTabCount();
            ((SOTextView) this.af.getTabWidget().getChildTabViewAt(var2 - 1).findViewById(id.tabText)).setText(var1);
        }
    }

    private void setTab(String var1) {
        this.aq = var1;
        ((TabHost) this.findViewById(id.tabhost)).setCurrentTabByTag(this.aq);
        this.setSingleTabTitle(var1);
        if (Utilities.isPhoneDevice(this.activity())) {
            this.scaleHeader();
        }

    }

    private void setValid(boolean var1) {
        DocView var2 = this.i;
        if (var2 != null) {
            var2.setValid(var1);
        }

        if (this.usePagesView()) {
            DocListPagesView var3 = this.j;
            if (var3 != null) {
                var3.setValid(var1);
            }
        }

    }

    private void setupForDocType(String var1) {
        this.a(var1);
        this.s();
    }

    private void t() {
        Utilities.hideKeyboard(this.getContext());
        this.u();
        String var1 = this.x.getText().toString();
        SODoc var2 = this.getDoc();
        var2.b(var1);
        var2.q();
    }

    private void u() {
        if (this.as == null) {
            (new Handler()).postDelayed(new Runnable() {
                public void run() {
                    SODoc var1 = NUIDocView.this.getDoc();
                    if (var1 != null && NUIDocView.this.as == null && var1.p()) {
                        NUIDocView var3 = NUIDocView.this;
                        var3.as = new ProgressDialog(var3.getContext(), style.sodk_editor_alert_dialog_style);
                        ProgressDialog var4 = NUIDocView.this.as;
                        StringBuilder var2 = new StringBuilder();
                        var2.append(NUIDocView.this.getResources().getString(string.sodk_editor_searching));
                        var2.append("...");
                        var4.setMessage(var2.toString());
                        NUIDocView.this.as.setCancelable(false);
                        NUIDocView.this.as.setButton(-2, NUIDocView.this.getResources().getString(string.sodk_editor_cancel), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface var1, int var2) {
                                NUIDocView.this.getDoc().cancelSearch();
                            }
                        });
                        NUIDocView.this.as.show();
                    }

                }
            }, 1000L);
        }
    }

    private void v() {
        ProgressDialog var1 = this.as;
        if (var1 != null) {
            var1.dismiss();
            this.as = null;
        }

    }

    private void w() {
        DocView var1 = this.getDocView();
        var1.smoothScrollBy(0, var1.getHeight() * 9 / 10, 400);
    }

    private void x() {
        DocView var1 = this.getDocView();
        var1.smoothScrollBy(0, -var1.getHeight() * 9 / 10, 400);
    }

    private void y() {
        DocView var1 = this.getDocView();
        var1.smoothScrollBy(0, var1.getHeight() * 1 / 20, 100);
    }

    private void z() {
        DocView var1 = this.getDocView();
        var1.smoothScrollBy(0, -var1.getHeight() * 1 / 20, 100);
    }

    protected Activity activity() {
        return (Activity) this.getContext();
    }

    public void addDeleteOnClose(String var1) {
        this.ai.add(var1);
    }

    protected void afterFirstLayoutComplete() {
        this.mFinished = false;
        if (this.mConfigOptions.r()) {
            SOFileDatabase.init(this.activity());
        }

        this.createEditButtons();
        this.createEditButtons2();
        this.createReviewButtons();
        this.createPagesButtons();
        this.createInsertButtons();
        this.mBackButton = (ImageView) this.createToolbarButton(id.back_button);
        this.mUndoButton = (ImageView) this.createToolbarButton(id.undo_button);
        this.mRedoButton = (ImageView) this.createToolbarButton(id.redo_button);
        this.u = (Button) this.createToolbarButton(id.search_button);
        this.v = (Button) this.createToolbarButton(id.fullscreen_button);
        this.y = (LinearLayout) this.createToolbarButton(id.search_next);
        this.z = (LinearLayout) this.createToolbarButton(id.search_previous);
        Button var1;
        if (!this.hasSearch()) {
            var1 = this.u;
            if (var1 != null) {
                var1.setVisibility(GONE);
            }
        }

        if (!this.hasUndo()) {
            if (this.mUndoButton != null) {
                this.mUndoButton.setVisibility(GONE);
            }
        }

        if (!this.hasRedo()) {
            if (this.mRedoButton != null) {
                this.mRedoButton.setVisibility(GONE);
            }
        }

        if (!this.mConfigOptions.A()) {
            var1 = this.v;
            if (var1 != null) {
                var1.setVisibility(GONE);
            }
        }

        if (!this.mConfigOptions.c()) {
            if (this.mUndoButton != null) {
                this.mUndoButton.setVisibility(GONE);
            }

            if (this.mRedoButton != null) {
                this.mRedoButton.setVisibility(GONE);
            }
        }

        this.showSearchSelected(false);
        this.x = (SOEditText) this.findViewById(id.search_text_input);
        this.A = (SOTextView) this.findViewById(id.footer_page_text);
        this.x.setOnEditorActionListener(new SOEditTextOnEditorActionListener() {
            public boolean onEditorAction(SOEditText var1, int var2, KeyEvent var3) {
                if (var2 == 5) {
                    NUIDocView.this.onSearchNext((View) null);
                    return true;
                } else {
                    return false;
                }
            }
        });
        this.x.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View var1, int var2, KeyEvent var3) {
                return var2 == 67 && NUIDocView.this.x.getSelectionStart() == 0 && NUIDocView.this.x.getSelectionEnd() == 0;
            }
        });
        this.y.setEnabled(false);
        this.z.setEnabled(false);
        this.x.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable var1) {
            }

            public void beforeTextChanged(CharSequence var1, int var2, int var3, int var4) {
            }

            public void onTextChanged(CharSequence var1, int var2, int var3, int var4) {
                NUIDocView.this.setSearchStart();
                var2 = var1.toString().length();
                boolean var6 = false;
                boolean var7;
                if (var2 > 0) {
                    var7 = true;
                } else {
                    var7 = false;
                }

                NUIDocView.this.y.setEnabled(var7);
                var7 = var6;
                if (var1.toString().length() > 0) {
                    var7 = true;
                }

                NUIDocView.this.z.setEnabled(var7);
            }
        });
        this.x.setCustomSelectionActionModeCallback(Utilities.editFieldDlpHandler);
        ImageView var3 = (ImageView) this.findViewById(id.search_text_clear);
        this.w = var3;
        var3.setOnClickListener(new OnClickListener() {
            public void onClick(View var1) {
                NUIDocView.this.x.setText("");
            }
        });
        this.mSaveButton = (LinearLayout) this.createToolbarButton(id.save_button);
        this.mSaveAsButton = (LinearLayout) this.createToolbarButton(id.save_as_button);
        this.mSavePdfButton = (LinearLayout) this.createToolbarButton(id.save_pdf_button);
        this.mPrintButton = (LinearLayout) this.createToolbarButton(id.print_button);
        this.mShareButton = (LinearLayout) this.createToolbarButton(MainR.getMainAppInt("share_file"));
        this.mPreviewPrint = (LinearLayout) this.createToolbarButton(MainR.getMainAppInt("imv_activity_preview_print"));
//        this.shareButton = (LinearLayout) this.createToolbarButton(com.document.docease.R.id.imv_share_button_text);
//        this.editorHighlight = (LinearLayout) this.createToolbarButton(MainR.getMainAppInt("imv_activity_editor_highlight"));
        this.editorExpandFontTv = (TextView) this.createToolbarButton(MainR.getMainAppInt("editor_expand_font_tv"));
        this.editorExpandFontUp = (ImageView) this.createToolbarButton(MainR.getMainAppInt("editor_expand_font_up"));
        this.editorExpandFontDown = (ImageView) this.createToolbarButton(MainR.getMainAppInt("editor_expand_font_down"));
        this.editorExpandFontNameTv = (TextView) this.createToolbarButton(MainR.getMainAppInt("editor_expand_font_name"));
        this.editorExpandFont = (LinearLayout) this.createToolbarButton(MainR.getMainAppInt("editor_expand_font"));
        this.editorExpandInsertImage = (ImageView) this.createToolbarButton(MainR.getMainAppInt("editor_expand_insert_image"));
        this.editorExpandInsertPhoto = (ImageView) this.createToolbarButton(MainR.getMainAppInt("editor_expand_insert_photo"));
//        this.editorFontFamily = (LinearLayout) this.createToolbarButton(MainR.getMainAppInt("imv_activity_editor_font_family"));
        this.layoutUndo = (LinearLayout) this.createToolbarButton(MainR.getMainAppInt("imv_activity_editor_undo"));
        this.layoutRedo = (LinearLayout) this.createToolbarButton(MainR.getMainAppInt("imv_activity_editor_redo"));

        this.editorToolbarDone = (TextView) this.createToolbarButton(MainR.getMainAppInt("toolbar_done"));
        this.editorToolbarShare = (ImageView) this.createToolbarButton(MainR.getMainAppInt("toolbar_share"));
        this.editorToolbarCopy = (ImageView) this.createToolbarButton(MainR.getMainAppInt("toolbar_copy"));
        this.editorToolbarUndo = (ImageView) this.createToolbarButton(MainR.getMainAppInt("toolbar_undo"));
        this.editorToolbarRedo = (ImageView) this.createToolbarButton(MainR.getMainAppInt("toolbar_redo"));
        this.editorToolbarSearch = (ImageView) this.createToolbarButton(MainR.getMainAppInt("toolbar_search"));
        this.editorSearchLayout = (LinearLayout) this.createToolbarButton(MainR.getMainAppInt("editor_search_layout"));
        this.editorCloseSearch = (ImageView) this.createToolbarButton(MainR.getMainAppInt("editor_toolbar_close"));
//        this.previewSearch = (LinearLayout) this.createToolbarButton(MainR.getMainAppInt("other_doc_search_tool"));
        this.editorExpandBulletNumber = (ImageView) this.createToolbarButton(MainR.getMainAppInt("editor_expand_bullets_number"));
        this.editorExpandBulletDots = (ImageView) this.createToolbarButton(MainR.getMainAppInt("editor_expand_bullets_dots"));
//        this.tvSave = (TextView) this.createToolbarButton(com.document.docease.R.id.editor_save);
//        this.tvPrint = (TextView) this.createToolbarButton(com.document.docease.R.id.editor_print);
        this.o = (ToolbarButton) this.createToolbarButton(id.share_button);
        this.mOpenInButton = (ToolbarButton) this.createToolbarButton(id.open_in_button);
        this.mOpenPdfInButton = (ToolbarButton) this.createToolbarButton(id.open_pdf_in_button);
        this.mProtectButton = (ToolbarButton) this.createToolbarButton(id.protect_button);
        int var2 = this.getContext().getResources().getIdentifier("custom_save_button", "id", this.getContext().getPackageName());
        if (var2 != 0) {
            this.p = (ToolbarButton) this.createToolbarButton(var2);
        }

        this.setupTabs();
        View var4;
        if (!com.artifex.solib.k.e(this.activity()) && this.mConfigOptions.a != null) {
            var4 = (View) this.tabMap.get(this.getContext().getString(string.sodk_editor_tab_review));
            if (var4 != null) {
                this.mConfigOptions.a.a(var4);
            }
        }
        context = this;

        //using viewmodel and live data to observe clicks
        homeViewModel = new ViewModelProvider((ViewModelStoreOwner) activity()).get(HomeViewModel.class);


        homeViewModel.getTriggerShare().observe((LifecycleOwner) activity(), aBoolean -> {
            if (context != null) {
                if (f != null) {
                    shareDocument(new File(f.getOpenedPath()));
                } else {
                    context.onShareButton();
                }
            }
        });


        homeViewModel.getTriggerUndo().observe((LifecycleOwner) activity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (context != null) {
                    context.doUndo();
                }
            }
        });

        homeViewModel.getTriggerRedo().observe((LifecycleOwner) activity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (context != null) {
                    context.doRedo();
                }
            }
        });


        homeViewModel.getTriggerBold().observe((LifecycleOwner) activity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (context != null) {
                    context.doBold();
                }
            }
        });
        homeViewModel.getTriggerItalic().observe((LifecycleOwner) activity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (context != null) {
                    context.doItalic();
                }
            }
        });
        homeViewModel.getTriggerUnderline().observe((LifecycleOwner) activity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (context != null) {
                    context.doUnderline();
                }
            }
        });
        homeViewModel.getTriggerAlignLeft().observe((LifecycleOwner) activity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (context != null) {
                    context.onAlignLeftButton(context.q);
                }
            }
        });
        homeViewModel.getTriggerAlignRight().observe((LifecycleOwner) activity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (context != null) {
                    context.onAlignRightButton(context.s);
                }
            }
        });
        homeViewModel.getTriggerAlignCenter().observe((LifecycleOwner) activity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (context != null) {
                    context.onAlignCenterButton(context.r);
                }
            }
        });
        homeViewModel.getTriggerJustify().observe((LifecycleOwner) activity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (context != null) {
                    context.onAlignJustifyButton(context.t);
                }
            }
        });

        homeViewModel.getTriggerInsertImage().observe((LifecycleOwner) activity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (context != null) {
                    context.onInsertImageButton(context.imgInsertImageButton);
                }
            }
        });
        homeViewModel.getTriggerCameraImage().observe((LifecycleOwner) activity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (context != null) {
                    context.onAddCameraClicked(context.imgInsertPhotoButton);
                }
            }
        });
        homeViewModel.getTriggerHighlight().observe((LifecycleOwner) activity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (NUIDocView.this.mSession != null) {
                    if (s.equals("transparent")) {
                        NUIDocView.this.mSession.getDoc().setSelectionBackgroundTransparent();
                    } else {
                        NUIDocView.this.mSession.getDoc().setSelectionBackgroundColor(s);
                    }
                }
            }
        });
        homeViewModel.getTriggerFontColor().observe((LifecycleOwner) activity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (NUIDocView.this.mSession != null) {
                    NUIDocView.this.mSession.getDoc().setSelectionFontColor(s);
//                    Utilities.showKeyboard(activity());
                }
            }
        });

        homeViewModel.getTriggerSave().observe((LifecycleOwner) activity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (context != null) {
                    activity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            context.onSaveButton(context.mSaveButton);
                        }
                    });
                }
            }
        });
        homeViewModel.getTriggerSaveAsPdf().observe((LifecycleOwner) activity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (context != null) {
                    context.onSavePDFButton(context.mSavePdfButton);
                }
            }
        });

        homeViewModel.getTriggerSearch().observe((LifecycleOwner) activity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (context != null) {
                    context.editorSearchLayout.setVisibility(VISIBLE);
                }
            }
        });

        homeViewModel.getTriggerFontFamily().observe((LifecycleOwner) activity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (context != null) {
                    context.setSelectionFontName(s);
                }
            }
        });

        homeViewModel.getTriggerFontSize().observe((LifecycleOwner) activity(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                if (context != null) {
                    context.setSelectionFontSize(aDouble);
                }
            }
        });
//        if(activity().getIntent()!=null && activity().getIntent().hasExtra(Constant.PRINT)){
//            if(activity().getIntent().getBooleanExtra(Constant.PRINT, true)){
//                Log.d("abhay", String.valueOf(activity().getIntent().getBooleanExtra(Constant.PRINT, false)));
//                Log.d("abhay","print");
//                this.onPrintButton();
//            }
//        }
        this.onDeviceSizeChange();
        this.setConfigurableButtons();
        this.fixFileToolbar(id.file_toolbar);
        this.mAdapter = this.createAdapter();
        DocView var5 = this.createMainView(this.activity());
        this.i = var5;
        var5.setHost(this);
        this.i.setAdapter(this.mAdapter);
        this.i.setConfigOptions(this.mConfigOptions);
        if (this.usePagesView()) {
            DocListPagesView var6 = new DocListPagesView(this.activity());
            this.j = var6;
            var6.setHost(this);
            this.j.setAdapter(this.mAdapter);
            this.j.setMainView(this.i);
            this.j.setBorderColor(this.i.getBorderColor());
        }

        RelativeLayout var7 = (RelativeLayout) this.findViewById(id.doc_inner_container);
        var7.addView(this.i, 0);
        this.i.setup(var7);
        if (this.usePagesView()) {
            var7 = (RelativeLayout) this.findViewById(id.pages_container);
            var7.addView(this.j);
            this.j.setup(var7);
            this.j.setCanManipulatePages(this.canCanManipulatePages());
        }

        this.L = (SOTextView) this.findViewById(id.footer_text);

        findViewById(com.document.docease.R.id.footer).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                String str;
                String str2;
                String str3;
                long currentTimeMillis = System.currentTimeMillis();
                long e = NUIDocView.this.N;
                NUIDocView nUIDocView = NUIDocView.this;
                if (currentTimeMillis - e > 500) {
                    int unused = nUIDocView.M = 1;
                } else {
                    int unused2 = nUIDocView.M = nUIDocView.M + 1;
                }
                if (NUIDocView.this.M == 5) {
                    String[] d = com.artifex.solib.k.d((Activity) NUIDocView.this.getContext());
                    String str4 = "";
                    if (d != null) {
                        str3 = d[0];
                        str2 = d[1];
                        str = d[3];
                    } else {
                        str = str4;
                        str3 = str;
                        str2 = str3;
                    }
                    try {
                        str4 = NUIDocView.this.getContext().getPackageManager().getPackageInfo(NUIDocView.this.getContext().getApplicationInfo().packageName, 0).versionName;
                    } catch (PackageManager.NameNotFoundException e2) {
                        e2.printStackTrace();
                    }
                    ///Utilities.showMessage((Activity) NUIDocView.this.getContext(), NUIDocView.this.getContext().getString(R.string.sodk_editor_version_title), String.format(NUIDocView.this.getContext().getString(R.string.sodk_editor_version_format), new Object[]{str3, str2, str4, str}));
                    int unused3 = NUIDocView.this.M = 0;
                }
                long unused4 = NUIDocView.this.N = currentTimeMillis;
            }
        });

        if (this.mConfigOptions.r()) {
            this.g = SOFileDatabase.getDatabase();
        }

        final Activity var9 = this.activity();
        this.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                if (NUIDocView.this.mFinished) {
                    NUIDocView.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    NUIDocView.this.f();
                }
            }
        });
        this.i.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                try {
                    if (NUIDocView.this.i != null) {
                        NUIDocView.this.i.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                    NUIDocView var1;
                    if (NUIDocView.this.mIsSession) {
                        var1 = NUIDocView.this;
                        var1.l = var1.mSession.getUserPath();
                        var1 = NUIDocView.this;
                        var1.setupForDocType(var1.l);
                        if (!NUIDocView.this.mConfigOptions.r()) {
                            throw new UnsupportedOperationException();
                        }

                        NUIDocView.this.i();
                        var1 = NUIDocView.this;
                        var1.f = var1.g.stateForPath(NUIDocView.this.l, NUIDocView.this.T);
                        NUIDocView.this.f.setForeignData(NUIDocView.this.U);
                        NUIDocView.this.mSession.setFileState(NUIDocView.this.f);
                        NUIDocView.this.f.openFile(NUIDocView.this.T);
                        NUIDocView.this.f.setHasChanges(false);
                        var1 = NUIDocView.this;
                        var1.setFooterText(var1.f.getUserPath());
                        NUIDocView.this.i.setDoc(NUIDocView.this.mSession.getDoc());
                        if (NUIDocView.this.usePagesView()) {
                            NUIDocView.this.j.setDoc(NUIDocView.this.mSession.getDoc());
                        }

                        NUIDocView.this.mAdapter.setDoc(NUIDocView.this.mSession.getDoc());
                        NUIDocView.this.mSession.setSODocSessionLoadListener(new SODocSessionLoadListener() {
                            public void onCancel() {
                                NUIDocView.this.j();
                            }

                            public void onDocComplete() {
                                NUIDocView.this.j();
                                NUIDocView.this.onDocCompleted();
                                NUIDocView.this.setPageNumberText();
                            }

                            public void onError(int var1, int var2) {
                                if (NUIDocView.this.mSession.isOpen()) {
                                    NUIDocView.this.b();
                                    NUIDocView.this.j();
                                    if (!NUIDocView.this.mSession.isCancelled() || var1 != 6) {
                                        String var3 = Utilities.getOpenErrorDescription(NUIDocView.this.getContext(), var1);
                                        Utilities.showMessage(var9, NUIDocView.this.getContext().getString(string.sodk_editor_error), var3);
                                    }
                                }

                            }

                            public void onLayoutCompleted() {
                                NUIDocView.this.onLayoutChanged();
                            }

                            public void onPageLoad(int var1) {
                                NUIDocView.this.j();
                                NUIDocView.this.onPageLoaded(var1);
                            }

                            public void onSelectionChanged(int var1, int var2) {
                                NUIDocView.this.onSelectionMonitor(var1, var2);
                            }
                        });
                        if (NUIDocView.this.usePagesView()) {
                            float var2 = (float) NUIDocView.this.getResources().getInteger(integer.sodk_editor_pagelist_width_percentage) / 100.0F;
                            NUIDocView.this.j.setScale(var2);
                        }
                    } else {
                        label74:
                        {
                            if (NUIDocView.this.mState != null) {
                                if (!NUIDocView.this.mConfigOptions.r()) {
                                    throw new UnsupportedOperationException();
                                }

                                var1 = NUIDocView.this;
                                var1.l = var1.mState.getOpenedPath();
                                var1 = NUIDocView.this;
                                var1.setupForDocType(var1.l);
                                var1 = NUIDocView.this;
                                var1.setFooterText(var1.mState.getUserPath());
                                NUIDocView.this.i();
                                var1 = NUIDocView.this;
                                var1.f = var1.mState;
                                NUIDocView.this.f.openFile(NUIDocView.this.T);
                                var1 = NUIDocView.this;
                                var1.mSession = new SODocSession(var9, var1.ah);
                                NUIDocView.this.mSession.setFileState(NUIDocView.this.f);
                                NUIDocView.this.mSession.setSODocSessionLoadListener(new SODocSessionLoadListener() {
                                    public void onCancel() {
                                        NUIDocView.this.j();
                                    }

                                    public void onDocComplete() {
                                        if (!NUIDocView.this.mFinished) {
                                            NUIDocView.this.j();
                                            NUIDocView.this.onDocCompleted();
                                        }
                                    }

                                    public void onError(int var1, int var2) {
                                        if (!NUIDocView.this.mFinished) {
                                            NUIDocView.this.b();
                                            NUIDocView.this.j();
                                            if (!NUIDocView.this.mSession.isCancelled() || var1 != 6) {
                                                String var3 = Utilities.getOpenErrorDescription(NUIDocView.this.getContext(), var1);
                                                Utilities.showMessage(var9, NUIDocView.this.getContext().getString(string.sodk_editor_error), var3);
                                            }

                                        }
                                    }

                                    public void onLayoutCompleted() {
                                        NUIDocView.this.onLayoutChanged();
                                    }

                                    public void onPageLoad(int var1) {
                                        if (!NUIDocView.this.mFinished) {
                                            NUIDocView.this.j();
                                            NUIDocView.this.onPageLoaded(var1);
                                        }
                                    }

                                    public void onSelectionChanged(int var1, int var2) {
                                        NUIDocView.this.onSelectionMonitor(var1, var2);
                                    }
                                });
                                NUIDocView.this.mSession.open(NUIDocView.this.f.getInternalPath());
                                NUIDocView.this.i.setDoc(NUIDocView.this.mSession.getDoc());
                                if (NUIDocView.this.usePagesView()) {
                                    NUIDocView.this.j.setDoc(NUIDocView.this.mSession.getDoc());
                                }

                                NUIDocView.this.mAdapter.setDoc(NUIDocView.this.mSession.getDoc());
                                if (!NUIDocView.this.usePagesView()) {
                                    break label74;
                                }
                            } else {
                                Uri var6 = NUIDocView.this.mStartUri;
                                String var3 = var6.getScheme();
                                if (var3 != null && var3.equalsIgnoreCase("content")) {
                                    String var7 = com.artifex.solib.a.b(NUIDocView.this.getContext(), var6);
                                    if (var7.equals("---fileOpen")) {
                                        Utilities.showMessage(var9, NUIDocView.this.getContext().getString(string.sodk_editor_content_error), NUIDocView.this.getContext().getString(string.sodk_editor_error_opening_from_other_app));
                                        return;
                                    }

                                    if (var7.startsWith("---")) {
                                        String var4 = NUIDocView.this.getResources().getString(string.sodk_editor_cant_create_temp_file);
                                        Activity var5 = var9;
                                        var3 = NUIDocView.this.getContext().getString(string.sodk_editor_content_error);
                                        StringBuilder var11 = new StringBuilder();
                                        var11.append(NUIDocView.this.getContext().getString(string.sodk_editor_error_opening_from_other_app));
                                        var11.append(": \n\n");
                                        var11.append(var4);
                                        Utilities.showMessage(var5, var3, var11.toString());
                                        return;
                                    }

                                    NUIDocView.this.l = var7;
                                    if (NUIDocView.this.T) {
                                        NUIDocView.this.addDeleteOnClose(var7);
                                    }
                                } else {
                                    NUIDocView.this.l = var6.getPath();
                                    if (NUIDocView.this.l == null) {
                                        Utilities.showMessage(var9, NUIDocView.this.getContext().getString(string.sodk_editor_invalid_file_name), NUIDocView.this.getContext().getString(string.sodk_editor_error_opening_from_other_app));
                                        StringBuilder var8 = new StringBuilder();
                                        var8.append(" Uri has no path: ");
                                        var8.append(var6.toString());
                                        Log.e("NUIDocView", var8.toString());
                                        return;
                                    }
                                }

                                var1 = NUIDocView.this;
                                var1.setupForDocType(var1.l);
                                var1 = NUIDocView.this;
                                var1.setFooterText(var1.l);
                                NUIDocView.this.i();
                                NUIDocView var9x;
                                Object var10;
                                if (NUIDocView.this.mConfigOptions.r()) {
                                    var9x = NUIDocView.this;
                                    var10 = var9x.g.stateForPath(NUIDocView.this.l, NUIDocView.this.T);
                                } else {
                                    var9x = NUIDocView.this;
                                    var10 = new SOFileStateDummy(var9x.l);
                                }

                                var9x.f = (SOFileState) var10;
                                NUIDocView.this.f.openFile(NUIDocView.this.T);
                                NUIDocView.this.f.setHasChanges(false);
                                var1 = NUIDocView.this;
                                var1.mSession = new SODocSession(var9, var1.ah);
                                NUIDocView.this.mSession.setFileState(NUIDocView.this.f);
                                NUIDocView.this.mSession.setSODocSessionLoadListener(new SODocSessionLoadListener() {
                                    public void onCancel() {
                                        NUIDocView.this.b();
                                        NUIDocView.this.j();
                                    }

                                    public void onDocComplete() {
                                        if (!NUIDocView.this.mFinished) {
                                            NUIDocView.this.j();
                                            NUIDocView.this.onDocCompleted();
                                        }
                                    }

                                    public void onError(int var1, int var2) {
                                        if (!NUIDocView.this.mFinished) {
                                            NUIDocView.this.b();
                                            NUIDocView.this.j();
                                            if (!NUIDocView.this.mSession.isCancelled() || var1 != 6) {
                                                String var3 = Utilities.getOpenErrorDescription(NUIDocView.this.getContext(), var1);
                                                Utilities.showMessage(var9, NUIDocView.this.getContext().getString(string.sodk_editor_error), var3);
                                            }

                                        }
                                    }

                                    public void onLayoutCompleted() {
                                        NUIDocView.this.onLayoutChanged();
                                    }

                                    public void onPageLoad(int var1) {
                                        if (!NUIDocView.this.mFinished) {
                                            NUIDocView.this.j();
                                            NUIDocView.this.onPageLoaded(var1);
                                        }
                                    }

                                    public void onSelectionChanged(int var1, int var2) {
                                        NUIDocView.this.onSelectionMonitor(var1, var2);
                                    }
                                });
                                NUIDocView.this.mSession.open(NUIDocView.this.f.getInternalPath());
                                NUIDocView.this.i.setDoc(NUIDocView.this.mSession.getDoc());
                                if (NUIDocView.this.usePagesView()) {
                                    NUIDocView.this.j.setDoc(NUIDocView.this.mSession.getDoc());
                                }

                                NUIDocView.this.mAdapter.setDoc(NUIDocView.this.mSession.getDoc());
                                if (!NUIDocView.this.usePagesView()) {
                                    break label74;
                                }
                            }

                            NUIDocView.this.j.setScale(0.2F);
                        }
                    }

                    NUIDocView.this.createInputView();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        if (Utilities.isPhoneDevice(this.activity())) {
            this.scaleHeader();
        }
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (activity().getIntent() != null && activity().getIntent().hasExtra(Constant.PRINT)) {
                if (activity().getIntent().getBooleanExtra(Constant.PRINT, true)) {
                    this.onPrintButton();
                }
            }
        }, 1000);
    }

    public boolean canCanManipulatePages() {
        return false;
    }

    private boolean shouldClearSelection() {
        boolean clearSelection = false;
        try {
            if (this.getDocView() != null && this.getDocView().getSelectionLimits() != null) {
                SOSelectionLimits limits = this.getDocView().getSelectionLimits();
                Float threshold = limits.getBox().right - limits.getBox().right;
                if (threshold > 5F) {
                    clearSelection = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clearSelection;
    }

    protected void changeTab(String var1) {
        this.aq = var1;
        this.setSingleTabTitle(var1);
        this.onSelectionChanged();
        this.findViewById(id.searchTab).setVisibility(GONE);
        this.showSearchSelected(false);
        this.handlePagesTab(var1);
        this.setTabColors(var1);
        this.i.layoutNow();
    }

    public void clickSheetButton(int var1, boolean var2) {
    }

    protected PageAdapter createAdapter() {
        return new PageAdapter(this.getContext(), this, 1);
    }

    protected void createEditButtons() {
        this.C = (SOTextView) this.createToolbarButton(id.font_size_text);
        this.D = (ToolbarButton) this.createToolbarButton(id.fontup_button);
        this.E = (ToolbarButton) this.createToolbarButton(id.fontdown_button);
        this.F = (SOTextView) this.createToolbarButton(id.font_name_text);
        this.G = (ToolbarButton) this.createToolbarButton(id.font_color_button);
        this.H = (LinearLayout) this.createToolbarButton(id.font_background_button);

        this.I = (LinearLayout) this.createToolbarButton(id.cut_button);
        this.J = (LinearLayout) this.createToolbarButton(id.copy_button);
        this.K = (LinearLayout) this.createToolbarButton(id.paste_button);

        this.mStyleBoldButton = (ToolbarButton) this.createToolbarButton(id.bold_button);
        this.mStyleItalicButton = (ToolbarButton) this.createToolbarButton(id.italic_button);
        this.mStyleUnderlineButton = (ToolbarButton) this.createToolbarButton(id.underline_button);
        this.mStyleLinethroughButton = (ToolbarButton) this.createToolbarButton(id.striketrough_button);
    }

    protected void createEditButtons2() {
        this.mListBulletsButton = (ToolbarButton) this.createToolbarButton(id.list_bullets_button);
        this.mListNumbersButton = (ToolbarButton) this.createToolbarButton(id.list_numbers_button);
        this.q = (ToolbarButton) this.createToolbarButton(id.align_left_button);
        this.r = (ToolbarButton) this.createToolbarButton(id.align_center_button);
        this.s = (ToolbarButton) this.createToolbarButton(id.align_right_button);
        this.t = (ToolbarButton) this.createToolbarButton(id.align_justify_button);
        this.mIncreaseIndentButton = (ToolbarButton) this.createToolbarButton(id.indent_increase_button);
        this.mDecreaseIndentButton = (ToolbarButton) this.createToolbarButton(id.indent_decrease_button);
    }

    protected void createInputView() {
        RelativeLayout var1 = (RelativeLayout) this.findViewById(id.doc_inner_container);
        InputView var2 = new InputView(this.getContext(), this.mSession.getDoc(), this);
        this.aj = var2;
        var1.addView(var2);
    }

    protected void createInsertButtons() {
        this.mInsertImageButton = (LinearLayout) this.createToolbarButton(id.insert_image_button);
        this.mInsertPhotoButton = (LinearLayout) this.createToolbarButton(id.insert_photo_button);
        this.imgInsertImageButton = (ImageView) this.createToolbarButton(com.document.docease.R.id.img_insert_png);
        this.imgInsertPhotoButton = (ImageView) this.createToolbarButton(com.document.docease.R.id.img_insert_photo);
        this.tvInsertImageButton = (TextView) this.createToolbarButton(com.document.docease.R.id.tv_insert_png);
        this.tvInsertPhotoButton = (TextView) this.createToolbarButton(com.document.docease.R.id.tv_insert_photo);

        if (imgInsertImageButton != null)
            imgInsertImageButton.setOnClickListener(this);
        if (imgInsertPhotoButton != null)
            imgInsertPhotoButton.setOnClickListener(this);
    }

    protected DocView createMainView(Activity var1) {
        return new DocViewWrapper(var1);
    }

    protected DocView createMainViewExcel(Activity var1) {
        return new DocView(var1);
    }

    protected void createPagesButtons() {
        this.O = (LinearLayout) this.createToolbarButton(id.first_page_button);
        this.P = (LinearLayout) this.createToolbarButton(id.last_page_button);
        LinearLayout var1 = (LinearLayout) this.createToolbarButton(id.reflow_button);
        this.Q = var1;
        if (var1 != null) {
            var1.setEnabled(false);
        }
        //ToolbarButton.setAllSameSize(new ToolbarButton[]{this.O, this.P, this.Q});
    }

    protected void createReviewButtons() {
    }

    protected View createToolbarButton(int var1) {
        View var2 = this.findViewById(var1);
        if (var2 != null) {
            var2.setOnClickListener(this);
        }

        return var2;
    }

    protected void defocusInputView() {
        InputView var1 = this.aj;
        if (var1 != null) {
            var1.clearFocus();
        }

    }

    public void doArrowKey(KeyEvent var1) {
        boolean var2 = var1.isShiftPressed();
        boolean var3 = var1.isAltPressed();
        switch (var1.getKeyCode()) {
            case 19:
                if (!var2 && !var3) {
                    this.mSession.getDoc().processKeyCommand(2);
                }

                if (var2 && !var3) {
                    this.mSession.getDoc().processKeyCommand(12);
                }

                if (!var2 && var3) {
                    this.mSession.getDoc().processKeyCommand(6);
                }

                if (var2 && var3) {
                    this.mSession.getDoc().processKeyCommand(14);
                }

                this.onTyping();
                return;
            case 20:
                if (!var2 && !var3) {
                    this.mSession.getDoc().processKeyCommand(3);
                }

                if (var2 && !var3) {
                    this.mSession.getDoc().processKeyCommand(13);
                }

                if (!var2 && var3) {
                    this.mSession.getDoc().processKeyCommand(7);
                }

                if (var2 && var3) {
                    this.mSession.getDoc().processKeyCommand(15);
                }

                this.onTyping();
                return;
            case 21:
                if (!var2 && !var3) {
                    this.mSession.getDoc().processKeyCommand(0);
                }

                if (var2 && !var3) {
                    this.mSession.getDoc().processKeyCommand(8);
                }

                if (!var2 && var3) {
                    this.mSession.getDoc().processKeyCommand(4);
                }

                if (var2 && var3) {
                    this.mSession.getDoc().processKeyCommand(10);
                }

                this.onTyping();
                return;
            case 22:
                if (!var2 && !var3) {
                    this.mSession.getDoc().processKeyCommand(1);
                }

                if (var2 && !var3) {
                    this.mSession.getDoc().processKeyCommand(9);
                }

                if (!var2 && var3) {
                    this.mSession.getDoc().processKeyCommand(5);
                }

                if (var2 && var3) {
                    this.mSession.getDoc().processKeyCommand(11);
                }

                this.onTyping();
                return;
            default:
        }
    }

    public void doBold() {
        try {
            if (this.mSession != null) {
                SODoc var1 = this.mSession.getDoc();
                boolean var2 = var1.getSelectionIsBold() ^ true;
                this.mStyleBoldButton.setSelected(var2);
                var1.setSelectionIsBold(var2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doCopy() {
        if (this.mSession != null) {
            this.mSession.getDoc().I();
        }
    }

    private void copyTextInPdf() {
        if (document != null) {
            try {
                SOSelectionLimits limits = this.getDocView().getSelectionLimits();
                PDFBoxResourceLoader.init(activity().getApplicationContext());
                PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                stripper.setSortByPosition(true);
                RectF rect = new RectF(limits.getBox().left, limits.getBox().top, limits.getBox().right, limits.getBox().bottom);
                stripper.addRegion("class1", rect);
                stripper.extractRegions(document.getPage(this.ab));
                String copiedText = stripper.getTextForRegion("class1");
//                System.out.println(stripper.getTextForRegion("class1"));
                ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText(copiedText, copiedText);
                clipboard.setPrimaryClip(clip);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    public void doCut() {
        if (this.mSession != null) {
            this.mSession.getDoc().H();
        }
    }

    public void doInsertImage(String var1) {
        try {
            if (!com.artifex.solib.a.b(var1)) {
                Utilities.showMessage((Activity) this.getContext(), this.getContext().getString(string.sodk_editor_insert_image_gone_title), this.getContext().getString(string.sodk_editor_insert_image_gone_body));
            } else {
                String var2 = Utilities.preInsertImage(this.getContext(), var1);
                this.getDoc().d(var2);
                if (!var1.equalsIgnoreCase(var2)) {
                    this.addDeleteOnClose(var2);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doItalic() {
        try {
            if (this.mSession != null) {
                SODoc var1 = this.mSession.getDoc();
                boolean var2 = var1.getSelectionIsItalic() ^ true;
                this.mStyleItalicButton.setSelected(var2);
                var1.setSelectionIsItalic(var2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean doKeyDown(int var1, KeyEvent var2) {
        boolean var3 = var2.isAltPressed();
        boolean var4 = var2.isCtrlPressed();
        boolean var5 = var2.isShiftPressed();
        BaseActivity var6 = (BaseActivity) this.getContext();
        switch (var2.getKeyCode()) {
            case 4:
                if (var6.isSlideShow()) {
                    var6.finish();
                } else {
                    this.onBackPressed();
                }

                return true;
            case 19:
                if (!this.inputViewHasFocus()) {
                    if (!var3 && !var4) {
                        this.y();
                    } else {
                        this.w();
                    }

                    return true;
                }
            case 20:
                if (!this.inputViewHasFocus()) {
                    if (!var3 && !var4) {
                        this.z();
                    } else {
                        this.x();
                    }

                    return true;
                }
            case 21:
            case 22:
                if (this.inputViewHasFocus()) {
                    this.onTyping();
                    this.doArrowKey(var2);
                    return true;
                }
            case 31:
                if (!this.inputViewHasFocus() || !var4 && !var3) {
                    break;
                }

                this.doCopy();
                return true;
            case 30:
                if (!this.inputViewHasFocus() || !var4 && !var3) {
                    break;
                }

                this.onTyping();
                this.doBold();
                return true;
            case 37:
                if (!this.inputViewHasFocus() || !var4 && !var3) {
                    break;
                }

                this.onTyping();
                this.doItalic();
                return true;
            case 47:
                if (var4 || var3) {
                    this.doSave();
                    return true;
                }
                break;
            case 49:
                if (!this.inputViewHasFocus() || !var4 && !var3) {
                    break;
                }

                this.onTyping();
                this.doUnderline();
                return true;
            case 50:
                if (!this.inputViewHasFocus() || !var4 && !var3) {
                    break;
                }

                this.onTyping();
                this.doPaste();
                return true;
            case 52:
                if (!this.inputViewHasFocus() || !var4 && !var3) {
                    break;
                }

                this.onTyping();
                this.doCut();
                return true;
            case 54:
                if (!var4 && !var3) {
                    break;
                }

                this.onTyping();
                if (var5) {
                    this.doRedo();
                } else {
                    this.doUndo();
                }

                return true;
            case 62:
                if (!this.inputViewHasFocus()) {
                    if (var5) {
                        this.w();
                    } else {
                        this.x();
                    }

                    return true;
                }
                break;
            case 66:
                if (this.inputViewHasFocus()) {
                    this.W = false;
                    if ((var2.getFlags() & 2) != 0) {
                        this.onTyping();
                        return true;
                    }
                }
                break;
            case 67:
                if (this.inputViewHasFocus()) {
                    this.onTyping();
                    this.getDoc().N();
                    return true;
                }

                return false;
        }

        if (this.inputViewHasFocus()) {
            char var7 = (char) var2.getUnicodeChar();
            if (var7 != 0) {
                this.onTyping();
                StringBuilder var8 = new StringBuilder();
                var8.append("");
                var8.append(var7);
                String var9 = var8.toString();
                this.getDoc().setSelectionText(var9, 0, true);
            }
        }

        return true;
    }

    public void doPaste() {
        if (this.mSession != null) {
            this.mSession.getDoc().a(this.getContext(), this.getTargetPageNumber());
        }
    }

    public void doRedo() {
        if (this.mSession != null) {
            int var1 = this.mSession.getDoc().getCurrentEdit();
            if (var1 < this.mSession.getDoc().getNumEdits()) {
                this.resetInputView();
                this.getDoc().clearSelection();
                this.mSession.getDoc().setCurrentEdit(var1 + 1);
            }
        }
    }

    public void doSave() {
        if (this.T) {
            this.a(false);
        } else {
            this.preSaveQuestion(new Runnable() {
                public void run() {
                    final ProgressDialog var1 = Utilities.createAndShowWaitSpinner(NUIDocView.this.getContext());
                    NUIDocView.this.mSession.getDoc().a(NUIDocView.this.f.getInternalPath(), new SODocSaveListener() {
                        public void onComplete(int var1x, int var2) {
                            var1.dismiss();
                            if (var1x == 0) {
                                NUIDocView.this.f.saveFile();
                                NUIDocView.this.updateUIAppearance();
                                if (NUIDocView.this.n != null) {
                                    NUIDocView.this.n.postSaveHandler(new SOSaveAsComplete() {
                                        public void onComplete(int var1x, String var2) {
                                            NUIDocView.this.reloadFile();
                                        }
                                    });
                                }
                            } else {
                                String var3 = String.format(NUIDocView.this.activity().getString(string.sodk_editor_error_saving_document_code), var2);
                                Utilities.showMessage(NUIDocView.this.activity(), NUIDocView.this.activity().getString(string.sodk_editor_error), var3);
                            }

                        }
                    });
                }
            }, new Runnable() {
                public void run() {
                }
            });
        }
    }

    public void doSelectAll() {
        this.getDocView().selectTopLeft();
        this.mSession.getDoc().processKeyCommand(6);
        this.mSession.getDoc().processKeyCommand(15);
    }

    public void doStrikethrough() {
        try {
            if (this.mSession != null) {
                SODoc var1 = this.mSession.getDoc();
                boolean var2 = var1.getSelectionIsLinethrough() ^ true;
                this.mStyleLinethroughButton.setSelected(var2);
                var1.setSelectionIsLinethrough(var2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doUnderline() {
        try {
            if (this.mSession != null) {
                SODoc var1 = this.mSession.getDoc();
                boolean var2 = var1.getSelectionIsUnderlined() ^ true;
                this.mStyleUnderlineButton.setSelected(var2);
                var1.setSelectionIsUnderlined(var2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteSelection(){
        if(this.mSession != null){
            if (this.getDocView() != null) {
                SOSelectionLimits var1 = this.getDocView().getSelectionLimits();
                if(var1 != null){
                    this.mSession.getDoc().selectionDelete();
                }
            }
        }
    }

    public void doUndo() {
        if (this.mSession != null) {
            int var1 = this.mSession.getDoc().getCurrentEdit();
            if (var1 > 0) {
                this.resetInputView();
                this.getDoc().clearSelection();
                this.mSession.getDoc().setCurrentEdit(var1 - 1);
            }
        }
    }

    public boolean documentHasBeenModified() {
        SODocSession var1 = this.mSession;
        boolean var2;
        if (var1 == null || var1.getDoc() == null || this.f == null || !this.mSession.getDoc().getHasBeenModified() && !this.f.hasChanges()) {
            var2 = false;
        } else {
            var2 = true;
        }

        return var2;
    }

    public void endDocSession(boolean var1) {
        DocView var2 = this.i;
        if (var2 != null) {
            var2.finish();
        }

        if (this.usePagesView()) {
            DocListPagesView var3 = this.j;
            if (var3 != null) {
                var3.finish();
            }
        }

        SODocSession var4 = this.mSession;
        if (var4 != null) {
            var4.endSession(var1);
        }

        this.j();
    }

    protected void fixFileToolbar(int var1) {
        LinearLayout var2 = (LinearLayout) this.findViewById(var1);
        if (var2 != null) {
            int[] var3 = new int[]{0, 0};
            View var4 = null;
            int var5 = 0;

            byte var8;
            for (byte var9 = 0; var5 < var2.getChildCount(); var9 = var8) {
                View var6 = var2.getChildAt(var5);
                View var7;
                if (var6 instanceof ToolbarButton) {
                    var7 = var4;
                    var8 = var9;
                    if (var6.getVisibility() == VISIBLE) {
                        int var10002 = var3[var9]++;
                        var7 = var4;
                        var8 = var9;
                    }
                } else {
                    var7 = var6;
                    var8 = 1;
                }

                ++var5;
                var4 = var7;
            }

            if (var4 != null && (var3[0] == 0 || var3[1] == 0)) {
                var4.setVisibility(GONE);
            }

            if (var3[0] == 0 && var3[1] == 0) {
                var2.setVisibility(GONE);
                ((View) var2.getParent()).setVisibility(GONE);
            }

        }
    }

    protected void focusInputView() {
        if (!ConfigOptions.a().c()) {
            this.defocusInputView();
        } else {
            InputView var1 = this.aj;
            if (var1 != null) {
                var1.setFocus();
            }

        }
    }

    public void forceReload() {
        this.au = true;
    }

    public int getBorderColor() {
        return ContextCompat.getColor(this.getContext(), color.sodk_editor_selected_page_border_color);
    }

    protected String getCurrentTab() {
        return this.aq;
    }

    public SODoc getDoc() {
        SODocSession var1 = this.mSession;
        return var1 == null ? null : var1.getDoc();
    }

    public String getDocFileExtension() {
        SOFileState var1 = this.mState;
        String var2;
        if (var1 != null) {
            var2 = var1.getUserPath();
        } else {
            SODocSession var3 = this.mSession;
            if (var3 == null) {
                var2 = com.artifex.solib.a.a(this.getContext(), this.mStartUri);
                return var2;
            }

            var2 = var3.getUserPath();
        }

        var2 = com.artifex.solib.a.g(var2);
        return var2;
    }

    public DocListPagesView getDocListPagesView() {
        return this.j;
    }

    public DocView getDocView() {
        return (DocView) this.i;
    }

    protected int getInitialTab() {
        return 0;
    }

    public InputView getInputView() {
        return this.aj;
    }

    public boolean getIsComposing() {
        return this.W;
    }

    public int getKeyboardHeight() {
        return this.am;
    }

    protected int getLayoutId() {
        return 0;
    }

    protected int getPageCount() {
        return this.mPageCount;
    }

    protected String getPageNumberText() {
        return String.format(this.getContext().getString(string.sodk_editor_page_d_of_d), this.ab + 1, this.getPageCount());
    }

    public SODocSession getSession() {
        return this.mSession;
    }

    protected int getStartPage() {
        return this.R;
    }

    protected TabData[] getTabData() {
        if (this.ag == null) {
            this.ag = new TabData[5];
            if (this.mConfigOptions.c()) {
                this.ag[0] = new TabData(this.getContext().getString(string.sodk_editor_tab_file), id.fileTab, layout.sodk_editor_tab_left, 0);
                this.ag[1] = new TabData(this.getContext().getString(string.sodk_editor_tab_edit), id.editTab, layout.sodk_editor_tab, 0);
                this.ag[2] = new TabData(this.getContext().getString(string.sodk_editor_tab_insert), id.insertTab, layout.sodk_editor_tab, 0);
                this.ag[3] = new TabData(this.getContext().getString(string.sodk_editor_tab_pages), id.pagesTab, layout.sodk_editor_tab, 0);
                this.ag[4] = new TabData(this.getContext().getString(string.sodk_editor_tab_review), id.reviewTab, layout.sodk_editor_tab_right, 0);
            } else {
                this.ag[0] = new TabData(this.getContext().getString(string.sodk_editor_tab_file), id.fileTab, layout.sodk_editor_tab_left, 0);
                this.ag[1] = new TabData(this.getContext().getString(string.sodk_editor_tab_edit), id.editTab, layout.sodk_editor_tab, 8);
                this.ag[2] = new TabData(this.getContext().getString(string.sodk_editor_tab_insert), id.insertTab, layout.sodk_editor_tab, 8);
                this.ag[3] = new TabData(this.getContext().getString(string.sodk_editor_tab_pages), id.pagesTab, layout.sodk_editor_tab_right, 0);
                this.ag[4] = new TabData(this.getContext().getString(string.sodk_editor_tab_review), id.reviewTab, layout.sodk_editor_tab_right, 8);
            }
        }

        if (!com.artifex.solib.k.e(this.activity()) && this.mConfigOptions.a == null) {
            TabData[] var1 = this.ag;
            var1[4].visibility = 8;
            var1[3].layoutId = layout.sodk_editor_tab_right;
        }

        return this.ag;
    }

    protected int getTabSelectedColor() {
        Activity var1;
        int var2;
        if (this.getResources().getInteger(integer.sodk_editor_ui_doc_tab_color_from_doctype) == 0) {
            var1 = this.activity();
            var2 = color.sodk_editor_header_color_selected;
        } else {
            var1 = this.activity();
            var2 = color.sodk_editor_header_doc_color;
        }

        return ContextCompat.getColor(var1, var2);
    }

    protected int getTabSelectedTextColor() {
        return ContextCompat.getColor(this.activity(), color.sodk_editor_header_text_color_selected);
    }

    protected int getTabUnselectedColor() {
        Activity var1;
        int var2;
        if (this.getResources().getInteger(integer.sodk_editor_ui_doc_tabbar_color_from_doctype) == 0) {
            var1 = this.activity();
            var2 = color.sodk_editor_header_color;
        } else {
            var1 = this.activity();
            var2 = color.sodk_editor_header_doc_color;
        }

        return ContextCompat.getColor(var1, var2);
    }

    protected int getTabUnselectedTextColor() {
        return ContextCompat.getColor(this.activity(), color.sodk_editor_header_text_color);
    }

    protected int getTargetPageNumber() {
        DocPageView docPageView = this.getDocView().findPageContainingSelection();
        if (docPageView != null) {
            return docPageView.getPageNumber();
        } else {
            Rect var3 = new Rect();
            this.getDocView().getGlobalVisibleRect(var3);
            docPageView = this.getDocView().findPageViewContainingPoint((var3.left + var3.right) / 2, (var3.top + var3.bottom) / 2, true);
            int var2 = 0;
            if (docPageView != null) {
                var2 = docPageView.getPageNumber();
            }
            return var2;
        }
    }

    protected void goBack() {
        this.prepareToGoBack();
        if (this.documentHasBeenModified()) {
            this.activity().runOnUiThread(new Runnable() {
                public void run() {
                    int var1 = string.sodk_editor_save;
                    int var2 = var1;
                    if (NUIDocView.this.k != null) {
                        int var3 = NUIDocView.this.getContext().getResources().getIdentifier("secure_save_upper", "string", NUIDocView.this.getContext().getPackageName());
                        var2 = var1;
                        if (var3 != 0) {
                            var2 = var3;
                        }
                    }

                    (new Builder(NUIDocView.this.activity(), style.sodk_editor_alert_dialog_style)).setTitle(string.sodk_editor_document_has_been_modified).setMessage(string.sodk_editor_would_you_like_to_save_your_changes).setCancelable(false).setPositiveButton(var2, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface var1, int var2) {
                            var1.dismiss();
                            NUIDocView.this.preSaveQuestion(new Runnable() {
                                public void run() {
                                    if (NUIDocView.this.k != null) {
                                        NUIDocView.this.onCustomSaveButton((View) null);
                                    } else if (NUIDocView.this.T) {
                                        NUIDocView.this.a(true);
                                    } else {
                                        NUIDocView.this.mSession.getDoc().a(NUIDocView.this.f.getInternalPath(), new SODocSaveListener() {
                                            public void onComplete(int var1, int var2) {
                                                if (var1 == 0) {
                                                    NUIDocView.this.f.saveFile();
                                                    if (NUIDocView.this.n != null) {
                                                        NUIDocView.this.n.postSaveHandler(new SOSaveAsComplete() {
                                                            public void onComplete(int var1, String var2) {
                                                                NUIDocView.this.f.closeFile();
                                                                NUIDocView.this.prefinish();
                                                            }
                                                        });
                                                    } else {
                                                        NUIDocView.this.f.closeFile();
                                                        NUIDocView.this.prefinish();
                                                    }
                                                } else {
                                                    NUIDocView.this.f.closeFile();
                                                    String var3 = String.format(NUIDocView.this.activity().getString(string.sodk_editor_error_saving_document_code), var2);
                                                    Utilities.showMessage(NUIDocView.this.activity(), NUIDocView.this.activity().getString(string.sodk_editor_error), var3);
                                                }

                                            }
                                        });
                                    }

                                }
                            }, new Runnable() {
                                public void run() {
                                }
                            });
                        }
                    }).setNegativeButton(string.sodk_editor_discard, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface var1, int var2) {
                            var1.dismiss();
                            NUIDocView.this.f.closeFile();
                            NUIDocView.this.e = new Boolean(false);
                            NUIDocView.this.prefinish();
                        }
                    }).setNeutralButton(string.sodk_editor_continue_editing, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface var1, int var2) {
                            var1.dismiss();
                        }
                    }).create().show();
                }
            });
        } else {
            this.e = new Boolean(false);
            this.prefinish();
        }

    }

    public void goToPage(int var1) {
        this.goToPage(var1, false);
    }

    public void goToPage(int var1, boolean var2) {
        this.i.scrollToPage(var1, var2);
        if (this.usePagesView()) {
            this.j.scrollToPage(var1, var2);
        }

    }

    protected void handlePagesTab(String var1) {
        if (var1.equals(this.activity().getString(string.sodk_editor_tab_pages))) {
            this.showPages();
        } else {
            this.hidePages();
        }

    }

    protected void handleStartPage() {
        int var1 = this.getStartPage();
        if (this.getStartPage() > 0 && this.getPageCount() >= this.getStartPage()) {
            this.setStartPage(0);
            this.i.setStartPage(var1);
            this.ab = var1 - 1;
            this.setPageNumberText();
            this.i.requestLayout();
        }

    }

    protected boolean hasRedo() {
        return true;
    }

    protected boolean hasSearch() {
        return false;
    }

    protected boolean hasUndo() {
        return true;
    }

    protected void hidePages() {
        RelativeLayout var1 = (RelativeLayout) this.findViewById(id.pages_container);
        if (var1 != null && var1.getVisibility() != GONE) {
            this.i.onHidePages();
            var1.setVisibility(GONE);
        }

    }

    protected boolean inputViewHasFocus() {
        InputView var1 = this.aj;
        return var1 != null ? var1.hasFocus() : false;
    }

    protected boolean isActivityActive() {
        return this.aa;
    }

    public boolean isFullScreen() {
        return !this.mConfigOptions.A() ? false : this.aw;
    }

    public boolean isKeyboardVisible() {
        return this.keyboardShown;
    }

    public boolean isLandscapePhone() {
        boolean var1;
        if (this.an == 2 && Utilities.isPhoneDevice(this.getContext())) {
            var1 = true;
        } else {
            var1 = false;
        }

        return var1;
    }

    public boolean isPageListVisible() {
        RelativeLayout var1 = (RelativeLayout) this.findViewById(id.pages_container);
        return var1 != null && var1.getVisibility() == VISIBLE;
    }

    protected boolean isPagesTab() {
        return this.getCurrentTab().equals(this.activity().getString(string.sodk_editor_tab_pages));
    }

    protected boolean isRedactionMode() {
        return false;
    }

    protected boolean isSearchVisible() {
        View var1 = this.findViewById(id.search_text_input);
        return var1 != null && var1.getVisibility() == VISIBLE && var1.isShown();
    }

    protected void layoutAfterPageLoad() {
        this.layoutNow();
    }

    public void layoutNow() {
        DocView var1 = this.i;
        if (var1 != null) {
            var1.layoutNow();
        }

        if (this.j != null && this.usePagesView() && this.isPageListVisible()) {
            this.j.layoutNow();
        }

    }

    public void onActivityResult(int var1, int var2, Intent var3) {
        SODataLeakHandlers var4 = this.n;
        if (var4 != null) {
            var4.onActivityResult(var1, var2, var3);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public void onAlignCenterButton(View var1) {
        try {
            this.mSession.getDoc().E();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onAlignJustifyButton(View var1) {
        try {
            this.mSession.getDoc().G();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onAlignLeftButton(View var1) {
        try {
            this.mSession.getDoc().D();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onAlignRightButton(View var1) {
        try {
            this.mSession.getDoc().F();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onAuthorButton(View var1) {
        final SODoc var2 = this.getDocView().getDoc();
        AuthorDialog.show(this.activity(), new AuthorDialogListener() {
            public void onCancel() {
            }

            public void onOK(String var1) {
                var2.setAuthor(var1);
                Utilities.setStringPreference(Utilities.getPreferencesObject(NUIDocView.this.activity(), "general"), "DocAuthKey", var1);
            }
        }, var2.getAuthor());
    }

    public void onBackPressed() {
        this.goBack();
    }

    public void onClick(View var1) {
        if (var1 != null) {
            if (var1 == this.mSaveAsButton) {
                this.onSaveAsButton(var1);
            }

            if (var1 == this.mSaveButton || var1 == this.tvSave) {
                logFirebaseEvents("fileViewerPage", "action", "save");
                logFirebaseEvents("fileViewerPage", "fileType", getDocFileExtension());

                this.onSaveButton(var1);
            }
            if (var1 == this.editorToolbarDone) {
                logFirebaseEvents("fileViewerPage", "action", "save");
                logFirebaseEvents("fileViewerPage", "fileType", getDocFileExtension());
                this.onSaveButton(var1);
            }

            if (var1 == this.p) {
                this.onCustomSaveButton(var1);
            }

            if (var1 == this.mSavePdfButton) {
                logFirebaseEvents("fileViewerPage", "action", "savePDF");
                logFirebaseEvents("fileViewerPage", "fileType", getDocFileExtension());
                this.onSavePDFButton(var1);
            }

            if (var1 == this.mPrintButton || var1 == this.tvPrint) {
                logFirebaseEvents("fileViewerPage", "action", "print");
                logFirebaseEvents("fileViewerPage", "fileType", getDocFileExtension());
                this.onPrintButton();
            }
            if (var1 == this.mPreviewPrint) {
                this.onPrintButton();
            }

            if (var1 == this.mShareButton || var1 == this.editorToolbarShare) {
                if (f != null) {
                    shareDocument(new File(f.getOpenedPath()));
                } else {
                    this.onShareButton();
                }
            }

            if (var1 == this.o) {
                this.onShareButton();
            }

//            if(var1==this.shareButton){
//                logFirebaseEvents("fileViewerPage","action","print");
//                logFirebaseEvents("fileViewerPage","fileType",getDocFileExtension());
//                shareDocument(new File(f.getOpenedPath()));
////                this.onShareButton(var1);
//            }

            if (var1 == this.mOpenInButton) {
                this.onOpenInButton(var1);
            }

            if (var1 == this.mOpenPdfInButton) {
                this.onOpenPDFInButton(var1);
            }

            if (var1 == this.mProtectButton) {
                this.onProtectButton(var1);
            }

            if (var1 == this.D) {
                this.onFontUpButton(var1);
            }

            if (var1 == this.editorExpandFontUp) {
                this.onFontUpButton(var1);
            }

            if (var1 == this.editorExpandFontDown) {
                this.onFontDownButton(var1);
            }

            if (var1 == this.E) {
                this.onFontDownButton(var1);
            }
            if (var1 == this.editorExpandFontNameTv || var1 == this.editorFontFamily) {
                this.onTapFontName(var1);
            }

            if (var1 == this.C) {
                this.onTapFontName(var1);
            }

            if (var1 == this.F) {
                this.onTapFontName(var1);
            }

            if (var1 == this.G) {
                this.onFontColorButton(var1);
            }

            if (var1 == this.H) {
                this.onFontBackgroundButton(var1);
            }

//            if(var1 == this.editorHighlight){
//                this.onEditorFontBackgroundButton(var1);
//
//            }

            if (var1 == this.I) {
                this.onCutButton(var1);
            }

            if (var1 == this.J) {
                this.onCopyButton(var1);
            }

            if (var1 == this.editorToolbarCopy) {
                this.onCopyButton(var1);
                Toast.makeText(activity(), "Copied", Toast.LENGTH_SHORT).show();
            }

            if (var1 == this.K) {
                this.onPasteButton(var1);
            }

            if (var1 == this.mStyleBoldButton) {
                this.doBold();
            }

            if (var1 == this.mStyleItalicButton) {
                this.doItalic();
            }

            if (var1 == this.mStyleUnderlineButton) {
                this.doUnderline();
            }

            if (var1 == this.mStyleLinethroughButton) {
                this.doStrikethrough();
            }

            if (var1 == this.mListBulletsButton || var1 == this.editorExpandBulletDots) {
                this.onListBulletsButton(var1);
            }

            if (var1 == this.mListNumbersButton || var1 == this.editorExpandBulletNumber) {
                this.onListNumbersButton(var1);
            }

            if (var1 == this.q) {
                this.onAlignLeftButton(var1);
            }

            if (var1 == this.r) {
                this.onAlignCenterButton(var1);
            }

            if (var1 == this.s) {
                this.onAlignRightButton(var1);
            }

            if (var1 == this.t) {
                this.onAlignJustifyButton(var1);
            }

            if (var1 == this.mIncreaseIndentButton) {
                this.onIndentIncreaseButton(var1);
            }

            if (var1 == this.mDecreaseIndentButton) {
                this.onIndentDecreaseButton(var1);
            }

            if (var1 == this.O) {
                this.onFirstPageButton(var1);
            }

            if (var1 == this.P) {
                this.onLastPageButton(var1);
            }

            if (var1 == this.Q) {
                this.onReflowButton(var1);
            }

            if (var1 == this.mUndoButton || var1 == this.editorToolbarUndo || var1 == this.layoutUndo) {
                logFirebaseEvents("fileViewerPage", "action", "undo");
                logFirebaseEvents("fileViewerPage", "fileType", getDocFileExtension());
                this.onUndoButton(var1);
            }

            if (var1 == this.mRedoButton || var1 == this.editorToolbarRedo || var1 == this.layoutRedo) {
                logFirebaseEvents("fileViewerPage", "action", "redo");
                logFirebaseEvents("fileViewerPage", "fileType", getDocFileExtension());
                this.onRedoButton(var1);
            }

            if (var1 == this.editorToolbarSearch) {
                this.editorSearchLayout.setVisibility(VISIBLE);
            }
            if (var1 == this.editorCloseSearch) {
                this.editorSearchLayout.setVisibility(GONE);
            }

            if (var1 == this.u) {
                this.onSearchButton(var1);
            }

            if (var1 == this.y) {
                this.onSearchNext(var1);
            }

            if (var1 == this.z) {
                this.onSearchPrevious(var1);
            }

            if (var1 == this.mBackButton) {
                this.goBack();
            }

            if (var1 == this.mInsertImageButton) {
                this.onInsertImageButton(var1);
            }
//chnaged here
            if (var1 == this.imgInsertImageButton) {
                if (isImageAreaSelected()) {
                    this.onInsertImageButton(var1);
                }
            }
            if (var1 == this.editorExpandInsertImage) {
                if (isImageAreaSelected()) {
                    this.onInsertImageButton(var1);
                } else {
                    Utilities.showMessage(activity(), "Select Image position", "Click on position to add image", "ok");
                }
            }
            if (var1 == this.editorExpandInsertPhoto) {
                if (isImageAreaSelected()) {
                    this.onAddCameraClicked(var1);
                } else {
                    Utilities.showMessage(activity(), "Select Image position", "Click on position to add image", "ok");
                }
            }

            if (var1 == this.mInsertPhotoButton) {
                this.onAddCameraClicked(var1);
            }

            if (var1 == this.imgInsertPhotoButton) {
                if (isImageAreaSelected()) {
                    this.onAddCameraClicked(var1);
                }
            }

            if (this.mConfigOptions.A()) {
                Button var2 = this.v;
                if (var2 != null && var1 == var2) {
                    this.a(var1);
                }
            }

        }
    }

    public void handleShare(File file) {
        if (this.documentHasBeenModified()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity());
            builder.setTitle(com.document.docease.R.string.sodk_editor_document_has_been_modified);
            builder.setMessage(com.document.docease.R.string.save_before_share_body);
            builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
//                    if(context!= null){
//                        context.doSave();
//                    }
                    String filePath = file.getAbsolutePath();
                    int dotIndex = filePath.lastIndexOf(".");
                    String fileExtension = filePath.substring(dotIndex + 1);
                    String filePathWithoutExtension = filePath.substring(0, dotIndex);
                    String tempNewFilePath = filePath + "-temp";
                    File updatedFile = new File(tempNewFilePath);
                    try {
                        if (file.exists() && file.canWrite()) {
                            file.delete();
                            updatedFile.renameTo(file);
                            Toast.makeText(activity(), "File Saved Successfully!", Toast.LENGTH_SHORT).show();
                        }
                        shareDocument(file);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // Handle positive button click here
                }
            });
            builder.setNegativeButton("Continue Sharing", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    shareDocument(file);
                    // Handle negative button click here
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            shareDocument(file);
        }
    }

    public void shareDocument(File file) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("application/*");
        Uri uri = FileProvider.getUriForFile(context.getContext(), BuildConfig.APPLICATION_ID + ".fileprovider", file);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        List<ResolveInfo> resInfoList = activity().getPackageManager().queryIntentActivities(shareIntent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            activity().grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        context.getContext().startActivity(Intent.createChooser(shareIntent, "Share"));
    }

    public void onConfigurationChange(Configuration var1) {
        if (var1 != null && var1.keyboard != this.V) {
            this.resetInputView();
            this.V = var1.keyboard;
        }

        DocView var2 = this.i;
        if (var2 != null) {
            var2.onConfigurationChange();
        }

        if (this.usePagesView()) {
            DocListPagesView var3 = this.j;
            if (var3 != null) {
                var3.onConfigurationChange();
            }
        }

    }

    public void onCopyButton(View var1) {
        if (this.isPdf) {
            this.copyTextInPdf();
        } else {
            this.doCopy();
        }
    }

    public void onCustomSaveButton(View var1) {
        if (this.n != null) {
            this.preSaveQuestion(new Runnable() {
                public void run() {
                    String var1 = NUIDocView.this.f.getUserPath();
                    String var2 = var1;
                    if (var1 == null) {
                        var2 = NUIDocView.this.f.getOpenedPath();
                    }

                    File var8 = new File(var2);
                    NUIDocView.this.preSave();

                    try {
                        SODataLeakHandlers var10 = NUIDocView.this.n;
                        String var3 = var8.getName();
                        SODoc var9 = NUIDocView.this.mSession.getDoc();
                        String var4 = NUIDocView.this.k;
                        SOCustomSaveComplete var5 = new SOCustomSaveComplete() {
                            public void onComplete(int var1, String var2, boolean var3) {
                                NUIDocView.this.f.setHasChanges(false);
                                if (var1 == 0) {
                                    NUIDocView.this.f.setHasChanges(false);
                                }

                                if (var3) {
                                    NUIDocView.this.prefinish();
                                }

                            }
                        };
                        var10.customSaveHandler(var3, var9, var4, var5);
                    } catch (UnsupportedOperationException var6) {
                    } catch (IOException var7) {
                    }
                }
            }, new Runnable() {
                public void run() {
                }
            });
        }

    }

    public void onCutButton(View var1) {
        this.doCut();
    }

    public void onDestroy() {
        this.q();
        if (this.ai != null) {
            for (int var1 = 0; var1 < this.ai.size(); ++var1) {
                com.artifex.solib.a.e((String) this.ai.get(var1));
            }

            this.ai.clear();
        }

        SODataLeakHandlers var2 = this.n;
        if (var2 != null) {
            var2.finaliseDataLeakHandlers();
        }
        if(document != null){
            try {
                document.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    protected void onDeviceSizeChange() {
        View var1 = this.findViewById(id.back_button_after);
        Button var2;
        int var3;
        TabHost var4;
        Resources var5;
        if (Utilities.isPhoneDevice(this.activity())) {
            this.scaleHeader();
            var2 = this.u;
            if (var2 != null) {
                var2.setVisibility(GONE);
            }

            var4 = this.af;
            if (var4 != null) {
                var4.getTabWidget().getLayoutParams().width = Utilities.convertDpToPixel(150.0F);
                this.l();
                this.getSingleTabView().setVisibility(VISIBLE);
            }

            var5 = this.getContext().getResources();
            var3 = dimen.sodk_editor_after_back_button_phone;
        } else {
            if (this.hasSearch()) {
                var2 = this.u;
                if (var2 != null) {
                    var2.setVisibility(VISIBLE);
                }
            }

            var4 = this.af;
            if (var4 != null) {
                var4.getTabWidget().getLayoutParams().width = Utilities.convertDpToPixel(550.0F);
                this.m();
                this.getSingleTabView().setVisibility(GONE);
            }

            var5 = this.getContext().getResources();
            var3 = dimen.sodk_editor_after_back_button;
        }

        var3 = (int) var5.getDimension(var3);
        var1.getLayoutParams().width = Utilities.convertDpToPixel((float) var3);
    }

    protected void onDocCompleted() {
        if (!this.mFinished) {
            int var1 = this.mSession.getDoc().r();
            this.mPageCount = var1;
            this.mAdapter.setCount(var1);
            this.layoutNow();
            if (com.artifex.solib.k.e(this.activity())) {
                String var2 = Utilities.getStringPreference(Utilities.getPreferencesObject(this.activity(), "general"), "DocAuthKey", Utilities.getApplicationName(this.activity()));
                this.mSession.getDoc().setAuthor(var2);
            }

        }
    }

    public void onFirstPageButton(View var1) {
        this.i.goToFirstPage();
        if (this.usePagesView()) {
            this.j.goToFirstPage();
        }

    }

    public void onFontBackgroundButton(View var1) {
        (new ColorDialog(2, this.getContext(), this.mSession.getDoc(), this.i, new ColorChangedListener() {
            public void onColorChanged(String var1) {
                if (var1.equals("transparent")) {
                    NUIDocView.this.mSession.getDoc().setSelectionBackgroundTransparent();
                } else {
                    NUIDocView.this.mSession.getDoc().setSelectionBackgroundColor(var1);
                }

            }
        })).show();
    }

    public void onFontColorButton(View var1) {
        (new ColorDialog(1, this.getContext(), this.mSession.getDoc(), this.i, new ColorChangedListener() {
            public void onColorChanged(String var1) {
                NUIDocView.this.mSession.getDoc().setSelectionFontColor(var1);
            }
        })).show();
    }

    public void onFontDownButton(View var1) {
        double var2 = this.mSession.getDoc().getSelectionFontSize();
        if ((int) var2 > 6) {
            this.mSession.getDoc().setSelectionFontSize(var2 - 1.0D);
        }
    }

    public void onFontUpButton(View var1) {
        double var2 = this.mSession.getDoc().getSelectionFontSize();
        if ((int) var2 < 72) {
            this.mSession.getDoc().setSelectionFontSize(var2 + 1.0D);
        }
    }

    protected void onFullScreenHide() {
        this.findViewById(id.tabhost).setVisibility(GONE);
        this.findViewById(id.header).setVisibility(GONE);
        this.findViewById(id.footer).setVisibility(GONE);
        this.hidePages();
        this.layoutNow();
    }

    public void onIndentDecreaseButton(View var1) {
        int[] var2 = this.mSession.getDoc().getIndentationLevel();
        if (var2 != null && var2[0] > 0) {
            this.mSession.getDoc().setIndentationLevel(var2[0] - 1);
        }

    }

    public void onIndentIncreaseButton(View var1) {
        int[] var2 = this.mSession.getDoc().getIndentationLevel();
        if (var2 != null && var2[0] < var2[1]) {
            this.mSession.getDoc().setIndentationLevel(var2[0] + 1);
        }

    }

    public void onInsertImageButton(View var1) {
        this.showKeyboard(false, new Runnable() {
            public void run() {
                if (NUIDocView.this.n != null) {
                    try {
                        NUIDocView.this.n.insertImageHandler(NUIDocView.this);
                    } catch (UnsupportedOperationException var2) {
                        var2.printStackTrace();
                    }

                } else {
                    throw new UnsupportedOperationException();
                }
            }
        });
    }

    public void onAddCameraClicked(View var1) {
//        if (activity() != null) {
//            if (SharedPreferencesUtility.INSTANCE.getCameraPermissionPrompt(activity())) {
//                showCameraPermissionInformation();
//                SharedPreferencesUtility.INSTANCE.setCameraPermissionPrompt(activity(), false);
//            } else {
//                requestCameraPermission();
//            }
//        } else {
//            requestCameraPermission();
//        }
    }

    public void showCameraPermissionInformation() {
        Builder builder = new Builder(activity());
        builder.setTitle(MainR.getMainAppStringResource("camera_permission_request"))
                .setMessage(MainR.getMainAppStringResource("camera_permission_request_message"))
                .setNegativeButton(MainR.getMainAppStringResource("cancel_button_label"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton(MainR.getMainAppStringResource("suggest_ok"), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        requestCameraPermission();
                    }
                });
        builder.create().show();
    }

    public void requestCameraPermission() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            String[] permissions = {Manifest.permission.CAMERA};
            List<String> permissionsToRequest = new ArrayList<>();
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(activity(), permission) != PackageManager.PERMISSION_GRANTED) {
                    permissionsToRequest.add(permission);
                }
            }
            if (!permissionsToRequest.isEmpty()) {
                ActivityCompat.requestPermissions(activity(), permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ViewEditorActivity.REQUEST_CAMERA_PERMISSIONS);
            } else {
                onInsertPhotoButton();
            }
        } else {
            onInsertPhotoButton();
        }
    }

    public void onInsertPhotoButton() {
        this.showKeyboard(false, new Runnable() {
            public void run() {
                if (NUIDocView.this.n != null) {
                    try {
                        NUIDocView.this.n.insertPhotoHandler(NUIDocView.this);
                    } catch (UnsupportedOperationException var2) {
                        var2.printStackTrace();
                    }
                } else {
                    throw new UnsupportedOperationException();
                }
            }
        });
    }

    public boolean onKeyPreIme(int var1, KeyEvent var2) {
        BaseActivity var3 = (BaseActivity) this.getContext();
        if (!var3.isSlideShow()) {
            View var4 = var3.getCurrentFocus();
            if (var4 != null && var2.getKeyCode() == 4) {
                var4.clearFocus();
                Utilities.hideKeyboard(this.getContext());
                return true;
            }
        }

        return super.onKeyPreIme(var1, var2);
    }

    public void onLastPageButton(View var1) {
        this.i.goToLastPage();
        if (this.usePagesView()) {
            this.j.goToLastPage();
        }

    }

    public void onLayoutChanged() {
        SODocSession var1 = this.mSession;
        if (var1 != null && var1.getDoc() != null && !this.mFinished) {
            this.i.onLayoutChanged();
        }

    }

    public void onListBulletsButton(View var1) {
        ToolbarButton var2;
        if (this.mListBulletsButton.isSelected()) {
            var2 = this.mListBulletsButton;
        } else {
            this.mListBulletsButton.setSelected(true);
            var2 = this.mListNumbersButton;
        }

        var2.setSelected(false);
        this.n();
    }

    public void onListNumbersButton(View var1) {
        ToolbarButton var2;
        if (this.mListNumbersButton.isSelected()) {
            var2 = this.mListNumbersButton;
        } else {
            this.mListNumbersButton.setSelected(true);
            var2 = this.mListBulletsButton;
        }

        var2.setSelected(false);
        this.n();
    }

    protected void onMeasure(int var1, int var2) {
        this.a();
        super.onMeasure(var1, var2);
    }

    public void onOpenInButton(View var1) {
        this.preSave();
        if (this.n != null) {
            try {
                File var4 = new File(this.f.getOpenedPath());
                this.n.openInHandler(var4.getName(), this.mSession.getDoc());
            } catch (NullPointerException var2) {
            } catch (UnsupportedOperationException var3) {
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public void onOpenPDFInButton(View var1) {
        this.preSave();
        if (this.n != null) {
            try {
                File var4 = new File(this.f.getOpenedPath());
                this.n.openPdfInHandler(var4.getName(), this.mSession.getDoc());
            } catch (NullPointerException var2) {
            } catch (UnsupportedOperationException var3) {
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    protected void onOrientationChange() {
        this.i.onOrientationChange();
        if (this.usePagesView()) {
            this.j.onOrientationChange();
        }

        if (!this.isFullScreen()) {
            this.showUI(this.keyboardShown ^ true);
        }

        this.onDeviceSizeChange();
    }

    protected void onPageLoaded(int var1) {
        int var2 = this.mPageCount;
        boolean var3 = false;
        boolean var7;
        if (var2 == 0) {
            var7 = true;
        } else {
            var7 = false;
        }

        this.mPageCount = var1;
        if (var7) {
            this.k();
            this.updateUIAppearance();
            if (this.Q != null) {
                this.Q.setEnabled(true);
            }
        }

        var2 = this.mAdapter.getCount();
        int var5 = this.mPageCount;
        boolean var6 = var3;
        if (var5 != var2) {
            var6 = true;
        }

        if (var5 < var2) {
            this.i.removeAllViewsInLayout();
            if (this.usePagesView()) {
                this.j.removeAllViewsInLayout();
            }
        }

        this.mAdapter.setCount(this.mPageCount);
        if (var6) {
            final ViewTreeObserver var8 = this.getViewTreeObserver();
            var8.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                public void onGlobalLayout() {
                    var8.removeOnGlobalLayoutListener(this);
                    if (!NUIDocView.this.mFinished) {
                        if (NUIDocView.this.i.getReflowMode()) {
                            NUIDocView.this.onReflowScale();
                        } else {
                            NUIDocView.this.i.scrollSelectionIntoView();
                        }

                    }
                }
            });
            this.layoutAfterPageLoad();
        } else {
            this.g();
        }

        this.handleStartPage();
        if (!this.ap) {
            this.ap = true;
            (new Handler()).postDelayed(new Runnable() {
                public void run() {
                    NUIDocView.this.setPageNumberText();
                    NUIDocView.this.ap = false;
                }
            }, 1000L);
        }

    }

    public void onPasteButton(View var1) {
        this.doPaste();
    }

    public void onPause() {
        this.onPauseCommon();
        DocView var1 = this.i;
        if (var1 == null || !var1.finished()) {
            if (this.f != null) {
                var1 = this.i;
                if (var1 != null && var1.getDoc() != null && this.n != null) {
                    SODoc var2 = this.i.getDoc();
                    this.n.pauseHandler(var2, var2.getHasBeenModified());
                }
            }

        }
    }

    protected void onPauseCommon() {
        this.aa = false;
        this.resetInputView();
    }

    public void onPrintButton() {
        Log.d("abhay", "print function entered");
        if (activity() != null && !activity().isFinishing() && !activity().isDestroyed()) {
            SODataLeakHandlers var3 = this.n;
            if (var3 != null) {
                try {
                    if (this.mSession != null) {
                        var3.printHandler(this.mSession.getDoc());
                    }
                } catch (UnsupportedOperationException var2) {
                    var2.printStackTrace();
                }
            } else {
                throw new UnsupportedOperationException();
            }
        }
    }

    public void onProtectButton(View var1) {
    }

    public void onRedoButton(View var1) {
        this.doRedo();
    }

    public void onReflowButton(View var1) {
        this.mSession.setSODocSessionLoadListener2(new SODocSessionLoadListener() {
            public void onCancel() {
            }

            public void onDocComplete() {
            }

            public void onError(int var1, int var2) {
            }

            public void onLayoutCompleted() {
                NUIDocView.this.onLayoutChanged();
            }

            public void onPageLoad(int var1) {
            }

            public void onSelectionChanged(int var1, int var2) {
                NUIDocView.this.mSession.setSODocSessionLoadListener2((SODocSessionLoadListener) null);
                NUIDocView.this.i.scrollTo(NUIDocView.this.i.getScrollX(), 0);
                if (NUIDocView.this.usePagesView()) {
                    NUIDocView.this.j.scrollTo(NUIDocView.this.j.getScrollX(), 0);
                }

                NUIDocView.this.setCurrentPage(0);
                if (NUIDocView.this.i.getReflowMode()) {
                    NUIDocView.this.i.setReflowWidth();
                    NUIDocView.this.i.onScaleEnd((ScaleGestureDetector) null);
                } else {
                    float var3 = 1.0F;
                    if (NUIDocView.this.usePagesView()) {
                        var3 = (float) NUIDocView.this.getResources().getInteger(integer.sodk_editor_page_width_percentage) / 100.0F;
                    }

                    NUIDocView.this.i.setScale(var3);
                    NUIDocView.this.i.scaleChildren();
                }

                if (NUIDocView.this.usePagesView()) {
                    NUIDocView.this.j.fitToColumns();
                }

                NUIDocView.this.layoutNow();
            }
        });
        if (this.getDoc().K() == 1) {
            if (this.usePagesView()) {
                this.j.setReflowMode(true);
            }

            this.i.setReflowMode(true);
            this.getDoc().a(2, (float) this.getDocView().getReflowWidth());
            this.i.mLastReflowWidth = (float) this.getDocView().getReflowWidth();
        } else {
            this.i.setReflowMode(false);
            if (this.usePagesView()) {
                this.j.setReflowMode(false);
            }

            this.getDoc().a(1, (float) this.getDocView().getReflowWidth());
        }

    }

    public void onReflowScale() {
        this.i.onReflowScale();
        if (this.usePagesView()) {
            this.j.onReflowScale();
        }

    }

    public void onResume() {
        this.onResumeCommon();
        this.am = 0;
        this.onShowKeyboard(false);
        SOFileState var1 = SOFileState.getAutoOpen(this.getContext());
        if (var1 != null && this.f != null && var1.getLastAccess() > this.f.getLastAccess()) {
            this.f.setHasChanges(var1.hasChanges());
        }

        SOFileState.clearAutoOpen(this.getContext());
        SODataLeakHandlers var2 = this.n;
        if (var2 != null) {
            var2.doInsert();
        }

    }

    protected void onResumeCommon() {
        ak = this;
        String var1 = this.l;
        if (var1 != null) {
            this.a(var1);
            this.s();
        }

        if (this.au) {
//            this.au = false;
//            this.getDoc().a(true);
//            this.reloadFile();
        }

        this.aa = true;
        this.focusInputView();
        DocView var2 = this.getDocView();
        if (var2 != null) {
            var2.forceLayout();
        }

        if (this.usePagesView()) {
            DocListPagesView var3 = this.getDocListPagesView();
            if (var3 != null) {
                var3.forceLayout();
            }
        }

    }

    public void onSaveAsButton(View var1) {
        this.preSave();
        this.a(false);
    }

    public void onSaveButton(View var1) {
        this.preSave();
        this.doSave();
    }

    public void onSavePDFButton(View var1) {
        if (this.n != null && this.f != null && this.mSession != null) {
            try {
                File var3 = new File(this.f.getOpenedPath());
                this.n.saveAsPdfHandler(var3.getName(), this.mSession.getDoc());
            } catch (Exception var2) {
            }
        }
    }

    protected void onSearch() {
        this.setTab(this.getContext().getString(string.sodk_editor_tab_hidden));
        if (Utilities.isPhoneDevice(this.getContext())) {
            this.l();
        }

        this.findViewById(id.searchTab).setVisibility(VISIBLE);
        this.showSearchSelected(true);
        this.x.getText().clear();
        this.x.requestFocus();
        Utilities.showKeyboard(this.getContext());
    }

    public void onSearchButton(View var1) {
        this.onSearch();
    }

    public void onSearchNext(View var1) {
        SODoc var2 = this.getDoc();
        if (var2 != null) {
            if (var2.p()) {
                return;
            }

            var2.d(false);
            this.t();
        }

    }

    public void onSearchPrevious(View var1) {
        SODoc var2 = this.getDoc();
        if (var2 != null) {
            if (var2.p()) {
                return;
            }

            var2.d(true);
            this.t();
        }

    }

    public void onSelectionChanged() {
        try {
            SODocSession var1 = this.mSession;
            if (var1 != null && var1.getDoc() != null && !this.mFinished) {
                this.i.onSelectionChanged();
                if (this.usePagesView() && this.isPageListVisible()) {
                    this.j.onSelectionChanged();
                }

                this.updateUIAppearance();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void onSelectionMonitor(int var1, int var2) {
        try {
            this.onSelectionChanged();
            if (this.getDocView().getSelectionLimits() == null) {
                this.defocusInputView();
            } else {
                this.focusInputView();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onShareButton() {
        this.preSave();
        if (this.n != null) {
            try {
                File var4 = new File(this.f.getOpenedPath());
                this.n.shareHandler(var4.getName(), this.mSession.getDoc());
            } catch (NullPointerException var2) {
            } catch (UnsupportedOperationException var3) {
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public void onShowKeyboard(final boolean var1) {
        //to hide the keyboard when expand editor is open
        if (var1) {
            Bundle bundle = new Bundle();
            bundle = activity().getIntent().getExtras();
            if (bundle == null || !bundle.containsKey(Constant.PREVIEW)) {
                this.editorExpandOptions = (LinearLayout) this.createToolbarButton(MainR.getMainAppInt("editor_expand_options"));
                if (this.editorExpandOptions != null)
                    this.editorExpandOptions.setVisibility(GONE);
            }
        }

        if (this.isActivityActive()) {
            if (this.getPageCount() > 0) {
                this.keyboardShown = var1;
                this.onShowKeyboardPreventPush(var1);
                if (!this.isFullScreen()) {
                    this.showUI(var1 ^ true);
                }

                if (this.usePagesView()) {
                    DocListPagesView var2 = this.getDocListPagesView();
                    if (var2 != null) {
                        var2.onShowKeyboard(var1);
                    }
                }

                final ViewTreeObserver var3 = this.getViewTreeObserver();
                var3.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                    public void onGlobalLayout() {
                        var3.removeOnGlobalLayoutListener(this);
                        DocView var1x = NUIDocView.this.getDocView();
                        if (var1x != null) {
                            var1x.onShowKeyboard(var1);
                        }

                        if (NUIDocView.this.at != null) {
                            NUIDocView.this.at.run();
                            NUIDocView.this.at = null;
                        }

                    }
                });
            }
        }
    }

    protected void onShowKeyboardPreventPush(boolean var1) {
        boolean var2;
        if ((this.activity().getWindow().getAttributes().flags & 1024) != 0) {
            var2 = true;
        } else {
            var2 = false;
        }

        if (var2) {
            View var3 = ((BaseActivity) this.getContext()).findViewById(16908290);
            if (var1) {
                var3.setPadding(0, 0, 0, this.getKeyboardHeight());
                this.findViewById(id.footer).setVisibility(GONE);
            } else {
                var3.setPadding(0, 0, 0, 0);
                this.findViewById(id.footer).setVisibility(VISIBLE);
            }
        }
    }

    public void onTabChanged(String var1) {
        try {
            this.onTabChanging(this.aq, var1);
            if (var1.equals(this.activity().getString(string.sodk_editor_tab_review)) && !this.getDocView().getDoc().docSupportsReview()) {
                Utilities.showMessage(this.activity(), this.activity().getString(string.sodk_editor_not_supported), this.activity().getString(string.sodk_editor_cant_review_doc_body));
                this.setTab(this.aq);
                if (this.aq.equals(this.activity().getString(string.sodk_editor_tab_hidden))) {
                    this.onSearchButton(this.u);
                }

                this.onSelectionChanged();
            } else if (var1.equals(this.activity().getString(string.sodk_editor_tab_single))) {
                this.setTab(this.aq);
                this.onSelectionChanged();
                this.activity();
                ListPopupWindow var6 = new ListPopupWindow(this.activity());
                this.ad = var6;
                var6.setBackgroundDrawable(ContextCompat.getDrawable(this.activity(), com.artifex.sonui.editor.R.drawable.sodk_editor_menu_popup));
                this.ad.setModal(true);
                this.ad.setAnchorView(this.getSingleTabView());
                final ArrayAdapter var2 = new ArrayAdapter(this.activity(), layout.sodk_editor_menu_popup_item);
                this.ad.setAdapter(var2);
                TabData[] var7 = this.getTabData();
                int var3 = var7.length;

                for (int var4 = 0; var4 < var3; ++var4) {
                    if (var7[var4].visibility == 0) {
                        String var5 = var7[var4].name;
                        var2.add(var5);
                        ((SOTextView) this.activity().getLayoutInflater().inflate(layout.sodk_editor_menu_popup_item, (ViewGroup) null)).setText(var5);
                    }
                }

                if (this.hasSearch()) {
                    var2.add(this.activity().getString(string.sodk_editor_tab_find));
                    ((SOTextView) this.activity().getLayoutInflater().inflate(layout.sodk_editor_menu_popup_item, (ViewGroup) null)).setText(this.activity().getString(string.sodk_editor_tab_find));
                }

                this.ad.setOnItemClickListener(new OnItemClickListener() {
                    public void onItemClick(AdapterView<?> var1, View var2x, int var3, long var4) {
                        if (NUIDocView.this.ad != null) {
                            NUIDocView.this.ad.dismiss();
                        }

                        NUIDocView.this.ad = null;
                        NUIDocView.this.h();
                        String var6 = (String) var2.getItem(var3);
                        if (var6.equals(NUIDocView.this.activity().getString(string.sodk_editor_tab_find))) {
                            NUIDocView.this.onSearch();
                            NUIDocView.this.setSingleTabTitle(var6);
                        } else if (var6.equals(NUIDocView.this.activity().getString(string.sodk_editor_tab_review)) && !NUIDocView.this.getDocView().getDoc().docSupportsReview()) {
                            Utilities.showMessage(NUIDocView.this.activity(), NUIDocView.this.activity().getString(string.sodk_editor_not_supported), NUIDocView.this.activity().getString(string.sodk_editor_cant_review_doc_body));
                        } else {
                            NUIDocView.this.changeTab(var6);
                            NUIDocView.this.setSingleTabTitle(var6);
                            NUIDocView.this.af.setCurrentTabByTag(var6);
                        }

                        NUIDocView var7 = NUIDocView.this;
                        var7.b(var7.getSingleTabView(), true);
                    }
                });
                this.ad.setContentWidth(this.a((ListAdapter) var2));
                this.ad.show();
            } else {
                this.changeTab(var1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void onTabChanging(String var1, String var2) {
    }

    public void onTapFontName(View var1) {
        if (this.getContext() != null && this.getDoc() != null) {
            EditFont.show(this.getContext(), var1, this.getDoc());
        }
    }

    public void setSelectionFontName(String fontItem) {
        if (this.getDoc() != null) {
            this.getDoc().setSelectionFontName(fontItem);
        }
    }

    public void setSelectionFontSize(Double fontItem) {
        if (this.getDoc() != null) {
            this.getDoc().setSelectionFontSize(fontItem);
        }
    }

    public void onTyping() {
        this.ar = System.currentTimeMillis();
    }

    public void onUndoButton(View var1) {
        this.doUndo();
    }

    public void preSave() {
    }

    protected void preSaveQuestion(Runnable var1, Runnable var2) {
        if (var1 != null) {
            var1.run();
        }

    }

    public void prefinish() {
        if (!this.mFinished) {
            this.mFinished = true;
            SODocSession var1 = this.mSession;
            if (var1 != null && var1.getDoc() != null) {
                this.mSession.getDoc().a((p) null);
            }

            this.m = null;
            this.j();
            SOFileState var2 = this.f;
            if (var2 != null) {
                var2.closeFile();
            }

            Utilities.hideKeyboard(this.getContext());
            DocView var3 = this.i;
            if (var3 != null) {
                var3.finish();
                this.i = null;
            }

            if (this.usePagesView()) {
                DocListPagesView var4 = this.j;
                if (var4 != null) {
                    var4.finish();
                    this.j = null;
                }
            }

            var1 = this.mSession;
            if (var1 != null) {
                var1.abort();
            }

            SODoc var5 = this.getDoc();
            if (var5 != null) {
                var5.M();
            }

            PageAdapter var6 = this.mAdapter;
            if (var6 != null) {
                var6.setDoc((SODoc) null);
            }

            this.mAdapter = null;
            Boolean var7 = this.e;
            if (var7 != null) {
                this.endDocSession(var7);
                this.e = null;
            }

            if (this.mSession != null) {
                final ProgressDialog var8 = new ProgressDialog(this.getContext(), style.sodk_editor_alert_dialog_style);
                var8.setMessage(this.getContext().getString(string.sodk_editor_wait));
                var8.setCancelable(false);
                var8.setIndeterminate(true);
                var8.getWindow().clearFlags(2);
                var8.setOnShowListener(new OnShowListener() {
                    public void onShow(DialogInterface var1) {
                        (new Handler()).post(new Runnable() {
                            public void run() {
                                if (NUIDocView.this.mSession != null) {
                                    NUIDocView.this.mSession.destroy();
                                }

                                var8.dismiss();
                                if (NUIDocView.this.a != null) {
                                    NUIDocView.this.a.done();
                                }

                            }
                        });
                    }
                });
                var8.show();
            } else {
                OnDoneListener var9 = this.a;
                if (var9 != null) {
                    var9.done();
                }
            }

        }
    }

    protected void prepareToGoBack() {
    }

    public void releaseBitmaps() {
        this.setValid(false);
        this.q();
        this.s();
    }

    public void reloadFile() {
    }

    protected void resetInputView() {
        InputView var1 = this.aj;
        if (var1 != null) {
            var1.resetEditable();
        }

    }

    protected void scaleHeader() {
    }

    protected void scaleSearchToolbar(float var1) {
        LinearLayout var2 = (LinearLayout) this.findViewById(id.search_toolbar);
        if (var2 != null) {
            this.a(var2, id.search_icon, var1);
            this.a(var2, id.search_text_clear, var1);
            this.a(var2, id.search_next, var1);
            this.a(var2, id.search_previous, var1);
            LinearLayout var3 = (LinearLayout) var2.findViewById(id.search_input_wrapper);
            Context var4;
            int var5;
            if (Utilities.isPhoneDevice(this.getContext())) {
                var4 = this.getContext();
                var5 = com.artifex.sonui.editor.R.drawable.sodk_editor_search_input_wrapper_phone;
            } else {
                var4 = this.getContext();
                var5 = com.artifex.sonui.editor.R.drawable.sodk_editor_search_input_wrapper;
            }

            var3.setBackground(ContextCompat.getDrawable(var4, var5));
            this.x.setTextSize(2, var1 * 20.0F);
            var3.measure(0, 0);
            var5 = var3.getMeasuredHeight();
            var3.getLayoutParams().height = (int) ((float) var5 * 0.85F);
            var2.setPadding(0, -15, 0, -15);
        }
    }

    protected void scaleTabArea(float var1) {
        LinearLayout var2 = (LinearLayout) this.findViewById(id.header_top);
        if (var2 != null) {
            var2.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int var3 = var2.getMeasuredHeight();
            var2.getLayoutParams().height = (int) ((float) var3 * var1);
            var2.requestLayout();
            var2.invalidate();
        }
    }

    protected void scaleToolbar(int var1, float var2) {
    }

    public void selectionupdated() {
        this.onSelectionChanged();
    }

    protected void setButtonColor(Button var1, int var2) {
        Drawable[] var4 = var1.getCompoundDrawables();

        for (int var3 = 0; var3 < var4.length; ++var3) {
            if (var4[var3] != null) {
                DrawableCompat.setTint(var4[var3], var2);
            }
        }

    }

    public void setConfigurableButtons() {
        ArrayList var1 = new ArrayList();
        if (this.mSaveButton != null) {
            if (this.mConfigOptions.p()) {
                this.mSaveButton.setVisibility(VISIBLE);
                var1.add(this.mSaveButton);
            } else {
                this.mSaveButton.setVisibility(GONE);
            }
        }

        if (this.p != null) {
            if (this.mConfigOptions.q()) {
                this.p.setVisibility(VISIBLE);
                var1.add(this.p);
            } else {
                this.p.setVisibility(GONE);
            }
        }

        if (this.mSaveAsButton != null) {
            if (this.mConfigOptions.d()) {
                this.mSaveAsButton.setVisibility(VISIBLE);
                var1.add(this.mSaveAsButton);
            } else {
                this.mSaveAsButton.setVisibility(GONE);
            }
        }

        if (this.shouldConfigureSaveAsPDFButton() && this.mSavePdfButton != null) {
            if (this.mConfigOptions.e()) {
                this.mSavePdfButton.setVisibility(VISIBLE);
                var1.add(this.mSavePdfButton);
            } else {
                this.mSavePdfButton.setVisibility(GONE);
            }
        }

        if (this.o != null) {
            if (this.mConfigOptions.h()) {
                this.o.setVisibility(VISIBLE);
                var1.add(this.o);
            } else {
                this.o.setVisibility(GONE);
            }
        }

        if (this.mOpenInButton != null) {
            if (this.mConfigOptions.f()) {
                this.mOpenInButton.setVisibility(VISIBLE);
                var1.add(this.mOpenInButton);
            } else {
                this.mOpenInButton.setVisibility(GONE);
            }
        }

        if (this.mOpenPdfInButton != null) {
            if (this.mConfigOptions.g()) {
                this.mOpenPdfInButton.setVisibility(VISIBLE);
                var1.add(this.mOpenPdfInButton);
            } else {
                this.mOpenPdfInButton.setVisibility(GONE);
            }
        }

        if (this.mPrintButton != null) {
            if (!this.mConfigOptions.m() && !this.mConfigOptions.n()) {
                this.mPrintButton.setVisibility(GONE);
            } else {
                this.mPrintButton.setVisibility(VISIBLE);
                var1.add(this.mPrintButton);
            }
        }
        if (this.mShareButton != null) {
            if (!this.mConfigOptions.m() && !this.mConfigOptions.n()) {
                this.mShareButton.setVisibility(GONE);
            } else {
                this.mShareButton.setVisibility(VISIBLE);
                var1.add(this.mShareButton);
            }
        }

        ToolbarButton var2 = this.mProtectButton;
        if (var2 != null) {
            var1.add(var2);
            this.mProtectButton.setVisibility(GONE);
        }

        //ToolbarButton.setAllSameSize((ToolbarButton[]) var1.toArray(new ToolbarButton[var1.size()]));
        if (!this.mConfigOptions.c()) {
            if (this.mUndoButton != null) {
                this.mUndoButton.setVisibility(GONE);
            }

            if (this.mRedoButton != null) {
                this.mRedoButton.setVisibility(GONE);
            }
        }

        ArrayList var4 = new ArrayList();
        if (this.mInsertImageButton != null) {
            if (this.mConfigOptions.k()) {
                this.mInsertImageButton.setVisibility(VISIBLE);
                var4.add(this.mInsertImageButton);
            } else {
                this.mInsertImageButton.setVisibility(GONE);
            }
        }

        if (this.mInsertPhotoButton != null) {
            if (this.mConfigOptions.l()) {
                this.mInsertPhotoButton.setVisibility(VISIBLE);
                var4.add(this.mInsertPhotoButton);
            } else {
                this.mInsertPhotoButton.setVisibility(GONE);
            }
        }

        /*if (var4.size() > 0) {
            ToolbarButton.setAllSameSize((ToolbarButton[]) var4.toArray(new ToolbarButton[var4.size()]));
        }*/

        this.setInsertTabVisibility();
    }

    public void setCurrentPage(int var1) {
        if (this.usePagesView()) {
            this.j.setCurrentPage(var1);
            this.j.scrollToPage(var1, false);
        }

        this.ab = var1;
        this.setPageNumberText();
        this.mSession.getFileState().setPageNumber(this.ab);
    }

    protected void setInsertTabVisibility() {
        if (this.af != null) {
            if (Utilities.isPhoneDevice(this.getContext())) {
                this.r();
            } else {
                TabWidget var1 = this.af.getTabWidget();
                if (var1 != null) {
                    int var2 = this.ae.indexOf(this.getContext().getString(string.sodk_editor_tab_insert));
                    if (var2 != -1) {
                        TabData[] var3 = this.getTabData();
                        byte var4;
                        View var5;
                        if (!this.mConfigOptions.k() && !this.mConfigOptions.l()) {
                            if (this.af.getCurrentTab() == var2) {
                                int var6 = this.ae.indexOf(var3[this.getInitialTab()].name);
                                this.af.setCurrentTab(var6);
                            }

                            var5 = var1.getChildTabViewAt(var2);
                            var4 = 8;
                        } else {
                            var5 = var1.getChildTabViewAt(var2);
                            var4 = 0;
                        }

                        var5.setVisibility(var4);
                    }
                }
            }
        }
    }

    public void setIsComposing(boolean var1) {
        this.W = var1;
    }

    protected void setPageCount(int var1) {
        try {
            this.mPageCount = var1;
            this.mAdapter.setCount(var1);
            this.g();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void setPageNumberText() {
        (new Handler()).post(new Runnable() {
            public void run() {
                NUIDocView.this.A.setText(NUIDocView.this.getPageNumberText());
                NUIDocView.this.A.measure(0, 0);
                //NUIDocView.this.B.getLayoutParams().width = NUIDocView.this.A.getMeasuredWidth();
                //NUIDocView.this.B.getLayoutParams().height = NUIDocView.this.A.getMeasuredHeight();
            }
        });
    }

    public void setSearchStart() {
    }

    protected void setStartPage(int var1) {
        this.R = var1;
    }

    protected void setTabColors(String var1) {
        Iterator var2 = this.tabMap.entrySet().iterator();

        while (var2.hasNext()) {
            Entry var3 = (Entry) var2.next();
            String var4 = (String) var3.getKey();
            this.b((View) var3.getValue(), var1.equals(var4));
        }

        this.b(this.getSingleTabView(), true);
    }

    protected void setupTab(TabHost var1, String var2, int var3, int var4, int var5) {
        if (!Utilities.isPhoneDevice(this.activity()) && var5 == 8) {
            this.findViewById(var3).setVisibility(GONE);
        } else {
            View var6 = LayoutInflater.from(var1.getContext()).inflate(var4, (ViewGroup) null);
            ((SOTextView) var6.findViewById(id.tabText)).setText(var2);
            this.tabMap.put(var2, var6);
            TabSpec var7 = var1.newTabSpec(var2);
            var7.setIndicator(var6);
            var7.setContent(var3);
            var1.addTab(var7);
            this.ae.add(var2);
        }
    }

    public void setupTabs() {
        TabHost tabHost = (TabHost) findViewById(com.document.docease.R.id.tabhost);
        Bundle bundle = new Bundle();
        bundle = activity().getIntent().getExtras();
        if (bundle != null && bundle.containsKey(Constant.PREVIEW)) {
            tabHost.setVisibility(GONE);
        }

        this.af = tabHost;
        tabHost.setup();
        final TabData[] tabData = getTabData();
        setupTab(this.af, getContext().getString(com.document.docease.R.string.sodk_editor_tab_hidden), com.document.docease.R.id.hiddenTab, com.document.docease.R.layout.sodk_editor_tab, 0);
        this.af.getTabWidget().getChildTabViewAt(0).setVisibility(GONE);
        for (TabData tabData2 : tabData) {
            setupTab(this.af, tabData2.name, tabData2.contentId, tabData2.layoutId, tabData2.visibility);
        }
        setupTab(this.af, getContext().getString(com.document.docease.R.string.sodk_editor_tab_single), com.document.docease.R.id.hiddenTab, com.document.docease.R.layout.sodk_editor_tab_single, 0);
        final int initialTab = getInitialTab();
        setTab(tabData[initialTab].name);
        final ViewTreeObserver viewTreeObserver = getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                viewTreeObserver.removeOnGlobalLayoutListener(this);
                NUIDocView.this.setTabColors(tabData[initialTab].name);
            }
        });
        setSingleTabTitle(tabData[initialTab].name);
        int i2 = 0;
        while (i2 < tabData.length) {
            i2++;
            View childAt = this.af.getTabWidget().getChildAt(i2);
            if (childAt != null) {
                SOTextView sOTextView = (SOTextView) childAt.findViewById(com.document.docease.R.id.tabText);
                if (sOTextView != null) {
                    sOTextView.measure(0, 0);
                    childAt.getLayoutParams().width = sOTextView.getMeasuredWidth() + childAt.getPaddingLeft() + childAt.getPaddingRight();
                }
            }
        }
        this.af.setOnTabChangedListener(this);
        TabWidget tabWidget = this.af.getTabWidget();
        int tabCount = tabWidget.getTabCount();
        for (int i3 = 0; i3 < tabCount; i3++) {
            tabWidget.getChildAt(i3).setOnTouchListener(new OnTouchListener() {
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getAction() == 1) {
                        NUIDocView.this.h();
                    }
                    return false;
                }
            });
        }
    }

    protected boolean shouldConfigureSaveAsPDFButton() {
        return true;
    }

    protected void showKeyboard(boolean var1, Runnable var2) {
        boolean var3;
        if (this.getKeyboardHeight() > 0) {
            var3 = true;
        } else {
            var3 = false;
        }

        label29:
        {
            if (var1) {
                Utilities.showKeyboard(this.getContext());
                if (var3) {
                    break label29;
                }
            } else {
                Utilities.hideKeyboard(this.getContext());
                if (!var3) {
                    break label29;
                }
            }

            this.at = var2;
            return;
        }

        var2.run();
    }

    public boolean showKeyboard() {
        Bundle bundle = new Bundle();
        bundle = activity().getIntent().getExtras();
        if (bundle != null && bundle.containsKey(Constant.PREVIEW)) {
            return false;
        }
        if (this.isBeingSelected) {
            return false;
        }
        Utilities.showKeyboard(this.getContext());
        return true;
    }

    protected void showPages() {
        RelativeLayout var1 = (RelativeLayout) this.findViewById(id.pages_container);
        if (var1 != null && var1.getVisibility() != VISIBLE) {
            final int var2 = this.i.getMostVisiblePage();
            this.i.onShowPages();
            var1.setVisibility(VISIBLE);
            final ViewTreeObserver var3 = this.i.getViewTreeObserver();
            var3.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                public void onGlobalLayout() {
                    var3.removeOnGlobalLayoutListener(this);
                    NUIDocView.this.j.setCurrentPage(var2);
                    NUIDocView.this.j.scrollToPage(var2, false);
                    NUIDocView.this.j.fitToColumns();
                    NUIDocView.this.j.layoutNow();
                }
            });
        }

    }

    protected void showSearchSelected(boolean var1) {
        Button var2 = this.u;
        if (var2 != null) {
            var2.setSelected(var1);
            Activity var3;
            int var4;
            if (var1) {
                var2 = this.u;
                var3 = this.activity();
                var4 = color.sodk_editor_button_tint;
            } else {
                var2 = this.u;
                var3 = this.activity();
                var4 = color.sodk_editor_header_button_enabled_tint;
            }

            this.setButtonColor(var2, ContextCompat.getColor(var3, var4));
        }

    }

    public void showUI(boolean var1) {
        label46:
        {
            label45:
            {
                View var2 = this.findViewById(id.tabhost);
                View var3 = this.findViewById(id.header);
                if (this.isLandscapePhone()) {
                    if (!var1 && var2.getVisibility() != GONE && !this.isSearchVisible()) {
                        var2.setVisibility(GONE);
                        var3.setVisibility(GONE);
                        break label45;
                    }

                    if (!var1 || var2.getVisibility() == VISIBLE) {
                        break label46;
                    }
                } else if (var2.getVisibility() == VISIBLE) {
                    break label46;
                }

                var2.setVisibility(VISIBLE);
                var3.setVisibility(VISIBLE);
            }

            this.layoutNow();
        }

        if (this.mConfigOptions.A() && var1) {
            Toast var4 = this.av;
            if (var4 != null) {
                var4.cancel();
            }

            this.findViewById(id.footer).setVisibility(VISIBLE);
            if (this.isPagesTab()) {
                this.showPages();
            }

            this.aw = false;
            if (this.getDocView() != null) {
                this.getDocView().onFullscreen(false);
            }

            this.layoutNow();
        }

        TabHost tabHost = (TabHost) findViewById(com.document.docease.R.id.tabhost);
        Bundle bundle = new Bundle();
        bundle = activity().getIntent().getExtras();
        if (bundle != null && bundle.containsKey(Constant.PREVIEW)) {
            tabHost.setVisibility(GONE);
        }

//        else if (Utility.INSTANCE.getFileType() == TYPE_PDF) {
//            tabHost.setVisibility(GONE);
//        }

    }

    public void handleSelectionPopup(float x, float y) {
        if (this.getDocView() != null && this.getDocView().getSelectionLimits() != null && popupWindow != null) {
            if (popupWindow.isShowing()) {
                popupWindow.dismiss();
                showPopUpOnSelection(x, y);
            } else {
                showPopUpOnSelection(x, y);
            }
        }
    }

    public void showPopUpOnSelection(float x, float y) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    if (popupWindow != null && NUIDocView.this.getDocView() != null && NUIDocView.this.getDocView().getSelectionLimits() != null) {
                        SOSelectionLimits limits = NUIDocView.this.getDocView().getSelectionLimits();
                        RectF rect = new RectF(limits.getBox().left, limits.getBox().top, limits.getBox().right, limits.getBox().bottom);
                        float threshold = Math.abs(limits.getBox().right - limits.getBox().left); //to check if it is selection or typing focus
                        if (threshold > 5F && !wasTyping()) {
                            isBeingSelected = true;
                            if (!isPreview) {
                                if (NUIDocView.this.getDoc().clipboardHasData() || checkIfClipBoardHasText()) {
                                    togglePaste(true);
                                } else {
                                    togglePaste(false);
                                }
                            }
                            //to prevent popup to cover toolbar
                            float posY = y;
                            if (posY < 350F) {
                                posY += 400;
                            }
                            popupWindow.showAtLocation(NUIDocView.this.getDocView().getRootView(), 0, (int) x, (int) posY);
                        } else {
                            isBeingSelected = false;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 200);
    }

    public boolean checkIfClipBoardHasText(){
        try {
            ClipboardManager clipboard = (ClipboardManager)activity().getSystemService(Context.CLIPBOARD_SERVICE);
            if(clipboard.hasPrimaryClip() && clipboard.getPrimaryClipDescription().hasMimeType(MIMETYPE_TEXT_PLAIN)){
                return true;
            } else {
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public void showPastePopUp(float x, float y) {
        try {
            hideCopy();
            if (popupWindow.isShowing()) {
                popupWindow.dismiss();
            }
            //to prevent popup to cover toolbar
            float posY = y;
            if (posY < 350F) {
                posY += 400;
            }
            popupWindow.showAtLocation(NUIDocView.this.getDocView().getRootView(), 0, (int) x, (int) posY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void start(Uri var1, boolean var2, int var3, String var4, OnDoneListener var5) {
        this.T = var2;
        this.mStartUri = var1;
        this.k = var4;
        this.a = var5;
        this.c();
        this.e();
        this.d();
        this.checkIfPdf();
    }

    private boolean isDeviceLowOnMemory(){
        try {
            ActivityManager activityManager = (ActivityManager) activity().getSystemService(Context.ACTIVITY_SERVICE);
            ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
            activityManager.getMemoryInfo(memoryInfo);
            return  memoryInfo.lowMemory;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    private void checkIfPdf() {
            try {
                if (getDocFileExtension() != null) {
                    if (getDocFileExtension().toLowerCase().contains("pdf")) {
                        isPdf = true;
                        FileUtil fileUtil = new FileUtil(activity());
                        String filePath = fileUtil.getPath(this.mStartUri);
                        if(isDeviceLowOnMemory()){
                            return;
                        }
                        initSelectionPopup();
                        if (filePath != null) {
                            File mFile = new File(filePath);
                            document = PDDocument.load(mFile, MemoryUsageSetting.setupTempFileOnly());
                        }
                    } else{
                        initSelectionPopup();
                    }
                } else{
                    initSelectionPopup();
                }
            }catch (Exception e){
                e.printStackTrace();
            }

    }

    private void togglePaste(boolean showPaste) {
        try {
            if (showPaste && !isPdf) {
                this.copyView.setVisibility(View.VISIBLE);
                this.pasteView.setVisibility(View.VISIBLE);
                this.lineView.setVisibility(View.VISIBLE);
            } else {
                this.copyView.setVisibility(View.VISIBLE);
                this.pasteView.setVisibility(View.GONE);
                this.lineView.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void hideCopy() {
        try {
            this.copyView.setVisibility(View.GONE);
            this.lineView.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initSelectionPopup() {
        try {
            if (activity().getIntent() != null) {
                if (activity().getIntent().hasExtra(Constant.PREVIEW)) {
                    isPreview = true;
                }
            }
            this.popupWindow = new PopupWindow(activity());
            popupView = LayoutInflater.from(activity()).inflate(com.document.docease.R.layout.layout_copy_popup, null);
            this.popupWindow.setContentView(popupView);
            this.popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
            this.popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            this.popupWindow.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            this.popupWindow.setOutsideTouchable(true);
//            this.popupWindow.setFocusable(true);
            copyView = popupView.findViewById(com.document.docease.R.id.popupTextView);
            pasteView = popupView.findViewById(com.document.docease.R.id.popupTextViewPaste);
            lineView = popupView.findViewById(com.document.docease.R.id.popupLine);
            copyView.setOnClickListener(v -> {
                if (NUIDocView.this.isPdf) {
                    NUIDocView.this.copyTextInPdf();
                } else {
                    NUIDocView.this.doCopy();
                }
                popupWindow.dismiss();
            });

            pasteView.setOnClickListener(v -> {
                this.doPaste();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start(SODocSession var1, int var2, String var3, OnDoneListener var4) {
        this.mIsSession = true;
        this.mSession = var1;
        this.T = false;
        this.setStartPage(var2);
        this.a = var4;
        this.U = var3;
        this.c();
        this.e();
        this.d();
    }

    public void start(SOFileState var1, int var2, OnDoneListener var3) {
        this.mIsSession = false;
        this.T = var1.isTemplate();
        this.mState = var1;
        this.setStartPage(var2);
        this.a = var3;
        this.c();
        this.e();
        this.d();
    }

    public void triggerOrientationChange() {
        this.ao = true;
    }

    public void triggerRender() {
        DocView var1 = this.i;
        if (var1 != null) {
            var1.triggerRender();
        }

        if (this.j != null && this.usePagesView() && this.isPageListVisible()) {
            this.j.triggerRender();
        }

    }

    protected void updateEditUIAppearance() {
        try {
            SOSelectionLimits var1 = this.getDocView().getSelectionLimits();
            boolean var2 = true;
            boolean var3;
            boolean var5;
            boolean var6;
            if (var1 != null) {
                var3 = var1.getIsActive();
                boolean var4;
                if (var3 && !var1.getIsCaret()) {
                    var4 = true;
                } else {
                    var4 = false;
                }

                var5 = var3;
                var6 = var4;
                if (var3) {
                    var1.getIsCaret();
                    var5 = var3;
                    var6 = var4;
                }
            } else {
                var5 = false;
                var6 = false;
            }

            SODoc var9 = this.mSession.getDoc();
            if (var6 && !var9.selectionIsAutoshapeOrImage()) {
                var3 = true;
            } else {
                var3 = false;
            }

            this.mStyleBoldButton.setEnabled(var3);
            ToolbarButton var7 = this.mStyleBoldButton;
            boolean var8;
            if (var3 && var9.getSelectionIsBold()) {
                var8 = true;
            } else {
                var8 = false;
            }

            var7.setSelected(var8);
            this.mStyleItalicButton.setEnabled(var3);
            var7 = this.mStyleItalicButton;
            if (var3 && var9.getSelectionIsItalic()) {
                var8 = true;
            } else {
                var8 = false;
            }

            var7.setSelected(var8);
            this.mStyleUnderlineButton.setEnabled(var3);
            var7 = this.mStyleUnderlineButton;
            if (var3 && var9.getSelectionIsUnderlined()) {
                var8 = true;
            } else {
                var8 = false;
            }

            var7.setSelected(var8);
            this.mStyleLinethroughButton.setEnabled(var3);
            var7 = this.mStyleLinethroughButton;
            if (var3 && var9.getSelectionIsLinethrough()) {
                var3 = true;
            } else {
                var3 = false;
            }

            var7.setSelected(var3);
            this.q.setEnabled(var5);
            var7 = this.q;
            if (var5 && var9.getSelectionIsAlignLeft()) {
                var3 = true;
            } else {
                var3 = false;
            }

            var7.setSelected(var3);
            this.r.setEnabled(var5);
            var7 = this.r;
            if (var5 && var9.getSelectionIsAlignCenter()) {
                var3 = true;
            } else {
                var3 = false;
            }

            var7.setSelected(var3);
            this.s.setEnabled(var5);
            var7 = this.s;
            if (var5 && var9.getSelectionIsAlignRight()) {
                var3 = true;
            } else {
                var3 = false;
            }

            var7.setSelected(var3);
            this.t.setEnabled(var5);
            var7 = this.t;
            if (var5 && var9.getSelectionIsAlignJustify()) {
                var3 = true;
            } else {
                var3 = false;
            }

            var7.setSelected(var3);
            this.mListBulletsButton.setEnabled(var5);
            var7 = this.mListBulletsButton;
            if (var5 && var9.getSelectionListStyleIsDisc()) {
                var3 = true;
            } else {
                var3 = false;
            }

            var7.setSelected(var3);
            this.mListNumbersButton.setEnabled(var5);
            var7 = this.mListNumbersButton;
            if (var5 && var9.getSelectionListStyleIsDecimal()) {
                var3 = true;
            } else {
                var3 = false;
            }

            var7.setSelected(var3);
            ToolbarButton var10 = this.mIncreaseIndentButton;
            if (var5 && this.o()) {
                var3 = true;
            } else {
                var3 = false;
            }

            var10.setEnabled(var3);
            var10 = this.mDecreaseIndentButton;
            if (var5 && this.p()) {
                var5 = var2;
            } else {
                var5 = false;
            }

            var10.setEnabled(var5);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Boolean isImageAreaSelected() {
        boolean var2 = false;
        if (this.getDocView() != null) {
            SOSelectionLimits var1 = this.getDocView().getSelectionLimits();
            if (var1 != null && var1.getIsActive() && var1.getIsCaret()) {
                var2 = true;
            } else {
                var2 = false;
            }
        }
        return var2;
    }

    protected void updateInsertUIAppearance() {
        try {
            SOSelectionLimits var1 = null;
            if (this.getDocView() != null) {
                var1 = this.getDocView().getSelectionLimits();
            }
            boolean var2;
            if (var1 != null && var1.getIsActive() && var1.getIsCaret()) {
                var2 = true;
            } else {
                var2 = false;
            }

            if (this.mInsertImageButton != null && this.mConfigOptions.k()) {
                this.mInsertImageButton.setEnabled(var2);
            }

            if (this.mInsertPhotoButton != null && this.mConfigOptions.l()) {
                this.mInsertPhotoButton.setEnabled(var2);
            }

            if (var2) {
                if (imgInsertImageButton != null)
                    imgInsertImageButton.setColorFilter(activity().getResources().getColor(com.document.docease.R.color.blue));
                if (imgInsertPhotoButton != null)
                    imgInsertPhotoButton.setColorFilter(activity().getResources().getColor(com.document.docease.R.color.blue));
                if (tvInsertImageButton != null)
                    tvInsertImageButton.setTextColor(activity().getResources().getColor(com.document.docease.R.color.blue));
                if (tvInsertPhotoButton != null)
                    tvInsertPhotoButton.setTextColor(activity().getResources().getColor(com.document.docease.R.color.blue));
            } else {
                if (imgInsertImageButton != null)
                    imgInsertImageButton.setColorFilter(activity().getResources().getColor(com.document.docease.R.color.black_trans_create));
                if (imgInsertPhotoButton != null)
                    imgInsertPhotoButton.setColorFilter(activity().getResources().getColor(com.document.docease.R.color.black_trans_create));
                if (tvInsertImageButton != null)
                    tvInsertImageButton.setTextColor(activity().getResources().getColor(com.document.docease.R.color.black_trans));
                if (tvInsertPhotoButton != null)
                    tvInsertPhotoButton.setTextColor(activity().getResources().getColor(com.document.docease.R.color.black_trans));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void updateReviewUIAppearance() {
    }

    protected void updateSaveUIAppearance() {
        if (this.mSaveButton != null) {
            try {
                boolean var1 = this.documentHasBeenModified();
                boolean var2 = this.mConfigOptions.c();
                boolean var3 = false;
                if (!var2) {
                    var1 = false;
                }

                if (this.T) {
                    var1 = var3;
                }

                //this.mSaveButton.setEnabled(var1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    protected void updateUIAppearance() {
        try {
            this.updateSaveUIAppearance();
            SOSelectionLimits var1 = this.getDocView().getSelectionLimits();
            boolean var2 = true;
            boolean var3;
            boolean var4;
            boolean var5;
            if (var1 != null) {
                var3 = var1.getIsActive();
                if (var3 && !var1.getIsCaret()) {
                    var4 = true;
                } else {
                    var4 = false;
                }

                if (var3 && var1.getIsCaret()) {
                    var5 = true;
                } else {
                    var5 = false;
                }
            } else {
                var5 = false;
                var4 = false;
            }

            if (this.mConfigOptions.c()) {
                this.updateEditUIAppearance();
                this.updateUndoUIAppearance();
                this.updateReviewUIAppearance();
                boolean var6 = this.getDoc().selectionIsAutoshapeOrImage();
                if (var4 && !var6) {
                    var3 = true;
                } else {
                    var3 = false;
                }

                this.F.setEnabled(var3);
                this.C.setEnabled(var3);
                long var7 = Math.round(this.mSession.getDoc().getSelectionFontSize());
                SOTextView var9 = this.C;
                String var11;
                if (var7 > 0L) {
                    var11 = String.format("%d", (int) var7);
                } else {
                    var11 = "12";
                }
                this.editorExpandFontTv.setText(var11);
                var9.setText(var11);
                ToolbarButton var12;
                if (var3) {
                    var12 = this.D;
                    boolean var10;
                    if (var7 < 72L) {
                        var10 = true;
                    } else {
                        var10 = false;
                    }

                    var12.setEnabled(var10);
                    var12 = this.E;
                    if (var7 > 6L) {
                        var10 = true;
                    } else {
                        var10 = false;
                    }

                    var12.setEnabled(var10);
                } else {
                    this.D.setEnabled(false);
                    this.E.setEnabled(false);
                }

                var11 = Utilities.getSelectionFontName(this.mSession.getDoc());
                this.F.setText(var11);
                this.editorExpandFontNameTv.setText(var11);
                this.G.setEnabled(var3);
                this.H.setEnabled(var3);
                if (var4 && this.mSession.getDoc().getSelectionCanBeDeleted()) {
                    var3 = true;
                } else {
                    var3 = false;
                }

                this.I.setEnabled(var3);
                if (var4 && this.mSession.getDoc().getSelectionCanBeCopied()) {
                    var3 = true;
                } else {
                    var3 = false;
                }

                this.J.setEnabled(var3);
                if (!var6 && (var5 || var4) && this.mSession.getDoc().J()) {
                    var3 = var2;
                } else {
                    var3 = false;
                }

                this.K.setEnabled(var3);
                this.updateInsertUIAppearance();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void updateUndoUIAppearance() {
        try {
            SODocSession var1 = this.mSession;
            if (var1 != null && var1.getDoc() != null) {
                int var2 = this.mSession.getDoc().getCurrentEdit();
                int var3 = this.mSession.getDoc().getNumEdits();
                boolean var4 = false;
                boolean var5;
                if (var2 > 0) {
                    var5 = true;
                } else {
                    var5 = false;
                }

                this.a(this.mUndoButton, var5);
                var5 = var4;
                if (var2 < var3) {
                    var5 = true;
                }

                this.a(this.mRedoButton, var5);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected boolean usePagesView() {
        return true;
    }

    public boolean wasTyping() {
        boolean var1;
        if (System.currentTimeMillis() - this.ar < 500L) {
            var1 = true;
        } else {
            var1 = false;
        }

        return var1;
    }

    protected interface ProgressCallback {
        void finishedLoading(View var1);
    }

    protected class TabData {
        public int contentId;
        public int layoutId;
        public String name;
        public int visibility;

        public TabData(String var2, int var3, int var4, int var5) {
            this.name = var2;
            this.contentId = var3;
            this.layoutId = var4;
            this.visibility = var5;
        }
    }

    private void logFirebaseEvents(String event, String properties, String value) {
        Bundle bundle = new Bundle();
        bundle.putString(properties, value);
    }


}
