//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.artifex.sonui.editor;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.artifex.solib.SOSelectionLimits;

import java.util.ArrayList;
import java.util.Iterator;

public class DocPdfView extends DocViewWrapper {
    DocPdfPageView a = null;
    ArrayList<DocPdfPageView> b = new ArrayList();
    private NoteEditor c = null;
    private boolean d = false;
    private boolean e = false;
    private float f;
    private float g;
    private int h = -65536;
    private float i = 4.5F;

    private float v;
    private float w;

    private long u = 0L;


    public DocPdfView(Context var1) {
        super(var1);
    }

    public DocPdfView(Context var1, AttributeSet var2) {
        super(var1, var2);
    }

    public DocPdfView(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
    }

    private void a(float var1, float var2) {
        Point var3 = this.eventToScreen(var1, var2);
        DocPdfPageView var4 = (DocPdfPageView) this.findPageViewContainingPoint(var3.x, var3.y, false);
        this.a = var4;
        if (var4 != null) {
            var4.startDrawInk((float) var3.x, (float) var3.y, this.h, this.i);
            if (!this.b.contains(this.a)) {
                this.b.add(this.a);
            }

            this.mHostActivity.selectionupdated();
        }

        this.f = var1;
        this.g = var2;
    }

    private void b() {
        DocPdfPageView var1 = this.a;
        if (var1 != null) {
            var1.endDrawInk();
        }

    }

    private void b(float var1, float var2) {
        float var3 = Math.abs(var1 - this.f);
        float var4 = Math.abs(var2 - this.g);
        if (var3 >= 2.0F || var4 >= 2.0F) {
            Point var5 = this.eventToScreen(var1, var2);
            DocPdfPageView var6 = this.a;
            if (var6 != null) {
                var6.continueDrawInk((float) var5.x, (float) var5.y);
            }

            this.f = var1;
            this.g = var2;
        }

    }

    protected boolean canEditText() {
        return false;
    }

    protected boolean canSelectionSpanPages() {
        return false;
    }

    public void clearInk() {
        Iterator var1 = this.b.iterator();

        while (var1.hasNext()) {
            ((DocPdfPageView) var1.next()).clearInk();
        }

        this.b.clear();
    }

    protected void doDoubleTap(float var1, float var2) {
        if (this.mConfigOptions.c()) {
            if (!((NUIDocView) this.mHostActivity).isFullScreen()) {
                this.doDoubleTap2(var1, var2);
            }
        }
    }

    public boolean getDrawMode() {
        return this.e;
    }

    public int getInkLineColor() {
        return this.h;
    }

    public float getInkLineThickness() {
        return this.i;
    }

    public boolean getNoteMode() {
        return this.d;
    }

    protected boolean handleFullscreenTap(float var1, float var2) {
        Point var3 = this.eventToScreen(var1, var2);
        DocPageView var4 = this.findPageViewContainingPoint(var3.x, var3.y, false);
        if (var4 == null) {
            return false;
        } else {
            var3 = var4.screenToPage(var3);
            return var4.handleFullscreenTap(var3.x, var3.y);
        }
    }

    public boolean hasNotSavedInk() {
        ArrayList var1 = this.b;
        return var1 != null && var1.size() > 0;
    }

    public void onDrawMode() {
        try {
            boolean var1 = this.e ^ true;
            this.e = var1;
            this.d = false;
            if (!var1) {
                this.saveInk();
            }

            this.getDoc().clearSelection();
            this.onSelectionChanged();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    protected void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
        super.onLayout(var1, var2, var3, var4, var5);
        if (!this.finished()) {
            this.c.move();
        }
    }

    public void onNoteMode() {
        this.d ^= true;
        this.e = false;
        this.clearAreaSelection();
        this.onSelectionChanged();
    }

    public void onReloadFile() {
        for (int var1 = 0; var1 < this.getPageCount(); ++var1) {
            ((DocMuPdfPageView) this.getOrCreateChild(var1)).onReloadFile();
        }

    }

    public void onSelectionChanged() {
        super.onSelectionChanged();
    }

    protected boolean onSingleTap(float var1, float var2, DocPageView var3) {
        DocPdfPageView var4 = (DocPdfPageView) var3;
        if (this.d && var4 != null) {
            var4.createNote(var1, var2);
            this.d = false;
            return true;
        } else {
            return false;
        }
    }

    public boolean onTouchEvent(MotionEvent var1) {
        if (this.finished()) {
            return true;
        } else if (this.getDrawMode()) {
            float var2 = var1.getX();
            float var3 = var1.getY();
            switch (var1.getAction()) {
                case 0:
                    this.a(var2, var3);
                    break;
                case 1:
                    this.b();
                    break;
                case 2:
                    this.b(var2, var3);
            }

            return true;
        } else {
            return super.onTouchEvent(var1);
        }
    }

    public void resetModes() {
        this.e = false;
        this.saveInk();
        this.d = false;
        if (this.c.isVisible()) {
            this.c.saveData();
            Utilities.hideKeyboard(this.getContext());
            this.c.hide();
        }

        this.clearAreaSelection();
        this.onSelectionChanged();
    }

    public void saveInk() {
        Iterator var1 = this.b.iterator();

        while (var1.hasNext()) {
            ((DocPdfPageView) var1.next()).saveInk();
        }

        this.b.clear();
        this.a = null;
    }

    public void saveNoteData() {
        NoteEditor var1 = this.c;
        if (var1 != null) {
            var1.saveData();
        }

    }

    public void setInkLineColor(int var1) {
        this.h = var1;
        Iterator var2 = this.b.iterator();

        while (var2.hasNext()) {
            ((DocPdfPageView) var2.next()).setInkLineColor(var1);
        }

    }

    public void setInkLineThickness(float var1) {
        this.i = var1;
        Iterator var2 = this.b.iterator();

        while (var2.hasNext()) {
            ((DocPdfPageView) var2.next()).setInkLineThickness(var1);
        }

    }

    public void setup(RelativeLayout var1) {
        super.setup(var1);
        this.c = new NoteEditor((Activity) this.getContext(), this, this.mHostActivity, new NoteEditor.NoteDataHandler() {
            public String getAuthor() {
                return DocPdfView.this.getDoc().getSelectionAnnotationAuthor();
            }

            public String getComment() {
                return DocPdfView.this.getDoc().getSelectionAnnotationComment();
            }

            public String getDate() {
                return DocPdfView.this.getDoc().getSelectionAnnotationDate();
            }

            public void setComment(String var1) {
                DocPdfView.this.getDoc().setSelectionAnnotationComment(var1);
            }
        });
    }

    protected void showKeyboardAfterDoubleTap(Point var1) {
    }

    protected void updateReview() {
        if (this.getDoc() == null) {
            Log.e("DocPdfView", "getDoc() returned NULL in updateReview");
        } else {
            if (this.getDoc().getSelectionHasAssociatedPopup()) {
                SOSelectionLimits var1 = this.getSelectionLimits();
                this.c.show(var1, this.mSelectionStartPage);
                this.c.move();
                this.c.setCommentEditable(true);
                this.requestLayout();
            } else if (this.c.isVisible()) {
                Utilities.hideKeyboard(this.getContext());
                this.c.hide();
            }

        }
    }

//    @Override
//    public void onLongPress(MotionEvent motionEvent) {
//        this.doDoubleTap(motionEvent.getX(), motionEvent.getY());
//    }
}
