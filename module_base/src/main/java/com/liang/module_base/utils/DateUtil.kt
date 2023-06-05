package com.liang.module_base.utils

import com.liang.module_base.R
import java.text.ParseException
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*

/**
 * @Time: 2023/4/17/0017 on 14:57
 * @User: Jerry
 * @Description: 时间处理工具类
 */
object DateUtil {

    const val STANDARD_TIME = "yyyy.MM.dd HH:mm:ss"
    const val FULL_TIME = "yyyy-MM-dd HH:mm:ss.SSS"
    const val MONTH_DAY_HOUR_MINUTE = "MM-dd HH:mm"
    const val YEAR_MONTH_DAY_HOUR_MINUTE = "yyyy.MM.dd HH:mm"
    const val MONTH_DAY = "MM-dd"
    const val YEAR_MONTH_DAY = "yyyyMMdd"
    const val YEAR_MONTH_DAY_MIDDLE_LINE = "yyyy-MM-dd"
    const val YEAR_MONTH_DAY_CN = "yyyy年MM月dd日"
    const val HOUR_MINUTE_SECOND = "HH:mm:ss"
    const val HOUR_MINUTE_SECOND_CN = "HH时mm分ss秒"
    const val YEAR = "yyyy"
    const val MONTH = "MM"
    const val DAY = "dd"
    const val HOUR = "HH"
    const val MINUTE = "mm"
    const val SECOND = "ss"
    const val MILLISECOND = "SSS"
    const val YESTERDAY = "昨天"
    const val TODAY = "今天"
    const val TOMORROW = "明天"
    const val SUNDAY = "星期日"
    const val MONDAY = "星期一"
    const val TUESDAY = "星期二"
    const val WEDNESDAY = "星期三"
    const val THURSDAY = "星期四"
    const val FRIDAY = "星期五"
    const val SATURDAY = "星期六"
    val WEEK_DAYS = arrayOf(SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY)
    val January = R.string.January
    val February = R.string.February
    val March = R.string.March
    val April = R.string.April
    val May = R.string.May
    val June = R.string.June
    val July = R.string.July
    val August = R.string.August
    val September = R.string.September
    val October = R.string.October
    val November = R.string.November
    val December = R.string.December
    val Months = intArrayOf(
        January,
        February,
        March,
        April,
        May,
        June,
        July,
        August,
        September,
        October,
        November,
        December
    )
    val First = R.string.FirstQuarter
    val Second = R.string.SecondQuarter
    val Third = R.string.ThirdQuarter
    val Fourth = R.string.FourthQuarter
    val Quarters = intArrayOf(First, Second, Third, Fourth)


    /**
     * 获取标准时间
     *
     * @return 例如 2021-07-01 10:35:53
     */
    val dateTime: String
        get() = SimpleDateFormat(STANDARD_TIME, Locale.CHINESE).format(Date())

    /**
     * 获取标准时间
     *
     * @return 例如 2021-07-01 10:35
     */
    val dateTimeForReport: String
        get() = SimpleDateFormat(YEAR_MONTH_DAY_HOUR_MINUTE, Locale.CHINESE).format(Date())

    /**
     * 获取完整时间
     *
     * @return 例如 2021-07-01 10:37:00.748
     */
    val fullDateTime: String
        get() = SimpleDateFormat(FULL_TIME, Locale.CHINESE).format(Date())

    /**
     * 获取年月日(今天)
     *
     * @return 例如 20210701
     */
    val theYearMonthAndDay: String
        get() = SimpleDateFormat(YEAR_MONTH_DAY, Locale.CHINESE).format(Date())

    /**
     * 获取年月日(今天)
     *
     * @return 例如 2021-07-01
     */
    val theYearMonthAndDayMiddleLine: String
        get() = SimpleDateFormat(YEAR_MONTH_DAY_MIDDLE_LINE, Locale.CHINESE).format(Date())

    /**
     * 获取年月日
     *
     * @return 例如 2021年07月01号
     */
    val theYearMonthAndDayCn: String
        get() = SimpleDateFormat(YEAR_MONTH_DAY_CN, Locale.CHINESE).format(Date())

