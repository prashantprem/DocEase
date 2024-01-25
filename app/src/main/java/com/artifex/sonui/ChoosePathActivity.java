package com.artifex.sonui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.Toast;

import com.a.o.Res;
import com.artifex.sonui.editor.BaseActivity;
import com.artifex.sonui.editor.SOEditText;
import com.artifex.sonui.editor.SOEditTextOnEditorActionListener;
import com.artifex.sonui.editor.Utilities;
import com.document.docease.ui.module.main.MainActivity;
import com.document.docease.utils.Constant;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class ChoosePathActivity extends BaseActivity {
    private static ChoosePathActivity.a a;
    private static int b;
    private static String c;
    private static boolean d;
    private File mFile;
    private boolean isImageFile = false;
    private String suffix;


    public ChoosePathActivity() {
    }

    public static void a(Activity var0, int var1, boolean var2, ChoosePathActivity.a var3, String var4) {
        a = var3;
        b = var1;
        c = var4;
        d = false;
        var0.startActivity(new Intent(var0, ChoosePathActivity.class));
    }

    public void finish() {
        //fix for a being null
        if (!d && a != null) {
            a.a();
        }

        super.finish();
    }

    protected void onCreate(Bundle var1) {
        super.onCreate(var1);
        this.setContentView(Res.getIntlayout("sodk_choose_path"));
        String var4 = c;
        final FileBrowser fileBrowser = (FileBrowser) this.findViewById(Res.getIntid("file_browser"));
        fileBrowser.a(this, var4);
        Button var3 = (Button) this.findViewById(Res.getIntid("save_button"));
        if (b == 3) {
            var4 = "sodk_editor_copy";
        } else {
            var4 = "sodk_editor_save";
        }
        Bundle intentData = getIntent().getExtras();
        if (intentData != null && intentData.containsKey(Constant.IS_IMAGE_FILE)) {
            isImageFile = true;
            String filePath = intentData.getString(Constant.FILE_PATH);
            mFile = new File(filePath);
            String fileName = mFile.getName();
            int pos = fileName.lastIndexOf(".");
            if (pos > 0 && pos < (fileName.length() - 1)) { // If '.' is not the first or last character.
                fileName = fileName.substring(0, pos);
            }
            suffix = mFile.getName().substring(mFile.getName().lastIndexOf(".") + 1);
            fileBrowser.getEditText().setText(fileName);
        }
        var3.setText(this.getString(Res.getIntstring(var4)));
        var3.setOnClickListener(var112 -> {
            try {
                runOnUiThread(() -> {
                    Utilities.hideKeyboard(ChoosePathActivity.this);
                    if (isImageFile) {
                        String destFileName = fileBrowser.getEditText().getText().toString() + "." + suffix;
                        File dest = new File(fileBrowser.b.get(0).c + File.separator + destFileName);
                        try {
                            saveImageFile(mFile, dest);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
//                        MainActivity mainActivity = Utility.INSTANCE.getMainActivity();
//                        if (mainActivity != null) {
//                            mainActivity.updateDataAfterChange();
//                        }
                        ChoosePathActivity.d = true;
                        ChoosePathActivity.this.finish();
                        if (ChoosePathActivity.a != null)
                            ChoosePathActivity.a.a(fileBrowser);
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        ((Button) this.findViewById(Res.getIntid("cancel_button"))).setOnClickListener(new OnClickListener() {
            public void onClick(View var1) {
                Utilities.hideKeyboard(ChoosePathActivity.this);
                if (isImageFile) {
                    finish();
                } else {
                    ChoosePathActivity.d = true;
                    ChoosePathActivity.this.finish();
                    if (ChoosePathActivity.a != null)
                        ChoosePathActivity.a.a();
                }
            }
        });

        SOEditText var5 = fileBrowser.getEditText();
        var5.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View var1, int var2x, KeyEvent var3) {
                if (var3.getAction() == 0 && var2x == 66) {
                    Utilities.hideKeyboard(ChoosePathActivity.this);
                    if (!isImageFile) {
                        ChoosePathActivity.d = true;
                        ChoosePathActivity.this.finish();
                        ChoosePathActivity.a.a(fileBrowser);
                    }
                    return true;
                } else {
                    return false;
                }
            }
        });

        var5.setOnEditorActionListener((SOEditTextOnEditorActionListener) (var11, var2x, var31) -> {
            boolean var41 = true;
            if (var2x == 6) {
                Utilities.hideKeyboard(ChoosePathActivity.this);
                if (!isImageFile) {
                    ChoosePathActivity.d = true;
                    ChoosePathActivity.this.finish();
                    if (ChoosePathActivity.a != null)
                        ChoosePathActivity.a.a();

                }
            } else {
                var41 = false;
            }

            return var41;
        });
    }

    public interface a {
        void a();

        void a(FileBrowser var1);
    }

    private void saveImageFile(File sourceFile, File destFile) throws IOException {
        if (!sourceFile.exists()) {
            return;
        }
        FileChannel source;
        FileChannel destination;
        source = new FileInputStream(sourceFile).getChannel();
        destination = new FileOutputStream(destFile).getChannel();

        if (destination != null && source != null) {
            destination.transferFrom(source, 0, source.size());
        }
        if (source != null) {
            source.close();
        }
        if (destination != null) {
            destination.close();
        }
//        try {
//            MainActivity mainActivity = Utility.INSTANCE.getMainActivity();
//            if (mainActivity != null) {
//                mainActivity.updateDataAfterChange();
//            }
//            Toast.makeText(this, "FIle saved successfully", Toast.LENGTH_LONG).show();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        finish();
    }
}
