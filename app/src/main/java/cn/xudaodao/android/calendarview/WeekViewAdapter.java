package cn.xudaodao.android.calendarview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.CalendarView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by chaohui.yang on 2016/3/20.
 */
public class WeekViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements WeekView.OnDayClickListener {
    private Context mContext;
    private DatePickerView.PickType mPickType;

    private Calendar mAvailableStartCalendar;
    private Calendar mAvailableEndCalendar;
    private Calendar mStartCalendar;
    private Calendar mEndCalendar;
    private String mStartDateMarkText;
    private String mEndDateMarkText;
    private DatePickerView.OnDatePickerListener mOnDatePickerListener;

    private Calendar mCheckedCalendar;
    private String mCheckedDateMarkText;
    private boolean mShowLunar;

    private List<Integer> mWeekCountOfMonthList;
    private int mTotalWeekCount;

    public final static int TYPE_WEEK_VIEW = 0;
    public final static int TYPE_MONTH_HEADER_VIEW = 1;

    private Map<String, ExtendEntity> mExtendMap;

    public void setExtendMap(Map<String, ExtendEntity> map) {
        if (mPickType == DatePickerView.PickType.SINGLE_PICKER) {
            mExtendMap = map;
            notifyDataSetChanged();
        }
    }

    /**
     * 日期范围选择时的构造方法
     *
     * @param context
     * @param availableStartCalendar
     * @param availableEndCalendar
     * @param startCalendar
     * @param endCalendar
     * @param startDateMarkText
     * @param endDateMarkText
     * @param onDatePickerListener
     */
    public WeekViewAdapter(Context context, Calendar availableStartCalendar, Calendar availableEndCalendar, Calendar startCalendar, Calendar endCalendar, String startDateMarkText, String endDateMarkText, DatePickerView.OnDatePickerListener onDatePickerListener) {
        mContext = context;
        mPickType = DatePickerView.PickType.RANGE_PICKER;
        mAvailableStartCalendar = availableStartCalendar;
        mAvailableEndCalendar = availableEndCalendar;
        mStartCalendar = startCalendar;
        mEndCalendar = endCalendar;
        mStartDateMarkText = startDateMarkText;
        mEndDateMarkText = endDateMarkText;
        mOnDatePickerListener = onDatePickerListener;

        //计算总行数
        mTotalWeekCount = initWeekCountOfMonthList();
    }

    /**
     * 单选日期的构造方法
     *
     * @param context
     * @param availableStartCalendar
     * @param availableEndCalendar
     * @param checkedCalendar
     * @param checkedDateMarkText
     * @param onDatePickerListener
     */
    public WeekViewAdapter(Context context, boolean showLunar, Calendar availableStartCalendar, Calendar availableEndCalendar, Calendar checkedCalendar, String checkedDateMarkText, DatePickerView.OnDatePickerListener onDatePickerListener) {
        mContext = context;
        mPickType = DatePickerView.PickType.SINGLE_PICKER;
        mShowLunar = showLunar;
        mAvailableStartCalendar = availableStartCalendar;
        mAvailableEndCalendar = availableEndCalendar;
        mCheckedCalendar = checkedCalendar;
        mCheckedDateMarkText = checkedDateMarkText;
        mOnDatePickerListener = onDatePickerListener;
        mTotalWeekCount = initWeekCountOfMonthList();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_MONTH_HEADER_VIEW) {
            MonthHeaderView monthHeaderView = new MonthHeaderView(mContext);
            return new MonthHeaderViewHolder(monthHeaderView);
        } else {
            WeekView weekView = new WeekView(mContext);
            return new WeekViewHolder(weekView, this);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MonthHeaderViewHolder) {
            MonthHeaderView monthHeaderView = ((MonthHeaderViewHolder) holder).monthHeaderView;
            WeekViewEntity weekViewEntity = getFirstDateFromPosition(position);
            if (weekViewEntity != null) {
                if (weekViewEntity.monthTitle) {
                    monthHeaderView.setParams(weekViewEntity.monthTitleCalendar);
                }
            }
        } else if (holder instanceof WeekViewHolder) {
            WeekView weekView = ((WeekViewHolder) holder).weekView;

            WeekViewEntity weekViewEntity = getFirstDateFromPosition(position);
            if (weekViewEntity != null) {
                if (!weekViewEntity.monthTitle) {
                    if (mPickType == DatePickerView.PickType.RANGE_PICKER) {
                        weekView.setCalendarParams(mPickType, mAvailableStartCalendar, mAvailableEndCalendar, mStartCalendar, mEndCalendar, mStartDateMarkText, mEndDateMarkText, weekViewEntity);
                    } else {
                        weekView.setCalendarParams(mPickType, mShowLunar, mAvailableStartCalendar, mAvailableEndCalendar, mCheckedCalendar, mCheckedDateMarkText, weekViewEntity, mExtendMap);
                    }
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return mTotalWeekCount;
    }

    @Override
    public int getItemViewType(int position) {
        WeekViewEntity weekViewEntity = getFirstDateFromPosition(position);
        if (weekViewEntity != null) {
            if (weekViewEntity.monthTitle) {
                return TYPE_MONTH_HEADER_VIEW;
            } else {
                return TYPE_WEEK_VIEW;
            }
        }
        return TYPE_WEEK_VIEW;
    }

    public static class WeekViewHolder extends RecyclerView.ViewHolder {
        private WeekView weekView;

        public WeekViewHolder(View itemView, WeekView.OnDayClickListener onDayClickListener) {
            super(itemView);
            weekView = (WeekView) itemView;
            weekView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT));
            weekView.setClickable(true);
            weekView.setOnDayClickListener(onDayClickListener);
        }
    }

