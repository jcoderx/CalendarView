package cn.xudaodao.android.calendarview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.Calendar;

/**
 * Created by chaohui.yang on 2016/3/21.
 */
public class WeekHeaderView extends View {
    private int mHeight;

    private int mWeekdayTextColor;
    private int mWeekendTextColor;

    private int mTextSize;

    private Paint mWeekdayTextPaint;
    private Paint mWeekendTextPaint;

    public WeekHeaderView(Context context) {
        this(context, null);
    }

    public WeekHeaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeekHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Resources resources = getResources();
        mHeight = resources.getDimensionPixelOffset(R.dimen.height);

        mWeekdayTextColor = resources.getColor(R.color.weekday_text_color);
        mWeekendTextColor = resources.getColor(R.color.weekend_text_color);

        mTextSize = resources.getDimensionPixelSize(R.dimen.text_size);

        mWeekdayTextPaint = new Paint();
        mWeekdayTextPaint.setAntiAlias(true);
        mWeekdayTextPaint.setTextSize(mTextSize);
        mWeekdayTextPaint.setColor(mWeekdayTextColor);
        mWeekdayTextPaint.setTextAlign(Paint.Align.CENTER);
        mWeekdayTextPaint.setStyle(Paint.Style.FILL);

        mWeekendTextPaint = new Paint();
        mWeekendTextPaint.setAntiAlias(true);
        mWeekendTextPaint.setTextSize(mTextSize);
        mWeekendTextPaint.setColor(mWeekendTextColor);
        mWeekendTextPaint.setTextAlign(Paint.Align.CENTER);
        mWeekendTextPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int avgWidth = width / (7 * 2);
        int y = mHeight/2 - getFontBaseline(mWeekdayTextPaint);
        for (int i = Calendar.SUNDAY; i <= Calendar.SATURDAY; i++) {
            int x = avgWidth * (2 * i - 1);
            switch (i) {
                case Calendar.SUNDAY:
                    canvas.drawText("周日", x, y, mWeekendTextPaint);
                    break;
                case Calendar.MONDAY:
                    canvas.drawText("周一", x, y, mWeekdayTextPaint);
                    break;
                case Calendar.TUESDAY:
                    canvas.drawText("周二", x, y, mWeekdayTextPaint);
                    break;
                case Calendar.WEDNESDAY:
                    canvas.drawText("周三", x, y, mWeekdayTextPaint);
                    break;
                case Calendar.THURSDAY:
                    canvas.drawText("周四", x, y, mWeekdayTextPaint);
                    break;
                case Calendar.FRIDAY:
                    canvas.drawText("周五", x, y, mWeekdayTextPaint);
                    break;
                case Calendar.SATURDAY:
                    canvas.drawText("周六", x, y, mWeekendTextPaint);
                    break;
            }
        }
    }

    private int getFontBaseline(Paint paint) {
        Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
        return (fontMetricsInt.bottom-fontMetricsInt.top)/2 + fontMetricsInt.top;
    }
}
