package com.liang.module_base.utils;

import com.liang.module_base.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

/**
 * 日期时间工具类
 */
public class DateUtilJava {
    public static final String STANDARD_TIME = "yyyy.MM.dd HH:mm:ss";
    public static final String FULL_TIME = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String MONTH_DAY_HOUR_MINUTE = "MM-dd HH:mm";
    public static final String YEAR_MONTH_DAY_HOUR_MINUTE = "yyyy.MM.dd HH:mm";
    public static final String MONTH_DAY = "MM-dd";
    public static final String YEAR_MONTH_DAY = "yyyyMMdd";
    public static final String YEAR_MONTH_DAY_MIDDLE_LINE = "yyyy-MM-dd";
    public static final String YEAR_MONTH_DAY_CN = "yyyy年MM月dd日";
    public static final String HOUR_MINUTE_SECOND = "HH:mm:ss";
    public static final String HOUR_MINUTE_SECOND_CN = "HH时mm分ss秒";
    public static final String YEAR = "yyyy";
    public static final String MONTH = "MM";
    public static final String DAY = "dd";
    public static final String HOUR = "HH";
    public static final String MINUTE = "mm";
    public static final String SECOND = "ss";
    public static final String MILLISECOND = "SSS";
    public static final String YESTERDAY = "昨天";
    public static final String TODAY = "今天";
    public static final String TOMORROW = "明天";
    public static final String SUNDAY = "星期日";
    public static final String MONDAY = "星期一";
    public static final String TUESDAY = "星期二";
    public static final String WEDNESDAY = "星期三";
    public static final String THURSDAY = "星期四";
    public static final String FRIDAY = "星期五";
    public static final String SATURDAY = "星期六";
    public static final String[] WEEK_DAYS = {SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY};

    public static final int January = R.string.January;
    public static final int February = R.string.February;
    public static final int March = R.string.March;
    public static final int April = R.string.April;
    public static final int May = R.string.May;
    public static final int June = R.string.June;
    public static final int July = R.string.July;
    public static final int August = R.string.August;
    public static final int September = R.string.September;
    public static final int October = R.string.October;
    public static final int November = R.string.November;
    public static final int December = R.string.December;
    public static final int[] Months = {January, February, March, April, May, June, July, August, September, October, November, December};
    public static final int First = R.string.FirstQuarter;
    public static final int Second = R.string.SecondQuarter;
    public static final int Third = R.string.ThirdQuarter;
    public static final int Fourth = R.string.FourthQuarter;
    public static final int[] Quarters = {First, Second, Third, Fourth};

    /**
     * 获取标准时间
     *
     * @return 例如 2021-07-01 10:35:53
     */
    public static String getDateTime() {
        return new SimpleDateFormat(STANDARD_TIME, Locale.CHINESE).format(new Date());
    }

    /**
     * 获取标准时间
     *
     * @return 例如 2021-07-01 10:35
     */
    public static String getDateTimeForReport() {
        return new SimpleDateFormat(YEAR_MONTH_DAY_HOUR_MINUTE, Locale.CHINESE).format(new Date());
    }

    /**
     * 获取完整时间
     *
     * @return 例如 2021-07-01 10:37:00.748
     */
    public static String getFullDateTime() {
        return new SimpleDateFormat(FULL_TIME, Locale.CHINESE).format(new Date());
    }

    /**
     * 获取年月日(今天)
     *
     * @return 例如 20210701
     */
    public static String getTheYearMonthAndDay() {
        return new SimpleDateFormat(YEAR_MONTH_DAY, Locale.CHINESE).format(new Date());
    }

    /**
     * 获取年月日(今天)
     *
     * @return 例如 2021-07-01
     */
    public static String getTheYearMonthAndDayMiddleLine() {
        return new SimpleDateFormat(YEAR_MONTH_DAY_MIDDLE_LINE, Locale.CHINESE).format(new Date());
    }

    /**
     * 获取年月日
     *
     * @return 例如 2021年07月01号
     */
    public static String getTheYearMonthAndDayCn() {
        return new SimpleDateFormat(YEAR_MONTH_DAY_CN, Locale.CHINESE).format(new Date());
    }

