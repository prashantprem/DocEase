package com.artifex.sonui.editor;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.document.docease.ui.module.editors.ViewEditorActivity;
import com.document.docease.utils.Constant;

import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BaseActivity extends AppCompatActivity {
    private static BaseActivity mCurrentActivity;
    private static PermissionResultHandler mPermissionResultHandler;
    private static ResultHandler mResultHandler;
    private static ResumeHandler mResumeHandler;

    public BaseActivity() {
    }

    public static BaseActivity getCurrentActivity() {
        return mCurrentActivity;
    }

    private void setLocale(String var1) {
        this.setLocale(new Locale(var1));
    }

    private void setLocale(Locale var1) {
        Locale.setDefault(var1);
        Configuration var2 = this.getBaseContext().getResources().getConfiguration();
        var2.locale = var1;
        this.getBaseContext().getResources().updateConfiguration(var2, this.getBaseContext().getResources().getDisplayMetrics());
    }

    public static void setPermissionResultHandler(PermissionResultHandler var0) {
        mPermissionResultHandler = var0;
    }

    public static void setResultHandler(ResultHandler var0) {
        mResultHandler = var0;
    }

    public static void setResumeHandler(ResumeHandler var0) {
        mResumeHandler = var0;
    }

    public boolean isSlideShow() {
        return false;
    }

    protected void onActivityResult(int var1, int var2, Intent var3) {
        ResultHandler var4 = mResultHandler;
        if (var4 == null || !var4.handle(var1, var2, var3)) {
            super.onActivityResult(var1, var2, var3);
        }
    }

    protected void onCreate(Bundle var1) {
        super.onCreate(var1);
    }

    protected void onPause() {
        mCurrentActivity = null;
        super.onPause();
    }

    public void onRequestPermissionsResult(int var1, String[] var2, int[] var3) {
        PermissionResultHandler var4 = mPermissionResultHandler;
        if (var4 == null || !var4.handle(var1, var2, var3)) {
            if(var1 == ViewEditorActivity.REQUEST_CAMERA_PERMISSIONS){
                if(var3.length > 0 && var3[0] == PackageManager.PERMISSION_DENIED){
//                    int count = SharedPreferencesUtility.INSTANCE.getCameraPermissionCount(this);
//                    SharedPreferencesUtility.INSTANCE.setCameraPermissionCount(this,count+1);
//                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && count > 2) {
//                        handlePermissionDenyForAPI30AndAbove(getResources().getString(com.document.docease.R.string.camera_permission_deny),getResources().getString(com.R.string.camera_permission_deny_message));
//                    } else {
//                        showExplanation(getResources().getString(com.document.docease.R.string.camera_permission_deny),getResources().getString(com.document.docease.R.string.camera_permission_deny_message), Manifest.permission.CAMERA,ViewEditorActivity.REQUEST_CAMERA_PERMISSIONS);
//                    }
                }
            }
            super.onRequestPermissionsResult(var1, var2, var3);
        }
    }

    protected void onResume() {
        mCurrentActivity = this;
        super.onResume();
        ResumeHandler var1 = mResumeHandler;
        if (var1 != null) {
            var1.handle();
        }

    }

    public interface PermissionResultHandler {
        boolean handle(int var1, String[] var2, int[] var3);
    }

    public interface ResultHandler {
        boolean handle(int var1, int var2, Intent var3);
    }

    public interface ResumeHandler {
        void handle();
    }

    private void showExplanation(String title,
                                 String message,
                                 final String permission,
                                 final int permissionRequestCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setNegativeButton(com.document.docease.R.string.no_thanks, (dialogInterface, i) -> {

                })
                .setPositiveButton(com.document.docease.R.string.licensing_again, (DialogInterface.OnClickListener) (dialog, id) ->
                        requestPermission(permission, permissionRequestCode));
        builder.create().show();
    }

    private void handlePermissionDenyForAPI30AndAbove (String title,
                                 String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setNegativeButton(com.document.docease.R.string.no_thanks, (dialogInterface, i) -> {

                })
                .setPositiveButton(com.document.docease.R.string.permisson_go_to_setting, (DialogInterface.OnClickListener) (dialog, id) -> {
                    try{
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts(Constant.PACKAGE, getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                });
        builder.create().show();
    }

    private void requestPermission(String permissionName, int permissionRequestCode) {
        ActivityCompat.requestPermissions(this,
                new String[]{permissionName}, permissionRequestCode);
    }
}
