//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.artifex.sonui.editor;


import com.artifex.sonui.MainApp;

public final class MainR {
    private MainR() {
    }

    public static int getMainAppInt(String var0) {
        return MainApp.getAppContext().getResources().getIdentifier(var0, "id", MainApp.getAppContext().getPackageName());
    }

    public static int getMainAppIntLayout(String var0) {
        return MainApp.getAppContext().getResources().getIdentifier(var0, "layout", MainApp.getAppContext().getPackageName());
    }

    public static int getMainAppStringResource(String var0) {
        return MainApp.getAppContext().getResources().getIdentifier(var0, "string", MainApp.getAppContext().getPackageName());

    }

    public static final class mainAppId {
        public static final int editorPrint = getMainAppInt("imv_activity_editor_print");
    }
}
