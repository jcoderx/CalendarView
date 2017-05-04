package cn.xudaodao.android.calendarview;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Lunar calendar in 200 years from 1900.
 *
 * @author Vincent Cheung
 * @since Aug. 25, 2015
 */
public final class Lunar {
    /**
     * Lunar information in 200 years from 1900.
     * <p/>
     * | 0 - 11(bit) | 12 - 15(bit) |
     * month      leap month
     * If last 4bit is 1111 or 0000 means no leap month.
     * If the last 4bit in next data is 1111, the days of leap month is 30 days,
     * otherwise, the days of leap month is 29days.
     */
    private static final int[] LUNR_INFO = {
            0x4bd8, 0x4ae0, 0xa570, 0x54d5, 0xd260, 0xd950, 0x5554, 0x56af,
            0x9ad0, 0x55d2, 0x4ae0, 0xa5b6, 0xa4d0, 0xd250, 0xd295, 0xb54f,
            0xd6a0, 0xada2, 0x95b0, 0x4977, 0x497f, 0xa4b0, 0xb4b5, 0x6a50,
            0x6d40, 0xab54, 0x2b6f, 0x9570, 0x52f2, 0x4970, 0x6566, 0xd4a0,
            0xea50, 0x6a95, 0x5adf, 0x2b60, 0x86e3, 0x92ef, 0xc8d7, 0xc95f,
            0xd4a0, 0xd8a6, 0xb55f, 0x56a0, 0xa5b4, 0x25df, 0x92d0, 0xd2b2,
            0xa950, 0xb557, 0x6ca0, 0xb550, 0x5355, 0x4daf, 0xa5b0, 0x4573,
            0x52bf, 0xa9a8, 0xe950, 0x6aa0, 0xaea6, 0xab50, 0x4b60, 0xaae4,
            0xa570, 0x5260, 0xf263, 0xd950, 0x5b57, 0x56a0, 0x96d0, 0x4dd5,
            0x4ad0, 0xa4d0, 0xd4d4, 0xd250, 0xd558, 0xb540, 0xb6a0, 0x95a6,
            0x95bf, 0x49b0, 0xa974, 0xa4b0, 0xb27a, 0x6a50, 0x6d40, 0xaf46,
            0xab60, 0x9570, 0x4af5, 0x4970, 0x64b0, 0x74a3, 0xea50, 0x6b58,
            0x5ac0, 0xab60, 0x96d5, 0x92e0, 0xc960, 0xd954, 0xd4a0, 0xda50,
            0x7552, 0x56a0, 0xabb7, 0x25d0, 0x92d0, 0xcab5, 0xa950, 0xb4a0,
            0xbaa4, 0xad50, 0x55d9, 0x4ba0, 0xa5b0, 0x5176, 0x52bf, 0xa930,
            0x7954, 0x6aa0, 0xad50, 0x5b52, 0x4b60, 0xa6e6, 0xa4e0, 0xd260,
            0xea65, 0xd530, 0x5aa0, 0x76a3, 0x96d0, 0x4afb, 0x4ad0, 0xa4d0,
            0xd0b6, 0xd25f, 0xd520, 0xdd45, 0xb5a0, 0x56d0, 0x55b2, 0x49b0,
            0xa577, 0xa4b0, 0xaa50, 0xb255, 0x6d2f, 0xada0, 0x4b63, 0x937f,
            0x49f8, 0x4970, 0x64b0, 0x68a6, 0xea5f, 0x6b20, 0xa6c4, 0xaaef,
            0x92e0, 0xd2e3, 0xc960, 0xd557, 0xd4a0, 0xda50, 0x5d55, 0x56a0,
            0xa6d0, 0x55d4, 0x52d0, 0xa9b8, 0xa950, 0xb4a0, 0xb6a6, 0xad50,
            0x55a0, 0xaba4, 0xa5b0, 0x52b0, 0xb273, 0x6930, 0x7337, 0x6aa0,
            0xad50, 0x4b55, 0x4b6f, 0xa570, 0x54e4, 0xd260, 0xe968, 0xd520,
            0xdaa0, 0x6aa6, 0x56df, 0x4ae0, 0xa9d4, 0xa4d0, 0xd150, 0xf252, 0xd520
    };

    /* lunar string used in month and day */
    private static final String[] LUNAR_STRING = {
            "零", "一", "二", "三", "四", "五", "六", "七", "八", "九"
    };

    /* special lunar string */
    private static final String[] LUNAR_SPEC_STRING = {
            "初", "十", "廿", "卅", "正", "冬", "腊", "闰"
    };

    private Calendar mSolar;
    private int mLunarYear;
    private int mLunarMonth;
    private int mLunarDay;
    private boolean mIsLeap;

    /**
     * The default constructor of lunar, create an empty Lunar, don't
     * forget to use {@link #setTimeInMillis}.
     */
    public Lunar() {

    }

    /**
     * The constructor of Lunar calendar.
     *
     * @param millisec millisecond
     */
    public Lunar(long millisec) {
        init(millisec);
    }

