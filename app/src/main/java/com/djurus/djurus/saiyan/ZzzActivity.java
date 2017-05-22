package com.djurus.djurus.saiyan;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.djurus.saiyan.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

public class ZzzActivity extends AppCompatActivity {
    private HashMap<CalendarDay,Integer> sleepCapsule = new HashMap<CalendarDay,Integer>();
    private MaterialCalendarView mcv;
    private TextView hoursSlept;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zzz);

        sleepCapsule.put(new CalendarDay(2017,4,21), new Integer(6));
        sleepCapsule.put(new CalendarDay(2017,4,20), new Integer(2));
        sleepCapsule.put(new CalendarDay(2017,4,19), new Integer(14));
        sleepCapsule.put(new CalendarDay(2017,4,18), new Integer(8));
        sleepCapsule.put(new CalendarDay(2017,4,17), new Integer(10));


        mcv = (MaterialCalendarView)findViewById(R.id.calendarView);
        hoursSlept = (TextView)findViewById(R.id.hoursSlept);
        mcv.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                if(sleepCapsule.containsKey(date)){

                    hoursSlept.setText(sleepCapsule.get(date).toString());
                }
                else{
                    hoursSlept.setText("ZZZ");
                }

            }
        });


        new ApiSimulator().executeOnExecutor(Executors.newSingleThreadExecutor());




    }
    public class EventDecorator implements DayViewDecorator {

        private final int color;
        private final HashSet<CalendarDay> dates;

        public EventDecorator(int color, Collection<CalendarDay> dates) {
            this.color = color;
            this.dates = new HashSet<>(dates);
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return dates.contains(day);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new DotSpan(10, color));
        }
    }
    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {

        @Override
        protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, -2);
            ArrayList<CalendarDay> dates = new ArrayList<>();
            for (int i = 0; i < 30; i++) {
                CalendarDay day = CalendarDay.from(calendar);
                dates.add(day);
                calendar.add(Calendar.DATE, 5);
            }

            return dates;
        }

        @Override
        protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);

            if (isFinishing()) {
                return;
            }

            ArrayList<CalendarDay> terribleSleep = new ArrayList<>();
            ArrayList<CalendarDay> badSleep = new ArrayList<>();
            ArrayList<CalendarDay> goodSleep = new ArrayList<>();
            ArrayList<CalendarDay> amazingSleep = new ArrayList<>();
            ArrayList<CalendarDay> slumberSleep = new ArrayList<>();



            Iterator it = sleepCapsule.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                System.out.println(pair.getKey() + " = " + pair.getValue());
                if ((int)pair.getValue() <= 4){
                    terribleSleep.add((CalendarDay)pair.getKey());
                }
                else if ((int)pair.getValue() <= 6){
                    badSleep.add((CalendarDay)pair.getKey());
                }
                else if ((int)pair.getValue() <= 8){
                    goodSleep.add((CalendarDay)pair.getKey());
                }
                else if ((int)pair.getValue() <= 12){
                    amazingSleep.add((CalendarDay)pair.getKey());
                }
                else if ((int)pair.getValue() <= 24){
                    slumberSleep.add((CalendarDay)pair.getKey());
                }

//                it.remove(); // avoids a ConcurrentModificationException
            }

            mcv.addDecorator(new EventDecorator(Color.RED, terribleSleep));
            mcv.addDecorator(new EventDecorator(Color.YELLOW, badSleep));
            mcv.addDecorator(new EventDecorator(Color.GREEN, goodSleep));
            mcv.addDecorator(new EventDecorator(Color.BLUE, amazingSleep));
            mcv.addDecorator(new EventDecorator(Color.GRAY, slumberSleep));
        }
    }
}
