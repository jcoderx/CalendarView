package cn.xudaodao.android.calendarview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import java.util.Calendar;
import java.util.Map;

/**
 * Created by chaohui.yang on 2016/3/20.
 */
public class DatePickerView extends RecyclerView {
    private Context mContext;
    WeekViewAdapter mWeekViewAdapter;

    /**
     * 选择类型：开始结束日期，单个日期
     */
    public enum PickType {
        RANGE_PICKER, SINGLE_PICKER
    }

    public DatePickerView(Context context) {
        this(context, null);
    }

    public DatePickerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DatePickerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        setLayoutManager(new LinearLayoutManager(context));
        mContext = context;
        //setVerticalScrollBarEnabled(false);
        //setFadingEdgeLength(0);
    }

    public interface OnDatePickerListener {
        void onDatePicked(Calendar calendar);

        void onDateRangePicked(Calendar startCalendar, Calendar endCalendar);
    }

    /**
     * 时间范围选择传参
     *
     * @param availableStartCalendar
     * @param availableEndCalendar
     * @param startCalendar
     * @param endCalendar
     * @param startDateMarkText
     * @param endDateMarkText
     * @param onDatePickerListener
     */
    public void setRangePickerParams(Calendar availableStartCalendar, Calendar availableEndCalendar, Calendar startCalendar, Calendar endCalendar, String startDateMarkText, String endDateMarkText, OnDatePickerListener onDatePickerListener) {
        mWeekViewAdapter = new WeekViewAdapter(mContext, availableStartCalendar, availableEndCalendar, startCalendar, endCalendar, startDateMarkText, endDateMarkText, onDatePickerListener);
        setAdapter(mWeekViewAdapter);
    }

    public void setSinglePickerParams(boolean showLunar, Calendar availableStartCalendar, Calendar availableEndCalendar, Calendar checkedCalendar, String checkedDateMarkText, OnDatePickerListener onDatePickerListener) {
        mWeekViewAdapter = new WeekViewAdapter(mContext, showLunar, availableStartCalendar, availableEndCalendar, checkedCalendar, checkedDateMarkText, onDatePickerListener);
        setAdapter(mWeekViewAdapter);
    }

    public void setExtendMap(Map<String, ExtendEntity> map) {
        mWeekViewAdapter.setExtendMap(map);
    }
}
