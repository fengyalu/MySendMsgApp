package cn.com.fyl.learn.mysendmsgapp.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.com.fyl.learn.mysendmsgapp.activity.MainActivity;
import cn.com.fyl.learn.mysendmsgapp.localdata.LocalDataDBManager;
import cn.com.fyl.learn.mysendmsgapp.localdata.TTemplate;

/**
 * Created by Administrator on 2019/8/2 0002.
 */

public class FixedTimeService extends Service {

    private String setTime;
    private Thread thread;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        List<TTemplate> tTemplates = LocalDataDBManager.getInstance(this).queryAllTTemplate();
        if (null!=tTemplates&&!tTemplates.isEmpty()){
            setTime = tTemplates.get(0).getTime().toString().trim();
        }
        timeOutTask();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                    Intent intent=new Intent(FixedTimeService.this,MainActivity.class);
                    intent.putExtra("serviceFlag","OK");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Service跳转到Activity 要加这个标记
                    startActivity(intent);
            }
            super.handleMessage(msg);

        }
    };

    private void timeOutTask() {
        thread = new Thread(new MyThread());
        thread.start();
    }



    class MyThread extends Thread {
        @Override
        public void run() {
               while (true) {
                   try {
                       Thread.sleep(1000);//每隔1s执行一次
                       Message msg = new Message();
                       msg.what = 1;
                       String time = getTime();
                       if (time.equals(setTime)){
                           Log.i("*******", "当前时间==设定时间"+time+"=="+setTime);
                           handler.sendMessage(msg);
                       }
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }

               }
        }
    }

    private String getTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        //获取当前时间     
       String time = formatter.format(curDate);
        return time;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
