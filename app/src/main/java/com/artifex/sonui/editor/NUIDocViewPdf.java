package com.artifex.sonui.editor;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.Bundle;
import android.supportv1.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListPopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.artifex.solib.ConfigOptions;
import com.artifex.solib.SOLib;
import com.artifex.solib.SOSelectionLimits;
import com.artifex.solib.c;
import com.artifex.sonui.editor.History.HistoryItem;
import com.artifex.sonui.editor.InkColorDialog.ColorChangedListener;
import com.artifex.sonui.editor.InkLineWidthDialog.WidthChangedListener;
import com.artifex.sonui.editor.R.color;
import com.artifex.sonui.editor.R.id;
import com.artifex.sonui.editor.R.integer;
import com.artifex.sonui.editor.R.layout;
import com.artifex.sonui.editor.R.string;
import com.document.docease.utils.Constant;
import com.document.docease.utils.FileUtil;
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader;
import com.tom_roush.pdfbox.io.MemoryUsageSetting;
import com.tom_roush.pdfbox.pdmodel.PDDocument;
import com.tom_roush.pdfbox.text.PDFTextStripperByArea;

import java.io.File;
import java.io.IOException;

public class NUIDocViewPdf extends NUIDocView {
    private ToolbarButton b;
    private ToolbarButton c;
    private ToolbarButton d;
    private ToolbarButton e;
    private ToolbarButton f;
    private LinearLayout g;
    private ImageView imgColorButton;
    private LinearLayout h;
    private ToolbarButton i;
//    private ToolbarButton j;
//    private ToolbarButton k;
//    private ToolbarButton l;
    private LinearLayout m;
    private LinearLayout n;
    private TextView tvPrevious;
    private TextView tvNext;
//    private ImageView imgPrevious;
//    private ImageView imgNext;
    private Button o;
    private TabData[] p = null;
    private boolean q = false;

    private ToolbarButton tools;

    private LinearLayout toolbarPdfCopy;


    public NUIDocViewPdf(Context var1) {
        super(var1);
        this.a(var1);
    }

    public NUIDocViewPdf(Context var1, AttributeSet var2) {
        super(var1, var2);
        this.a(var1);
    }

    public NUIDocViewPdf(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
        this.a(var1);
    }

    private void a() {
        Button var1 = (Button) this.createToolbarButton(id.toc_button);
        this.o = var1;
        var1.setEnabled(false);
    }

    private void a(Context var1) {
    }

    private void a(View var1) {
        (new a(this.getContext(), this.getDoc(), this, var11 -> {
            DocView var2 = NUIDocViewPdf.this.getDocView();
            var2.addHistory(var2.getScrollX(), var2.getScrollY(), var2.getScale(), true);
            int var3 = var2.scrollBoxToTopAmount(var11.a, var11.b);
            var2.addHistory(var2.getScrollX(), var2.getScrollY() - var3, var2.getScale(), false);
            var2.scrollBoxToTop(var11.a, var11.b);
            NUIDocViewPdf.this.updateUIAppearance();
        })).a();
    }

    private void a(HistoryItem var1) {
        this.getDocView().onHistoryItem(var1);
        this.updateUIAppearance();
    }

    private void b() {
        if (this.mSavePdfButton != null) {
            this.mSavePdfButton.setVisibility(GONE);
        }
//
//        if (this.mOpenPdfInButton != null) {
//            this.mOpenPdfInButton.setVisibility(GONE);
//        }

    }

    private void b(View var1) {
        HistoryItem previous = this.getDocView().getHistory().previous();
        if (previous != null) {
            this.a(previous);
        }

    }

    private void c(View var1) {
        HistoryItem var2 = this.getDocView().getHistory().next();
        if (var2 != null) {
            this.a(var2);
        }

    }

