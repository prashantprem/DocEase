//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.artifex.solib;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.RectF;

import com.artifex.mupdf.fitz.Document;

import java.util.ArrayList;

public class SODoc {
    public final int DocType_DOC = 4;
    public final int DocType_DOCX = 5;
    public final int DocType_Error = -1;
    public final int DocType_Other = 7;
    public final int DocType_PDF = 6;
    public final int DocType_PPT = 2;
    public final int DocType_PPTX = 3;
    public final int DocType_XLS = 0;
    public final int DocType_XLSX = 1;
    private final int a = 0;
    private final int b = 1;
    protected int c = 0;
    private final int d = 2;
    private final int e = 3;
    private final int f = 4;
    private final int g = 5;
    private final int h = 6;
    private boolean i;
    private long internal;
    private String j;
    private boolean k;
    private boolean l;
    private p m;
    private int n = 0;
    private int o = 1;
    private ArrayList<SOPage> p = new ArrayList();
    private int q = 0;
    private int r = 0;

    protected SODoc(long var1) {
        this.internal = var1;
    }

    private native void destroy();

    private native String getSelectionNaturalDimensionsInternal();

    private native void insertImageAtSelection(String var1);

    private native void insertImageCenterPage(int var1, String var2);

    private native void nativeCloseSearch();

    private native void nativeInsertAutoshapeCenterPage(int var1, String var2, String var3, boolean var4, float var5, float var6);

    private native int nativeSearch(String var1, boolean var2, boolean var3);

    private native void nativeSetSearchStart(int var1, float var2, float var3);

    private native int saveToInternal(String var1, SODocSaveListener var2);

    private native int saveToPDFInternal(String var1, boolean var2, SODocSaveListener var3);

    private void searchProgress(final int var1, final int var2, final float var3, final float var4, final float var5, final float var6) {
        SOLib.a(new Runnable() {
            public void run() {
                if (var1 != 5) {
                    SODoc.this.i = false;
                }

                switch (var1) {
                    case 0:
                        if (SODoc.this.m != null) {
                            SODoc.this.m.a(var2, new RectF(var3, var4, var5, var6));
                        }
                        break;
                    case 1:
                        if (SODoc.this.m != null) {
                            SODoc.this.m.a();
                        }
                        break;
                    case 2:
                        if (SODoc.this.m != null) {
                            SODoc.this.m.b();
                        }
                        break;
                    case 3:
                        if (SODoc.this.m != null) {
                            SODoc.this.m.c();
                        }
                        break;
                    case 4:
                    default:
                        if (SODoc.this.m != null) {
                            SODoc.this.m.d();
                        }
                        break;
                    case 5:
                        if (SODoc.this.m != null) {
                            SODoc.this.m.a(var2);
                        }
                        break;
                    case 6:
                        if (SODoc.this.m != null) {
                            SODoc.this.m.e();
                        }
                }

            }
        });
    }

    private native void setSelectionListStyle(int var1);

    public void A() {
        this.setSelectionListStyle(0);
    }

    public void B() {
        this.setSelectionListStyle(1);
    }

    public void C() {
        this.setSelectionListStyle(2);
    }

    public void D() {
        this.setSelectionAlignment(0);
    }

    public void E() {
        this.setSelectionAlignment(1);
    }

    public void F() {
        this.setSelectionAlignment(2);
    }

    public void G() {
        this.setSelectionAlignment(3);
    }

    public void H() {
        if (this.getSelectionCanBeCopied() && this.getSelectionCanBeDeleted()) {
            this.selectionCutToClip();
            this.n = SOLib.b() + 1;
            SOLib.a(this.getClipboardAsText());
        }

    }

    public void I() {
        if (this.getSelectionCanBeCopied()) {
            this.selectionCopyToClip();
            this.n = SOLib.b() + 1;
            SOLib.a(this.getClipboardAsText());
        }
    }

    public boolean J() {
        boolean var1;
        if (!this.clipboardHasData() && !SOLib.c()) {
            var1 = false;
        } else {
            var1 = true;
        }

        return var1;
    }

    public int K() {
        return this.o;
    }

    public PointF L() {
        PointF var1 = new PointF(0.0F, 0.0F);
        String var2 = this.getSelectionNaturalDimensionsInternal();
        if (var2 != null) {
            String[] var3 = var2.split(",");
            if (var3.length == 2) {
                var1.set(Float.parseFloat(var3[0]), Float.parseFloat(var3[1]));
            }
        }

        return var1;
    }

    public void M() {
        for (int var1 = 0; var1 < this.p.size(); ++var1) {
            ((SOPage) this.p.get(var1)).m();
        }

        this.p.clear();
    }

    public void N() {
        if (this.getSelectionCanBeDeleted()) {
            this.selectionDelete();
        } else {
            this.deleteChar();
        }

    }

