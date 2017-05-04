package cn.xudaodao.android.calendarview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import java.util.Calendar;

/**
 * Created by chaohui.yang on 2016/3/23.
 */
public class MonthHeaderView extends View {
    private int mMonthTitleTextSize;
    private int mMonthTitleTextColor;
    private Paint mMonthTitleTextPaint;
    private int mMonthTitlePaddingTop;

    private int mMonthHeaderHeight;

    private int mWidth;
    private int mYear;
    private int mMonth;

    public MonthHeaderView(Context context) {
        this(context, null);
    }

    public MonthHeaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MonthHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Resources resources = getResources();
        mMonthHeaderHeight = resources.getDimensionPixelOffset(R.dimen.month_header_height);

        mMonthTitleTextColor = resources.getColor(R.color.month_title_text_color);
        mMonthTitleTextSize = resources.getDimensionPixelSize(R.dimen.month_title_text_size);

        mMonthTitlePaddingTop = resources.getDimensionPixelOffset(R.dimen.month_title_padding_top);

        mMonthTitleTextPaint = new Paint();
        mMonthTitleTextPaint.setAntiAlias(true);
        mMonthTitleTextPaint.setTextSize(mMonthTitleTextSize);
        mMonthTitleTextPaint.setColor(mMonthTitleTextColor);
        mMonthTitleTextPaint.setTextAlign(Paint.Align.CENTER);
        mMonthTitleTextPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), mMonthHeaderHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int x = mWidth / 2;
        String monthTitle = CalendarUtils.getMonthTitle(mYear, mMonth);
        int y = mMonthTitlePaddingTop + getTextBoundsTop(mMonthTitleTextPaint, monthTitle);
        canvas.drawText(monthTitle, x, y, mMonthTitleTextPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
    }

    private int getTextBoundsTop(Paint paint, String text) {
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return -bounds.top;
    }

    public void setParams(Calendar calendar) {
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
    }
}
