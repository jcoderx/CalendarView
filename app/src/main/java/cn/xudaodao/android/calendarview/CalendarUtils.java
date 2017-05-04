package cn.xudaodao.android.calendarview;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by chaohui.yang on 2016/3/19.
 */
public class CalendarUtils {
    public final static int DAY_COUNT_PER_ROW = 7;

    public final static int MONTHS_IN_YEAR = 12;
    public final static String PATTERN = "yyyy-MM-dd";

    /**
     * 获取当前日期
     *
     * @return
     */
    public static Calendar getCalendar() {
        //TODO 根据开发实际策略返回
        return Calendar.getInstance();
    }

    /**
     * 月份title
     *
     * @param year
     * @param month
     * @return 如2016年03月
     */
    public static String getMonthTitle(int year, int month) {
        Calendar calendar = getCalendar();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return formatCalendar(calendar, "yyyy年MM月");
    }

    public static String getYearMonthDay(int year, int month, int day) {
        Calendar calendar = getCalendar();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return formatCalendar(calendar, PATTERN);
    }

    public static Calendar getCalendar(int year, int month, int day) {
        Calendar calendar = getCalendar();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    public static String formatCalendar(Calendar calendar, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(calendar.getTime());
    }

    public static boolean equalsCalendar(Calendar calendar1, Calendar calendar2) {
        if (calendar1 == null || calendar2 == null) {
            return false;
        }
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) && calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)
                && calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH);
    }

    public static boolean betweenStartEndDay(Calendar calendar, Calendar startCalendar, Calendar endCalendar) {
        if (calendar == null || startCalendar == null || endCalendar == null) {
            return false;
        }
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        startCalendar.set(Calendar.HOUR_OF_DAY, 0);
        startCalendar.set(Calendar.MINUTE, 0);
        startCalendar.set(Calendar.SECOND, 0);
        startCalendar.set(Calendar.MILLISECOND, 0);

        endCalendar.set(Calendar.HOUR_OF_DAY, 0);
        endCalendar.set(Calendar.MINUTE, 0);
        endCalendar.set(Calendar.SECOND, 0);
        endCalendar.set(Calendar.MILLISECOND, 0);
        return calendar.compareTo(startCalendar) > 0 && calendar.compareTo(endCalendar) < 0;
    }

    /**
     * 禁止选择的日期
     *
     * @param calendar
     * @param availableStartCalendar
     * @param availableEndCalendar
     * @return
     */
    public static boolean isInvalidDay(Calendar calendar, Calendar availableStartCalendar, Calendar availableEndCalendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        availableStartCalendar.set(Calendar.HOUR_OF_DAY, 0);
        availableStartCalendar.set(Calendar.MINUTE, 0);
        availableStartCalendar.set(Calendar.SECOND, 0);
        availableStartCalendar.set(Calendar.MILLISECOND, 0);

        availableEndCalendar.set(Calendar.HOUR_OF_DAY, 0);
        availableEndCalendar.set(Calendar.MINUTE, 0);
        availableEndCalendar.set(Calendar.SECOND, 0);
        availableEndCalendar.set(Calendar.MILLISECOND, 0);

        return calendar.compareTo(availableStartCalendar) < 0 || calendar.compareTo(availableEndCalendar) > 0;
    }

    /**
     * 两个日期相差的月份数
     *
     * @param startCalendar
     * @param endCalendar
     * @return
     */
    public static int getMonthCountBetween2Calendar(Calendar startCalendar, Calendar endCalendar) {
        int startYear = startCalendar.get(Calendar.YEAR);
        int startMonth = startCalendar.get(Calendar.MONTH);
        int endYear = endCalendar.get(Calendar.YEAR);
        int endMonth = endCalendar.get(Calendar.MONTH);
        return (endYear - startYear) * MONTHS_IN_YEAR + (endMonth - startMonth);

    }

    /**
     * 根据年月返回当月的天数
     *
     * @param year
     * @param month
     * @return
     */
    public static int getDaysInMonth(int year, int month) {
        switch (month) {
            case Calendar.JANUARY:
            case Calendar.MARCH:
            case Calendar.MAY:
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.OCTOBER:
            case Calendar.DECEMBER:
                return 31;
            case Calendar.APRIL:
            case Calendar.JUNE:
            case Calendar.SEPTEMBER:
            case Calendar.NOVEMBER:
                return 30;
            case Calendar.FEBRUARY:
                return ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) ? 29 : 28;
            default:
                throw new IllegalArgumentException("Invalid Month");
        }
    }

    /**
     * 是否节假日，如果是返回节假日名称，否则返回null
     *
     * @param date yyyy-MM-dd
     * @return
     */
    public static String getHoliday(String date) {
        return CalendarConfig.HOLIDAYS.get(date);
    }

    public static String getHoliday(Calendar calendar) {
        return getHoliday(formatCalendar(calendar, PATTERN));
    }

    /**
     * 是否休息日
     *
     * @param date
     * @return
     */
    public static boolean isRestday(String date) {
        for (String s : CalendarConfig_.RESTDAYS) {
            if (date.equals(s)) {
                return true;
            }
        }
        return false;
        //return CalendarConfig.RESTDAYS.containsKey(date);
    }

    public static boolean isRestday(Calendar calendar) {
        return isRestday(formatCalendar(calendar, PATTERN));
    }

    /**
     * 是否工作日("班")
     *
     * @param date
     * @return
     */
    public static boolean isWorkday(String date) {
        for (String s : CalendarConfig_.WORKDAYS) {
            if (date.equals(s)) {
                return true;
            }
        }
        return false;
        //return CalendarConfig.WORKDAYS.containsKey(date);
    }

    public static boolean isWorkday(Calendar calendar) {
        return isWorkday(formatCalendar(calendar, PATTERN));
    }

    public static int findDayOffset(Calendar calendar) {
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        //由于时区的不同，返回的dayOfWeek和firstDayOfWeek是不一样的。
        int dayOfWeekStart = calendar.get(Calendar.DAY_OF_WEEK);
        int weekStart = calendar.getFirstDayOfWeek();
        //偏移量等于当月第一天与一周第一天的差
        return (dayOfWeekStart < weekStart) ? (dayOfWeekStart + DAY_COUNT_PER_ROW) : (dayOfWeekStart) - weekStart;
    }

    public static int calculateRowCount(Calendar calendar) {
        int offset = findDayOffset(calendar);
        int dayCount = getDaysInMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH));
        int dividend = (offset + dayCount) / DAY_COUNT_PER_ROW;
        int remainder = (offset + dayCount) % DAY_COUNT_PER_ROW;
        return (dividend + (remainder > 0 ? 1 : 0));
    }
}
