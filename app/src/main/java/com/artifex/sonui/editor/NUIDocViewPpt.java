package com.artifex.sonui.editor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.supportv1.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatImageView;

import com.artifex.solib.SOSelectionLimits;
import com.artifex.sonui.editor.R.id;
import com.artifex.sonui.editor.R.integer;
import com.artifex.sonui.editor.R.layout;
import com.artifex.sonui.editor.R.string;
import com.artifex.sonui.editor.ShapeDialog.Shape;
import com.artifex.sonui.editor.ShapeDialog.onSelectShapeListener;
import com.document.docease.utils.Constant;

public class NUIDocViewPpt extends NUIDocView {
    boolean b = false;
    private ToolbarButton c;
    private ToolbarButton d;
    private ToolbarButton e;
    private ToolbarButton f;
    private ToolbarButton g;
    private ToolbarButton h;
    private ToolbarButton i;
    private ToolbarButton j;
    private LinearLayout k;
    private LinearLayout l;
    private TabData[] m = null;
    private boolean n = false;
    private LinearLayout editorExpandBullets;

    private AppCompatImageView mToolbarPaste;
    private LinearLayout mSlideShowContainer;
    private AppCompatImageView ctaSlideShow;


    public NUIDocViewPpt(Context var1) {
        super(var1);
        this.a(var1);
    }

    public NUIDocViewPpt(Context var1, AttributeSet var2) {
        super(var1, var2);
        this.a(var1);
    }

    public NUIDocViewPpt(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
        this.a(var1);
    }

    private void a(Context var1) {
    }

    protected void afterFirstLayoutComplete() {
        super.afterFirstLayoutComplete();
        this.c = (ToolbarButton)this.createToolbarButton(id.shape_color);
        this.d = (ToolbarButton)this.createToolbarButton(id.line_color);
        this.e = (ToolbarButton)this.createToolbarButton(id.line_width);
        this.f = (ToolbarButton)this.createToolbarButton(id.line_type);
        this.g = (ToolbarButton)this.createToolbarButton(id.arrange_back);
        this.h = (ToolbarButton)this.createToolbarButton(id.arrange_backwards);
        this.i = (ToolbarButton)this.createToolbarButton(id.arrange_forward);
        this.j = (ToolbarButton)this.createToolbarButton(id.arrange_front);
        this.k = (LinearLayout)this.createToolbarButton(id.insert_shape_button);
        this.l = (LinearLayout)this.createToolbarButton(id.slideshow_button);
        this.editorExpandBullets = (LinearLayout) this.findViewById(MainR.getMainAppInt("editor_expand_bullets"));
        this.editorExpandBullets.setVisibility(GONE);
        this.mToolbarPaste =  this.findViewById(com.document.docease.R.id.toolbar_paste);
        this.mSlideShowContainer = this.findViewById(com.document.docease.R.id.editor_expand_slide_show);
        this.mSlideShowContainer.setVisibility(View.VISIBLE);
        this.ctaSlideShow = this.findViewById(com.document.docease.R.id.editor_expand_slide_show_image);
        this.ctaSlideShow.setOnClickListener(v -> {
            onClickSlideshow(this.ctaSlideShow);
        });
        this.mToolbarPaste.setOnClickListener(v -> {
            this.doPaste();
        });
    }

    public boolean canCanManipulatePages() {
        return this.mConfigOptions.c();
    }

    protected void createEditButtons() {
        super.createEditButtons();
    }

    protected DocView createMainView(Activity var1) {
        DocPowerPointView var2 = new DocPowerPointView(var1);
        this.mIncreaseIndentButton.setVisibility(GONE);
        this.mDecreaseIndentButton.setVisibility(GONE);
        this.mListBulletsButton.setVisibility(GONE);
        this.mListNumbersButton.setVisibility(GONE);
        return var2;
    }

    protected void createPagesButtons() {
    }

    protected void createReviewButtons() {
    }

    public void doInsertImage(String var1) {
        var1 = Utilities.preInsertImage(this.getContext(), var1);
        int var2 = this.getTargetPageNumber();
        this.getDoc().clearSelection();
        this.getDoc().a(var2, var1);
        this.getDocView().scrollToPage(var2, false);
    }

    public int getBorderColor() {
        return ContextCompat.getColor(this.getContext(), R.color.sodk_editor_header_ppt_color);
    }

    protected int getLayoutId() {
        return layout.sodk_editor_powerpoint_document;
    }