    /* init lunar calendar with millisecond */
    private void init(long millisec) {
        mSolar = Calendar.getInstance();
        mSolar.setTimeInMillis(millisec);
        mLunarYear = 1900;

        Calendar baseDate = new GregorianCalendar(1900, 0, 31);
        long offset = (millisec - baseDate.getTimeInMillis()) / 86400000;

        int daysInLunarYear = getLunarYearDays(mLunarYear);
        /* get current lunar year */
        while (mLunarYear < 2100 && offset >= daysInLunarYear) {
            offset -= daysInLunarYear;
            daysInLunarYear = getLunarYearDays(++mLunarYear);
        }

		/* get current lunar month */
        int lunarMonth = 1;
        int leapMonth = getLunarLeapMonth(mLunarYear);
        boolean leapDec = false;
        boolean isLeap = false;
        int daysInLunarMonth = 0;

		/* to get lunar year, month and day */
        while (lunarMonth < 13 && offset > 0) {
            if (isLeap && leapDec) {
                daysInLunarMonth = getLunarLeapDays(mLunarYear);
                leapDec = false;
            } else {
                daysInLunarMonth = getLunarMonthDays(mLunarYear, lunarMonth);
            }

            if (offset < daysInLunarMonth) {
                break;
            }
            offset -= daysInLunarMonth;

            if (leapMonth == lunarMonth && !isLeap) {
                leapDec = true;
                isLeap = true;
            } else {
                lunarMonth++;
            }
        }

        mLunarMonth = lunarMonth;
        mIsLeap = (lunarMonth == leapMonth && isLeap);
        mLunarDay = (int) offset + 1;
    }

    /**
     * Get the leap month in lunar year.
     *
     * @param lunarYear lunar year
     * @return the month in specified lunar year, otherwise return 0
     */
    private int getLunarLeapMonth(int lunarYear) {
        int leapMonth = LUNR_INFO[lunarYear - 1900] & 0xf;
        return leapMonth == 0xf ? 0 : leapMonth;
    }

    /**
     * Get total days of leap month in lunar year.
     *
     * @param lunarYear lunar year
     * @return total days of leap month, otherwise return 0 if no leap month.
     */
    private int getLunarLeapDays(int lunarYear) {
        return getLunarLeapMonth(lunarYear) > 0 ?
                ((LUNR_INFO[lunarYear - 1899] & 0xf) == 0xf ? 30 : 29) : 0;
    }

    /**
     * Get total days of lunar year.
     *
     * @param lunarYear lunar year
     * @return total days of lunar year
     */
    private int getLunarYearDays(int lunarYear) {
        /* lunar year has (12 * 29 =) 348 days at least */
        int totalDays = 348;
        for (int i = 0x8000; i > 0x8; i >>= 1) {
            totalDays += ((LUNR_INFO[lunarYear - 1900] & i) != 0) ? 1 : 0;
        }

        return totalDays + getLunarLeapDays(lunarYear);
    }

    /**
     * Get total days of lunar month in normal case.
     *
     * @param lunarYear  lunar year
     * @param lunarMonth lunar month
     * @return total days
     */
    private int getLunarMonthDays(int lunarYear, int lunarMonth) {
        return ((LUNR_INFO[lunarYear - 1900] & (0x10000 >> lunarMonth)) != 0) ? 30 : 29;
    }

    /**
     * Set time in millisecond.
     *
     * @param millisecond millisecond to set
     */
    public void setTimeInMillis(long millisecond) {
        init(millisecond);
    }

    /**
     * Get lunar month in Chinese according to lunar month numeric.
     *
     * @param lunarMonth lunar month numeric
     * @param isLeap     is current month leap or not
     * @return lunar month in Chinese
     */
    public String getLunarMonth(int lunarMonth, boolean isLeap) {
        String lunarMonthStr = "";
        if (lunarMonth == 1) {
            lunarMonthStr = LUNAR_SPEC_STRING[4];
        } else {
            switch (lunarMonth) {
                case 10:
                    lunarMonthStr = LUNAR_SPEC_STRING[1];
                    break;

                case 11:
                    lunarMonthStr = LUNAR_SPEC_STRING[5];
                    break;

                case 12:
                    lunarMonthStr = LUNAR_SPEC_STRING[6];
                    break;

                default:
                    lunarMonthStr += LUNAR_STRING[lunarMonth % 10];
                    break;
            }
        }

        return (isLeap ? "闰" : "") + lunarMonthStr;
    }

    /**
     * Get month in Chinese of lunar calendar.
     *
     * @return month in lunar calendar
     */
    public String getLunarMonth() {
        return getLunarMonth(mLunarMonth, mIsLeap);
    }

    /**
     * Get lunar day in Chinese according to lunar day numeric.
     *
     * @param lunarDay lunar day numeric
     * @return lunar day in Chinese
     */
    public String getLunarDay(int lunarDay) {
        if (lunarDay < 1 || lunarDay > 30) {
            return "";
        }

        int decade = lunarDay / 10;
        int unit = lunarDay % 10;
        String decadeStr = LUNAR_SPEC_STRING[decade];
        String unitStr = LUNAR_STRING[unit];

        if (lunarDay < 11) {
            decadeStr = LUNAR_SPEC_STRING[0];
        }

        if (unit == 0) {
            unitStr = LUNAR_SPEC_STRING[1];
        }

        return decadeStr + unitStr;
    }

    /**
     * Get day in lunar calendar.
     *
     * @return day in lunar calendar
     */
    public String getLunarDay() {
        return getLunarDay(mLunarDay);
    }

    public String getChineseDay() {
        int lunarDay = mLunarDay;
        if (lunarDay < 1 || lunarDay > 30) {
            return "";
        }

        int decade = lunarDay / 10;
        int unit = lunarDay % 10;

        if (decade == 0 && unit == 1) {
            return getLunarMonth() + "月";
        }

        String decadeStr = LUNAR_SPEC_STRING[decade];
        String unitStr = LUNAR_STRING[unit];

        if (lunarDay < 11) {
            decadeStr = LUNAR_SPEC_STRING[0];
        }

        if (unit == 0) {
            unitStr = LUNAR_SPEC_STRING[1];
        }

        return decadeStr + unitStr;
    }
}