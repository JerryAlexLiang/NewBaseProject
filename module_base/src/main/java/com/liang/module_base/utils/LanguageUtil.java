package com.liang.module_base.utils;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;

import com.liang.module_base.base.BaseApp;
import com.liang.module_base.extension.MmkvExtKt;

import java.util.Locale;

/**
 * @Time: 2023/5/5/0005 on 16:19
 * @User: Jerry
 * @Description: 多语言切换工具类
 */
public class LanguageUtil {

    private static final String TAG = "LanguageUtil";

    public static final String ERROR_LABEL = "";

    private static final String SPECIFIC_COUNTRY = "US";
    private static final String SPECIFIC_LANGUAGE = "en";

    private static final String CHINESE_COUNTRY = "CN";
    private static final String CHINESE_LANGUAGE = "zh";

    private static final String APP_PACKAGE_NAME = PackageUtils.getAppPackageName(BaseApp.Companion.getAppContext());

    private static Locale getLocale(Context context) {
        if (isChineseVersion(context)) {
            return Locale.CHINA;
        } else {
            return Locale.ENGLISH;
        }

    }

    public static boolean isChineseVersion(Context context) {
        return MmkvExtKt.getChineseMode();
    }

    public static void setChineseMode(Context context, boolean isChineseVersion) {
        MmkvExtKt.setChineseMode(isChineseVersion);
        LogUtils.INSTANCE.d(TAG, "setIsChineseVersion: " + isChineseVersion);
        changeLang(context);
    }

    private static String getStringByLocale(Context context, int stringId, String language, String country) {

        Resources resources = getApplicationResource(context, context.getApplicationContext().getPackageManager(),
                APP_PACKAGE_NAME, new Locale(language, country));
        if (resources == null) {
            return "ERROR_LABEL";
        } else {
            try {
                return resources.getString(stringId);
            } catch (Exception e) {
                return ERROR_LABEL;
            }
        }
    }

    private static String[] getStringsByLocale(Context context, int stringId, String language, String country) {

        Resources resources = getApplicationResource(context, context.getApplicationContext().getPackageManager(),
                APP_PACKAGE_NAME, new Locale(language, country));
        if (resources == null) {
            return new String[0];
        } else {
            try {
                return resources.getStringArray(stringId);
            } catch (Exception e) {
                return new String[0];
            }
        }
    }


    public static String getCurrentString(Context context, int stringId) {
        return isChineseVersion(context) ? getStringToChinese(context, stringId) : getStringToEnglish(context, stringId);
    }

    public static String[] getCurrentStrings(Context context, int stringId) {
        return isChineseVersion(context) ? getStringsToChinese(context, stringId) : getStringsToEnglish(context, stringId);
    }

    public static String getStringToEnglish(Context context, int stringId) {
        return getStringByLocale(context, stringId, SPECIFIC_LANGUAGE, SPECIFIC_COUNTRY);
    }

    public static String getStringToChinese(Context context, int stringId) {
        return getStringByLocale(context, stringId, CHINESE_LANGUAGE, CHINESE_COUNTRY);
    }


    public static String[] getStringsToEnglish(Context context, int stringId) {
        return getStringsByLocale(context, stringId, SPECIFIC_LANGUAGE, SPECIFIC_COUNTRY);
    }

    public static String[] getStringsToChinese(Context context, int stringId) {
        return getStringsByLocale(context, stringId, CHINESE_LANGUAGE, CHINESE_COUNTRY);
    }

    private static Resources getApplicationResource(Context context, PackageManager pm, String pkgName, Locale l) {
        Resources resourceForApplication = null;
        try {
            resourceForApplication = pm.getResourcesForApplication(pkgName);
            updateResource(context, resourceForApplication, l);
        } catch (PackageManager.NameNotFoundException e) {

        }
        return resourceForApplication;

    }

    private static void updateResource(Context context, Resources resource, Locale l) {
        Configuration config = resource.getConfiguration();
        config.locale = l;
        resource.updateConfiguration(config, null);
    }

    public static Context changeLang(Context context) {
        Resources res = context.getResources();
        Configuration config = res.getConfiguration();
        Locale locale = getLocale(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocale(locale);
            config.setLocales(new LocaleList(locale));
        } else {
            config.locale = locale;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLayoutDirection(locale);
            return new ContextWrapper(context.createConfigurationContext(config));
        } else {
            context.getResources().updateConfiguration(config, res.getDisplayMetrics());
            return new ContextWrapper(context);
        }
    }

    public static boolean isChinese(Context context) {
        return LanguageUtil.isChineseVersion(context);
    }

    public static String getChineseStr(Context context, int id) {
        return LanguageUtil.getStringToChinese(context, id);
    }

    public static String getEnglishStr(Context context, int id) {
        return LanguageUtil.getStringToEnglish(context, id);
    }

    public static String getStr(Context context, int id) {
        if (isChinese(context)) {
            return getChineseStr(context, id);
        } else {
            return getEnglishStr(context, id);
        }
    }

}