    public int O() {
        return this.q;
    }

    public int P() {
        return this.r;
    }

    public void a(int var1, float var2) {
        this.o = var1;
        this.setFlowModeInternal(var1, var2);
    }

    public void a(int var1, float var2, float var3) {
        if (!this.i) {
            this.nativeSetSearchStart(var1, var2, var3);
        } else {
            throw new IllegalArgumentException("Search already in progess");
        }
    }

    public void a(int var1, String var2) {
        this.insertImageCenterPage(var1, var2);
    }

    public void a(int var1, String var2, String var3) {
        this.nativeInsertAutoshapeCenterPage(var1, var2, var3, true, 0.0F, 0.0F);
    }

    public void a(Context var1, int var2) {
        if (this.getSelectionCanBePasteTarget() && !this.selectionIsAutoshapeOrImage()) {
            boolean var3 = this.clipboardHasData();
            if (SOLib.c() && (!var3 || var3 && SOLib.b() > this.n)) {
                this.setSelectionText(SOLib.b(var1), 0, true);
                return;
            }

            this.selectionPaste(var2);
        }

    }

    public void a(SOPage var1) {
        this.p.add(var1);
    }

    public void a(p var1) {
        if (!this.i) {
            this.m = var1;
        } else {
            throw new IllegalArgumentException("Search already in progess");
        }
    }

    public void a(String var1) {
    }

    public void a(String var1, final SODocSaveListener var2) {
        if (this.saveToInternal(var1, new SODocSaveListener() {
            public void onComplete(final int var1, final int var2x) {
                SOLib.a(new Runnable() {
                    public void run() {
                        var2.onComplete(var1, var2x);
                    }
                });
            }
        }) != 0) {
            throw new OutOfMemoryError();
        }
    }

    public void a(boolean var1) {
    }

    public native void abortLoad();

    public native void acceptTrackedChange();

    public native void addBlankPage(int var1);

    public native void addColumnsLeft();

    public native void addColumnsRight();

    public native void addHighlightAnnotation();

    public native void addRowsAbove();

    public native void addRowsBelow();

    public void b(SOPage var1) {
        this.p.remove(var1);
    }

    public void b(String var1) {
        if (!this.i) {
            this.j = new String(var1);
        } else {
            throw new IllegalArgumentException("Search already in progess");
        }
    }

    public void b(String var1, boolean var2, final SODocSaveListener var3) {
        if (this.saveToPDFInternal(var1, var2, new SODocSaveListener() {
            public void onComplete(final int var1, final int var2) {
                SOLib.a(new Runnable() {
                    public void run() {
                        var3.onComplete(var1, var2);
                    }
                });
            }
        }) != 0) {
            throw new OutOfMemoryError();
        }
    }

    void c(int var1) {
        this.c = var1;
    }

    public void c(boolean var1) {
        if (!this.i) {
            this.k = var1;
        } else {
            throw new IllegalArgumentException("Search already in progess");
        }
    }

    public native void cancelSearch();

    public native void clearSelection();

    public native boolean clipboardHasData();

    public native void createInkAnnotation(int var1, SOPoint[] var2, float var3, int var4);

    public native void createTextAnnotationAt(PointF var1, int var2);

    public void d(int var1) {
        this.q = var1;
    }

    public void d(String var1) {
        this.insertImageAtSelection(var1);
    }

    public void d(boolean var1) {
        if (!this.i) {
            this.l = var1;
        } else {
            throw new IllegalArgumentException("Search already in progess");
        }
    }

    public native void deleteChar();

    public native void deleteColumns();

    public native void deleteHighlightAnnotation();

    public native void deletePage(int var1);

    public native void deleteRows();

    public native boolean docSupportsReview();

    public native void duplicatePage(int var1);

    public void e(int var1) {
        this.r = var1;
    }

    public native int enumerateToc(SOEnumerateTocListener var1);

    protected void finalize() throws Throwable {
        try {
            this.destroy();
        } finally {
            super.finalize();
        }

    }

    public native String getAuthor();

    public native String[] getBgColorList();

    public native String getClipboardAsText();

    public native int getCurrentEdit();

    public native String getFontList();

    public native boolean getHasBeenModified();

    public native int[] getIndentationLevel();

    public native int getNumEdits();

    public native SOPage getPage(int var1, SOPageListener var2);

    public native Document e();

    public Document getDocument() {
        return this.e();
    }


    public native String getSelectedCellFormat();

    public native float getSelectedColumnWidth();

    public native float getSelectedRowHeight();

    public native String getSelectedTrackedChangeAuthor();

    public native String getSelectedTrackedChangeComment();

    public native String getSelectedTrackedChangeDate();

    public native int getSelectedTrackedChangeType();

    public native int getSelectionAlignment();

    public native int getSelectionAlignmentV();

