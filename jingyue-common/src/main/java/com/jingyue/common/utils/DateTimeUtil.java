/**
 * 文件名：DateTimeUtil.java
 * 版权：Copyright 2017-2022 CMCC All Rights Reserved.
 * 描述：时间工具类
 */
package com.jingyue.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 时间工具类
 *
 * @author Hailiang.Chang
 * @version 1.0
 * @date 2018/2/26 22:29
 */
public final class DateTimeUtil {
    public static final int NUM_4 = 4;
    public static final int NUM_6 = 6;
    public static final int NUM_24 = 24;
    public static final int NUM_60 = 60;
    public static final int NUM_1000 = 1000;
    public static final int NUM_3600 = 3600;
    private static final int NUM_12 = 12;
    private static final int NUM_7 = 7;

    private static Logger logger = LoggerFactory.getLogger(DateTimeUtil.class);

    private DateTimeUtil() {
        throw new UnsupportedOperationException("Cannot be instantiated");
    }

    /**
     * 英文简写（默认）如：2017/11/09
     */
    public static final String FORMAT_SHORT = "yyyy-MM-dd";

    /**
     * 英文简写+缩写 20170511
     */
    public static final String FORMAT_SHORT_SIMPLE = "yyyyMMdd";

    /**
     * 格式化时分秒
     */
    public static final String FORMAT_TIME_SIMPLE = "HHmmss";

    /**
     * 取时间的日期
     */
    public static final String FORMAT_DATE_SINGLE = "dd";

    /**
     * 格式化时分秒毫秒
     */
    public static final String FORMAT_TIME_STAMP_SIMPLE = "HHmmssSSS";

    /**
     * 格式化时分秒毫秒
     */
    public static final String FORMAT_TIME_STAMP = "HH:mm:ss.SSS";

    /**
     * 英文全称 如：2017-03-24 23:15:06
     */
    public static final String FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";

    /**
     * 全称无秒 如：2017-08-16 09:17
     */
    public static final String FORMAT_LONG_NO_SECOND = "yyyy-MM-dd HH:mm";

    /**
     * 长名称简写：20170511152202
     */
    public static final String FORMAT_LONG_SIMPLE = "yyyyMMddHHmmss";

    /**
     * 长名称简写年份取两位：170511152202，12位
     */
    public static final String FORMAT_LONG_SIMPLE_YEAR_SHORT = "yyMMddHHmmss";

    /**
     * 时间戳格式
     */
    public static final String FORMAT_TIMESTAMP = "yyyyMMddHHmmssSSS";

    /**
     * 斜杠日期格式
     */
    public static final String FORMAT_LONG_SLASH = "yyyy/MM/dd HH:mm:ss";

    /**
     * 精确到毫秒的完整时间 如：yyyy-MM-dd HH:mm:ss.SSS
     */
    public static final String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * 中文简写 如：2017年03月24日
     */
    public static final String FORMAT_SHORT_CN = "yyyy年MM月dd";

    /**
     * 中文简写 如：2017年03月
     */
    public static final String FORMAT_YEAR_MONTH_CN = "yyyy年MM月";

    /**
     * 中文简写 如：2017年03期
     */
    public static final String FORMAT_YEAR_MONTH_CN_FOR_ACCOUNTING = "yyyy年MM期";

    /**
     * 中文全称 如：2017年03月24日 23时15分06秒
     */
    public static final String FORMAT_LONG_CN = "yyyy年MM月dd日 HH时mm分ss秒";

    /**
     * 精确到毫秒的完整中文时间
     */
    public static final String FORMAT_FULL_CN = "yyyy年MM月dd日 HH时mm分ss秒SSS毫秒";

    /**
     * 接口长时间格式，以T分隔
     */
    public static final String FORMAT_LONG_T = "yyyy-MM-dd'T'HH:mm:ss";

    /**
     * 接口长时间格式，以T分隔，带时区
     */
    public static final String FORMAT_LONG_T_Z = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

    /**
     * 默认时间格式
     */
    public static final String FORMAT_DEFAULT = "EEE MMM dd HH:mm:ss z yyyy";


    /**
     * 一天的毫秒数,MILLI
     */
    public static final long MILLISECOND_OF_DAY = 86400000;


