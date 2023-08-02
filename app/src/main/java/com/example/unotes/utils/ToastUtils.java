package com.example.unotes.utils;


import android.content.Context;
import android.widget.Toast;

public class ToastUtils {

    private static Toast toast;

    /**
     * 显示一个短时间的 Toast 消息
     *
     * @param context 上下文
     * @param message 要显示的消息
     */
    public static void showToast(Context context, String message) {
        showToast(context, message, Toast.LENGTH_SHORT);
    }

    /**
     * 显示一个 Toast 消息
     *
     * @param context  上下文
     * @param message  要显示的消息
     * @param duration 显示时间，可以是 Toast.LENGTH_SHORT 或 Toast.LENGTH_LONG
     */
    public static void showToast(Context context, String message, int duration) {
        if (toast != null) {
            // 取消之前的 Toast，避免消息累积
            toast.cancel();
        }
        toast = Toast.makeText(context.getApplicationContext(), message, duration);
        toast.show();
    }

    // 以下是一些重载方法，方便在其他场景下调用

    public static void showToast(Context context, int resId) {
        showToast(context, context.getString(resId), Toast.LENGTH_SHORT);
    }

    public static void showToast(Context context, int resId, int duration) {
        showToast(context, context.getString(resId), duration);
    }

    public static void showToast(Context context, int resId, Object... formatArgs) {
        showToast(context, context.getString(resId, formatArgs), Toast.LENGTH_SHORT);
    }

    public static void showToast(Context context, int resId, int duration, Object... formatArgs) {
        showToast(context, context.getString(resId, formatArgs), duration);
    }
}
