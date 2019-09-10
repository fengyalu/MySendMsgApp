package cn.com.fyl.learn.mysendmsgapp.utils;

import android.content.Context;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;


public class UIUtils {

    public static void Toast(Context context,String text) {
        Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
    }

    public static void ToastLong(Context context,String text, boolean isLong) {
        Toast.makeText(context,text,isLong == true?Toast.LENGTH_LONG:Toast.LENGTH_SHORT).show();
    }

    public static Toast ToastL(Context context,String text) {
       return Toast.makeText(context,text,Toast.LENGTH_LONG);
    }
    public static void showMyToast(final Toast toast, final int cnt) {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                toast.show();
            }
        }, 0, 3000);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                toast.cancel();
                timer.cancel();
            }
        }, cnt);
    }
}