    /**
     * 获取年月日
     *
     * @param delimiter 分隔符
     * @return 例如 2021年07月01号
     */
    fun getTheYearMonthAndDayDelimiter(delimiter: CharSequence): String {
        return SimpleDateFormat(YEAR + delimiter + MONTH + delimiter + DAY, Locale.CHINESE).format(
            Date()
        )
    }

    /**
     * 获取时分秒
     *
     * @return 例如 10:38:25
     */
    val hoursMinutesAndSeconds: String
        get() = SimpleDateFormat(HOUR_MINUTE_SECOND, Locale.CHINESE).format(Date())

    /**
     * 获取时分秒
     *
     * @return 例如 10时38分50秒
     */
    val hoursMinutesAndSecondsCn: String
        get() = SimpleDateFormat(HOUR_MINUTE_SECOND_CN, Locale.CHINESE).format(Date())

    /**
     * 获取时分秒
     *
     * @param delimiter 分隔符
     * @return 例如 2021/07/01
     */
    fun getHoursMinutesAndSecondsDelimiter(delimiter: CharSequence): String {
        return SimpleDateFormat(
            HOUR + delimiter + MINUTE + delimiter + SECOND,
            Locale.CHINESE
        ).format(
            Date()
        )
    }

    /**
     * 获取年
     *
     * @return 例如 2021
     */
    val year: String
        get() = SimpleDateFormat(YEAR, Locale.CHINESE).format(Date())

    /**
     * 获取月
     *
     * @return 例如 07
     */
    val month: String
        get() = SimpleDateFormat(MONTH, Locale.CHINESE).format(Date())

    /**
     * 获取天
     *
     * @return 例如 01
     */
    val day: String
        get() = SimpleDateFormat(DAY, Locale.CHINESE).format(Date())


    /**
     * 获取小时
     *
     * @return 例如 10
     */
    val hour: String
        get() = SimpleDateFormat(HOUR, Locale.CHINESE).format(Date())

    /**
     * 获取分钟
     *
     * @return 例如 40
     */
    val minute: String
        get() = SimpleDateFormat(MINUTE, Locale.CHINESE).format(Date())

    /**
     * 获取秒
     *
     * @return 例如 58
     */
    fun getSecond(): String {
        return SimpleDateFormat(SECOND, Locale.CHINESE).format(Date())
    }

    /**
     * 获取毫秒
     *
     * @return 例如 666
     */
    val milliSecond: String
        get() = SimpleDateFormat(MILLISECOND, Locale.CHINESE).format(Date())

    /**
     * 获取时间戳
     * 日期加1
     * @return 例如 1625107306051
     */
    val timestamp: Long
        get() = System.currentTimeMillis()

    /**
     * 时间设定到0点整
     * 获取第二天凌晨0点时间戳
     *
     * @return
     */
    val millisNextEarlyMorning: Long
        get() {
            val cal = Calendar.getInstance()
            //日期加1
            cal.add(Calendar.DAY_OF_YEAR, 1)
            //时间设定到0点整
            cal[Calendar.HOUR_OF_DAY] = 0
            cal[Calendar.SECOND] = 0
            cal[Calendar.MINUTE] = 0
            cal[Calendar.MILLISECOND] = 0
            return cal.timeInMillis
        }

