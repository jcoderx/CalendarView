package cn.xudaodao.android.calendarview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.Calendar;
import java.util.Map;

/**
 * 月份组件
 */
public class WeekView extends View {
    public static final String TODAY = "今天";
    public static final String RESTDAY = "休";
    public static final String WORKDAY = "班";
    private int mYear;
    private int mMonth;
    private WeekViewEntity mWeekViewEntity;
    private int mDayCount;

    public void setCalendarParams(DatePickerView.PickType pickType, Calendar availableStartCalendar, Calendar availableEndCalendar, Calendar startCalendar, Calendar endCalendar, String startDateMarkText, String endDateMarkText, WeekViewEntity weekViewEntity) {
        mPickType = pickType;
        mWeekViewEntity = weekViewEntity;
        mYear = mWeekViewEntity.startDrawCalendar.get(Calendar.YEAR);
        mMonth = mWeekViewEntity.startDrawCalendar.get(Calendar.MONTH);
        mAvailableStartCalendar = availableStartCalendar;
        mAvailableEndCalendar = availableEndCalendar;
        mStartCalendar = startCalendar;
        mEndCalendar = endCalendar;
        mStartDateMarkText = startDateMarkText;
        mEndDateMarkText = endDateMarkText;
        mDayCount = CalendarUtils.getDaysInMonth(mYear, mMonth);

        invalidate();
    }

    private Calendar mCheckedCalendar;
    private String mCheckedDateMarkText;

    public void setCalendarParams(DatePickerView.PickType pickType, boolean showLunar, Calendar availableStartCalendar, Calendar availableEndCalendar, Calendar checkedCalendar, String checkedDateMarkText, WeekViewEntity weekViewEntity, Map<String, ExtendEntity> extendMap) {
        mPickType = pickType;
        mShowLunar = showLunar;
        mWeekViewEntity = weekViewEntity;
        mYear = mWeekViewEntity.startDrawCalendar.get(Calendar.YEAR);
        mMonth = mWeekViewEntity.startDrawCalendar.get(Calendar.MONTH);
        mAvailableStartCalendar = availableStartCalendar;
        mAvailableEndCalendar = availableEndCalendar;
        mCheckedCalendar = checkedCalendar;
        mCheckedDateMarkText = checkedDateMarkText;
        mDayCount = CalendarUtils.getDaysInMonth(mYear, mMonth);
        mExtendMap = extendMap;

        invalidate();
    }

    //----------
    /**
     * 日历选择类型
     */
    private DatePickerView.PickType mPickType;
    /**
     * 选择开始日期
     */
    private Calendar mStartCalendar;
    /**
     * 选择结束日期
     */
    private Calendar mEndCalendar;

    private String mStartDateMarkText;
    private String mEndDateMarkText;
    /**
     * 可选范围的开始日期
     */
    private Calendar mAvailableStartCalendar;
    /**
     * 可选范围的结束日期
     */
    private Calendar mAvailableEndCalendar;

    //---------
    /**
     * 禁用时的颜色(包括：日期、节假日、班、休)
     */
    private int mDisableTextColor;
    /**
     * 日期(或显示节假日)正常时的颜色
     */
    private int mDayNormalTextColor;
    /**
     * "今天"的颜色
     */
    private int mTodayTextColor;
    /**
     * 右上角"班"正常时的颜色
     */
    private int mWorkdayNormalTextColor;
    /**
     * 右上角"休"正常时的颜色
     */
    private int mRestdayNormalTextColor;
    /**
     * 扩展字体颜色(酒店的入店、离店；机票的低价日历；火车票的农历)
     */
    private int mExtendNomalTextColor;
    /**
     * 选中开始日期时的颜色(日期、今天、节假日、班、休)
     */
    private int mStartDayTextColor;
    /**
     * 选中日期标记的颜色(入店、离店)
     */
    private int mStartDayMarkTextColor;
    /**
     * 扩展字段字体颜色
     */
    private int mStartDayExtendTextColor;

    private int mStartDayRestWorkDayTextColor;
    /**
     * 选中结束日期时的颜色(日期、今天、节假日、班、休)
     */
    private int mEndDayTextColor;
    private int mEndDayMarkTextColor;
    private int mEndDayExtendTextColor;
    private int mEndDayRestWorkDayTextColor;
    /**
     * 开始结束范围内的颜色(日期、今天、节假日、班、休)
     */
    private int mRangeDayTextColor;
    private int mRangeDayExtendTextColor;
    private int mRangeDayRestWorkDayTextColor;

    private int mBgRangeDayColor;
    /**
     * 月份之间分割线颜色
     */
    private int mMonthDividerColor;

    //TextSize---------------
    /**
     * 日期(或显示节假日)字体大小
     */
    private int mDayTextSize;
    /**
     * 节假日字体大小
     */
    private int mHolidayTextSize;
    /**
     * 扩展字体大小(酒店的入店、离店；机票的低价日历；火车票的农历)
     */
    private int mExtendTextSize;
    /**
     * "休"、"班"的字体大小
     */
    private int mRestWorkDayTextSize;

    private int mStartEndMarkTextSize;


    //单选特有配置---------------------------------
    private int mCheckedDayTextColor;
    private int mCheckedDayExtendTextColor;
    private int mCheckedDayRestWorkDayTextColor;
    private int mCheckedDayMarkTextColor;
    private int mCheckedDayMarkTextSize;
    private int mStartDayHolidayTextColor;
    private int mCheckedDayBgColor;


    private int mRowPadding;
    private int mDayPaddingTop;
    private int mExtendPaddingTop;
    private int mRestWorkDayPadding;

    /**
     * 月份之间的padding
     */
    private int mMonthPadding;

    //Paint--------------
    /**
     * 日期(或显示节假日)禁用的Paint
     */
    private Paint mDayDisableTextPaint;
    /**
     * 扩展(酒店的入店、离店；机票的低价日历；火车票的农历)禁用的Paint
     */
    private Paint mExtendDisableTextPaint;
    /**
     * "休"、"班"禁用的Paint
     */
    private Paint mRestWorkDayDisableTextPaint;
    /**
     * 日期(或显示节假日)的Paint
     */
    private Paint mDayNormalTextPaint;
    /**
     * "今天"的Paint
     */
    private Paint mTodayTextPaint;
    /**
     * 扩展(酒店的入店、离店；机票的低价日历；火车票的农历)的Paint
     */
    private Paint mExtendNormalTextPaint;


