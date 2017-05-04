package cn.xudaodao.android.calendarview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements DatePickerView.OnDatePickerListener {
    DatePickerView datePickerView;
    long x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x = System.currentTimeMillis();
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(MainActivity.this, MainActivity.class);
                //startActivity(intent);

                Map<String, ExtendEntity> map = new HashMap<>();
                int color = 0xff888888;
                for (int i = 0; i < 450; i++) {
                    if (i % 7 == 0) {
                        color = 0xff4499ff;
                    }else{
                        color = 0xff888888;
                    }
                    ExtendEntity entity = new ExtendEntity();
                    entity.text = "¥248";
                    entity.color = color;
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.MONTH,-1);
                    calendar.add(Calendar.DAY_OF_MONTH, i);
                    String date = CalendarUtils.formatCalendar(calendar,"yyyy-MM-dd");
                    map.put(date,entity);
                }
                datePickerView.setExtendMap(map);
            }
        });

        Calendar calendar = CalendarUtils.getCalendar();


        Calendar availableStartCalendar = (Calendar) calendar.clone();
        availableStartCalendar.add(Calendar.MONTH, -1);

        Calendar availableEndCalendar = (Calendar) calendar.clone();
        availableEndCalendar.add(Calendar.MONTH, 6);

        Calendar startCalendar = (Calendar) calendar.clone();
        startCalendar.set(Calendar.DAY_OF_MONTH, 3);

        Calendar endCalendar = (Calendar) calendar.clone();
        endCalendar.add(Calendar.MONTH, 1);
        endCalendar.set(Calendar.DAY_OF_MONTH, 6);

        String startDateMarkText = "入住";
        String endDateMarkText = "离店";

        datePickerView = (DatePickerView) findViewById(R.id.datepickerview);
        //datePickerView.setRangePickerParams(availableStartCalendar, availableEndCalendar, startCalendar, endCalendar, startDateMarkText, endDateMarkText, this);


        datePickerView.setSinglePickerParams(false, availableStartCalendar, availableEndCalendar, null, "出发", this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, String.valueOf(System.currentTimeMillis() - x), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDatePicked(Calendar calendar) {
        String date = CalendarUtils.formatCalendar(calendar, "yyyy-MM-dd HH:mm:ss");
        Toast.makeText(this, date, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDateRangePicked(Calendar startCalendar, Calendar endCalendar) {
        String startDate = CalendarUtils.formatCalendar(startCalendar, "yyyy-MM-dd HH:mm:ss");
        String endDate = CalendarUtils.formatCalendar(endCalendar, "yyyy-MM-dd HH:mm:ss");
        Toast.makeText(this, startDate + "----" + endDate, Toast.LENGTH_SHORT).show();
    }
}
