package cn.com.fyl.learn.mysendmsgapp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import cn.com.fyl.learn.mysendmsgapp.service.FixedTimeService;

/**
 * Created by Administrator on 2019/8/8 0008.
 */

public class BootBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(context, FixedTimeService.class);
        context.startService(service);
    }
}