    /**
     * 获取年月日
     *
     * @param delimiter 分隔符
     * @return 例如 2021年07月01号
     */
    public static String getTheYearMonthAndDayDelimiter(CharSequence delimiter) {
        return new SimpleDateFormat(YEAR + delimiter + MONTH + delimiter + DAY, Locale.CHINESE).format(new Date());
    }

    /**
     * 获取时分秒
     *
     * @return 例如 10:38:25
     */
    public static String getHoursMinutesAndSeconds() {
        return new SimpleDateFormat(HOUR_MINUTE_SECOND, Locale.CHINESE).format(new Date());
    }

    /**
     * 获取时分秒
     *
     * @return 例如 10时38分50秒
     */
    public static String getHoursMinutesAndSecondsCn() {
        return new SimpleDateFormat(HOUR_MINUTE_SECOND_CN, Locale.CHINESE).format(new Date());
    }

    /**
     * 获取时分秒
     *
     * @param delimiter 分隔符
     * @return 例如 2021/07/01
     */
    public static String getHoursMinutesAndSecondsDelimiter(CharSequence delimiter) {
        return new SimpleDateFormat(HOUR + delimiter + MINUTE + delimiter + SECOND, Locale.CHINESE).format(new Date());
    }

    /**
     * 获取年
     *
     * @return 例如 2021
     */
    public static String getYear() {
        return new SimpleDateFormat(YEAR, Locale.CHINESE).format(new Date());
    }

    /**
     * 获取月
     *
     * @return 例如 07
     */
    public static String getMonth() {
        return new SimpleDateFormat(MONTH, Locale.CHINESE).format(new Date());
    }

    /**
     * 获取天
     *
     * @return 例如 01
     */
    public static String getDay() {
        return new SimpleDateFormat(DAY, Locale.CHINESE).format(new Date());
    }

    /**
     * 获取小时
     *
     * @return 例如 10
     */
    public static String getHour() {
        return new SimpleDateFormat(HOUR, Locale.CHINESE).format(new Date());
    }

    /**
     * 获取分钟
     *
     * @return 例如 40
     */
    public static String getMinute() {
        return new SimpleDateFormat(MINUTE, Locale.CHINESE).format(new Date());
    }

    /**
     * 获取秒
     *
     * @return 例如 58
     */
    public static String getSecond() {
        return new SimpleDateFormat(SECOND, Locale.CHINESE).format(new Date());
    }

    /**
     * 获取毫秒
     *
     * @return 例如 666
     */
    public static String getMilliSecond() {
        return new SimpleDateFormat(MILLISECOND, Locale.CHINESE).format(new Date());
    }

    /**
     * 获取时间戳
     *
     * @return 例如 1625107306051
     */
    public static long getTimestamp() {
        return System.currentTimeMillis();
    }