    public native String getSelectionAnnotationAuthor();

    public native String getSelectionAnnotationComment();

    public native String getSelectionAnnotationDate();

    public native SOBitmap getSelectionAsBitmap();

    public native String getSelectionAsText();

    public native boolean getSelectionCanBeAbsolutelyPositioned();

    public native boolean getSelectionCanBeCopied();

    public native boolean getSelectionCanBeDeleted();

    public native boolean getSelectionCanBePasteTarget();

    public native boolean getSelectionCanBeResized();

    public native boolean getSelectionCanBeRotated();

    public native boolean getSelectionCanHaveTextAltered();

    public native String getSelectionFontName();

    public native double getSelectionFontSize();

    public native boolean getSelectionHasAssociatedPopup();

    public native boolean getSelectionIsAlignCenter();

    public native boolean getSelectionIsAlignJustify();

    public native boolean getSelectionIsAlignLeft();

    public native boolean getSelectionIsAlignRight();

    public native boolean getSelectionIsAlterableTextSelection();

    public native boolean getSelectionIsBold();

    public native boolean getSelectionIsItalic();

    public native boolean getSelectionIsLinethrough();

    public native boolean getSelectionIsUnderlined();

    public native int getSelectionLineType();

    public native float getSelectionLineWidth();

    public native boolean getSelectionListStyleIsDecimal();

    public native boolean getSelectionListStyleIsDisc();

    public native float getSelectionRotation();

    public native boolean getShowingTrackedChanges();

    public native boolean getTableCellsMerged();

    public native boolean getTrackingChanges();

    public boolean i() {
        return true;
    }

    public boolean j() {
        return true;
    }

    public void k() {
        this.destroy();
    }

    public native void movePage(int var1, int var2);

    public native boolean nextTrackedChange();

    public void o() {
        this.nativeCloseSearch();
        this.i = false;
    }

    public boolean p() {
        return this.i;
    }

    public native boolean previousTrackedChange();

    public native void processKeyCommand(int var1);

    public native boolean providePassword(String var1);

    public int q() {
        String var1 = this.j;
        if (var1 != null) {
            if (!this.i) {
                this.i = true;
                boolean var4 = false;

                int var2;
                try {
                    var4 = true;
                    var2 = this.nativeSearch(var1, this.k, this.l);
                    var4 = false;
                } finally {
                    if (var4) {
                        this.i = false;
                    }
                }

                if (var2 != 0) {
                    this.i = false;
                }

                return var2;
            } else {
                throw new IllegalArgumentException("Search already in progess");
            }
        } else {
            throw new IllegalArgumentException("No Search Text specified");
        }
    }

    public int r() {
        return this.c;
    }

    public native void rejectTrackedChange();

    public native void selectionCopyToClip();

    public native void selectionCutToClip();

    public native void selectionDelete();

    public native boolean selectionIsAutoshapeOrImage();

    public native boolean selectionIsReviewable();

    public native void selectionPaste(int var1);

    public native SOSelectionTableRange selectionTableRange();

    public native boolean setAuthor(String var1);

    public native void setCurrentEdit(int var1);

    public native void setFlowModeInternal(int var1, float var2);

    public native void setIndentationLevel(int var1);

    public native void setSelectedCellFormat(String var1);

    public native void setSelectedColumnWidth(float var1);

    public native void setSelectedRowHeight(float var1);

    public native void setSelectionAlignment(int var1);

    public native void setSelectionAlignmentV(int var1);

    public native void setSelectionAnnotationComment(String var1);

    public native void setSelectionArrangeBack();

    public native void setSelectionArrangeBackwards();

    public native void setSelectionArrangeForwards();

    public native void setSelectionArrangeFront();

    public native void setSelectionBackgroundColor(String var1);

    public native void setSelectionBackgroundTransparent();

    public native void setSelectionFillColor(String var1);

    public native void setSelectionFontColor(String var1);

    public native void setSelectionFontName(String var1);

    public native void setSelectionFontSize(double var1);

    public native void setSelectionIsBold(boolean var1);

    public native void setSelectionIsItalic(boolean var1);

    public native void setSelectionIsLinethrough(boolean var1);

    public native void setSelectionIsUnderlined(boolean var1);

    public native void setSelectionLineColor(String var1);

    public native void setSelectionLineType(int var1);

    public native void setSelectionLineWidth(float var1);

    public native void setSelectionRotation(float var1);

    public native void setSelectionText(String var1, int var2, boolean var3);

    public native void setShowingTrackedChanges(boolean var1);

    public native void setTableCellsMerged(boolean var1);

    public native void setTrackingChanges(boolean var1);

    public boolean x() {
        return false;
    }

    public boolean y() {
        return false;
    }

    public String z() {
        return "dd/MM/yyyy HH:mm:ss";
    }
}