    public static class MonthHeaderViewHolder extends RecyclerView.ViewHolder {
        private MonthHeaderView monthHeaderView;

        public MonthHeaderViewHolder(View itemView) {
            super(itemView);
            monthHeaderView = (MonthHeaderView) itemView;
            monthHeaderView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT));
        }
    }

    @Override
    public void onDayClick(Calendar calendar) {
        if (mPickType == DatePickerView.PickType.RANGE_PICKER) {
            if ((mStartCalendar != null && mEndCalendar != null) || mStartCalendar == null) {
                //已经选择了开始结束日期，或者没有选择开始日期，再次点击选择开始日期
                mStartCalendar = calendar;
                mEndCalendar = null;
                mOnDatePickerListener.onDatePicked(mStartCalendar);
                notifyDataSetChanged();
            } else if (mStartCalendar != null && mEndCalendar == null) {
                //如果选择了开始日期，再次点击选择的是结束日期
                if (calendar.compareTo(mStartCalendar) > 0) {
                    //如果选择日期>开始日期，则选择日期为结束日期
                    mEndCalendar = calendar;
                    mOnDatePickerListener.onDateRangePicked(mStartCalendar, mEndCalendar);
                    notifyDataSetChanged();
                } else if (calendar.compareTo(mStartCalendar) < 0) {
                    //如果选择日期>开始日期，则选择日期为开始日期，相当于重选选择
                    mStartCalendar = calendar;
                    mOnDatePickerListener.onDatePicked(mStartCalendar);
                    notifyDataSetChanged();
                } else {
                    //不做任何处理
                }
            }
        } else {
            if (mCheckedCalendar == null || !(calendar.compareTo(mCheckedCalendar) == 0)) {
                mCheckedCalendar = calendar;
                mOnDatePickerListener.onDatePicked(mCheckedCalendar);
                notifyDataSetChanged();
            }
        }
    }

    /**
     * 计算每个月的周行数，同时返回总行数
     *
     * @return
     */
    private int initWeekCountOfMonthList() {
        Calendar startCalendar = (Calendar) mAvailableStartCalendar.clone();
        startCalendar.set(Calendar.DAY_OF_MONTH, 1);
        startCalendar.set(Calendar.HOUR_OF_DAY, 0);
        startCalendar.set(Calendar.MINUTE, 0);
        startCalendar.set(Calendar.SECOND, 0);
        startCalendar.set(Calendar.MILLISECOND, 0);

        Calendar endCalendar = (Calendar) mAvailableEndCalendar.clone();
        endCalendar.set(Calendar.DAY_OF_MONTH, 1);
        endCalendar.set(Calendar.HOUR_OF_DAY, 0);
        endCalendar.set(Calendar.MINUTE, 0);
        endCalendar.set(Calendar.SECOND, 0);
        endCalendar.set(Calendar.MILLISECOND, 0);

        mWeekCountOfMonthList = new ArrayList<>();
        int totalCount = 0;
        while (startCalendar.compareTo(endCalendar) <= 0) {
            int weekCount = CalendarUtils.calculateRowCount(startCalendar);
            mWeekCountOfMonthList.add(weekCount + 1);
            totalCount += weekCount + 1;
            startCalendar.add(Calendar.MONTH, 1);
        }
        return totalCount;
    }

    private WeekViewEntity getFirstDateFromPosition(int position) {
        Calendar startCalendar = (Calendar) mAvailableStartCalendar.clone();
        startCalendar.set(Calendar.DAY_OF_MONTH, 1);
        startCalendar.set(Calendar.HOUR_OF_DAY, 0);
        startCalendar.set(Calendar.MINUTE, 0);
        startCalendar.set(Calendar.SECOND, 0);
        startCalendar.set(Calendar.MILLISECOND, 0);

        for (int i = 0; i < mWeekCountOfMonthList.size(); i++) {
            if (position - mWeekCountOfMonthList.get(i) >= 0) {
                startCalendar.add(Calendar.MONTH, 1);
                position -= mWeekCountOfMonthList.get(i);
            } else {
                if (position == 0) {
                    //title
                    WeekViewEntity weekViewEntity = new WeekViewEntity();
                    weekViewEntity.monthTitle = true;
                    weekViewEntity.monthTitleCalendar = startCalendar;
                    return weekViewEntity;
                } else if (position == 1) {
                    //第一行
                    WeekViewEntity weekViewEntity = new WeekViewEntity();
                    weekViewEntity.monthTitle = false;
                    weekViewEntity.startDrawCalendar = startCalendar;
                    weekViewEntity.firstWeekOfMonth = true;
                    return weekViewEntity;
                } else if (position == mWeekCountOfMonthList.get(i) - 1) {
                    //最后一行
                    int offset = CalendarUtils.findDayOffset(startCalendar);
                    int day = (position - 1) * CalendarUtils.DAY_COUNT_PER_ROW - offset + 1;
                    startCalendar.set(Calendar.DAY_OF_MONTH, day);
                    WeekViewEntity weekViewEntity = new WeekViewEntity();
                    weekViewEntity.monthTitle = false;
                    weekViewEntity.startDrawCalendar = startCalendar;
                    weekViewEntity.lastWeekOfMonth = true;
                    return weekViewEntity;
                } else {
                    //其他行
                    int offset = CalendarUtils.findDayOffset(startCalendar);
                    int day = (position - 1) * CalendarUtils.DAY_COUNT_PER_ROW - offset + 1;
                    startCalendar.set(Calendar.DAY_OF_MONTH, day);
                    WeekViewEntity weekViewEntity = new WeekViewEntity();
                    weekViewEntity.monthTitle = false;
                    weekViewEntity.startDrawCalendar = startCalendar;
                    return weekViewEntity;
                }
            }
        }
        return null;
    }
}