    /**
     * 将时间转换为时间戳
     *
     * @param time 例如 2021-07-01 10:44:11
     * @return 1625107451000
     */
    fun dateToStamp(time: String): Long {
        val simpleDateFormat = SimpleDateFormat(STANDARD_TIME, Locale.CHINESE)
        var date: Date? = null
        try {
            date = simpleDateFormat.parse(time)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return Objects.requireNonNull(date)!!.time
    }

    /**
     * 将时间转换为时间戳
     *
     * @param time 例如 2021.07.01 10:44:11
     * @return date
     */
    fun timeToDate(time: String?): Date? {
        val simpleDateFormat = SimpleDateFormat(STANDARD_TIME, Locale.CHINESE)
        var date: Date? = null
        try {
            date = simpleDateFormat.parse(time)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return date
    }

    /**
     * 将时间戳转换为时间
     *
     * @param timeMillis 例如 1625107637084
     * @return 例如 2021-07-01 10:47:17
     */
    fun stampToDate(timeMillis: Long): String {
        return SimpleDateFormat(STANDARD_TIME, Locale.CHINESE).format(Date(timeMillis))
    }

    /**
     * 将时间戳转换为时间
     *
     * @param timeMillis 例如 1625107637084
     * @return 例如 07-01
     */
    fun stampToDate2(timeMillis: Long): String {
        return SimpleDateFormat(MONTH_DAY, Locale.CHINESE).format(Date(timeMillis))
    }

    /**
     * 将时间戳转换为时间
     *
     * @param timeMillis 例如 1625107637084
     * @return 例如 2021-07-01 10:47
     */
    fun stampToDate3(timeMillis: Long): String {
        return if (timeMillis != 0L) {
            SimpleDateFormat(
                YEAR_MONTH_DAY_HOUR_MINUTE,
                Locale.CHINESE
            ).format(Date(timeMillis))
        } else {
            ""
        }
    }

    /**
     * 将时间戳转换为时间
     *
     * @param timeMillis 例如 1625107637084
     * @return 例如 2021-07-01 10:47
     */
    fun stampToDate4(timeMillis: Long): String {
        return SimpleDateFormat(YEAR_MONTH_DAY_MIDDLE_LINE, Locale.CHINESE).format(Date(timeMillis))
    }

    /**
     * 将时间戳转换为时间
     *
     * @param timeMillis 例如 1625107637084
     * @return 例如 07-01 10:47
     */
    fun stampToMonthMinute(timeMillis: Long): String {
        return SimpleDateFormat(MONTH_DAY_HOUR_MINUTE, Locale.CHINESE).format(Date(timeMillis))
    }

    /**
     * 将时间戳转换为时间
     *
     * @param timeMillis
     * @return 例如 2021-07-01
     */
    fun stampToDateMiddleLine(timeMillis: Long): String {
        return SimpleDateFormat(YEAR_MONTH_DAY_MIDDLE_LINE, Locale.CHINESE).format(Date(timeMillis))
    }

    val currentMonthStr: Int
        get() {
            val cal = Calendar.getInstance()
            cal.time = Date()
            val index = cal[Calendar.MONTH]
            return Months[index]
        }

    fun getMonthStr(cal: Calendar): Int {
        val index = cal[Calendar.MONTH]
        return Months[index]
    }

    fun getMonthEnStr(index: Int): String {
        return if (index <= 1 || index > 13) {
            "MONTH ERROR"
        } else ""
        //        return LanguageUtil.getStringToEnglish(BaseApp.Companion.getAppContext(), Months[index - 1]);
    }

    fun getQuarterStr(cal: Calendar): Int {
        var index = (cal[Calendar.MONTH] + 1) / 3
        if (index == 4) {
            index = 3
        }
        return Quarters[index]
    }

    fun getYearStr(cal: Calendar): Int {
        return cal[Calendar.YEAR]
    }

    val currentMonthInt: Int
        get() {
            val cal = Calendar.getInstance()
            cal.time = Date()
            val index = cal[Calendar.MONTH]
            return index + 1
        }

    fun getCurrentMonth(time: Long): Int {
        val cal = Calendar.getInstance()
        cal.time = Date(time)
        val index = cal[Calendar.MONTH]
        return index + 1
    }

    val currentQuarter: Int
        get() {
            val cal = Calendar.getInstance()
            cal.time = Date()
            return cal[Calendar.MONTH] / 3 + 1
        }

    fun getCurrentQuarter(time: Long): Int {
        val cal = Calendar.getInstance()
        cal.time = Date(time)
        return cal[Calendar.MONTH] / 3 + 1
    }

    val currentYearInt: Int
        get() {
            val cal = Calendar.getInstance()
            cal.time = Date()
            return cal[Calendar.YEAR]
        }

    fun getCurrentYear(time: Long): Int {
        val cal = Calendar.getInstance()
        cal.time = Date(time)
        return cal[Calendar.YEAR]
    }

    /**
     * 获取今天是星期几
     *
     * @return 例如 星期四
     */
    val todayOfWeek: String
        get() {
            val cal = Calendar.getInstance()
            cal.time = Date()
            var index = cal[Calendar.DAY_OF_WEEK] - 1
            if (index < 0) {
                index = 0
            }
            return WEEK_DAYS[index]
        }

    /**
     * 根据输入的日期时间计算是星期几
     *
     * @param dateTime 例如 2021-06-20
     * @return 例如 星期日
     */
    fun getWeek(dateTime: String): String {
        val cal = Calendar.getInstance()
        if ("" == dateTime) {
            cal.time = Date(System.currentTimeMillis())
        } else {
            val sdf = SimpleDateFormat(YEAR_MONTH_DAY, Locale.getDefault())
            var date: Date?
            try {
                date = sdf.parse(dateTime)
            } catch (e: ParseException) {
                date = null
                e.printStackTrace()
            }
            if (date != null) {
                cal.time = Date(date.time)
            }
        }
        return WEEK_DAYS[cal[Calendar.DAY_OF_WEEK] - 1]
    }

    /**
     * 获取输入日期的昨天
     *
     * @param date 例如 2021-07-01
     * @return 例如 2021-06-30
     */
    fun getYesterday(date: Date?): String {
        var date = date
        val calendar: Calendar = GregorianCalendar()
        calendar.time = date
        calendar.add(Calendar.DATE, -1)
        date = calendar.time
        return SimpleDateFormat(YEAR_MONTH_DAY, Locale.getDefault()).format(date)
    }

    /**
     * 获取输入日期的明天
     *
     * @param date 例如 2021-07-01
     * @return 例如 2021-07-02
     */
    fun getTomorrow(date: Date?): String {
        var date = date
        val calendar: Calendar = GregorianCalendar()
        calendar.time = date
        calendar.add(Calendar.DATE, +1)
        date = calendar.time
        return SimpleDateFormat(YEAR_MONTH_DAY, Locale.getDefault()).format(date)
    }

    /**
     * 根据年月日计算是星期几并与当前日期判断  非昨天、今天、明天 则以星期显示
     * 把日期设置为当月第一天
     * 日期回滚一天，也就是最后一天
     * @param dateTime 例如 2021-07-03
     * @return 例如 星期六
     */
    fun getDayInfo(dateTime: String): String {
        val dayInfo: String
        val yesterday = getYesterday(Date())
        val today = theYearMonthAndDay
        val tomorrow = getTomorrow(Date())
        dayInfo = if (dateTime == yesterday) {
            YESTERDAY
        } else if (dateTime == today) {
            TODAY
        } else if (dateTime == tomorrow) {
            TOMORROW
        } else {
            getWeek(dateTime)
        }
        return dayInfo
    }

    /**
     * 获取本月天数
     *
     * @return 例如 31
     */
    val currentMonthDays: Int
        get() {
            val calendar = Calendar.getInstance()
            //把日期设置为当月第一天
            calendar[Calendar.DATE] = 1
            //日期回滚一天，也就是最后一天
            calendar.roll(Calendar.DATE, -1)
            return calendar[Calendar.DATE]
        }

    /**
     * 获得指定月的天数
     *
     * @param year  例如 2021
     * @param month 例如 7
     * @return 例如 31
     */
    fun getMonthDays(year: Int, month: Int): Int {
        val calendar = Calendar.getInstance()
        calendar[Calendar.YEAR] = year
        calendar[Calendar.MONTH] = month - 1
        //把日期设置为当月第一天
        calendar[Calendar.DATE] = 1
        //日期回滚一天，也就是最后一天
        calendar.roll(Calendar.DATE, -1)
        return calendar[Calendar.DATE]
    }

    //获取当天0点的时间字符串
    fun getZeroClock(time: Long): String {
        return stampToDate4(getZeroClockTimestamp(time))
    }

    //获取当天0点的时间戳
    fun getTodayZeroClockTimestamp(time: Long): Long {
        return time - (time + TimeZone.getDefault().rawOffset) % (24 * 60 * 60 * 1000)
    }

    //获取明天0点的时间戳
    fun getZeroClockTimestamp(time: Long): Long {
        val zeroTimestamp = time - (time + TimeZone.getDefault().rawOffset) % (24 * 60 * 60 * 1000)
        return zeroTimestamp + 24 * 60 * 60 * 1000
    }

    //获取若干天天0点的时间戳
    fun getTodayAgoClock(time: Long, day: Int): String {
        val zeroTimestamp = time - (time + TimeZone.getDefault().rawOffset) % (24 * 60 * 60 * 1000)
        return stampToDateMiddleLine(zeroTimestamp - day * 24 * 60 * 60 * 1000)
    }

    /**
     * 获取完整时间
     *
     * @return 例如 2021-07-01 10:37:00.748
     */
    fun getCreateUserDateTime(time: Long): Array<String> {
        val data = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.CHINESE).format(Date(time))
        return data.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    }

    fun getQuarter(month: Int): Int {
        var quarter = 0
        quarter = when (month) {
            1, 2, 3 -> 1
            4, 5, 6 -> 2
            7, 8, 9 -> 3
            10, 11, 12 -> 4
            else -> 0
        }
        return quarter
    }

    ///////////////

    /**
     * 获得当前年
     *
     * @return 当前年
     */
    val currentYear: String
        get() {
            val currentTime = Date()
            val dateString =
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(currentTime)
            return dateString.substring(0, 4)
        }

    /**
     * 获得当前月
     *
     * @return 当前月
     */
    val currentMonth: String
        get() {
            val currentTime = Date()
            val dateString =
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(currentTime)
            return dateString.substring(5, 7)
        }

    /**
     * 获得当前日
     *
     * @return 当前日
     */
    val nowDay: String
        get() {
            val currentTime = Date()
            val dateString =
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(currentTime)
            return dateString.substring(8, 10)
        }

    /**
     * 获得当前时
     *
     * @return 当前时
     */
    val nowHour: String
        get() {
            val currentTime = Date()
            val dateString =
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(currentTime)
            return dateString.substring(11, 13)
        }

    /**
     * 获得当前分
     *
     * @return 当前分
     */
    val nowMinute: String
        get() {
            val currentTime = Date()
            val dateString =
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(currentTime)
            return dateString.substring(14, 16)
        }

    /**
     * 获取yyyy-MM-dd型生日时间的年
     *
     * @param dayStr yyyy-MM-dd型生日时间
     * @return yyyy-MM-dd型生日时间的年
     */
    fun getBirthdayYear(dayStr: String?): Int {
        return try {
            dayStr!!.split("-")[0].toInt()
        } catch (e: Exception) {
            currentYear.toInt()
        }
    }

    /**
     * 获取yyyy-MM-dd型生日时间的月
     *
     * @param dayStr yyyy-MM-dd型生日时间
     * @return yyyy-MM-dd型生日时间的月
     */
    fun getBirthdayMonth(dayStr: String?): Int {
        return try {
            dayStr!!.split("-")[1].toInt() - 1
        } catch (e: Exception) {
            currentMonth.toInt() - 1
        }
    }

    /**
     * 获取yyyy-MM-dd型生日时间的日
     *
     * @param dayStr yyyy-MM-dd型生日时间
     * @return yyyy-MM-dd型生日时间的日
     */
    fun getBirthdayDay(dayStr: String?): Int {
        return try {
            dayStr!!.split("-")[2].toInt()
        } catch (e: Exception) {
            nowDay.toInt()
        }
    }

    /**
     * 将短日期转为长日期（例如2018-9-8变成2018-09-08）
     *
     * @param dateStr 短日期 (如2018-9-8)
     * @return 长日期 (如2018-09-08)
     */
    fun shortDateStrToLong(dateStr: String): String {
        val strArray = dateStr.split("-".toRegex(), 3).toTypedArray()
        val year = strArray[0]
        val month = if (strArray[1].toInt() > 9) strArray[1] else "0${strArray[1]}"
        val day = if (strArray[2].toInt() > 9) strArray[2] else "0${strArray[2]}"
        return "$year-$month-$day"
    }

    /**
     * 获取当前时间(yyyy-MM-dd)
     *
     * @return yyyy-MM-dd
     */
    fun getNowDayString(): String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    }