    protected void afterFirstLayoutComplete() {
        super.afterFirstLayoutComplete();
        this.b = (ToolbarButton) this.createToolbarButton(id.show_annot_button);
        this.c = (ToolbarButton) this.createToolbarButton(id.highlight_button);
        this.e = (ToolbarButton) this.createToolbarButton(id.note_button);
        this.i = (ToolbarButton) this.createToolbarButton(id.author_button);
        this.f = (ToolbarButton) this.createToolbarButton(id.draw_button);
        this.g = (LinearLayout) this.createToolbarButton(id.line_color_button);
        this.imgColorButton = (ImageView) this.createToolbarButton(com.document.docease.R.id.img_color_button);
        this.h = (LinearLayout) this.createToolbarButton(id.line_thickness_button);
        this.d = (ToolbarButton) this.createToolbarButton(id.delete_button);
//        this.j = (ToolbarButton) this.createToolbarButton(id.redact_button_mark);
//        this.k = (ToolbarButton) this.createToolbarButton(id.redact_button_remove);
//        this.l = (ToolbarButton) this.createToolbarButton(id.redact_button_apply);
        this.a();
        this.b();
        this.m = (LinearLayout) this.createToolbarButton(id.previous_link_button);
        this.n = (LinearLayout) this.createToolbarButton(id.next_link_button);

        this.tvPrevious = (TextView) this.createToolbarButton(com.document.docease.R.id.txt_previous);
        this.tvNext = (TextView) this.createToolbarButton(com.document.docease.R.id.tv_next);
//        this.imgPrevious = (ImageView) this.createToolbarButton(com.document.docease.R.id.img_previous);
//        this.imgNext = (ImageView) this.createToolbarButton(com.document.docease.R.id.img_next);
        this.toolbarPdfCopy = (LinearLayout) this.createToolbarButton(com.document.docease.R.id.toolbar_copy_pdf);


//        imgPrevious.setOnClickListener(this);
//        imgNext.setOnClickListener(this);
        imgColorButton.setOnClickListener(this);

        TabHost tabHost = (TabHost) findViewById(com.document.docease.R.id.tabhost);
        tabHost.setVisibility(GONE);

    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            TabHost tabHost = (TabHost) findViewById(com.document.docease.R.id.tabhost);
            tabHost.setVisibility(GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void checkXFA() {
        if (ConfigOptions.a().x()) {
            boolean var1;
            if (this.mPageCount == 0) {
                var1 = true;
            } else {
                var1 = false;
            }

            if (var1) {
                boolean var2 = this.getDoc().x();
                boolean var3 = this.getDoc().y();
                if (var2 && !var3) {
                    Utilities.showMessage((Activity) this.getContext(), this.getContext().getString(string.sodk_editor_xfa_title), this.getContext().getString(string.sodk_editor_xfa_body));
                }
            }
        }

    }

    protected PageAdapter createAdapter() {
        return new PageAdapter(this.activity(), this, 2);
    }

    protected void createEditButtons() {
    }

    protected void createEditButtons2() {
    }

    protected void createInputView() {
    }

    protected void createInsertButtons() {
    }

    protected DocView createMainView(Activity var1) {
        return (DocViewWrapper) new DocPdfView(var1);
    }

    protected void createReviewButtons() {
    }

    public int getBorderColor() {
        return ContextCompat.getColor(this.getContext(), color.sodk_editor_header_pdf_color);
    }

    public InputView getInputView() {
        return null;
    }

    protected int getLayoutId() {
        Bundle bundle = new Bundle();
        bundle = activity().getIntent().getExtras();
        if (bundle != null && bundle.containsKey(Constant.PREVIEW)) {
            return MainR.getMainAppIntLayout("sodk_editor_pdf_preview_document");
        } else {
            return layout.sodk_editor_pdf_document;
        }
    }

    public DocPdfView getPdfDocView() {
        return (DocPdfView) this.getDocView();
    }

    public TabData[] getTabData() {
        TabData tabData = null;
        if (this.p == null) {
            this.p = new TabData[4];
            int i2 = this.mConfigOptions.z() ? 0 : 8;
            boolean c2 = this.mConfigOptions.c();
            TabData[] tabDataArr = this.p;
            if (c2) {
                tabData = new TabData(getContext().getString(R.string.sodk_editor_tab_file), R.id.fileTab, R.layout.sodk_editor_tab_left, 0);
                tabDataArr[0] = tabData;
                TabData[] tabDataArr2 = this.p;
                TabData tabData2 = new TabData(getContext().getString(R.string.sodk_editor_tab_annotate), R.id.annotateTab, R.layout.sodk_editor_tab, 0);
                tabDataArr2[1] = tabData2;
                TabData[] tabDataArr3 = this.p;
                TabData tabData3 = new TabData(getContext().getString(R.string.sodk_editor_tab_redact), R.id.redactTab, R.layout.sodk_editor_tab, i2);
                tabDataArr3[2] = tabData3;
                TabData[] tabDataArr4 = this.p;
                TabData tabData4 = new TabData(getContext().getString(R.string.sodk_editor_tab_pages), R.id.pagesTab, R.layout.sodk_editor_tab_right, 0);
                tabDataArr4[3] = tabData4;
            } else {
                new TabData(getContext().getString(R.string.sodk_editor_tab_file), R.id.fileTab, R.layout.sodk_editor_tab_left, 0);
                tabDataArr[0] = tabData;
                TabData[] tabDataArr5 = this.p;
                TabData tabData5 = new TabData(getContext().getString(R.string.sodk_editor_tab_annotate), R.id.annotateTab, R.layout.sodk_editor_tab, 8);
                tabDataArr5[1] = tabData5;
                TabData[] tabDataArr6 = this.p;
                TabData tabData6 = new TabData(getContext().getString(R.string.sodk_editor_tab_redact), R.id.redactTab, R.layout.sodk_editor_tab, i2);
                tabDataArr6[2] = tabData6;
                TabData[] tabDataArr7 = this.p;
                TabData tabData7 = new TabData(getContext().getString(R.string.sodk_editor_tab_pages), R.id.pagesTab, R.layout.sodk_editor_tab_right, 0);
                tabDataArr7[3] = tabData7;
            }
        }
        return this.p;
    }


    protected int getTabSelectedColor() {
        Activity var1;
        int var2;
        if (this.getResources().getInteger(integer.sodk_editor_ui_doc_tab_color_from_doctype) == 0) {
            var1 = this.activity();
            var2 = color.sodk_editor_header_color_selected;
        } else {
            var1 = this.activity();
            var2 = color.sodk_editor_header_pdf_color;
        }

        return ContextCompat.getColor(var1, var2);
    }

    protected int getTabUnselectedColor() {
        int var1;
        if (this.getResources().getInteger(integer.sodk_editor_ui_doc_tabbar_color_from_doctype) == 0) {
            var1 = ContextCompat.getColor(this.activity(), color.sodk_editor_header_color);
        } else {
            var1 = Utilities.colorForDocExt(this.getContext(), this.getDocFileExtension());
        }

        return var1;
    }

    protected void goBack() {
        super.goBack();
    }

    protected boolean inputViewHasFocus() {
        return false;
    }

    protected boolean isRedactionMode() {
        String var1 = this.getCurrentTab();
        return var1 != null && var1.equals("REDACT");
    }

    protected void layoutAfterPageLoad() {
    }

    public void onClick(View var1) {
        super.onClick(var1);

        if (var1 == this.toolbarPdfCopy) {
            this.copyTextInDocument(var1);
        }

        if (var1 == this.b) {
            this.onToggleAnnotationsButton(var1);
        }

        if (var1 == this.c) {
            if (this.getDoc().getSelectionIsAlterableTextSelection()) {
                this.onHighlightButton(var1);
            } else {
                Toast.makeText(getContext(), "Please select the text to highlight!", Toast.LENGTH_SHORT).show();
            }
        }

        if (var1 == this.d) {
            this.onDeleteButton(var1);
        }

        if (var1 == this.e) {
            this.onNoteButton(var1);
        }

        if (var1 == this.i) {
            this.onAuthorButton(var1);
        }

        if (var1 == this.f) {
            this.onDrawButton(var1);
        }

        if (var1 == this.g) {
            this.onLineColorButton(var1);
        }

        if (var1 == imgColorButton) {
            this.onLineColorButton(var1);
        }

        if (var1 == this.h) {
            this.onLineThicknessButton(var1);
        }

        if (var1 == this.o) {
            this.a(var1);
        }

//        if (var1 == this.j) {
//            this.onRedactMark(var1);
//        }
//
//        if (var1 == this.k) {
//            this.onRedactRemove(var1);
//        }
//
//        if (var1 == this.l) {
//            this.onRedactApply(var1);
//        }

        if (var1 == this.m) {
            this.b(var1);
        }

//        if (var1 == this.imgPrevious) {
//            this.b(var1);
//        }

        if (var1 == this.n) {
            this.c(var1);
        }

//        if (var1 == this.imgNext) {
//            this.c(var1);
//        }
    }

    public void onDeleteButton(View var1) {
        DocPdfView var2 = this.getPdfDocView();
        if (this.getDoc().getSelectionCanBeDeleted()) {
            this.getDoc().selectionDelete();
        } else {
            if (!var2.getDrawMode()) {
                return;
            }

            var2.clearInk();
        }

        this.updateUIAppearance();
    }

    protected void onDeviceSizeChange() {
        super.onDeviceSizeChange();
        com.artifex.sonui.editor.a.b();
    }

    protected void onDocCompleted() {
        try {
            if (!this.mFinished) {
                if (!this.q) {
                    this.mSession.getDoc().clearSelection();
                    this.q = true;
                }

                this.mPageCount = this.mSession.getDoc().r();
                String var1;
                if (this.mPageCount <= 0) {
                    var1 = Utilities.getOpenErrorDescription(this.getContext(), 17);
                    Utilities.showMessage((Activity) this.getContext(), this.getContext().getString(string.sodk_editor_error), var1);
                } else {
                    this.mAdapter.setCount(this.mPageCount);
                    this.layoutNow();
                    this.o.setEnabled(false);
                    //this.setButtonColor(this.o, this.getResources().getInteger(com.artifex.sonui.editor.R.color.sodk_editor_button_disabled));
                    com.artifex.solib.k.a(this.getDoc(), new com.artifex.solib.k.a() {
                        public void a(int var1, int var2, int var3, String var4, String var5, float var6, float var7) {
                            NUIDocViewPdf.this.o.setEnabled(true);
                            NUIDocViewPdf var8 = NUIDocViewPdf.this;
                            //var8.setButtonColor(var8.o, NUIDocViewPdf.this.getResources().getInteger(com.artifex.sonui.editor.R.color.sodk_editor_header_button_enabled_tint));
                        }
                    });
                    if (this.mSession.getDoc().getAuthor() == null) {
                        var1 = Utilities.getApplicationName(this.activity());
                        var1 = Utilities.getStringPreference(Utilities.getPreferencesObject(this.activity(), "general"), "DocAuthKey", var1);
                        this.mSession.getDoc().setAuthor(var1);
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onDrawButton(View var1) {
        this.getPdfDocView().onDrawMode();
        this.updateUIAppearance();
    }

    public void onHighlightButton(View var1) {
        this.getDoc().addHighlightAnnotation();
        this.getDoc().clearSelection();
    }

    public void onLineColorButton(View var1) {
        try {
            DocPdfView var2 = this.getPdfDocView();
            if (var2.getDrawMode()) {
                InkColorDialog inkColorDialog = new InkColorDialog(1, this.activity(), this.g, new ColorChangedListener() {
                    public void onColorChanged(String var1) {
                        int var2x = Color.parseColor(var1);
                        var2.setInkLineColor(var2x);
                        NUIDocViewPdf.this.imgColorButton.setColorFilter(var2x);
                    }
                }, true);
                inkColorDialog.setShowTitle(false);
                inkColorDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onLineThicknessButton(View var1) {
        final DocPdfView var3 = this.getPdfDocView();
        if (var3.getDrawMode()) {
            float var2 = var3.getInkLineThickness();
            InkLineWidthDialog.show(this.activity(), this.h, var2, new WidthChangedListener() {
                public void onWidthChanged(float var1) {
                    var3.setInkLineThickness(var1);
                }
            });
        }
    }

    public void onNoteButton(View var1) {
        try {
            this.getPdfDocView().onNoteMode();
            this.updateUIAppearance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void onPageLoaded(int var1) {
        this.checkXFA();
        super.onPageLoaded(var1);
    }

    public void onRedactApply(View var1) {
        Utilities.yesNoMessage((Activity) this.getContext(), "", this.getContext().getString(string.sodk_editor_redact_confirm_apply_body), this.getContext().getString(string.sodk_editor_yes), this.getContext().getString(string.sodk_editor_no), new Runnable() {
            public void run() {
                c var1 = (c) NUIDocViewPdf.this.getDoc();
                var1.u();
                var1.clearSelection();
                NUIDocViewPdf.this.updateUIAppearance();
            }
        }, new Runnable() {
            public void run() {
            }
        });
    }

    public void onRedactMark(View var1) {
        c var2 = (c) this.getDoc();
        var2.s();
        var2.clearSelection();
        this.updateUIAppearance();
    }

    public void onRedactRemove(View var1) {
        if (this.getDoc().getSelectionCanBeDeleted()) {
            this.getDoc().selectionDelete();
            this.updateUIAppearance();
        }

    }

    public void onRedoButton(View var1) {
        super.onRedoButton(var1);
    }

    public void onReflowButton(View var1) {
    }

    protected void onSearch() {
        super.onSearch();
    }

    public void onTabChanged(String var1) {
        super.onTabChanged(var1);
    }

    protected void onTabChanging(String var1, String var2) {
        if (this.getPdfDocView() != null) {
            try {
                this.getPdfDocView().saveNoteData();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (var1.equals(this.getContext().getString(string.sodk_editor_tab_redact))) {
            this.getDoc().clearSelection();
        }

        if (var1.equals(this.getContext().getString(string.sodk_editor_tab_annotate))) {
            if (this.getPdfDocView().getDrawMode()) {
                this.getPdfDocView().onDrawMode();
            }

            this.getDoc().clearSelection();
        }

    }

    public void onToggleAnnotationsButton(View var1) {
    }

    public void onUndoButton(View var1) {
        super.onUndoButton(var1);
    }

    protected void preSaveQuestion(final Runnable var1, final Runnable var2) {
        try {
            if (!((c) this.getDoc()).t()) {
                if (var1 != null) {
                    var1.run();
                }

            } else {
                Utilities.yesNoMessage((Activity) this.getContext(), "", this.getContext().getString(string.sodk_editor_redact_confirm_save), this.getContext().getString(string.sodk_editor_yes), this.getContext().getString(string.sodk_editor_no), new Runnable() {
                    public void run() {
                        Runnable var1x = var1;
                        if (var1x != null) {
                            var1x.run();
                        }

                    }
                }, new Runnable() {
                    public void run() {
                        Runnable var1 = var2;
                        if (var1 != null) {
                            var1.run();
                        }

                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void prepareToGoBack() {
        if (this.mSession == null || this.mSession.getDoc() != null) {
            if (this.getPdfDocView() != null) {
                this.getPdfDocView().resetModes();
            }

        }
    }

    public void reloadFile() {
        c cVar = (c) getDoc();
        String openedPath = this.mSession.getFileState().getOpenedPath();
        if (!cVar.h()) {
            if (!cVar.f()) {
                if (com.artifex.solib.a.a(openedPath) < cVar.a()) {
                    return;
                }
            } else {
                return;
            }
        }
        cVar.a(false);
        cVar.a(openedPath);
        final ProgressDialog createAndShowWaitSpinner = Utilities.createAndShowWaitSpinner(getContext());
        cVar.a((c.c) new c.c() {
            public void a() {
                if (NUIDocViewPdf.this.getDocView() != null) {
                    NUIDocViewPdf.this.getDocView().onReloadFile();
                }
                if (NUIDocViewPdf.this.usePagesView() && NUIDocViewPdf.this.getDocListPagesView() != null) {
                    NUIDocViewPdf.this.getDocListPagesView().onReloadFile();
                }
                createAndShowWaitSpinner.dismiss();
            }
        });
    }


    public void setConfigurableButtons() {
        super.setConfigurableButtons();
        this.b();
    }

    protected boolean shouldConfigureSaveAsPDFButton() {
        return false;
    }

    protected void updateUIAppearance() {
        try {
            DocPdfView var1 = this.getPdfDocView();
            this.updateSaveUIAppearance();
            this.updateUndoUIAppearance();
            SOSelectionLimits var2 = this.getDocView().getSelectionLimits();
            if (var2 != null && var2.getIsActive()) {
                var2.getIsCaret();
            }

            boolean var3 = this.getDoc().getSelectionCanBeDeleted();
            boolean var4 = this.getDoc().getSelectionIsAlterableTextSelection();
            this.c.setEnabled(var4);
            boolean var5 = var1.getNoteMode();
            this.e.setSelected(var5);
            this.findViewById(id.note_holder).setSelected(var5);
            boolean var6 = var1.getDrawMode();
            this.f.setSelected(var6);
            this.g.setOnClickListener(this);
            this.h.setEnabled(var6);
            var5 = ((DocPdfView) this.getDocView()).hasNotSavedInk();
            ToolbarButton var11 = this.d;
            boolean var7 = false;
            if ((!var6 || !var5) && !var3) {
                var5 = false;
            } else {
                var5 = true;
            }

            var11.setEnabled(var5);
            var11 = this.e;
            var5 = var6 ^ true;
            var11.setEnabled(var5);
            this.i.setEnabled(var5);
            this.c.setEnabled(var5);
            int var8 = ((DocPdfView) this.getDocView()).getInkLineColor();
            if (imgColorButton != null)
                imgColorButton.setColorFilter(var8);
            this.findViewById(id.draw_tools_holder).setSelected(var6);
            try {
                c var12 = (c) this.getDoc();
//                this.j.setEnabled(var4);
                var5 = var7;
                if (var3) {
                    var5 = var7;
                    if (var12.n()) {
                        var5 = true;
                    }
                }
//                this.k.setEnabled(var5);
//                this.l.setEnabled(var12.t());
            } catch (Exception e) {
                e.printStackTrace();
            }
            History var10 = var1.getHistory();
            //this.m.setEnabled(var10.canPrevious());
            //this.n.setEnabled(var10.canNext());

            try {
                if (var10.canPrevious()) {
//                    tvPrevious.setTextColor(activity().getResources().getColor(com.document.docease.R.color.blue));
//                    imgPrevious.setColorFilter(activity().getResources().getColor(com.document.docease.R.color.blue));
                } else {
//                    tvPrevious.setTextColor(activity().getResources().getColor(com.document.docease.R.color.black_trans));
//                    imgPrevious.setColorFilter(activity().getResources().getColor(com.document.docease.R.color.black_trans));
                }

                if (var10.canNext()) {
//                    tvNext.setTextColor(activity().getResources().getColor(com.document.docease.R.color.blue));
//                    imgNext.setColorFilter(activity().getResources().getColor(com.document.docease.R.color.blue));
                } else {
//                    tvNext.setTextColor(activity().getResources().getColor(com.document.docease.R.color.black_trans));
//                    imgNext.setColorFilter(activity().getResources().getColor(com.document.docease.R.color.black_trans));
                }
            } catch (Resources.NotFoundException notFoundException) {
                notFoundException.printStackTrace();
            }
            this.getPdfDocView().onSelectionChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void updateUndoUIAppearance() {
//        this.mUndoButton.setVisibility(GONE);
//        this.mRedoButton.setVisibility(GONE);
    }


    @Override
    public void copyTextInDocument(View view) {
        if (this.mStartUri != null) {
            FileUtil fileUtil = new FileUtil(activity());
            String filePath = fileUtil.getPath(this.mStartUri);
            SOSelectionLimits limits = this.getDocView().getSelectionLimits();
            if (filePath != null && limits != null) {
                PDDocument document = new PDDocument();
                File mFile = new File(filePath);
                try {
                    document = PDDocument.load(mFile, MemoryUsageSetting.setupTempFileOnly());
                    PDFBoxResourceLoader.init(activity().getApplicationContext());
                    PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                    stripper.setSortByPosition(true);
                    RectF rect = new RectF(limits.getBox().left, limits.getBox().top, limits.getBox().right, limits.getBox().bottom);
                    stripper.addRegion("class1", rect);
                    stripper.extractRegions(document.getPage(this.mSession.getFileState().getPageNumber()));
                    String copiedText = stripper.getTextForRegion("class1");
                    ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText(copiedText, copiedText);
                    clipboard.setPrimaryClip(clip);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (document != null) {
                        try {
                            document.close();
                        } catch (IOException ignored) {
                        }
                    }
                }
            }
        }
    }

}
