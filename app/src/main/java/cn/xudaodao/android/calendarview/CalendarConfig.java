package cn.xudaodao.android.calendarview;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chaohui.yang on 2016/3/19.
 */
public class CalendarConfig {
    /**
     * 节假日
     */
    public static Map<String, String> HOLIDAYS;

    static {
        HOLIDAYS = new HashMap<>();
        HOLIDAYS.put("2016-04-04", "清明节");
        HOLIDAYS.put("2016-05-01", "劳动节");
        HOLIDAYS.put("2016-06-09", "端午节");
        HOLIDAYS.put("2016-07-01", "建党节");
        HOLIDAYS.put("2016-08-01", "建军节");
        HOLIDAYS.put("2016-08-09", "七夕节");
        HOLIDAYS.put("2016-09-10", "教师节");
        HOLIDAYS.put("2016-09-15", "中秋节");
        HOLIDAYS.put("2016-10-01", "国庆节");
        HOLIDAYS.put("2016-10-09", "重阳节");
        HOLIDAYS.put("2016-12-21", "冬至");
        HOLIDAYS.put("2017-01-01", "元旦");
        HOLIDAYS.put("2017-01-27", "除夕");
        HOLIDAYS.put("2017-01-28", "春节");
        HOLIDAYS.put("2017-02-11", "元宵节");
        HOLIDAYS.put("2017-02-14", "情人节");
        HOLIDAYS.put("2017-03-08", "妇女节");
    }

    /**
     * 休
     */
    public static Map<String, String> RESTDAYS;

    static {
        RESTDAYS = new HashMap<>();
        RESTDAYS.put("2016-04-02", "");
        RESTDAYS.put("2016-04-03", "");
        RESTDAYS.put("2016-04-04", "");
        RESTDAYS.put("2016-04-30", "");
        RESTDAYS.put("2016-05-01", "");
        RESTDAYS.put("2016-05-02", "");
        RESTDAYS.put("2016-06-09", "");
        RESTDAYS.put("2016-06-10", "");
        RESTDAYS.put("2016-06-11", "");
        RESTDAYS.put("2016-09-15", "");
        RESTDAYS.put("2016-09-16", "");
        RESTDAYS.put("2016-09-17", "");
        RESTDAYS.put("2016-10-01", "");
        RESTDAYS.put("2016-10-02", "");
        RESTDAYS.put("2016-10-03", "");
        RESTDAYS.put("2016-10-04", "");
        RESTDAYS.put("2016-10-05", "");
        RESTDAYS.put("2016-10-06", "");
        RESTDAYS.put("2016-10-07", "");
    }

    /**
     * 班
     */
    public static Map<String, String> WORKDAYS;

    static {
        WORKDAYS = new HashMap<>();
        WORKDAYS.put("2016-06-12", "");
        WORKDAYS.put("2016-09-18", "");
        WORKDAYS.put("2016-10-08", "");
        WORKDAYS.put("2016-10-09", "");
    }
}