    /**
     * 获取第二天凌晨0点时间戳
     *
     * @return
     */
    public static long getMillisNextEarlyMorning() {
        Calendar cal = Calendar.getInstance();
        //日期加1
        cal.add(Calendar.DAY_OF_YEAR, 1);
        //时间设定到0点整
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    /**
     * 将时间转换为时间戳
     *
     * @param time 例如 2021-07-01 10:44:11
     * @return 1625107451000
     */
    public static long dateToStamp(String time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(STANDARD_TIME, Locale.CHINESE);
        Date date = null;
        try {
            date = simpleDateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Objects.requireNonNull(date).getTime();
    }


    /**
     * 将时间转换为时间戳
     *
     * @param time 例如 2021.07.01 10:44:11
     * @return date
     */
    public static Date timeToDate(String time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(STANDARD_TIME, Locale.CHINESE);
        Date date = null;
        try {
            date = simpleDateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    /**
     * 将时间戳转换为时间
     *
     * @param timeMillis 例如 1625107637084
     * @return 例如 2021-07-01 10:47:17
     */
    public static String stampToDate(long timeMillis) {
        return new SimpleDateFormat(STANDARD_TIME, Locale.CHINESE).format(new Date(timeMillis));
    }

    /**
     * 将时间戳转换为时间
     *
     * @param timeMillis 例如 1625107637084
     * @return 例如 07-01
     */
    public static String stampToDate2(long timeMillis) {
        return new SimpleDateFormat(MONTH_DAY, Locale.CHINESE).format(new Date(timeMillis));
    }

    /**
     * 将时间戳转换为时间
     *
     * @param timeMillis 例如 1625107637084
     * @return 例如 2021-07-01 10:47
     */
    public static String stampToDate3(long timeMillis) {
        if (timeMillis != 0) {
            return new SimpleDateFormat(YEAR_MONTH_DAY_HOUR_MINUTE, Locale.CHINESE).format(new Date(timeMillis));
        } else {
            return "";
        }
    }


    /**
     * 将时间戳转换为时间
     *
     * @param timeMillis 例如 1625107637084
     * @return 例如 2021-07-01 10:47
     */
    public static String stampToDate4(long timeMillis) {
        return new SimpleDateFormat(YEAR_MONTH_DAY_MIDDLE_LINE, Locale.CHINESE).format(new Date(timeMillis));
    }

    /**
     * 将时间戳转换为时间
     *
     * @param timeMillis 例如 1625107637084
     * @return 例如 07-01 10:47
     */
    public static String stampToMonthMinute(long timeMillis) {
        return new SimpleDateFormat(MONTH_DAY_HOUR_MINUTE, Locale.CHINESE).format(new Date(timeMillis));
    }

    /**
     * 将时间戳转换为时间
     *
     * @param timeMillis
     * @return 例如 2021-07-01
     */
    public static String stampToDateMiddleLine(long timeMillis) {
        return new SimpleDateFormat(YEAR_MONTH_DAY_MIDDLE_LINE, Locale.CHINESE).format(new Date(timeMillis));
    }

    public static int getCurrentMonthStr() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int index = cal.get(Calendar.MONTH);
        return Months[index];
    }


    public static int getMonthStr(Calendar cal) {
        int index = cal.get(Calendar.MONTH);
        return Months[index];
    }

    public static String getMonthEnStr(int index) {

        if (index <= 1 || index > 13) {
            return "MONTH ERROR";
        }
//        return LanguageUtil.getStringToEnglish(BaseApp.Companion.getAppContext(), Months[index - 1]);
        return "";
    }

    public static int getQuarterStr(Calendar cal) {
        int index = (cal.get(Calendar.MONTH) + 1) / 3;
        if (index == 4) {
            index = 3;
        }
        return Quarters[index];
    }

    public static int getYearStr(Calendar cal) {
        int index = cal.get(Calendar.YEAR);
        return index;
    }

    public static int getCurrentMonth() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int index = cal.get(Calendar.MONTH);
        return index + 1;
    }

    public static int getCurrentMonth(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(time));
        int index = cal.get(Calendar.MONTH);
        return index + 1;
    }

    public static int getCurrentQuarter() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int index = cal.get(Calendar.MONTH) / 3 + 1;
        return index;
    }

