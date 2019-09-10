package cn.com.fyl.learn.mysendmsgapp.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.com.fyl.learn.mysendmsgapp.R;
import cn.com.fyl.learn.mysendmsgapp.databinding.ActivityToosBinding;
import cn.com.fyl.learn.mysendmsgapp.localdata.LocalDataDBManager;
import cn.com.fyl.learn.mysendmsgapp.localdata.TTempPlateContent;
import cn.com.fyl.learn.mysendmsgapp.localdata.TTemplate;
import cn.com.fyl.learn.mysendmsgapp.popwindow.PopAddTemplate;
import cn.com.fyl.learn.mysendmsgapp.service.FixedTimeService;
import cn.com.fyl.learn.mysendmsgapp.utils.DateFormatUtils;
import cn.com.fyl.learn.mysendmsgapp.view.TimePicker;

public class ToosActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "ToosActivity";

    private ActivityToosBinding binding;

    private TimePicker timePicker;

    private PopAddTemplate popAddTemplate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_toos);
        initView();
        initDatePicker();

    }

    private void initView() {
        popAddTemplate=new PopAddTemplate(this);
        binding.title.setOnClickListener(this);
        binding.timeToos.setOnClickListener(this);
        binding.ok.setOnClickListener(this);
        binding.addTemplate.setOnClickListener(this);
    }

    private void initDatePicker() {
        final String systemTime = getSystemTime();
        final long beginTimestamp = DateFormatUtils.str2Long(systemTime, false);
        long endTimestamp = System.currentTimeMillis();

        String time = getStartTime();
        if (!TextUtils.isEmpty(time)){
            binding.timeToos.setText(systemTime+" "+time);
        }else {
            binding.timeToos.setText(DateFormatUtils.long2Str(beginTimestamp, true));
        }

        // 通过时间戳初始化日期，毫秒级别
        timePicker = new TimePicker(this, new TimePicker.Callback() {

            @Override
            public void onTimeSelected(long timestamp) {
                binding.timeToos.setText(DateFormatUtils.long2Str(timestamp, true));
            }
        }, beginTimestamp, endTimestamp);

        // 不允许点击屏幕或物理返回键关闭
        timePicker.setCancelable(true);
        // 不显示时和分
        timePicker.setCanShowPreciseTime(true);
        // 不允许循环滚动
        timePicker.setScrollLoop(true);
        // 不允许滚动动画
        timePicker.setCanShowAnim(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title:
                finish();
                break;
            case R.id.time_toos:
                timePicker.show(binding.timeToos.getText().toString());
                break;
            case R.id.add_template:
                popAddTemplate.setTxtTitle("增加短信模板")
                        .setTxtLeft("取消")
                        .setTxtRight("确定")
                        .setCancelable(false)
                        .show(PopAddTemplate.Gravitys.CENTER)
                        .setOnLeftButtonClickListener(new PopAddTemplate.OnLeftButtonClickListener() {
                            @Override
                            public void onLeftClick() {
                                popAddTemplate.dismiss();
                            }
                        })
                        .setOnRightButtonClickListener(new PopAddTemplate.OnRightButtonClickListener() {

                            @Override
                            public void onRightClick(String content) {
                                try {
                                    if (!TextUtils.isEmpty(content)){
                                        LocalDataDBManager.getInstance(ToosActivity.this).deleteAllTemplateContent();
                                        TTempPlateContent tTempPlateContent=new TTempPlateContent();
                                        tTempPlateContent.setContent(content);
                                        LocalDataDBManager.getInstance(ToosActivity.this).saveOneTemplateContent(tTempPlateContent);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                popAddTemplate.dismiss();
                            }
                        });
                break;
            case R.id.ok:
               LocalDataDBManager.getInstance(ToosActivity.this).deleteAllTemplate();
                String systemTime = getSystemTime();
                String time = binding.timeToos.getText().toString().trim();
                if (!TextUtils.isEmpty(time)){
                    time=time.replace(systemTime,"");
                    TTemplate tTemplate=new TTemplate();
                    tTemplate.setTime(time);
                    LocalDataDBManager.getInstance(ToosActivity.this).saveOneTemplate(tTemplate);
                }
                Intent startIntent = new Intent(ToosActivity.this, FixedTimeService.class);
                startService(startIntent);
                finish();
                break;
            default:
                break;
        }
    }


    private String getStartTime() {
        String time=null;
        List<TTemplate> tTemplates = LocalDataDBManager.getInstance(this).queryAllTTemplate();
        if (null!=tTemplates&&!tTemplates.isEmpty()){
            time = tTemplates.get(0).getTime();
        }
        return time;
    }

    private String getSystemTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());
        //获取当前时间     
        String time = formatter.format(curDate);
        return time;
    }
}
