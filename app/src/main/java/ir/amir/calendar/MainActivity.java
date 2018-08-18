package ir.mirrajabi.pc.sample;

import android.content.Context;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ir.mirrajabi.persiancalendar.PersianCalendarView;
import ir.mirrajabi.persiancalendar.core.Constants;
import ir.mirrajabi.persiancalendar.core.PersianCalendarHandler;
import ir.mirrajabi.persiancalendar.core.interfaces.OnDayClickedListener;
import ir.mirrajabi.persiancalendar.core.interfaces.OnMonthChangedListener;
import ir.mirrajabi.persiancalendar.core.models.CalendarEvent;
import ir.mirrajabi.persiancalendar.core.models.PersianDate;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private PersianCalendarView persianCalendarView;
    private Typeface mTypeface;
    private Context mContext;

    private void initTypeface() {
        if (mTypeface == null) {
            mTypeface = Typeface.createFromAsset(mContext.getAssets(), Constants.FONT_PATH);
        }
    }

    public void setFont(TextView textView) {
        initTypeface();
        textView.setTypeface(mTypeface);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        persianCalendarView  = (PersianCalendarView)findViewById(R.id.persian_calendar);
        final PersianCalendarHandler calendar = persianCalendarView.getCalendar();
        final PersianDate today = calendar.getToday();
        final TextView txtYear = (TextView) findViewById(R.id.txt_year);
        final ImageView next = (ImageView) findViewById(R.id.ic_next);
        final ImageView prev = (ImageView) findViewById(R.id.ic_prev);
        String monthName = calendar.getMonthName(today);

        txtYear.setText( monthName + "     " + calendar.formatNumber(today.getYear()));
        mContext = this;
        setFont(txtYear);

        calendar.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onChanged(PersianDate date) {
//                Toast.makeText(MainActivity.this, calendar.getMonthName(date),Toast.LENGTH_SHORT).show();
                txtYear.setText( calendar.getMonthName(date) + "     " + date.getYear());
            }
        });
        persianCalendarView.setOnDayClickedListener(new OnDayClickedListener() {
            @Override
            public void onClick(PersianDate date) {
                Toast.makeText(MainActivity.this, date.getDayOfMonth()+" "+calendar.getMonthName(date)+" "+date.getYear()
                        , Toast.LENGTH_LONG).show();
            }
        });
        next.setOnClickListener(this);
        prev.setOnClickListener(this);
        calendar.setHighlightOfficialEvents(false);

        calendar.setColorBackground(getResources().getColor(android.R.color.white));
        persianCalendarView.update();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.ic_next) {
            persianCalendarView.goToPreviousMonth();
        } else if(view.getId() == R.id.ic_prev) {
            persianCalendarView.goToNextMonth();
        }
    }
}