    /**
     * "休"的Paint
     */
    private Paint mRestdayNormalTextPaint;
    /**
     * "班"的Paint
     */
    private Paint mWorkdayNormalTextPaint;
    /**
     * 日期(或显示节假日)开始选中的Paint
     */
    private Paint mStartDayTextPaint;
    /**
     * 扩展(酒店的入店、离店；机票的低价日历；火车票的农历)开始选中的Paint
     */
    private Paint mStartDayExtendTextPaint;
    /**
     * "休"、"班"开始选中的Paint
     */
    private Paint mStartDayRestWorkDayTextPaint;
    /**
     * 日期(或显示节假日)结束选中的Paint
     */
    private Paint mEndDayTextPaint;
    /**
     * 扩展(酒店的入店、离店；机票的低价日历；火车票的农历)结束选中的Paint
     */
    private Paint mEndDayExtendTextPaint;
    /**
     * "休"、"班"结束选中的Paint
     */
    private Paint mEndDayRestWorkDayTextPaint;
    /**
     * 日期(或显示节假日)选中范围内的Paint
     */
    private Paint mRangeDayTextPaint;
    /**
     * 扩展(酒店的入店、离店；机票的低价日历；火车票的农历)选中范围的Paint
     */
    private Paint mRangeExtendTextPaint;
    /**
     * "休"、"班"选中范围的Paint
     */
    private Paint mRangeRestWorkDayTextPaint;
    /**
     * 禁止选择的日期Paint
     */
    private Paint mDisableDayTextPaint;

    private Paint mBgRangeDayPaint;

    private Paint mStartDayMarkTextPaint;
    private Paint mEndDayMarkTextPaint;

    private Paint mHolidayNormalTextPaint;
    private Paint mDisableHolidayTextPaint;
    private Paint mStartDayHolidayTextPaint;
    private Paint mEndDayHolidayTextPaint;
    private Paint mRangeDayHolidayTextPaint;


    //单选特有的Paint------------------------
    private Paint mCheckedDayTextPaint;
    private Paint mCheckedDayExtendTextPaint;
    private Paint mCheckedDayRestWorkDayTextPaint;
    private Paint mCheckedDayMarkTextPaint;
    private Paint mCheckedDayHolidayTextPaint;
    private Paint mCheckedDayBgPaint;

    private Paint mExtendCustomTextPaint;
    /**
     * 月份之间分割线Paint
     */
    private Paint mMonthDividerPaint;

    //--------------
    /**
     * 控件宽度
     */
    private int mWidth;
    /**
     * 日期一行的高度
     */
    private int mRowHeight;

    private boolean mShowLunar;


    public WeekView(Context context) {
        this(context, null);
    }

