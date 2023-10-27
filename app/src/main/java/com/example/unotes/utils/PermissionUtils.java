package com.example.unotes.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.util.Log;

public class PermissionUtils {
    static Boolean isGranted = false;
    public static Boolean checkPermission(Activity context) {
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            if (context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                isGranted = false;
            }
            if (context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                isGranted = false;
            }
            Log.i("读写权限获取", " ： " + isGranted);
            if (!isGranted) {
                context.requestPermissions(
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission
                                .ACCESS_FINE_LOCATION,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        102);
            }
        }
        return isGranted;
    }
    //            AlertDialog.Builder aler = new AlertDialog.Builder(this)
//                        .setTitle("权限未开启")
//                        .setPositiveButton("去设置", (dialog, which) -> {
//                                    toSelfSetting(getApplicationContext());
//                                }
//                        ).setNegativeButton("取消", (dialog, which) -> {
//                            dialog.dismiss();
//                        });
//            if (!isGranted) {
//                        aler.create().show();
//            } else {}
}