    /**
     * 一天的秒数
     */
    public static final long SECOND_OF_DAY = 86400;


    /**
     * 一小时的秒数
     */
    public static final long SECOND_OF_HOUR = 3600;

    /**
     * 一分钟的秒数
     */
    public static final long SECOND_OF_MIN = 60;

    /**
     * 时分秒进制转换
     */
    public static final long CONVERT_BASE_NUM = 60;

    /**
     * 获取格式
     *
     * @return String
     */
    public static String getDatePattern() {
        return FORMAT_LONG;
    }

    /**
     * 获取当前日期（预设格式）
     *
     * @return String
     */
    public static String getNow() {
        return date2String(new Date());
    }

    /**
     * 获取当前日期（自定义格式）
     *
     * @param format 自定义格式
     * @return String
     */
    public static String getNow(String format) {
        return date2String(new Date(), format);
    }

    /**
     * 格式化目标日期（预设格式）
     *
     * @param date 目标日期
     * @return String
     */
    public static String date2String(Date date) {
        return date2String(date, getDatePattern());
    }

    /**
     * 格式格式化日期（自定义格式）
     *
     * @param date    日期
     * @param pattern 日期格式
     * @return String
     */
    public static String date2String(Date date, String pattern) {

        String returnValue = "";
        if (null == pattern || pattern.length() <= 0) {
            return returnValue;
        }

        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern, Locale.CHINA);
            returnValue = df.format(date);
        }

        return (returnValue);
    }

    /**
     * 格式化字符串（预设格式）
     *
     * @param dateString 日期字符串
     * @return Date
     */
    public static Date string2Date(String dateString) {

        return string2Date(dateString, getDatePattern());
    }

    /**
     * 格式化字符串（自定义格式）
     *
     * @param dateString 日期字符串
     * @param pattern    日期格式
     * @return Date
     */
    public static Date string2Date(String dateString, String pattern) {

        SimpleDateFormat df = new SimpleDateFormat(pattern, Locale.CHINA);

        try {

            return df.parse(dateString);

        } catch (ParseException e) {
            logger.error("error:", e);
        }

        return null;
    }

    /**
     * 在日期上增加数个整月
     *
     * @param date 日期
     * @param n    要增加的月数
     * @return Date
     */
    public static Date addMonth(Date date, int n) {
        Calendar cal = Calendar.getInstance();

        cal.setTime(date);

        cal.add(Calendar.MONTH, n);

        return cal.getTime();
    }

    /**
     * 在日期上增加天数
     *
     * @param date 日期
     * @param n    要增加的天数
     * @return Date
     */
    public static Date addDay(Date date, int n) {

        Calendar cal = Calendar.getInstance();

        cal.setTime(date);

        cal.add(Calendar.DATE, n);

        return cal.getTime();
    }

    /**
     * 在日期上增加分钟
     *
     * @param date 日期
     * @param n    要增加的天数
     * @return Date
     */
    public static Date addMin(Date date, int n) {

        Calendar cal = Calendar.getInstance();

        cal.setTime(date);

        cal.add(Calendar.MINUTE, n);

        return cal.getTime();
    }

    /**
     * 获取时间戳
     *
     * @return String
     */
    public static String getTimeString() {

        SimpleDateFormat df = new SimpleDateFormat(FORMAT_FULL, Locale.CHINA);

        Calendar calendar = Calendar.getInstance();

        return df.format(calendar.getTime());
    }

    /**
     * 获取日期年份
     *
     * @param date 日期
     * @return String
     */
    public static String getYear(Date date) {
        return date2String(date).substring(0, NUM_4);
    }

    /**
     * 按默认格式的字符串距离今天的天数
     *
     * @param date 日期字符串
     * @return int 天数
     */
    public static int countDays(String date) {
        long t = Calendar.getInstance().getTime().getTime();
        Calendar c = Calendar.getInstance();
        c.setTime(string2Date(date));
        long t1 = c.getTime().getTime();
        return (int) (t / NUM_1000 - t1 / NUM_1000) / NUM_3600 / NUM_24;
    }

    /**
     * 按用户格式字符串距离今天的天数
     *
     * @param date   日期字符串
     * @param format 日期格式
     * @return int 天数
     */
    public static int countDays(String date, String format) {

        long t = Calendar.getInstance().getTime().getTime();

        Calendar c = Calendar.getInstance();

        c.setTime(string2Date(date, format));

        long t1 = c.getTime().getTime();

        return (int) (t / NUM_1000 - t1 / NUM_1000) / NUM_3600 / NUM_24;
    }

    /**
     * 根据时间戳获取时间（预设格式）
     *
     * @param date    时间戳
     * @param pattern 预设时间格式
     * @return String
     */
    public static String getDate(String date, String pattern) {

        Date dates = new Date();

        dates.setTime(Long.parseLong(date));

        SimpleDateFormat format = new SimpleDateFormat(pattern);

        return format.format(dates);
    }

    /**
     * 格式化时间 判断一个日期 是否为 今天、昨天
     *
     * @param time 目标时间
     * @return String
     */
    public static String formatDateTime(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if (time == null || "".equals(time)) {
            return "";
        }
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            logger.error("error:", e);
        }

        Calendar current = Calendar.getInstance();

        Calendar today = Calendar.getInstance();    //今天

        today.set(Calendar.YEAR, current.get(Calendar.YEAR));
        today.set(Calendar.MONTH, current.get(Calendar.MONTH));
        today.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH));
        //  Calendar.HOUR——12小时制的小时数 Calendar.HOUR_OF_DAY——24小时制的小时数
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        Calendar yesterday = Calendar.getInstance();    //昨天

        yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));
        yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));
        yesterday.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH) - 1);
        yesterday.set(Calendar.HOUR_OF_DAY, 0);
        yesterday.set(Calendar.MINUTE, 0);
        yesterday.set(Calendar.SECOND, 0);

        current.setTime(date);

        if (current.after(today)) {
            return "Today " + time.split(" ")[1];
        } else if (current.before(today) && current.after(yesterday)) {

            return "Yesterday " + time.split(" ")[1];
        } else {
            int index = time.indexOf(" ");
            return time.substring(0, index);
        }
    }

    /**
     * <p>获取某天的初始时间点</p>
     *
     * @param date 目标
     * @return Date
     */
    public static Date dateInitialize(Date date) {

        if (date == null) {

            return null;
        }

        String date2String = date2String(date, DateTimeUtil.FORMAT_SHORT);

        return string2Date(date2String, DateTimeUtil.FORMAT_SHORT);

    }

    /**
     * <p>获取某天的最后一个时间点[23:59:59]</p>
     *
     * @param date 目标
     * @return Date
     */
    public static Date getLastOfDate(Date date) {

        if (date == null) {

            return null;
        }

        Date initializeDate = dateInitialize(date);

        long timeMis = initializeDate.getTime();

        return new Date(timeMis + DateTimeUtil.MILLISECOND_OF_DAY - 1);
    }

    /**
     * <p>获取指定日期所处的月份</p>
     *
     * @param date 目标
     * @return int
     */
    public static int getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int m = cal.get(Calendar.MONTH) + 1;
        return m;

    }

    /**
     * <p>获取两个日期相差的天数</p>
     *
     * @param day1 日期1
     * @param day2 日期2
     * @return long
     */
    public static long getDays(Date day1, Date day2) {

        return (day1.getTime() - day2.getTime()) / (NUM_24 * NUM_60 * NUM_60 * NUM_1000);

    }

    /**
     * @param str param
     * @return java.lang.String
     * @author hongan.dong
     * @description 日期字符串格式化
     * @date 2018/3/31 18:18
     */
    public static String formatString(String str) {

        Date date = DateTimeUtil.string2Date(str, FORMAT_SHORT);
        return DateTimeUtil.date2String(date, FORMAT_SHORT_SIMPLE);

    }

    /**
     * @param startDate param
     * @param endDate   param
     * @return boolean
     * @author hongan.dong
     * @description 校验时间是否在某个区间
     * @date 2018/3/31 18:19
     */
    public static boolean checkRequestDate(Date startDate, Date endDate) {

        long curTime = System.currentTimeMillis();

        if (curTime < startDate.getTime() || curTime > endDate.getTime()) {

            return false;

        }

        return true;

    }

    /**
     * @param hour        param
     * @param second      param
     * @param minute      param
     * @param milltsecond param
     * @return java.util.Date
     * @author hongan.dong
     * @description 修改当前日期的时间
     * @date 2018/3/31 18:20
     */
    public static Date updateCurDateTime(int hour, int second, int minute, int milltsecond) {

        Calendar calStart = Calendar.getInstance();
        calStart.set(Calendar.HOUR_OF_DAY, hour);
        calStart.set(Calendar.SECOND, second);
        calStart.set(Calendar.MINUTE, minute);
        calStart.set(Calendar.MILLISECOND, milltsecond);

        return calStart.getTime();

    }

    /**
     * <p>判断是否是周末</p>
     *
     * @param calendar 日期
     * @return boolean
     */
    public static boolean isWeekend(Calendar calendar) {

        int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return (week == NUM_6 || week == 0);

    }

    /**
     * 获取月份起始日期
     *
     * @param date 日期
     * @return String
     * @throws ParseException 转换异常
     */
    public static String getMinMonthDate(String date) throws ParseException {

        if (null == date || date.length() <= 0) {
            return null;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(DateTimeUtil.FORMAT_SHORT);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFormat.parse(date));
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));

        return dateFormat.format(calendar.getTime());
    }

    /**
     * 获取月份最后日期
     *
     * @param date 日期参数
     * @return String
     * @throws ParseException 转换异常
     */
    public static String getMaxMonthDate(String date) throws ParseException {

        if (null == date || date.length() <= 0) {
            return null;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(DateTimeUtil.FORMAT_SHORT);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFormat.parse(date));
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

        return dateFormat.format(calendar.getTime());
    }

    /**
     * @param dateStr 默认日期格式字符串
     * @param format  转换格式
     * @return String
     * @author hongan.dong
     * @description 日期转换
     * @date 2018/3/27 20:13
     */
    public static String parseDate(String dateStr, String format) {
        SimpleDateFormat sdf1 = new SimpleDateFormat(FORMAT_DEFAULT, Locale.ENGLISH);

        SimpleDateFormat sdf2 = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = sdf1.parse(dateStr);
        } catch (ParseException e) {
            logger.error("error:", e);
        }
        return sdf2.format(date);
    }

    /**
     * @param dateStr 默认日期格式字符串
     * @param format  转换格式
     * @return String
     * @author yihao.xing
     * @description 字符串转换日期
     * @date 2018/3/27 20:13
     */
    public static Date stringParseDate(String dateStr, String format) {
        format = StringUtils.isEmpty(format) ? FORMAT_DEFAULT : format;
        SimpleDateFormat sdf2 = new SimpleDateFormat(format, Locale.ENGLISH);
        Date date = null;
        try {
            date = sdf2.parse(dateStr);
        } catch (ParseException e) {
            logger.error("error:", e);
        }
        return date;
    }

    /**
     * 日期格式化(Date-->Date)
     *
     * @param date   日期参数
     * @param format 转换格式
     * @return date
     * @author yihao.xing
     */
    public static Date formatDate(Date date, String format) {
        format = StringUtils.isEmpty(format) ? FORMAT_DEFAULT : format;
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
        try {
            String dateStr = dateFormat.format(date);
            date = dateFormat.parse(dateStr);
        } catch (ParseException e) {
            logger.error("error:", e);
        }
        return date;
    }

    /**
     * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
     *
     * @param nowTime   当前时间
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return boolean
     */
    public static boolean dateRange(Date nowTime, Date startTime, Date endTime) {
        nowTime = formatDate(nowTime, FORMAT_LONG);
        startTime = formatDate(startTime, FORMAT_LONG);
        endTime = formatDate(endTime, FORMAT_LONG);
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }

        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        }
        return false;
    }

    /**
     * 将Date类转换为XMLGregorianCalendar
     *
     * @param date 参数
     * @return javax.xml.datatype.XMLGregorianCalendar
     */
    public static XMLGregorianCalendar dateToXmlDate(Date date) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        DatatypeFactory dtf = null;
        try {
            dtf = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException e) {
            logger.error(e.toString());
        }
        XMLGregorianCalendar dateType = dtf.newXMLGregorianCalendar();
        dateType.setYear(cal.get(Calendar.YEAR));
        //由于Calendar.MONTH取值范围为0~11,需要加1
        dateType.setMonth(cal.get(Calendar.MONTH) + 1);
        dateType.setDay(cal.get(Calendar.DAY_OF_MONTH));
        dateType.setHour(cal.get(Calendar.HOUR_OF_DAY));
        dateType.setMinute(cal.get(Calendar.MINUTE));
        dateType.setSecond(cal.get(Calendar.SECOND));
        return dateType;
    }


    /**
     * 将XMLGregorianCalendar转换为Date
     *
     * @param calDate 参数
     * @return java.util.Date
     */
    public static Date xmlDate2Date(XMLGregorianCalendar calDate) {
        if (calDate == null) {
            return null;
        }
        return calDate.toGregorianCalendar().getTime();
    }

    /**
     * 获取两个日期之间相差的月份数
     *
     * @param start 起始日期
     * @param end   结束日期
     * @return 差数
     */
    public static int getMonth(Date start, Date end) {
        //判断开始是否大于截止日期
        if (start.after(end)) {
            Date t = start;
            start = end;
            end = t;
        }
        //截止日期+1，用于后续区分月底和月初的计算
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(start);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(end);
        Calendar temp = Calendar.getInstance();
        temp.setTime(end);
        temp.add(Calendar.DATE, 1);

        //相隔年数=跨年计算
        int year = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
        //相隔月份
        int month = endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);

        /**
         * 1. 起始、截止日期（加1后的日期）都是1号，则额外加1月
         * 2. （起始 != 1 && 截止 = 1）或者（起始 = 1 && 截止 != 1）时，则按照正常计算
         * 3. 起始、截止日期都处于月中，则判断是否满足一个月，进行计算
         * 根据需求，暂时不需要这部分逻辑
         */
        return year * NUM_12 + month + 1;
    }

    /**
     * 获取下一个星期日的日期
     *
     * @param date 参数
     * @return java.util.Date
     */
    public static Date getNextSunday(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        if (week > 1) {
            calendar.add(Calendar.DAY_OF_MONTH, -(week - 1) + NUM_7);
        } else {
            calendar.add(Calendar.DAY_OF_MONTH, 1 - week + NUM_7);
        }
        return calendar.getTime();
    }

    /**
     * 获取该月最后一天
     *
     * @param date 日期
     * @return 最后一天日期
     */
    public static Date getLastDayOfMonth(Date date) {

        LocalDateTime localDateTime =
                LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());

        LocalDateTime endDayOfMonth =
                localDateTime.with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX);

        Date endDay = Date.from(endDayOfMonth.atZone(ZoneId.systemDefault()).toInstant());

        return endDay;
    }

    /**
     * 获取该月第一天
     *
     * @param date 日期
     * @return 第一天
     */
    public static Date getFirstDayOfMonth(Date date) {

        LocalDateTime localDateTime =
                LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());

        LocalDateTime firstDayOfMonth =
                localDateTime.with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN);

        Date firstDay = Date.from(firstDayOfMonth.atZone(ZoneId.systemDefault()).toInstant());

        return firstDay;
    }

    /**
     * 计算两日期的月份差
     * 结束日<=开始日（不算月数），直接取 结束月-开始月；
     * 否则  取 结束月-开始月+1
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 月份差
     */
    public static int getMonthSum(Date startDate, Date endDate) {
        int monthSum = 0;
        if (null == startDate || endDate == null) {
            return monthSum;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        int startMonth = calendar.get(Calendar.MONTH) + 1;
        int startDateOfMoth = calendar.get(Calendar.DAY_OF_MONTH);
        int startYear = calendar.get(Calendar.YEAR);
        calendar.setTime(endDate);
        int endMonth = calendar.get(Calendar.MONTH) + 1;
        int endDateOfMoth = calendar.get(Calendar.DAY_OF_MONTH);
        int endYear = calendar.get(Calendar.YEAR);
        if (startDateOfMoth >= endDateOfMoth) {
            monthSum = Math.abs(endMonth - startMonth);
        } else {
            monthSum = Math.abs(endMonth - startMonth) + 1;
        }
        monthSum = monthSum + Math.abs(endYear - startYear) * NUM_12;
        return monthSum;
    }
}
