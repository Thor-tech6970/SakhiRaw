package com.example.sakhiraw;

import android.app.Notification;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.net.Uri;
import android.os.CountDownTimer;
import android.util.Log;

public class StartCallService extends JobService {

    boolean jobCancelledInBetween = false ;

    private static  final String TAG = "Class" ;

    Intent intent;

    @Override
    public boolean onStartJob(JobParameters params) {

        Log.e(TAG, " Job start method called " );

        String phone = "tel:" + "8219948385" ;

        intent = new Intent(Intent.ACTION_CALL);

        intent.setData(Uri.parse(phone));

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);

        jobFinished(params, false);


//        doBackgroundWork(params);

        return true;

    }

//    private void doBackgroundWork(JobParameters params) {



//        new Thread(new Runnable() {
//            @Override
//            public void run() {

//                for(int i=0 ; i<11 ; i++){

//                    if(jobCancelledInBetween){

//                        return;

//                    }
//
//

//                    Log.e(TAG, "Calling active at : " +i + "th second" );

//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }


//                }

//                Log.e(TAG, "Call rang succesfully for 10 seconds" );

//                jobFinished(params , false);


//            }
//        }).start();
//    }

    @Override
    public boolean onStopJob(JobParameters params) {

        Log.e(TAG, "Job cancelled before completion" );

        jobCancelledInBetween = true;

        return true;
    }
}