    public TabData[] getTabData() {
        if (this.m == null) {
            this.m = new TabData[5];
            if (this.mConfigOptions.c()) {
                TabData[] tabDataArr = this.m;
                TabData tabData = new TabData(getContext().getString(R.string.sodk_editor_tab_file), R.id.fileTab, R.layout.sodk_editor_tab_left, 0);
                tabDataArr[0] = tabData;
                TabData[] tabDataArr2 = this.m;
                TabData tabData2 = new TabData(getContext().getString(R.string.sodk_editor_tab_edit), R.id.editTab, R.layout.sodk_editor_tab, 0);
                tabDataArr2[1] = tabData2;
                TabData[] tabDataArr3 = this.m;
                TabData tabData3 = new TabData(getContext().getString(R.string.sodk_editor_tab_insert), R.id.insertTab, R.layout.sodk_editor_tab, 0);
                tabDataArr3[2] = tabData3;
                TabData[] tabDataArr4 = this.m;
                TabData tabData4 = new TabData(getContext().getString(R.string.sodk_editor_tab_format), R.id.formatTab, R.layout.sodk_editor_tab, 0);
                tabDataArr4[3] = tabData4;
                TabData[] tabDataArr5 = this.m;
                TabData tabData5 = new TabData(getContext().getString(R.string.sodk_editor_tab_slides), R.id.slidesTab, R.layout.sodk_editor_tab_right, 0);
                tabDataArr5[4] = tabData5;
            } else {
                TabData[] tabDataArr6 = this.m;
                TabData tabData6 = new TabData(getContext().getString(R.string.sodk_editor_tab_file), R.id.fileTab, R.layout.sodk_editor_tab_left, 0);
                tabDataArr6[0] = tabData6;
                TabData[] tabDataArr7 = this.m;
                TabData tabData7 = new TabData(getContext().getString(R.string.sodk_editor_tab_edit), R.id.editTab, R.layout.sodk_editor_tab, 8);
                tabDataArr7[1] = tabData7;
                TabData[] tabDataArr8 = this.m;
                TabData tabData8 = new TabData(getContext().getString(R.string.sodk_editor_tab_insert), R.id.insertTab, R.layout.sodk_editor_tab, 8);
                tabDataArr8[2] = tabData8;
                TabData[] tabDataArr9 = this.m;
                TabData tabData9 = new TabData(getContext().getString(R.string.sodk_editor_tab_format), R.id.formatTab, R.layout.sodk_editor_tab, 8);
                tabDataArr9[3] = tabData9;
                TabData[] tabDataArr10 = this.m;
                TabData tabData10 = new TabData(getContext().getString(R.string.sodk_editor_tab_slides), R.id.slidesTab, R.layout.sodk_editor_tab_right, 0);
                tabDataArr10[4] = tabData10;
            }
        }
        return this.m;
    }


    protected int getTabSelectedColor() {
        Activity var1;
        int var2;
        if (this.getResources().getInteger(integer.sodk_editor_ui_doc_tab_color_from_doctype) == 0) {
            var1 = this.activity();
            var2 = R.color.sodk_editor_header_color_selected;
        } else {
            var1 = this.activity();
            var2 = R.color.sodk_editor_header_ppt_color;
        }

        return ContextCompat.getColor(var1, var2);
    }

    protected int getTabUnselectedColor() {
        Activity var1;
        int var2;
        if (this.getResources().getInteger(integer.sodk_editor_ui_doc_tabbar_color_from_doctype) == 0) {
            var1 = this.activity();
            var2 = R.color.sodk_editor_header_color;
        } else {
            var1 = this.activity();
            var2 = R.color.sodk_editor_header_ppt_color;
        }

        return ContextCompat.getColor(var1, var2);
    }

    protected void handlePagesTab(String var1) {
        if (var1.equals(this.getResources().getString(string.sodk_editor_tab_slides))) {
            this.showPages();
        } else {
            this.hidePages();
        }

    }

    protected boolean isPagesTab() {
        return this.getCurrentTab().equals(this.activity().getString(string.sodk_editor_tab_slides));
    }

    public void onClick(View var1) {
        super.onClick(var1);
        if (var1 == this.c) {
            this.onClickFillColor(var1);
        }

        if (var1 == this.d) {
            this.onClickLineColor(var1);
        }

        if (var1 == this.e) {
            this.onClickLineWidth(var1);
        }

        if (var1 == this.f) {
            this.onClickLineType(var1);
        }

        if (var1 == this.g) {
            this.onClickArrangeBack(var1);
        }

        if (var1 == this.h) {
            this.onClickArrangeBackwards(var1);
        }

        if (var1 == this.i) {
            this.onClickArrangeForwards(var1);
        }

        if (var1 == this.j) {
            this.onClickArrangeFront(var1);
        }

        if (var1 == this.k) {
            this.onInsertShapeButton(var1);
        }

        if (var1 == this.l) {
            this.onClickSlideshow(var1);
        }

    }

    public void onClickArrangeBack(View var1) {
        if (!this.n) {
            this.n = true;
            this.updateUIAppearance();
            this.getDoc().setSelectionArrangeBack();
        }
    }

