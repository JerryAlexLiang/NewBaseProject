package com.liang.module_base.utils;

import android.content.ClipboardManager;
import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.liang.module_base.base.BaseApp;

/**
 * 创建日期：2019/9/9 on 19:34
 * 描述: 复制到剪贴板
 * 作者: liangyang
 */
public class CopyUtils {

    public static void copyText(@NonNull String text) {
        ClipboardManager clipboardManager = (ClipboardManager) BaseApp.Companion.getAppContext().getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardManager.setText(text);
    }

    public static void copyText(@NonNull TextView textView) {
        copyText(textView.getText().toString());
    }
}