    public static int getCurrentQuarter(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(time));
        int index = cal.get(Calendar.MONTH) / 3 + 1;
        return index;
    }


    public static int getCurrentYear() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int index = cal.get(Calendar.YEAR);
        return index;
    }

    public static int getCurrentYear(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(time));
        int index = cal.get(Calendar.YEAR);
        return index;
    }

    /**
     * 获取今天是星期几
     *
     * @return 例如 星期四
     */
    public static String getTodayOfWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (index < 0) {
            index = 0;
        }
        return WEEK_DAYS[index];
    }

    /**
     * 根据输入的日期时间计算是星期几
     *
     * @param dateTime 例如 2021-06-20
     * @return 例如 星期日
     */
    public static String getWeek(String dateTime) {
        Calendar cal = Calendar.getInstance();
        if ("".equals(dateTime)) {
            cal.setTime(new Date(System.currentTimeMillis()));
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(YEAR_MONTH_DAY, Locale.getDefault());
            Date date;
            try {
                date = sdf.parse(dateTime);
            } catch (ParseException e) {
                date = null;
                e.printStackTrace();
            }
            if (date != null) {
                cal.setTime(new Date(date.getTime()));
            }
        }
        return WEEK_DAYS[cal.get(Calendar.DAY_OF_WEEK) - 1];
    }

    /**
     * 获取输入日期的昨天
     *
     * @param date 例如 2021-07-01
     * @return 例如 2021-06-30
     */
    public static String getYesterday(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);
        date = calendar.getTime();
        return new SimpleDateFormat(YEAR_MONTH_DAY, Locale.getDefault()).format(date);
    }


    /**
     * 获取输入日期的明天
     *
     * @param date 例如 2021-07-01
     * @return 例如 2021-07-02
     */
    public static String getTomorrow(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, +1);
        date = calendar.getTime();
        return new SimpleDateFormat(YEAR_MONTH_DAY, Locale.getDefault()).format(date);
    }

    /**
     * 根据年月日计算是星期几并与当前日期判断  非昨天、今天、明天 则以星期显示
     *
     * @param dateTime 例如 2021-07-03
     * @return 例如 星期六
     */
    public static String getDayInfo(String dateTime) {
        String dayInfo;
        String yesterday = getYesterday(new Date());
        String today = getTheYearMonthAndDay();
        String tomorrow = getTomorrow(new Date());

        if (dateTime.equals(yesterday)) {
            dayInfo = YESTERDAY;
        } else if (dateTime.equals(today)) {
            dayInfo = TODAY;
        } else if (dateTime.equals(tomorrow)) {
            dayInfo = TOMORROW;
        } else {
            dayInfo = getWeek(dateTime);
        }
        return dayInfo;
    }

    /**
     * 获取本月天数
     *
     * @return 例如 31
     */
    public static int getCurrentMonthDays() {
        Calendar calendar = Calendar.getInstance();
        //把日期设置为当月第一天
        calendar.set(Calendar.DATE, 1);
        //日期回滚一天，也就是最后一天
        calendar.roll(Calendar.DATE, -1);
        return calendar.get(Calendar.DATE);
    }

    /**
     * 获得指定月的天数
     *
     * @param year  例如 2021
     * @param month 例如 7
     * @return 例如 31
     */
    public static int getMonthDays(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        //把日期设置为当月第一天
        calendar.set(Calendar.DATE, 1);
        //日期回滚一天，也就是最后一天
        calendar.roll(Calendar.DATE, -1);
        return calendar.get(Calendar.DATE);
    }


    //获取当天0点的时间字符串
    public static String getZeroClock(long time) {
        return stampToDate4(getZeroClockTimestamp(time));
    }

    //获取当天0点的时间戳
    public static long getTodayZeroClockTimestamp(long time) {
        long zeroTimestamp = time - (time + TimeZone.getDefault().getRawOffset()) % (24 * 60 * 60 * 1000);
        return zeroTimestamp;
    }

    //获取明天0点的时间戳
    public static long getZeroClockTimestamp(long time) {
        long zeroTimestamp = time - (time + TimeZone.getDefault().getRawOffset()) % (24 * 60 * 60 * 1000);
        return zeroTimestamp + 24 * 60 * 60 * 1000;
    }

    //获取若干天天0点的时间戳
    public static String getTodayAgoClock(long time, int day) {
        long zeroTimestamp = time - (time + TimeZone.getDefault().getRawOffset()) % (24 * 60 * 60 * 1000);
        return stampToDateMiddleLine(zeroTimestamp - day * 24 * 60 * 60 * 1000);
    }

    /**
     * 获取完整时间
     *
     * @return 例如 2021-07-01 10:37:00.748
     */
    public static String[] getCreateUserDateTime(long time) {
        String data = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.CHINESE).format(new Date(time));
        return data.split("-");
    }

    public static int getQuarter(int month) {
        int quarter = 0;
        switch (month) {
            case 1:
            case 2:
            case 3:
                quarter = 1;
                break;
            case 4:
            case 5:
            case 6:
                quarter = 2;
                break;
            case 7:
            case 8:
            case 9:
                quarter = 3;
                break;
            case 10:
            case 11:
            case 12:
                quarter = 4;
                break;
            default:
                quarter = 0;
        }
        return quarter;
    }
}