    public void onClickArrangeBackwards(View var1) {
        if (!this.n) {
            this.n = true;
            this.updateUIAppearance();
            this.getDoc().setSelectionArrangeBackwards();
        }
    }

    public void onClickArrangeForwards(View var1) {
        if (!this.n) {
            this.n = true;
            this.updateUIAppearance();
            this.getDoc().setSelectionArrangeForwards();
        }
    }

    public void onClickArrangeFront(View var1) {
        if (!this.n) {
            this.n = true;
            this.updateUIAppearance();
            this.getDoc().setSelectionArrangeFront();
        }
    }

    public void onClickFillColor(View var1) {
        ColorDialog var2 = new ColorDialog(2, this.getContext(), this.getDoc(), var1, new ColorChangedListener() {
            public void onColorChanged(String var1) {
                NUIDocViewPpt.this.getDoc().setSelectionFillColor(var1);
            }
        });
        var2.setShowTitle(false);
        var2.show();
    }

    public void onClickLineColor(View var1) {
        ColorDialog var2 = new ColorDialog(2, this.getContext(), this.getDoc(), var1, new ColorChangedListener() {
            public void onColorChanged(String var1) {
                NUIDocViewPpt.this.getDoc().setSelectionLineColor(var1);
            }
        });
        var2.setShowTitle(false);
        var2.show();
    }

    public void onClickLineType(View var1) {
        LineTypeDialog.show(this.activity(), var1, this.getDoc());
    }

    public void onClickLineWidth(View var1) {
        LineWidthDialog.show(this.activity(), var1, this.getDoc());
    }

    public void onClickSlideshow(View var1) {
        try{
            if(this.getSession() != null){
                this.getDoc().clearSelection();
                this.getDoc().o();
                SlideShowActivity.setSession(this.mSession);
                Intent var2 = new Intent(this.getContext(), SlideShowActivity.class);
                var2.setAction(Constant.INTENT_ACTION_VIEW);
                this.activity().startActivity(var2);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    protected void onDocCompleted() {
        super.onDocCompleted();
    }

    public void onInsertShapeButton(View var1) {
        (new ShapeDialog(this.getContext(), var1, new onSelectShapeListener() {
            public void onSelectShape(Shape var1) {
                int var2 = NUIDocViewPpt.this.getTargetPageNumber();
                NUIDocViewPpt var3 = NUIDocViewPpt.this;
                var3.b = true;
                var3.getDoc().clearSelection();
                NUIDocViewPpt.this.getDoc().a(var2, var1.shape, var1.properties);
                NUIDocViewPpt.this.getDocView().scrollToPage(var2, false);
            }
        })).show();
    }

    public void onSelectionChanged() {
        super.onSelectionChanged();
        this.n = false;
        this.updateUIAppearance();
        if (this.b) {
            this.getDocView().scrollSelectionIntoView();
        }

        this.b = false;
    }

    protected void setInsertTabVisibility() {
    }

    public boolean showKeyboard() {
        SOSelectionLimits var1 = this.getDocView().getSelectionLimits();
        if (var1 != null && var1.getIsActive() && !this.getDoc().getSelectionCanBeAbsolutelyPositioned()) {
            super.showKeyboard();
            return true;
        } else {
            return false;
        }
    }

    protected void updateEditUIAppearance() {
        super.updateEditUIAppearance();
    }

    protected void updateInsertUIAppearance() {
        if (this.mInsertImageButton != null && this.mConfigOptions.k()) {
            this.mInsertImageButton.setEnabled(true);
        }

        if (this.mInsertPhotoButton != null && this.mConfigOptions.l()) {
            this.mInsertPhotoButton.setEnabled(true);
        }

    }

    protected void updateReviewUIAppearance() {
    }

    protected void updateUIAppearance() {
        super.updateUIAppearance();
        boolean var1 = this.getDoc().selectionIsAutoshapeOrImage();
        this.c.setEnabled(var1);
        this.d.setEnabled(var1);
        this.e.setEnabled(var1);
        this.f.setEnabled(var1);
        ToolbarButton var2 = this.g;
        boolean var3 = false;
        boolean var4;
        if (var1 && !this.n) {
            var4 = true;
        } else {
            var4 = false;
        }

        var2.setEnabled(var4);
        var2 = this.h;
        if (var1 && !this.n) {
            var4 = true;
        } else {
            var4 = false;
        }

        var2.setEnabled(var4);
        var2 = this.i;
        if (var1 && !this.n) {
            var4 = true;
        } else {
            var4 = false;
        }

        var2.setEnabled(var4);
        var2 = this.j;
        var4 = var3;
        if (var1) {
            var4 = var3;
            if (!this.n) {
                var4 = true;
            }
        }

        var2.setEnabled(var4);
    }
}
