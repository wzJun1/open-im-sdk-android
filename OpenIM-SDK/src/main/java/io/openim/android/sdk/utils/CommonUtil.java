package io.openim.android.sdk.utils;

import android.os.Handler;
import android.os.Looper;

import java.util.List;

import io.openim.android.sdk.listener.OnBase;

public class CommonUtil {
    private final static Handler MAIN_HANDLER = new Handler(Looper.getMainLooper());

    public static void runMainThread(Runnable runnable) {
        MAIN_HANDLER.post(runnable);
    }

    public static <T> void returnError(OnBase<T> onBase, int code, String error) {
        if (onBase != null) {
            CommonUtil.runMainThread(() -> onBase.onError(code, error));
        }
    }

    public static void returnSuccess(OnBase<String> onBase, String s) {
        if (onBase != null) {
            CommonUtil.runMainThread(() -> onBase.onSuccess(s));
        }
    }

    public static <T> void returnObject(OnBase<T> onBase, Class<T> clazz, String s) {
        if (onBase != null) {
            try {
                T obj = JsonUtil.toObj(s, clazz);
                CommonUtil.runMainThread(() -> onBase.onSuccess(obj));
            } catch (Exception e) {
                CommonUtil.runMainThread(() -> onBase.onError(-1, e.getMessage()));
            }
        }
    }

    public static <T> void returnList(OnBase<List<T>> onBase, Class<T> clazz, String s) {
        if (onBase != null) {
            try {
                List<T> list = JsonUtil.toArray(s, clazz);
                CommonUtil.runMainThread(() -> onBase.onSuccess(list));
            } catch (Exception e) {
                CommonUtil.runMainThread(() -> onBase.onError(-1, e.getMessage()));
            }
        }
    }
}
