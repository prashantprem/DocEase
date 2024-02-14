package com.artifex.sonui.editor;

import android.app.Activity;
import android.content.Context;
import android.supportv1.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class NUIDocViewPreview extends NUIDocView{
    private TabData[] b = null;

    public NUIDocViewPreview(Context var1) {
        super(var1);
        this.a(var1);
    }

    public NUIDocViewPreview(Context var1, AttributeSet var2) {
        super(var1, var2);
        this.a(var1);
    }

    public NUIDocViewPreview(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
        this.a(var1);
    }

    private void a(Context var1) {
    }

    private String getFileExtension() {
        if (this.mStartUri != null) {
            return com.artifex.solib.a.a(this.getContext(), this.mStartUri);
        } else {
            String var1;
            if (this.mSession != null) {
                var1 = this.mSession.getUserPath();
            } else if (this.mState != null) {
                var1 = this.mState.getInternalPath();
            } else {
                var1 = "";
            }

            return com.artifex.solib.a.g(var1);
        }
    }

    protected void afterFirstLayoutComplete() {
        super.afterFirstLayoutComplete();
        String var1 = this.getFileExtension();
        if (var1 == null || var1.compareToIgnoreCase("txt") != 0 && var1.compareToIgnoreCase("csv") != 0) {
            this.findViewById(R.id.edit_toolbar);
//            this.findViewById(com.artifex.sonui.editor.R.id.search_toolbar).setVisibility(GONE);  //-------
            this.findViewById(com.artifex.sonui.editor.R.id.first_page_button).setVisibility(GONE);
            this.findViewById(com.artifex.sonui.editor.R.id.last_page_button).setVisibility(GONE);
            this.findViewById(com.artifex.sonui.editor.R.id.reflow_button).setVisibility(GONE);
            this.findViewById(com.artifex.sonui.editor.R.id.divider_1).setVisibility(GONE);
            this.findViewById(com.artifex.sonui.editor.R.id.divider_2).setVisibility(GONE);
            this.findViewById(com.artifex.sonui.editor.R.id.print_button).setVisibility(GONE);
            this.findViewById(com.artifex.sonui.editor.R.id.other_toolbar).setVisibility(GONE);
        }
        else if(var1.compareToIgnoreCase("png") == 0 || var1.compareToIgnoreCase("jpeg") == 0 || var1.compareToIgnoreCase("jpg") == 0){
            this.findViewById(com.artifex.sonui.editor.R.id.search_toolbar).setVisibility(GONE);
            this.findViewById(com.artifex.sonui.editor.R.id.first_page_button).setVisibility(GONE);
            this.findViewById(com.artifex.sonui.editor.R.id.last_page_button).setVisibility(GONE);
            this.findViewById(com.artifex.sonui.editor.R.id.reflow_button).setVisibility(GONE);
            this.findViewById(com.artifex.sonui.editor.R.id.divider_1).setVisibility(GONE);
            this.findViewById(com.artifex.sonui.editor.R.id.divider_2).setVisibility(GONE);
            this.findViewById(com.artifex.sonui.editor.R.id.print_button).setVisibility(GONE);
            this.findViewById(com.artifex.sonui.editor.R.id.other_toolbar).setVisibility(GONE);
        }
        else {
            this.hideUnnecessaryDivider2(com.artifex.sonui.editor.R.id.other_toolbar);
        }

    }

    protected PageAdapter createAdapter() {
        return new PageAdapter(this.activity(), this, 1);
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
        return new DocViewWrapper(var1);
    }

    protected void createReviewButtons() {
    }

    public int getBorderColor() {
        return ContextCompat.getColor(this.getContext(), com.artifex.sonui.editor.R.color.sodk_editor_selected_page_border_color);
    }

    protected int getLayoutId() {
        return MainR.getMainAppIntLayout("sodk_editor_preview_document");
    }

    public TabData[] getTabData() {
        if (this.b == null) {
            TabData[] tabDataArr = new TabData[2];
            this.b = tabDataArr;
            TabData tabData = new TabData(getContext().getString(R.string.sodk_editor_tab_file), R.id.fileTab, R.layout.sodk_editor_tab_one, 0);
            tabDataArr[0] = tabData;
            TabData[] tabDataArr2 = this.b;
            TabData tabData2 = new TabData(getContext().getString(R.string.sodk_editor_tab_pages), R.id.pagesTab, R.layout.sodk_editor_tab_right, 8);
            tabDataArr2[1] = tabData2;
        }
        return this.b;
    }

    protected int getTabUnselectedColor() {
        return ContextCompat.getColor(this.getContext(), com.artifex.sonui.editor.R.color.sodk_editor_header_other_color);
    }

    protected boolean hasRedo() {
        return false;
    }

    protected boolean hasSearch() {
        String var1 = this.getFileExtension();
        return var1 != null && var1.compareToIgnoreCase("txt") == 0;
    }

    protected boolean hasUndo() {
        return false;
    }

    protected void hideUnnecessaryDivider2(int var1) {
        LinearLayout var2 = (LinearLayout)this.findViewById(var1);
        if (var2 != null) {
            int var3 = 0;
            int var4 = 0;

            int var6;
            for(var1 = 0; var3 < var2.getChildCount(); var1 = var6) {
                View var5 = var2.getChildAt(var3);
                var6 = var5.getId();
                int var7;
                if (var6 != com.artifex.sonui.editor.R.id.divider_1 && var6 != com.artifex.sonui.editor.R.id.divider_2) {
                    var7 = var4;
                    var6 = var1;
                    if (var5.getVisibility() == VISIBLE) {
                        var7 = var4;
                        var6 = var1;
                        if (var1 == 1) {
                            var7 = var4 + 1;
                            var6 = var1;
                        }
                    }
                } else {
                    var6 = var1 + 1;
                    var7 = var4;
                }

                ++var3;
                var4 = var7;
            }

            if (var4 == 0) {
                this.findViewById(com.artifex.sonui.editor.R.id.divider_1).setVisibility(GONE);
                this.findViewById(com.artifex.sonui.editor.R.id.divider_2).setVisibility(GONE);
            }

        }
    }

    protected boolean inputViewHasFocus() {
        return false;
    }

    public void onClick(View var1) {
        super.onClick(var1);
    }

    protected void onFullScreenHide() {
        this.findViewById(com.artifex.sonui.editor.R.id.other_top).setVisibility(GONE);
        this.findViewById(com.artifex.sonui.editor.R.id.footer).setVisibility(GONE);
        this.findViewById(com.artifex.sonui.editor.R.id.header).setVisibility(GONE);
        this.layoutNow();
    }

    public void onPause() {
        this.onPauseCommon();
    }

    public void onRedoButton(View var1) {
        super.onRedoButton(var1);
    }

    public void onReflowButton(View var1) {
        String var2 = this.getFileExtension();
        if (var2 != null && (var2.compareToIgnoreCase("txt") == 0 || var2.compareToIgnoreCase("csv") == 0)) {
            super.onReflowButton(var1);
        }

    }

    public void onResume() {
        this.onResumeCommon();
    }

    public void onUndoButton(View var1) {
        super.onUndoButton(var1);
    }

    protected void scaleHeader() {
//        this.scaleToolbar(com.artifex.sonui.editor.R.id.other_toolbar, 0.65F);
//        this.mBackButton.setScaleX(0.65F);
//        this.mBackButton.setScaleY(0.65F);
    }

    public void setupTabs() {
    }

    public void showUI(boolean var1) {
        View var2 = this.findViewById(com.artifex.sonui.editor.R.id.other_top);
        if (var1) {
            var2.setVisibility(VISIBLE);
            this.findViewById(com.artifex.sonui.editor.R.id.footer).setVisibility(VISIBLE);
            this.findViewById(com.artifex.sonui.editor.R.id.header).setVisibility(VISIBLE);
        } else {
            if (!this.isSearchVisible()) {
                var2.setVisibility(GONE);
            }

            this.findViewById(com.artifex.sonui.editor.R.id.footer).setVisibility(GONE);
        }

        this.layoutNow();
    }

    protected void updateUIAppearance() {
    }

    protected boolean usePagesView() {
        return false;
    }
}