    public WeekView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeekView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        long xxx = System.currentTimeMillis();
        initColor();
        initTextSize();
        initAreaHeight();
        initPaint();
        Log.v("init" + mMonth, String.valueOf(System.currentTimeMillis() - xxx));
    }

    private void initColor() {
        Resources resources = getResources();
        mDisableTextColor = resources.getColor(R.color.disable_text_color);
        mDayNormalTextColor = resources.getColor(R.color.day_normal_text_color);
        mTodayTextColor = resources.getColor(R.color.today_text_color);
        mWorkdayNormalTextColor = resources.getColor(R.color.workday_normal_text_color);
        mRestdayNormalTextColor = resources.getColor(R.color.restday_normal_text_color);
        mExtendNomalTextColor = resources.getColor(R.color.extend_normal_text_color);
        mStartDayTextColor = resources.getColor(R.color.start_day_text_color);
        mStartDayMarkTextColor = resources.getColor(R.color.start_day_mark_text_color);
        mStartDayExtendTextColor = resources.getColor(R.color.start_day_extend_text_color);
        mStartDayRestWorkDayTextColor = resources.getColor(R.color.start_day_rest_work_day_text_color);
        mEndDayTextColor = resources.getColor(R.color.end_day_text_color);
        mEndDayMarkTextColor = resources.getColor(R.color.end_day_mark_text_color);
        mEndDayExtendTextColor = resources.getColor(R.color.end_day_extend_text_color);
        mEndDayRestWorkDayTextColor = resources.getColor(R.color.end_day_rest_work_day_text_color);
        mRangeDayTextColor = resources.getColor(R.color.range_day_text_color);
        mRangeDayExtendTextColor = resources.getColor(R.color.range_day_extend_text_color);
        mRangeDayRestWorkDayTextColor = resources.getColor(R.color.range_day_rest_work_day_text_color);
        mBgRangeDayColor = resources.getColor(R.color.bg_range_day_color);

        //单选
        mCheckedDayTextColor = resources.getColor(R.color.checked_day_text_color);
        mCheckedDayExtendTextColor = resources.getColor(R.color.checked_day_extend_text_color);
        mCheckedDayRestWorkDayTextColor = resources.getColor(R.color.checked_day_rest_work_day_text_color);
        mCheckedDayMarkTextColor = resources.getColor(R.color.checked_day_mark_text_color);
        mStartDayHolidayTextColor = resources.getColor(R.color.checked_day_holiday_text_color);
        mCheckedDayBgColor = resources.getColor(R.color.checked_day_bg_color);
        mCheckedDayMarkTextSize = resources.getDimensionPixelSize(R.dimen.checked_day_mark_text_size);

        mMonthDividerColor = resources.getColor(R.color.month_divider_color);
    }

    private void initTextSize() {
        Resources resources = getResources();
        mDayTextSize = resources.getDimensionPixelSize(R.dimen.day_text_size);
        mHolidayTextSize = resources.getDimensionPixelOffset(R.dimen.holiday_text_size);
        mExtendTextSize = resources.getDimensionPixelSize(R.dimen.extend_text_size);
        mRestWorkDayTextSize = resources.getDimensionPixelSize(R.dimen.rest_work_day_text_size);
        mStartEndMarkTextSize = resources.getDimensionPixelSize(R.dimen.start_end_day_mark_text_size);
    }

    private void initAreaHeight() {
        Resources resources = getResources();
        mRowHeight = resources.getDimensionPixelOffset(R.dimen.row_height);

        mRowPadding = resources.getDimensionPixelOffset(R.dimen.row_padding);

        mDayPaddingTop = resources.getDimensionPixelOffset(R.dimen.day_padding_top);
        mExtendPaddingTop = resources.getDimensionPixelOffset(R.dimen.extend_padding_top);
        mRestWorkDayPadding = resources.getDimensionPixelOffset(R.dimen.rest_work_day_padding);

        mMonthPadding = resources.getDimensionPixelOffset(R.dimen.month_padding);
    }

    private void initPaint() {
        mDayDisableTextPaint = new Paint();
        mDayDisableTextPaint.setAntiAlias(true);
        mDayDisableTextPaint.setTextSize(mDayTextSize);
        mDayDisableTextPaint.setColor(mDisableTextColor);
        mDayDisableTextPaint.setTextAlign(Paint.Align.CENTER);
        mDayDisableTextPaint.setStyle(Paint.Style.FILL);

        mExtendDisableTextPaint = new Paint();
        mExtendDisableTextPaint.setAntiAlias(true);
        mExtendDisableTextPaint.setTextSize(mExtendTextSize);
        mExtendDisableTextPaint.setColor(mDisableTextColor);
        mExtendDisableTextPaint.setTextAlign(Paint.Align.CENTER);
        mExtendDisableTextPaint.setStyle(Paint.Style.FILL);

        mRestWorkDayDisableTextPaint = new Paint();
        mRestWorkDayDisableTextPaint.setAntiAlias(true);
        mRestWorkDayDisableTextPaint.setTextSize(mRestWorkDayTextSize);
        mRestWorkDayDisableTextPaint.setColor(mDisableTextColor);
        mRestWorkDayDisableTextPaint.setTextAlign(Paint.Align.CENTER);
        mRestWorkDayDisableTextPaint.setStyle(Paint.Style.FILL);

        mDayNormalTextPaint = new Paint();
        mDayNormalTextPaint.setAntiAlias(true);
        mDayNormalTextPaint.setTextSize(mDayTextSize);
        mDayNormalTextPaint.setColor(mDayNormalTextColor);
        mDayNormalTextPaint.setTextAlign(Paint.Align.CENTER);
        mDayNormalTextPaint.setStyle(Paint.Style.FILL);

        mTodayTextPaint = new Paint();
        mTodayTextPaint.setAntiAlias(true);
        mTodayTextPaint.setTextSize(mDayTextSize);
        mTodayTextPaint.setColor(mTodayTextColor);
        mTodayTextPaint.setTextAlign(Paint.Align.CENTER);
        mTodayTextPaint.setStyle(Paint.Style.FILL);

        mExtendNormalTextPaint = new Paint();
        mExtendNormalTextPaint.setAntiAlias(true);
        mExtendNormalTextPaint.setTextSize(mExtendTextSize);
        mExtendNormalTextPaint.setColor(mExtendNomalTextColor);
        mExtendNormalTextPaint.setTextAlign(Paint.Align.CENTER);
        mExtendNormalTextPaint.setStyle(Paint.Style.FILL);

        mExtendCustomTextPaint = new Paint();
        mExtendCustomTextPaint.setAntiAlias(true);
        mExtendCustomTextPaint.setTextSize(mExtendTextSize);
        mExtendCustomTextPaint.setTextAlign(Paint.Align.CENTER);
        mExtendCustomTextPaint.setStyle(Paint.Style.FILL);

        mRestdayNormalTextPaint = new Paint();
        mRestdayNormalTextPaint.setAntiAlias(true);
        mRestdayNormalTextPaint.setTextSize(mRestWorkDayTextSize);
        mRestdayNormalTextPaint.setColor(mRestdayNormalTextColor);
        mRestdayNormalTextPaint.setTextAlign(Paint.Align.CENTER);
        mRestdayNormalTextPaint.setStyle(Paint.Style.FILL);

        mWorkdayNormalTextPaint = new Paint();
        mWorkdayNormalTextPaint.setAntiAlias(true);
        mWorkdayNormalTextPaint.setTextSize(mRestWorkDayTextSize);
        mWorkdayNormalTextPaint.setColor(mWorkdayNormalTextColor);
        mWorkdayNormalTextPaint.setTextAlign(Paint.Align.CENTER);
        mWorkdayNormalTextPaint.setStyle(Paint.Style.FILL);

        mStartDayTextPaint = new Paint();
        mStartDayTextPaint.setAntiAlias(true);
        mStartDayTextPaint.setTextSize(mDayTextSize);
        mStartDayTextPaint.setColor(mStartDayTextColor);
        mStartDayTextPaint.setTextAlign(Paint.Align.CENTER);
        mStartDayTextPaint.setStyle(Paint.Style.FILL);

        mStartDayExtendTextPaint = new Paint();
        mStartDayExtendTextPaint.setAntiAlias(true);
        mStartDayExtendTextPaint.setTextSize(mExtendTextSize);
        mStartDayExtendTextPaint.setColor(mStartDayExtendTextColor);
        mStartDayExtendTextPaint.setTextAlign(Paint.Align.CENTER);
        mStartDayExtendTextPaint.setStyle(Paint.Style.FILL);

        mStartDayRestWorkDayTextPaint = new Paint();
        mStartDayRestWorkDayTextPaint.setAntiAlias(true);
        mStartDayRestWorkDayTextPaint.setTextSize(mRestWorkDayTextSize);
        mStartDayRestWorkDayTextPaint.setColor(mStartDayRestWorkDayTextColor);
        mStartDayRestWorkDayTextPaint.setTextAlign(Paint.Align.CENTER);
        mStartDayRestWorkDayTextPaint.setStyle(Paint.Style.FILL);

        mEndDayTextPaint = new Paint();
        mEndDayTextPaint.setAntiAlias(true);
        mEndDayTextPaint.setTextSize(mDayTextSize);
        mEndDayTextPaint.setColor(mEndDayTextColor);
        mEndDayTextPaint.setTextAlign(Paint.Align.CENTER);
        mEndDayTextPaint.setStyle(Paint.Style.FILL);

        mEndDayExtendTextPaint = new Paint();
        mEndDayExtendTextPaint.setAntiAlias(true);
        mEndDayExtendTextPaint.setTextSize(mExtendTextSize);
        mEndDayExtendTextPaint.setColor(mEndDayExtendTextColor);
        mEndDayExtendTextPaint.setTextAlign(Paint.Align.CENTER);
        mEndDayExtendTextPaint.setStyle(Paint.Style.FILL);

        mEndDayRestWorkDayTextPaint = new Paint();
        mEndDayRestWorkDayTextPaint.setAntiAlias(true);
        mEndDayRestWorkDayTextPaint.setTextSize(mRestWorkDayTextSize);
        mEndDayRestWorkDayTextPaint.setColor(mEndDayRestWorkDayTextColor);
        mEndDayRestWorkDayTextPaint.setTextAlign(Paint.Align.CENTER);
        mEndDayRestWorkDayTextPaint.setStyle(Paint.Style.FILL);

        mRangeDayTextPaint = new Paint();
        mRangeDayTextPaint.setAntiAlias(true);
        mRangeDayTextPaint.setTextSize(mDayTextSize);
        mRangeDayTextPaint.setColor(mRangeDayTextColor);
        mRangeDayTextPaint.setTextAlign(Paint.Align.CENTER);
        mRangeDayTextPaint.setStyle(Paint.Style.FILL);

        mRangeExtendTextPaint = new Paint();
        mRangeExtendTextPaint.setAntiAlias(true);
        mRangeExtendTextPaint.setTextSize(mExtendTextSize);
        mRangeExtendTextPaint.setColor(mRangeDayExtendTextColor);
        mRangeExtendTextPaint.setTextAlign(Paint.Align.CENTER);
        mRangeExtendTextPaint.setStyle(Paint.Style.FILL);

        mRangeRestWorkDayTextPaint = new Paint();
        mRangeRestWorkDayTextPaint.setAntiAlias(true);
        mRangeRestWorkDayTextPaint.setTextSize(mRestWorkDayTextSize);
        mRangeRestWorkDayTextPaint.setColor(mRangeDayRestWorkDayTextColor);
        mRangeRestWorkDayTextPaint.setTextAlign(Paint.Align.CENTER);
        mRangeRestWorkDayTextPaint.setStyle(Paint.Style.FILL);

        mBgRangeDayPaint = new Paint();
        mBgRangeDayPaint.setAntiAlias(true);
        mBgRangeDayPaint.setColor(mBgRangeDayColor);
        mBgRangeDayPaint.setStyle(Paint.Style.FILL);

        mStartDayMarkTextPaint = new Paint();
        mStartDayMarkTextPaint.setAntiAlias(true);
        mStartDayMarkTextPaint.setTextSize(mStartEndMarkTextSize);
        mStartDayMarkTextPaint.setColor(mStartDayMarkTextColor);
        mStartDayMarkTextPaint.setTextAlign(Paint.Align.CENTER);
        mStartDayMarkTextPaint.setStyle(Paint.Style.FILL);

        mEndDayMarkTextPaint = new Paint();
        mEndDayMarkTextPaint.setAntiAlias(true);
        mEndDayMarkTextPaint.setTextSize(mStartEndMarkTextSize);
        mEndDayMarkTextPaint.setColor(mEndDayMarkTextColor);
        mEndDayMarkTextPaint.setTextAlign(Paint.Align.CENTER);
        mEndDayMarkTextPaint.setStyle(Paint.Style.FILL);

        mStartDayHolidayTextPaint = new Paint();
        mStartDayHolidayTextPaint.setAntiAlias(true);
        mStartDayHolidayTextPaint.setTextSize(mHolidayTextSize);
        mStartDayHolidayTextPaint.setColor(mStartDayTextColor);
        mStartDayHolidayTextPaint.setTextAlign(Paint.Align.CENTER);
        mStartDayHolidayTextPaint.setStyle(Paint.Style.FILL);

        mEndDayHolidayTextPaint = new Paint();
        mEndDayHolidayTextPaint.setAntiAlias(true);
        mEndDayHolidayTextPaint.setTextSize(mHolidayTextSize);
        mEndDayHolidayTextPaint.setColor(mEndDayTextColor);
        mEndDayHolidayTextPaint.setTextAlign(Paint.Align.CENTER);
        mEndDayHolidayTextPaint.setStyle(Paint.Style.FILL);

        mRangeDayHolidayTextPaint = new Paint();
        mRangeDayHolidayTextPaint.setAntiAlias(true);
        mRangeDayHolidayTextPaint.setTextSize(mHolidayTextSize);
        mRangeDayHolidayTextPaint.setColor(mRangeDayTextColor);
        mRangeDayHolidayTextPaint.setTextAlign(Paint.Align.CENTER);
        mRangeDayHolidayTextPaint.setStyle(Paint.Style.FILL);
        //单选
        mCheckedDayTextPaint = new Paint();
        mCheckedDayTextPaint.setAntiAlias(true);
        mCheckedDayTextPaint.setTextSize(mDayTextSize);
        mCheckedDayTextPaint.setColor(mCheckedDayTextColor);
        mCheckedDayTextPaint.setTextAlign(Paint.Align.CENTER);
        mCheckedDayTextPaint.setStyle(Paint.Style.FILL);

        mCheckedDayExtendTextPaint = new Paint();
        mCheckedDayExtendTextPaint.setAntiAlias(true);
        mCheckedDayExtendTextPaint.setTextSize(mExtendTextSize);
        mCheckedDayExtendTextPaint.setColor(mCheckedDayExtendTextColor);
        mCheckedDayExtendTextPaint.setTextAlign(Paint.Align.CENTER);
        mCheckedDayExtendTextPaint.setStyle(Paint.Style.FILL);

        mCheckedDayRestWorkDayTextPaint = new Paint();
        mCheckedDayRestWorkDayTextPaint.setAntiAlias(true);
        mCheckedDayRestWorkDayTextPaint.setTextSize(mRestWorkDayTextSize);
        mCheckedDayRestWorkDayTextPaint.setColor(mCheckedDayRestWorkDayTextColor);
        mCheckedDayRestWorkDayTextPaint.setTextAlign(Paint.Align.CENTER);
        mCheckedDayRestWorkDayTextPaint.setStyle(Paint.Style.FILL);

        mCheckedDayBgPaint = new Paint();
        mCheckedDayBgPaint.setAntiAlias(true);
        mCheckedDayBgPaint.setColor(mCheckedDayBgColor);
        mCheckedDayBgPaint.setStyle(Paint.Style.FILL);

        mCheckedDayMarkTextPaint = new Paint();
        mCheckedDayMarkTextPaint.setAntiAlias(true);
        mCheckedDayMarkTextPaint.setTextSize(mCheckedDayMarkTextSize);
        mCheckedDayMarkTextPaint.setColor(mCheckedDayMarkTextColor);
        mCheckedDayMarkTextPaint.setTextAlign(Paint.Align.CENTER);
        mCheckedDayMarkTextPaint.setStyle(Paint.Style.FILL);

        mCheckedDayHolidayTextPaint = new Paint();
        mCheckedDayHolidayTextPaint.setAntiAlias(true);
        mCheckedDayHolidayTextPaint.setTextSize(mHolidayTextSize);
        mCheckedDayHolidayTextPaint.setColor(mStartDayHolidayTextColor);
        mCheckedDayHolidayTextPaint.setTextAlign(Paint.Align.CENTER);
        mCheckedDayHolidayTextPaint.setStyle(Paint.Style.FILL);
        //单选结束

        mDisableDayTextPaint = new Paint();
        mDisableDayTextPaint.setAntiAlias(true);
        mDisableDayTextPaint.setTextSize(mDayTextSize);
        mDisableDayTextPaint.setColor(mDisableTextColor);
        mDisableDayTextPaint.setTextAlign(Paint.Align.CENTER);
        mDisableDayTextPaint.setStyle(Paint.Style.FILL);

        mHolidayNormalTextPaint = new Paint();
        mHolidayNormalTextPaint.setAntiAlias(true);
        mHolidayNormalTextPaint.setTextSize(mHolidayTextSize);
        mHolidayNormalTextPaint.setColor(mDayNormalTextColor);
        mHolidayNormalTextPaint.setTextAlign(Paint.Align.CENTER);
        mHolidayNormalTextPaint.setStyle(Paint.Style.FILL);

        mDisableHolidayTextPaint = new Paint();
        mDisableHolidayTextPaint.setAntiAlias(true);
        mDisableHolidayTextPaint.setTextSize(mHolidayTextSize);
        mDisableHolidayTextPaint.setColor(mDisableTextColor);
        mDisableHolidayTextPaint.setTextAlign(Paint.Align.CENTER);
        mDisableHolidayTextPaint.setStyle(Paint.Style.FILL);

        mMonthDividerPaint = new Paint();
        mMonthDividerPaint.setAntiAlias(true);
        mMonthDividerPaint.setColor(mMonthDividerColor);
        mMonthDividerPaint.setStrokeWidth(1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = mRowHeight + mRowPadding * 2;
        if (mWeekViewEntity.lastWeekOfMonth) {
            height += mMonthPadding;
        }
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawAllDays(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            //处理点击事件
            Calendar calendar = getCalendarFromLocation(event.getX(), event.getY());
            if (calendar != null && !CalendarUtils.isInvalidDay(calendar, mAvailableStartCalendar, mAvailableEndCalendar)) {
                mOnDayClickListener.onDayClick(calendar);
            }
        }
        return true;
    }

    private Calendar getCalendarFromLocation(float x, float y) {
        if (x < 0 || x > mWidth) {
            return null;
        }
        if (y <= 0 || y >= mRowHeight + mRowPadding * 2) {
            return null;
        }
        int day = mWeekViewEntity.startDrawCalendar.get(Calendar.DAY_OF_MONTH) + (int) (x * CalendarUtils.DAY_COUNT_PER_ROW / mWidth);
        if (mWeekViewEntity.startDrawCalendar.get(Calendar.DAY_OF_MONTH) == 1) {
            day -= findDayOffset();
        }
        if (mMonth > 11 || mMonth < 0 || CalendarUtils.getDaysInMonth(mYear, mMonth) < day || day < 1) {
            return null;
        }
        Calendar calendar = CalendarUtils.getCalendar();
        calendar.set(Calendar.YEAR, mYear);
        calendar.set(Calendar.MONTH, mMonth);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    private Map<String, ExtendEntity> mExtendMap;

    /**
     * 用户扩展展示(如机票的低价日历)
     *
     * @param map
     */
    public void setExtendMap(Map<String, ExtendEntity> map) {
        mExtendMap = map;
        invalidate();
    }

    public boolean isNullExtendMap() {
        if (mExtendMap == null || mExtendMap.size() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 绘制月份中的日期
     */
    private void drawAllDays(Canvas canvas) {
        long xxx = System.currentTimeMillis();
        //TODO:for test
        //int testY = 0;

        int rectY = mRowPadding;
        int y = mRowPadding + mDayPaddingTop;
        int restWorkDayY = mRowPadding + mRestWorkDayPadding;
        //把一行平分成14份，为了找到每天中心坐标
        int paddingDay = mWidth / (2 * CalendarUtils.DAY_COUNT_PER_ROW);
        int day = mWeekViewEntity.startDrawCalendar.get(Calendar.DAY_OF_MONTH);
        int dayOffset = 0;
        if (day == 1) {
            dayOffset = findDayOffset();
        }
        Calendar todayCalendar = CalendarUtils.getCalendar();
        //遍历绘制每一天
        for (; dayOffset <= CalendarUtils.DAY_COUNT_PER_ROW && day <= mDayCount; day++, dayOffset++) {
            //canvas.drawLine(0, testY, mWidth, testY, mMonthTitleTextPaint);
            int x = paddingDay * (1 + dayOffset * 2);
            Calendar currentCalendar = CalendarUtils.getCalendar(mYear, mMonth, day);
            String currentDate = CalendarUtils.getYearMonthDay(mYear, mMonth, day);
            if (mPickType == DatePickerView.PickType.RANGE_PICKER) {
                //当前日期是选中开始日期
                if (CalendarUtils.equalsCalendar(currentCalendar, mStartCalendar)) {
                    // 绘制背景
                    drawNinePatch(canvas, R.drawable.bg_start_picker, new Rect(paddingDay * dayOffset * 2, rectY, paddingDay * (dayOffset + 1) * 2, rectY + mRowHeight));
                    //今天
                    if (CalendarUtils.equalsCalendar(currentCalendar, todayCalendar)) {
                        canvas.drawText(TODAY, x, y + getTextBoundsTop(mStartDayTextPaint, TODAY), mStartDayTextPaint);
                    } else {
                        //节假日
                        String holiday = CalendarUtils.getHoliday(currentCalendar);
                        if (!TextUtils.isEmpty(holiday)) {
                            //绘制节假日
                            canvas.drawText(holiday, x, y + getTextBoundsTop(mStartDayHolidayTextPaint, holiday), mStartDayHolidayTextPaint);
                        } else {
                            //绘制几号
                            canvas.drawText(String.valueOf(day), x, y + getTextBoundsTop(mStartDayTextPaint, String.valueOf(day)), mStartDayTextPaint);
                        }
                    }
                    if (CalendarUtils.isRestday(currentCalendar)) {
                        int restdayX = x + paddingDay - measureText(mStartDayRestWorkDayTextPaint, RESTDAY) / 2 - mRestWorkDayPadding;
                        canvas.drawText(RESTDAY, restdayX, restWorkDayY + getTextBoundsTop(mStartDayRestWorkDayTextPaint, RESTDAY) + mRestWorkDayPadding, mStartDayRestWorkDayTextPaint);
                    }
                    if (CalendarUtils.isWorkday(currentCalendar)) {
                        int workdayX = x + paddingDay - measureText(mStartDayRestWorkDayTextPaint, WORKDAY) / 2 - mRestWorkDayPadding;
                        canvas.drawText(WORKDAY, workdayX, restWorkDayY + getTextBoundsTop(mStartDayRestWorkDayTextPaint, WORKDAY) + mRestWorkDayPadding, mStartDayRestWorkDayTextPaint);
                    }
                    // 入住
                    canvas.drawText(mStartDateMarkText, x, y + getTextBoundsTop(mStartDayTextPaint, String.valueOf(day)) + mExtendPaddingTop + getTextBoundsTop(mStartDayMarkTextPaint, mStartDateMarkText), mStartDayMarkTextPaint);
                } else if (CalendarUtils.betweenStartEndDay(currentCalendar, mStartCalendar, mEndCalendar)) {
                    canvas.drawRect(paddingDay * dayOffset * 2, rectY, paddingDay * (dayOffset + 1) * 2, rectY + mRowHeight, mBgRangeDayPaint);
                    //今天
                    if (CalendarUtils.equalsCalendar(currentCalendar, todayCalendar)) {
                        canvas.drawText(TODAY, x, y + getTextBoundsTop(mRangeDayTextPaint, TODAY), mRangeDayTextPaint);
                    } else {
                        //节假日
                        String holiday = CalendarUtils.getHoliday(currentCalendar);
                        if (!TextUtils.isEmpty(holiday)) {
                            //绘制节假日
                            canvas.drawText(holiday, x, y + getTextBoundsTop(mRangeDayHolidayTextPaint, holiday), mRangeDayHolidayTextPaint);
                        } else {
                            //绘制几号
                            canvas.drawText(String.valueOf(day), x, y + getTextBoundsTop(mRangeDayTextPaint, String.valueOf(day)), mRangeDayTextPaint);
                        }
                    }
                    if (CalendarUtils.isRestday(currentCalendar)) {
                        int restdayX = x + paddingDay - measureText(mRangeRestWorkDayTextPaint, RESTDAY) / 2 - mRestWorkDayPadding;
                        canvas.drawText(RESTDAY, restdayX, restWorkDayY + getTextBoundsTop(mRangeRestWorkDayTextPaint, RESTDAY) + mRestWorkDayPadding, mRangeRestWorkDayTextPaint);
                    }
                    if (CalendarUtils.isWorkday(currentCalendar)) {
                        int workdayX = x + paddingDay - measureText(mRangeRestWorkDayTextPaint, WORKDAY) / 2 - mRestWorkDayPadding;
                        canvas.drawText(WORKDAY, workdayX, restWorkDayY + getTextBoundsTop(mRangeRestWorkDayTextPaint, WORKDAY) + mRestWorkDayPadding, mRangeRestWorkDayTextPaint);
                    }
                    //TODO 农历、扩展字段
                } else if (CalendarUtils.equalsCalendar(currentCalendar, mEndCalendar)) {
                    // 绘制背景
                    drawNinePatch(canvas, R.drawable.bg_end_picker, new Rect(paddingDay * dayOffset * 2, rectY, paddingDay * (dayOffset + 1) * 2, rectY + mRowHeight));
                    //今天
                    if (CalendarUtils.equalsCalendar(currentCalendar, todayCalendar)) {
                        canvas.drawText(TODAY, x, y + getTextBoundsTop(mEndDayTextPaint, TODAY), mEndDayTextPaint);
                    } else {
                        //节假日
                        String holiday = CalendarUtils.getHoliday(currentCalendar);
                        if (!TextUtils.isEmpty(holiday)) {
                            //绘制节假日
                            canvas.drawText(holiday, x, y + getTextBoundsTop(mEndDayHolidayTextPaint, holiday), mEndDayHolidayTextPaint);
                        } else {
                            //绘制几号
                            canvas.drawText(String.valueOf(day), x, y + getTextBoundsTop(mEndDayTextPaint, String.valueOf(day)), mEndDayTextPaint);
                        }
                    }
                    if (CalendarUtils.isRestday(currentCalendar)) {
                        int restdayX = x + paddingDay - measureText(mEndDayRestWorkDayTextPaint, RESTDAY) / 2 - mRestWorkDayPadding;
                        canvas.drawText(RESTDAY, restdayX, restWorkDayY + getTextBoundsTop(mEndDayRestWorkDayTextPaint, RESTDAY) + mRestWorkDayPadding, mEndDayRestWorkDayTextPaint);
                    }
                    if (CalendarUtils.isWorkday(currentCalendar)) {
                        int workdayX = x + paddingDay - measureText(mEndDayRestWorkDayTextPaint, WORKDAY) / 2 - mRestWorkDayPadding;
                        canvas.drawText(WORKDAY, workdayX, restWorkDayY + getTextBoundsTop(mEndDayRestWorkDayTextPaint, WORKDAY) + mRestWorkDayPadding, mEndDayRestWorkDayTextPaint);
                    }
                    // 离店
                    canvas.drawText(mEndDateMarkText, x, y + getTextBoundsTop(mEndDayTextPaint, String.valueOf(day)) + mExtendPaddingTop + getTextBoundsTop(mEndDayMarkTextPaint, mEndDateMarkText), mEndDayMarkTextPaint);
                } else if (CalendarUtils.isInvalidDay(currentCalendar, mAvailableStartCalendar, mAvailableEndCalendar)) {
                    //今天
                    if (CalendarUtils.equalsCalendar(currentCalendar, todayCalendar)) {
                        canvas.drawText(TODAY, x, y + getTextBoundsTop(mDisableDayTextPaint, TODAY), mDisableDayTextPaint);
                    } else {
                        //节假日
                        String holiday = CalendarUtils.getHoliday(currentCalendar);
                        if (!TextUtils.isEmpty(holiday)) {
                            //绘制节假日
                            canvas.drawText(holiday, x, y + getTextBoundsTop(mDisableHolidayTextPaint, holiday), mDisableHolidayTextPaint);
                        } else {
                            //绘制几号
                            canvas.drawText(String.valueOf(day), x, y + getTextBoundsTop(mDisableDayTextPaint, String.valueOf(day)), mDisableDayTextPaint);
                        }
                    }
                    if (CalendarUtils.isRestday(currentCalendar)) {
                        int restdayX = x + paddingDay - measureText(mRestWorkDayDisableTextPaint, RESTDAY) / 2 - mRestWorkDayPadding;
                        //TODO 禁止选择的“休”展示？
                        canvas.drawText(RESTDAY, restdayX, restWorkDayY + getTextBoundsTop(mRestWorkDayDisableTextPaint, RESTDAY) + mRestWorkDayPadding, mRestWorkDayDisableTextPaint);
                    }
                    if (CalendarUtils.isWorkday(currentCalendar)) {
                        int workdayX = x + paddingDay - measureText(mRestWorkDayDisableTextPaint, WORKDAY) / 2 - mRestWorkDayPadding;
                        canvas.drawText(WORKDAY, workdayX, restWorkDayY + getTextBoundsTop(mRestWorkDayDisableTextPaint, WORKDAY) + mRestWorkDayPadding, mRestWorkDayDisableTextPaint);
                    }
                } else {
                    //正常情况
                    //今天
                    if (CalendarUtils.equalsCalendar(currentCalendar, todayCalendar)) {
                        canvas.drawText(TODAY, x, y + getTextBoundsTop(mTodayTextPaint, TODAY), mTodayTextPaint);
                    } else {
                        //节假日
                        String holiday = CalendarUtils.getHoliday(currentCalendar);
                        if (!TextUtils.isEmpty(holiday)) {
                            //绘制节假日
                            canvas.drawText(holiday, x, y + getTextBoundsTop(mHolidayNormalTextPaint, holiday), mHolidayNormalTextPaint);
                        } else {
                            //绘制几号
                            canvas.drawText(String.valueOf(day), x, y + getTextBoundsTop(mDayNormalTextPaint, String.valueOf(day)), mDayNormalTextPaint);
                        }
                    }
                    if (CalendarUtils.isRestday(currentCalendar)) {
                        int restdayX = x + paddingDay - measureText(mRestdayNormalTextPaint, RESTDAY) / 2 - mRestWorkDayPadding;
                        canvas.drawText(RESTDAY, restdayX, restWorkDayY + getTextBoundsTop(mRestdayNormalTextPaint, RESTDAY) + mRestWorkDayPadding, mRestdayNormalTextPaint);
                    }
                    if (CalendarUtils.isWorkday(currentCalendar)) {
                        int workdayX = x + paddingDay - measureText(mWorkdayNormalTextPaint, WORKDAY) / 2 - mRestWorkDayPadding;
                        canvas.drawText(WORKDAY, workdayX, restWorkDayY + getTextBoundsTop(mWorkdayNormalTextPaint, WORKDAY) + mRestWorkDayPadding, mWorkdayNormalTextPaint);
                    }
                    //TODO 农历、扩展字段
                }
            } else if (mPickType == DatePickerView.PickType.SINGLE_PICKER) {
                // 处理单选
                if (CalendarUtils.isInvalidDay(currentCalendar, mAvailableStartCalendar, mAvailableEndCalendar)) {
                    //今天
                    if (CalendarUtils.equalsCalendar(currentCalendar, todayCalendar)) {
                        canvas.drawText(TODAY, x, y + getTextBoundsTop(mDisableDayTextPaint, TODAY), mDisableDayTextPaint);
                    } else {
                        //节假日
                        String holiday = CalendarUtils.getHoliday(currentCalendar);
                        if (!TextUtils.isEmpty(holiday)) {
                            //绘制节假日
                            canvas.drawText(holiday, x, y + getTextBoundsTop(mDisableHolidayTextPaint, holiday), mDisableHolidayTextPaint);
                        } else {
                            //绘制几号
                            canvas.drawText(String.valueOf(day), x, y + getTextBoundsTop(mDisableDayTextPaint, String.valueOf(day)), mDisableDayTextPaint);
                        }
                    }
                    if (CalendarUtils.isRestday(currentCalendar)) {
                        int restdayX = x + paddingDay - measureText(mRestWorkDayDisableTextPaint, RESTDAY) / 2 - mRestWorkDayPadding;
                        //TODO 禁止选择的“休”展示？
                        canvas.drawText(RESTDAY, restdayX, restWorkDayY + getTextBoundsTop(mRestWorkDayDisableTextPaint, RESTDAY) + mRestWorkDayPadding, mRestWorkDayDisableTextPaint);
                    }
                    if (CalendarUtils.isWorkday(currentCalendar)) {
                        int workdayX = x + paddingDay - measureText(mRestWorkDayDisableTextPaint, WORKDAY) / 2 - mRestWorkDayPadding;
                        canvas.drawText(WORKDAY, workdayX, restWorkDayY + getTextBoundsTop(mRestWorkDayDisableTextPaint, WORKDAY) + mRestWorkDayPadding, mRestWorkDayDisableTextPaint);
                    }

                    // 农历
                    if (isNullExtendMap() && mShowLunar) {
                        Lunar lunar = new Lunar(currentCalendar.getTimeInMillis());
                        String lunarDay = lunar.getChineseDay();
                        if (!TextUtils.isEmpty(lunarDay)) {
                            canvas.drawText(lunarDay, x, y + getTextBoundsTop(mDisableDayTextPaint, String.valueOf(day)) + mExtendPaddingTop + getTextBoundsTop(mExtendDisableTextPaint, lunarDay), mExtendDisableTextPaint);
                        }
                    }
                } else if (CalendarUtils.equalsCalendar(currentCalendar, mCheckedCalendar)) {
                    //TODO 绘制背景
                    RectF rect = new RectF(paddingDay * dayOffset * 2, rectY, paddingDay * (dayOffset + 1) * 2, rectY + mRowHeight);
                    canvas.drawRoundRect(rect, 8, 8, mCheckedDayBgPaint);
                    if (CalendarUtils.equalsCalendar(currentCalendar, todayCalendar)) {
                        canvas.drawText(TODAY, x, y + getTextBoundsTop(mCheckedDayTextPaint, TODAY), mCheckedDayTextPaint);
                    } else {
                        //节假日
                        String holiday = CalendarUtils.getHoliday(currentCalendar);
                        if (!TextUtils.isEmpty(holiday)) {
                            //绘制节假日
                            canvas.drawText(holiday, x, y + getTextBoundsTop(mCheckedDayHolidayTextPaint, holiday), mCheckedDayHolidayTextPaint);
                        } else {
                            //绘制几号
                            canvas.drawText(String.valueOf(day), x, y + getTextBoundsTop(mCheckedDayTextPaint, String.valueOf(day)), mCheckedDayTextPaint);
                        }
                    }
                    if (CalendarUtils.isRestday(currentCalendar)) {
                        int restdayX = x + paddingDay - measureText(mCheckedDayRestWorkDayTextPaint, RESTDAY) / 2 - mRestWorkDayPadding;
                        canvas.drawText(RESTDAY, restdayX, restWorkDayY + getTextBoundsTop(mCheckedDayRestWorkDayTextPaint, RESTDAY) + mRestWorkDayPadding, mCheckedDayRestWorkDayTextPaint);
                    }
                    if (CalendarUtils.isWorkday(currentCalendar)) {
                        int workdayX = x + paddingDay - measureText(mCheckedDayRestWorkDayTextPaint, WORKDAY) / 2 - mRestWorkDayPadding;
                        canvas.drawText(WORKDAY, workdayX, restWorkDayY + getTextBoundsTop(mCheckedDayRestWorkDayTextPaint, WORKDAY) + mRestWorkDayPadding, mCheckedDayRestWorkDayTextPaint);
                    }
                    if (isNullExtendMap()) {
                        if (!TextUtils.isEmpty(mCheckedDateMarkText)) {
                            canvas.drawText(mCheckedDateMarkText, x, y + getTextBoundsTop(mCheckedDayTextPaint, String.valueOf(day)) + mExtendPaddingTop + getTextBoundsTop(mCheckedDayMarkTextPaint, mCheckedDateMarkText), mCheckedDayMarkTextPaint);
                        }
                    } else {
                        ExtendEntity extendEntity = mExtendMap.get(currentDate);
                        if (extendEntity != null) {
                            //绘制扩展
                            canvas.drawText(extendEntity.text, x, y + getTextBoundsTop(mCheckedDayTextPaint, String.valueOf(day)) + mExtendPaddingTop + getTextBoundsTop(mCheckedDayExtendTextPaint, extendEntity.text), mCheckedDayExtendTextPaint);
                        }
                    }
                } else {
                    //正常情况
                    //今天
                    if (CalendarUtils.equalsCalendar(currentCalendar, todayCalendar)) {
                        canvas.drawText(TODAY, x, y + getTextBoundsTop(mTodayTextPaint, TODAY), mTodayTextPaint);
                    } else {
                        //节假日
                        String holiday = CalendarUtils.getHoliday(currentCalendar);
                        if (!TextUtils.isEmpty(holiday)) {
                            //绘制节假日
                            canvas.drawText(holiday, x, y + getTextBoundsTop(mHolidayNormalTextPaint, holiday), mHolidayNormalTextPaint);
                        } else {
                            //绘制几号
                            canvas.drawText(String.valueOf(day), x, y + getTextBoundsTop(mDayNormalTextPaint, String.valueOf(day)), mDayNormalTextPaint);
                        }
                    }
                    if (CalendarUtils.isRestday(currentCalendar)) {
                        int restdayX = x + paddingDay - measureText(mRestdayNormalTextPaint, RESTDAY) / 2 - mRestWorkDayPadding;
                        canvas.drawText(RESTDAY, restdayX, restWorkDayY + getTextBoundsTop(mRestdayNormalTextPaint, RESTDAY) + mRestWorkDayPadding, mRestdayNormalTextPaint);
                    }
                    if (CalendarUtils.isWorkday(currentCalendar)) {
                        int workdayX = x + paddingDay - measureText(mWorkdayNormalTextPaint, WORKDAY) / 2 - mRestWorkDayPadding;
                        canvas.drawText(WORKDAY, workdayX, restWorkDayY + getTextBoundsTop(mWorkdayNormalTextPaint, WORKDAY) + mRestWorkDayPadding, mWorkdayNormalTextPaint);
                    }
                    //农历、扩展字段
                    if (isNullExtendMap()) {
                        if (mShowLunar) {
                            Lunar lunar = new Lunar(currentCalendar.getTimeInMillis());
                            String lunarDay = lunar.getChineseDay();
                            if (!TextUtils.isEmpty(lunarDay)) {
                                canvas.drawText(lunarDay, x, y + getTextBoundsTop(mDayNormalTextPaint, String.valueOf(day)) + mExtendPaddingTop + getTextBoundsTop(mExtendNormalTextPaint, lunarDay), mExtendNormalTextPaint);
                            }
                        }
                    } else {
                        ExtendEntity extendEntity = mExtendMap.get(currentDate);
                        if (extendEntity != null) {
                            //绘制扩展
                            mExtendCustomTextPaint.setColor(extendEntity.color);
                            canvas.drawText(extendEntity.text, x, y + getTextBoundsTop(mDayNormalTextPaint, String.valueOf(day)) + mExtendPaddingTop + getTextBoundsTop(mExtendCustomTextPaint, extendEntity.text), mExtendCustomTextPaint);
                        }
                    }
                }
            }
        }
        int dividerY = rectY - mRowPadding + mMonthPadding - 1;
        if (dayOffset > 0) {
            dividerY = rectY + mRowHeight + mRowPadding + mMonthPadding - 1;
        }
        canvas.drawLine(0, dividerY, mWidth, dividerY, mMonthDividerPaint);

        Log.v("xxxxxxxxxxx" + mMonth, String.valueOf(System.currentTimeMillis() - xxx));
    }

    /**
     * 点击日期回调
     */
    public interface OnDayClickListener {
        void onDayClick(Calendar calendar);
    }


    ////------------
    private Calendar getFirstDayOfMonth() {
        Calendar calendar = CalendarUtils.getCalendar();
        calendar.set(Calendar.YEAR, mYear);
        calendar.set(Calendar.MONTH, mMonth);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar;
    }

    private int findDayOffset() {
        Calendar calendar = getFirstDayOfMonth();
        //由于时区的不同，返回的dayOfWeek和firstDayOfWeek是不一样的。
        int dayOfWeekStart = calendar.get(Calendar.DAY_OF_WEEK);
        int weekStart = calendar.getFirstDayOfWeek();
        //偏移量等于当月第一天与一周第一天的差
        return (dayOfWeekStart < weekStart) ? (dayOfWeekStart + CalendarUtils.DAY_COUNT_PER_ROW) : (dayOfWeekStart) - weekStart;
    }


    private OnDayClickListener mOnDayClickListener;

    public void setOnDayClickListener(OnDayClickListener onDayClickListener) {
        mOnDayClickListener = onDayClickListener;
    }

    private void drawNinePatch(Canvas canvas, int resId, Rect rect) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resId);
        NinePatch ninePatch = new NinePatch(bmp, bmp.getNinePatchChunk(), null);
        ninePatch.draw(canvas, rect);
    }

    private int getTextBoundsTop(Paint paint, String text) {
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return -bounds.top;
    }

    private int measureText(Paint paint, String text) {
        return (int) paint.measureText(text, 0, text.length());
    }
}
