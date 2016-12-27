package com.gerli.gerli.TimedRemind;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import com.gerli.handsomeboy.gerlisqlitedemo.GerliDatabaseManager;

/**
 * Service to notify the user record the accounting information
 * <p/>
 * Code by 瀚勳
 */
public class SetAlarmService extends Service {
    // Log tag
    private final static String TAG = "## SetAlarmService";

    // The time variable related to remind time
    private final static int MORNING = 1;
    private final static int AFTERNOON = 17;
    private final static int NIGHT = 23;
    private final static ArrayList<Integer> alarmHour = new ArrayList<>(
        Arrays.asList(9, 10, MORNING, AFTERNOON, NIGHT));
    private final static int alarmMinute = 46;
    private final static int alarmSecond = 0;

    // System intent symbol
    public final static String ACTION_BROADCAST = "ACTION_MY_BROADCAST";

    // Thread variable
    Thread doInBackground;

    // Other variable
    long today, endTime;
    int date;

    // SQLite manager
    GerliDatabaseManager manager;

    // Control flag to determine if break the system notification setting looping
    private boolean isContinue = true;

    // Runnable to implement the system notification
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            while (isContinue) {
                Calendar cal = Calendar.getInstance();
                if (date != cal.get(Calendar.DATE))
                    setTimes();
                // 設定於 1 min 後執行
                cal.add(Calendar.MINUTE, 1);
                int alarmH = cal.get(Calendar.HOUR_OF_DAY);
                int alarmMin = cal.get(Calendar.MINUTE);
                int alarmSec = cal.get(Calendar.SECOND);
                if (alarmHour.contains(alarmH) && alarmMinute == alarmMin && alarmSecond == alarmSec) {
                    boolean flag = true;
                    flag = !hasDone(cal.getTimeInMillis());// <--- comment out to change to version 1
                    if (flag) {
                        Log.d(TAG, "SetAlarm");
                        Intent intent = new Intent(ACTION_BROADCAST);

                        PendingIntent pi = PendingIntent.getBroadcast(SetAlarmService.this, 1, intent, PendingIntent.FLAG_ONE_SHOT);

                        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pi);
                    }

                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate() executed");
        setTimes();
        manager = new GerliDatabaseManager(this);
        if (doInBackground == null) {
            doInBackground = new Thread(runnable);
            doInBackground.start();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand() executed");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() executed");
        isContinue = false;
        try {
            doInBackground.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Return if we record the account info in the day
     *
     * @param currentTime The current time
     * @return If we have keyed in the info
     */
    private boolean hasDone(long currentTime) {
        boolean done = false;
        long latestTime = manager.getLatestRecordTime().getTime();
        if (today <= currentTime && currentTime < endTime) {
            done = latestTime - today >= 0;
        }
        return done;
    }

    /**
     * Set the time object for the specific constants
     */
    private void setTimes() {
        Calendar calendar = Calendar.getInstance();
        date = calendar.get(Calendar.DATE);

        //TODAY in long represent
        calendar.clear(Calendar.MILLISECOND);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.HOUR_OF_DAY);
        today = calendar.getTimeInMillis();

        //TODAY  endTime in long represent
        calendar.set(Calendar.HOUR_OF_DAY, NIGHT);
        calendar.set(Calendar.MINUTE, alarmMinute);
        calendar.set(Calendar.SECOND, alarmSecond + 1);
        Log.d(TAG, calendar.getTime().toString());
        endTime = calendar.getTimeInMillis();
    }
}
