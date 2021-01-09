package com.example.sakhiraw;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Timer timer;

    ViewPager viewPager;

    int currentPage;

    Spinner spinner;

    Button dailyButton , alternateDaysButton , selectDaysButton;

    LinearLayout linearLayout ;

    Button scheduleButton , cancelScheduleButton ;

    private static final String TAG = "Main Activity" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayout = findViewById(R.id.ll7);

        linearLayout.setVisibility(View.GONE);

        dailyButton = findViewById(R.id.daily);

        alternateDaysButton = findViewById(R.id.alternate);

        selectDaysButton = findViewById(R.id.selectDays);

        viewPager = findViewById(R.id.viewPager);

        ImageAdapter adapterView = new ImageAdapter(this);

        viewPager.setAdapter(adapterView);

        spinner = findViewById(R.id.spinner);

        scheduleButton = findViewById(R.id.scheduleButton);

        cancelScheduleButton = findViewById(R.id.cancelScheduleButton);

        scheduleButton.setBackgroundColor(Color.parseColor("#a9a9a9"));
        cancelScheduleButton.setBackgroundColor(Color.parseColor("#a9a9a9"));

        scheduleButton.setTextColor(Color.parseColor("#000000"));
        cancelScheduleButton.setTextColor(Color.parseColor("#000000"));


        final Handler handler = new Handler();
        final Runnable Update = () -> {
            if (currentPage == 3) {
                currentPage = 0;
            }
            viewPager.setCurrentItem(currentPage++, true);
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 0, 1500);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this , R.array.numbers , android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        dailyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dailyButton.setBackgroundColor(Color.parseColor("#129CFF"));

                dailyButton.setTextColor(Color.parseColor("#FFFFFF"));

                alternateDaysButton.setBackgroundColor(Color.parseColor("#a9a9a9"));

                selectDaysButton.setBackgroundColor(Color.parseColor("#a9a9a9"));

                alternateDaysButton.setTextColor(Color.parseColor("#000000") );

                selectDaysButton.setTextColor(Color.parseColor("#000000"));

                linearLayout.setVisibility(View.GONE);



            }
        });


        alternateDaysButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alternateDaysButton.setBackgroundColor(Color.parseColor("#129CFF"));

                alternateDaysButton.setTextColor(Color.parseColor("#FFFFFF"));

                selectDaysButton.setBackgroundColor(Color.parseColor("#a9a9a9"));

                dailyButton.setBackgroundColor(Color.parseColor("#a9a9a9"));

                dailyButton.setTextColor(Color.parseColor("#000000") );

                selectDaysButton.setTextColor(Color.parseColor("#000000") );

                linearLayout.setVisibility(View.GONE);

            }
        });

        selectDaysButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                selectDaysButton.setBackgroundColor(Color.parseColor("#129CFF"));

                selectDaysButton.setTextColor(Color.parseColor("#FFFFFF"));

                dailyButton.setBackgroundColor(Color.parseColor("#a9a9a9"));

                dailyButton.setTextColor(Color.parseColor("#000000") );

                alternateDaysButton.setBackgroundColor(Color.parseColor("#a9a9a9"));

                alternateDaysButton.setTextColor(Color.parseColor("#000000") );

                linearLayout.setVisibility(View.VISIBLE);



            }
        });



        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this, "Call scheduled", Toast.LENGTH_SHORT).show();

                scheduleButton.setBackgroundColor(Color.parseColor("#129CFF"));

                scheduleButton.setTextColor(Color.parseColor("#FFFFFF"));

                cancelScheduleButton.setBackgroundColor(Color.parseColor("#a9a9a9"));

                cancelScheduleButton.setTextColor(Color.parseColor("#000000"));


                ComponentName componentName = new ComponentName(getApplicationContext()  , StartCallService.class);

                JobInfo jobInfo = new JobInfo.Builder(123 , componentName)
                                 .setPersisted(true)
                                  .setPeriodic(15*60*1000)
                                  .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                                  .build();


                JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);

                int resultCode = jobScheduler.schedule(jobInfo);

                if(resultCode == jobScheduler.RESULT_SUCCESS){

                    Log.e(TAG, "Job scheduled succesfully" );

                }

                else {

                    Log.e(TAG, "Job scheduling failed" );
                }

            }
        });


        cancelScheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this, "Call scheduling cancelled", Toast.LENGTH_SHORT).show();

                cancelScheduleButton.setBackgroundColor(Color.parseColor("#129CFF"));

                cancelScheduleButton.setTextColor(Color.parseColor("#FFFFFF"));

                scheduleButton.setBackgroundColor(Color.parseColor("#a9a9a9"));

                scheduleButton.setTextColor(Color.parseColor("#000000"));

                JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);

                jobScheduler.cancel(123);

                Log.e(TAG, "Job cancelled willingly" );


            }
        });


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String text = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}