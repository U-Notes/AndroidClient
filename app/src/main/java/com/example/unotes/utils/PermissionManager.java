package com.example.unotes.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class PermissionManager {

    private Activity activity;
    private int requestCode;
    private PermissionCallback permissionCallback;

    public PermissionManager(Activity activity, int requestCode, PermissionCallback permissionCallback) {
        this.activity = activity;
        this.requestCode = requestCode;
        this.permissionCallback = permissionCallback;
    }

    public void requestPermissions(String... permissions) {
        List<String> permissionsToRequest = new ArrayList<>();

        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(permission);
            }
        }

        if (permissionsToRequest.isEmpty()) {
            // 所有权限已经被授予
            permissionCallback.onPermissionsGranted(requestCode);
        } else {
            // 请求权限
            ActivityCompat.requestPermissions(activity, permissionsToRequest.toArray(new String[0]), requestCode);
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == this.requestCode) {
            boolean allGranted = true;
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    allGranted = false;
                    break;
                }
            }

            if (allGranted) {
                // 所有权限被授予
                permissionCallback.onPermissionsGranted(requestCode);
            } else {
                // 有权限被拒绝
                permissionCallback.onPermissionsDenied(requestCode);
            }
        }
    }

    public interface PermissionCallback {
        void onPermissionsGranted(int requestCode);

        void onPermissionsDenied(int requestCode);
    }
}