    /**
     * 将时间对象转换为年月日时间格式的字符串 yyyy-MM-dd
     *
     * @param dateDate 时间对象
     * @return 中时间格式的字符串 yyyy-MM-dd
     */
    fun dateToStrDay(dateDate: Date): String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(dateDate)
    }

    /**
     * 获取现在时间
     *
     * @return 返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    val nowStringDateLong: String
        get() = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())

    /**
     * 获取以现在时间命名的文件名
     *
     * @return 返回字符串文件名 yyyyMMddHHmmss
     */
    val nowDateFileName: String
        get() {
            val formatter = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
            return formatter.format(Date())
        }

    /**
     * 获取现在时间
     *
     * @return 返回短时间字符串格式yyyy-MM-dd
     */
    val nowStringDateShort: String
        get() = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

    /**
     * 获取现在时间 小时:分;秒 HH:mm:ss
     *
     * @return 返回短时间字符串格式HH:mm:ss
     */
    val nowTimeShort: String
        get() = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())


    /**
     * 得到现在时间
     *
     * @return 字符串 yyyyMMdd HHmmss
     */
    val stringToday: String
        get() {
            val currentTime = Date()
            val formatter = SimpleDateFormat("yyyyMMdd HHmmss", Locale.getDefault())
            return formatter.format(currentTime)
        }

    /**
     * 获取指定时间值(long)对应的日期yyyy-MM-dd
     *
     * @param currentTimeMills 指定时间值(long)
     * @return 指定时间值(long)对应的日期
     */
    fun getShortDateStr(currentTimeMills: Long): String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(currentTimeMills)
    }

    /**
     * 获取指定时间值(long)对应的日期yyyy-MM-dd HH:mm:ss
     *
     * @param timeMills 指定时间值(long)
     * @return 指定时间值(long)对应的日期
     */
    @JvmStatic
    fun getLongDateStr(timeMills: Long): String {
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(timeMills)
    }

    /**
     * 将时间对象转换为长时间格式的字符串 yyyy-MM-dd HH:mm:ss
     *
     * @param dateDate Date时间对象
     * @return 时间格式为 yyyy-MM-dd HH:mm:ss 的字符串
     */
    fun dateToStrLong(dateDate: Date): String {
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(dateDate)
    }

    /**
     * 将时间对象转换为中时间格式的字符串 yyyy-MM-dd HH:mm
     *
     * @param dateDate 时间对象
     * @return 中时间格式的字符串 yyyy-MM-dd HH:mm
     */
    fun dateToStrMiddle(dateDate: Date): String {
        return SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(dateDate)
    }

    /**
     * 将时间对象转换为短时间格式的字符串 yyyy-MM-dd
     *
     * @param dateDate 时间对象
     * @return 短时间格式的字符串 yyyy-MM-dd
     */
    fun dateToStrShort(dateDate: Date): String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(dateDate)
    }

    /**
     * 获取指定时间值(long)对应的年份yyyy
     *
     * @param dateDate 时间对象
     * @return 对应的年份yyyy
     */
    fun dateToStrYear(dateDate: Date): String {
        return SimpleDateFormat("yyyy", Locale.getDefault()).format(dateDate)
    }

    /**
     * 将时间对象转换为HH:mm时间格式的字符串
     *
     * @param dateDate 时间对象
     * @return 短时间格式的字符串 HH:mm
     */
    fun dateToStrHour(dateDate: Date): String {
        return SimpleDateFormat("HH:mm", Locale.getDefault()).format(dateDate)
    }

    /**
     * 获取一个月的最后一天
     *
     * @param dateStr 日期
     * @return 日期所在月的最后一天
     */
    fun getEndDateOfMonth(dateStr: String): String {// yyyy-MM-dd
        val strArray = dateStr.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        var str = strArray[0] + "-" + strArray[1] + "-"
        val month = strArray[1]

        val mon = Integer.parseInt(month)
        str += if (mon == 1 || mon == 3 || mon == 5 || mon == 7 || mon == 8 || mon == 10 || mon == 12) {
            "31"
        } else if (mon == 4 || mon == 6 || mon == 9 || mon == 11) {
            "30"
        } else {
            if (isLeapYear(dateStr)) {
                "29"
            } else {
                "28"
            }
        }
        return str
    }

    /**
     * 将长时间格式字符串yyyy-MM-dd HH:mm:ss 转换为时间
     *
     * @param strDate yyyy-MM-dd HH:mm:ss
     * @return Date类型的时间
     */
    fun strToDateLong(strDate: String?): Date {
        val pos = ParsePosition(0)
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(strDate, pos)
    }

    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     *
     * @param strDate
     * @return
     */
    fun strToDateMiddle(strDate: String): Date {
        val pos = ParsePosition(0)
        return SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).parse(strDate, pos)
    }

    /**
     * 将短时间格式字符串转换为时间 yyyy-MM-dd
     *
     * @param strDate yyyy-MM-dd的短时间格式
     * @return 短时间格式字符串转换的时间
     */
    fun strToDateShort(strDate: String): Date {
        val pos = ParsePosition(0)
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(strDate, pos)
    }

    /**
     * 获取与当前时间(yyyy-MM-dd HH:mm:ss)的间隔毫秒值
     *
     * @param dateStr 要比较的时间String (yyyy-MM-dd HH:mm:ss)
     */
    fun getMinutesSpaceFromNow(dateStr: String): Long {
        val date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(dateStr)
        return date.time - Date().time
    }

    /**
     * 两个时间之间的间隔天数
     *
     * @param date1 yyyy-MM-dd
     * @param date2 yyyy-MM-dd
     * @return 间隔天数
     */
    fun getDaySpace(date1: String?, date2: String?): Long {
        if (date1 == null || "" == date1) {
            return 0
        }
        if (date2 == null || "" == date2) {
            return 0
        }
        return try {
            // 转换为标准时间
            val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(date1)
            val myDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(date2)
            (date.time - myDate.time) / (24 * 60 * 60 * 1000)
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }

    /**
     * 修改时间格式(例：2018-12-04 19:08:03 改为 12-04 19：08)
     *
     * @param timeStr 要修改的时间
     */
    fun long2MiddleDateStr(timeStr: String?): String {
        return when {
            timeStr.isNullOrEmpty() -> "--"
            timeStr.length > 7 -> timeStr.substring(5, 16)
            else -> timeStr
        }
    }

    /**
     * 将长时间格式变为短时间格式(例：2018-12-04 19:08:03 改为 2018-12-04)
     *
     * @param timeStr 要修改的时间 yyyy-MM-dd HH:mm:ss
     *
     * @return 修改后的时间yyyy-MM-dd（报错返回本身或null返回"--"）
     */
    fun long2ShortDateStr(timeStr: String?): String {
        return try {
            timeStr!!.substring(0, 10)
        } catch (e: Exception) {
            timeStr ?: "--"
        }
    }

    /**
     * 获取时间特殊显示，当为当天时显示HH:mm,昨天是显示昨天，否则显示日期（收发文列表中使用）
     *
     * @param dateStr yyyy-MM-dd HH:mm:ss的字符串时间格式
     * @return 当为当天时显示HH:mm,昨天是显示昨天，否则显示具体日期
     */
    fun getTimeFromNow(dateStr: String): String {
        return if (dateStr.length > 16) {
            val nowDateStr = nowStringDateShort
            val date = dateStr.substring(0, 10)
            when {
                getDaySpace(nowDateStr, date) == 0L -> // 当天显示HH:mm即可
                    dateStr.substring(11, 16)
                getDaySpace(nowDateStr, date) == 1L -> "昨天"
                else -> date
            }
        } else {
            dateStr
        }
    }

    /**
     * 根据用户传入的时间表示格式，返回当前时间的格式 如果是yyyyMMdd，注意字母y不能大写。
     *
     * @param format yyyyMMddhhmmss
     * @return
     */
    fun getUserDate(format: String): String {
        val currentTime = Date()
        val formatter = SimpleDateFormat(format, Locale.getDefault())
        return formatter.format(currentTime)
    }

    /**
     * 得到二个日期间的间隔天数
     *
     * @param startDateStr 开始时间 yyyy-MM-dd
     * @param endDateStr 结束时间 yyyy-MM-dd
     *
     * @return 间格的天数（null表示时间格式不正确）
     */
    fun getTwoDay(startDateStr: String, endDateStr: String): Int? {
        val day: Int
        try {
            val startDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(startDateStr)
            val endDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(endDateStr)
            day = ((endDate.time - startDate.time) / (24 * 60 * 60 * 1000)).toInt()
        } catch (e: Exception) {
            return null
        }

        return day
    }

    /**
     * 获取某年某月是否含某一天如果含则返回此天不含则返回当月的最后一天
     *
     * @param year  某年
     * @param month 某月
     * @param day   检测所含的天
     * @return 获取某年某月是否含某一天如果含则返回此天不含则返回当月的最后一天
     */
    fun getCurrentDayInEveryMonth(year: Int, month: Int, day: Int): Int {
        return try {
            var endDateOfMonth = getEndDateOfMonth("$year-$month-$day")
            endDateOfMonth =
                endDateOfMonth.substring(endDateOfMonth.length - 2, endDateOfMonth.length)
            if (day > 0 && day < Integer.parseInt(endDateOfMonth)) {
                day
            } else {
                Integer.parseInt(endDateOfMonth)
            }
        } catch (e: NumberFormatException) {
            e.printStackTrace()
            1
        }

    }

    /**
     * 得到一个时间延后或前移几天的时间,nowdate(yyyy-mm-dd)为时间,delay为前移或后延的天数
     */
    fun getNextDay(nowDate: String, delay: String): String {
        return try {
            val mDate: String
            val d = strToDateShort(nowDate)
            val myTime = d.time / 1000 + Integer.parseInt(delay) * 24 * 60 * 60
            d.time = myTime * 1000
            mDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(d)
            mDate
        } catch (e: Exception) {
            ""
        }

    }

    /**
     * 功能：<br></br> 距离现在几天的时间是多少
     * 获得一个时间字符串，格式为：yyyy-MM-dd HH:mm:ss
     * day  如果为整数，表示未来时间
     * 如果为负数，表示过去时间
     *
     * @author Tony
     * @version 2016年11月29日 上午11:02:56 <br></br>
     */
    fun getFromNow(day: Int): String {
        val date = Date()
        val dateTime = date.time / 1000 + day * 24 * 60 * 60
        date.time = dateTime * 1000
        return SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(date)
    }

    /**
     * 判断是否润年
     *
     * @param ddate
     * @return
     */
    fun isLeapYear(ddate: String): Boolean {
        val d = strToDateShort(ddate)
        val gc = Calendar.getInstance() as GregorianCalendar
        gc.time = d
        val year = gc.get(Calendar.YEAR)
        return year % 400 == 0 || year % 4 == 0 && year % 100 != 0
    }

    /**
     * 返回当前日期所在周或所在周后weekOffSet周的日期集合
     *
     * @param weekOffSet 周偏移，上周为-1，本周为0，下周为1，以此类推
     */
    fun getWeekList(weekOffSet: Int): ArrayList<String> {
        val dateList: ArrayList<String> = ArrayList()
        // Locale.FRANCE是由于法国第一天是周一最后一天是周日
        val calendar = Calendar.getInstance(Locale.FRANCE)
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        calendar.time = Date()
        calendar.add(Calendar.WEEK_OF_YEAR, weekOffSet)
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        dateList.add(format.format(calendar.time))
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY)
        dateList.add(format.format(calendar.time))
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY)
        dateList.add(format.format(calendar.time))
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY)
        dateList.add(format.format(calendar.time))
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY)
        dateList.add(format.format(calendar.time))
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY)
        dateList.add(format.format(calendar.time))
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
        dateList.add(format.format(calendar.time))
        return dateList
    }

    /**
     * 获取yyyy-MM-dd类型的时间字符串在日历上显示的日期（如2017-08-02显示为2）
     *
     * @param dateStr yyyy-MM-dd类型的时间字符串
     */
    fun getDayInCalendar(dateStr: String): String {
        return try {
            val day = dateStr.substring(dateStr.length - 2, dateStr.length)
            if (day.substring(0, 1) == "0") day.substring(1, 2) else day
        } catch (e: Exception) {
            ""
        }
    }

    /**
     * 根据一个日期，返回是星期几的字符串
     *
     * @param date 日期 yyyy-MM-dd
     * @return 对应的星期几
     */
    fun getWeekSimpleDateFormat(date: String): String {
        // 再转换为时间
        val d = strToDateShort(date)
        val c = Calendar.getInstance()
        c.time = d
        return SimpleDateFormat("EEEE", Locale.getDefault()).format(c.time)
    }

    fun getWeekStr(sdate: String): String {
        var str: String
        str = getWeekSimpleDateFormat(sdate)
        when (str) {
            "1" -> str = "星期日"
            "2" -> str = "星期一"
            "3" -> str = "星期二"
            "4" -> str = "星期三"
            "5" -> str = "星期四"
            "6" -> str = "星期五"
            "7" -> str = "星期六"
        }
        return str
    }

    /**
     * 获取上个月的今天
     *
     * @return yyyy-MM-dd
     */
    fun getLastMonthDayDateString(): String {
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1)  // 设置为上个月
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.time)
    }
}