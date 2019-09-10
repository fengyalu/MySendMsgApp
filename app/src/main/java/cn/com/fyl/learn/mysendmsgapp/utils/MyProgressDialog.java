package cn.com.fyl.learn.mysendmsgapp.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import cn.com.fyl.learn.mysendmsgapp.R;

public class MyProgressDialog extends ProgressDialog {
    private Context context;
    private ImageView tipImage;
    private TextView tipTextView;
    private Animation animation;

    public MyProgressDialog(Context context, int theme) {
        super(context, R.style.myProgressDialog);
        this.context = context;
    }

    public void change(String s){
        tipTextView.setText(s);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = LayoutInflater.from(context).inflate(R.layout.loading_dialog, null);
        tipImage = (ImageView) v.findViewById(R.id.img);
        tipTextView = (TextView)v.findViewById(R.id.tipTextView);
        animation = AnimationUtils.loadAnimation(context, R.anim.load_animation);
        setCancelable(false);
        setContentView(v);
    }

    @Override
    public void show() {
        super.show();
        tipImage.startAnimation(animation);
    }

}
