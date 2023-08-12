package com.example.unotes.utils;

import android.content.pm.PackageManager;
        import android.os.Build;
        import androidx.annotation.NonNull;
        import androidx.core.app.ActivityCompat;
        import androidx.core.content.ContextCompat;
        import androidx.fragment.app.Fragment;
        import androidx.appcompat.app.AppCompatActivity;

        import java.util.ArrayList;
        import java.util.List;

/**
 * @author WTL
 * @version 1.0.0
 * @date 2023/08/12
 * @describe 允许请求者
 */
public class PermissionRequester {

    public interface PermissionCallback {
        void onPermissionGranted();
        void onPermissionDenied(List<String> deniedPermissions);
    }

    private final Object host;
    private final PermissionCallback callback;
    private final int requestCode;

    private PermissionRequester(@NonNull Object host, PermissionCallback callback, int requestCode) {
        this.host = host;
        this.callback = callback;
        this.requestCode = requestCode;
    }

    public static PermissionRequester with(@NonNull AppCompatActivity activity, PermissionCallback callback, int requestCode) {
        return new PermissionRequester(activity, callback, requestCode);
    }

    public static PermissionRequester with(@NonNull Fragment fragment, PermissionCallback callback, int requestCode) {
        return new PermissionRequester(fragment, callback, requestCode);
    }

    public void request(String[] permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> deniedPermissions = new ArrayList<>();

            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(getContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                    deniedPermissions.add(permission);
                }
            }

            if (!deniedPermissions.isEmpty()) {
                ActivityCompat.requestPermissions(getActivity(), deniedPermissions.toArray(new String[0]), requestCode);
            } else {
                callback.onPermissionGranted();
            }
        } else {
            callback.onPermissionGranted();
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == this.requestCode) {
            List<String> deniedPermissions = new ArrayList<>();

            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    deniedPermissions.add(permissions[i]);
                }
            }

            if (deniedPermissions.isEmpty()) {
                callback.onPermissionGranted();
            } else {
                callback.onPermissionDenied(deniedPermissions);
            }
        }
    }

    private AppCompatActivity getActivity() {
        if (host instanceof AppCompatActivity) {
            return (AppCompatActivity) host;
        } else {
            return (AppCompatActivity) ((Fragment) host).getActivity();
        }
    }

    private AppCompatActivity getContext() {
        return getActivity();
    }
}
