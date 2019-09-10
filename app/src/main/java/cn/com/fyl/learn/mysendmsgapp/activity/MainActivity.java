package cn.com.fyl.learn.mysendmsgapp.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.telephony.SmsManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.com.fyl.learn.mysendmsgapp.R;
import cn.com.fyl.learn.mysendmsgapp.adapter.UserListAdapter;
import cn.com.fyl.learn.mysendmsgapp.databinding.ActivityMainBinding;
import cn.com.fyl.learn.mysendmsgapp.localdata.LocalDataDBManager;
import cn.com.fyl.learn.mysendmsgapp.localdata.Person;
import cn.com.fyl.learn.mysendmsgapp.localdata.TTempPlateContent;
import cn.com.fyl.learn.mysendmsgapp.localdata.TUser;
import cn.com.fyl.learn.mysendmsgapp.utils.UIUtils;
import cn.com.fyl.learn.mysendmsgapp.utils.ValidateUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private static final String TAG = "MainActivity";

    private static final int REQUEST_ADD_USER_CODE = 1000;
    private static final int REQUEST_TOOS_CODE = 1001;

    private ActivityMainBinding binding;
    private UserListAdapter userListAdapter;
    private List<Person> personList;

    private int IDs = 0;
    private List<TUser> birthdayList;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initView();
        initData();
        autoSend();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void autoSend() {
        Intent intent=getIntent();
        String serviceFlag = intent.getStringExtra("serviceFlag");
        if ("OK".equals(serviceFlag)){
            List<TTempPlateContent> tTempPlateContents = LocalDataDBManager.getInstance(this).queryAllTemplateContemt();
            if (null!=tTempPlateContents&&!tTempPlateContents.isEmpty()){
                String content = tTempPlateContents.get(0).getContent();
                if (null != birthdayList && !birthdayList.isEmpty()) {
                    for (TUser user:birthdayList) {
                        Person person=new Person();
                        person.setUserName(user.getName());
                        person.setPhoneNumber(user.getMobilePhone());
                        person.setMsgContent(user.getName()+","+content);
                        personList.add(person);
                    }
                }
                if (null!=personList&&!personList.isEmpty()){
                    for (Person item : personList){
                        sendSms(IDs,item.getPhoneNumber(),item.getMsgContent());
                    }
                }
            }else {
                UIUtils.Toast(this,"请设置短信发送模板");
            }
        }
    }

    private void initView() {
        personList = new ArrayList<>();
        birthdayList = new ArrayList<>();
        binding.addUser.setOnClickListener(this);
        binding.sentMessage.setOnClickListener(this);
        binding.rgAll.setOnCheckedChangeListener(this);
        binding.imgToos.setOnClickListener(this);
        binding.userListView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        requestPower();
    }

    private void initData() {
        String date = getDate();
        binding.tips.setText(date+"生日组员:");
        List<TUser> userList = LocalDataDBManager.getInstance(this).queryAllTUser();
        if (null != birthdayList) {
            birthdayList.clear();
        }
        if (null != userList && !userList.isEmpty()) {

            for (TUser user : userList) {
                String id = String.valueOf(user.getId());
                String cardId = ValidateUtil.getUserBrithdayByCardId(id);
                if (date.equals(cardId)) {
                    birthdayList.add(user);
                }
            }
        }
        userListAdapter = new UserListAdapter(this, birthdayList);
        binding.userListView.setAdapter(userListAdapter);
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_one:
                IDs = 0;
                break;
            case R.id.rb_two:
                IDs = 1;
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_user:
                Intent intent = new Intent(this, AddUserMessgaeActivity.class);
                startActivityForResult(intent, REQUEST_ADD_USER_CODE);
                break;

            case R.id.img_toos:
                Intent intentToos = new Intent(this, ToosActivity.class);
                startActivityForResult(intentToos,REQUEST_TOOS_CODE);
                break;

            case R.id.sent_message:
                if (null != birthdayList && !birthdayList.isEmpty()) {
                    List<TTempPlateContent> tTempPlateContents = LocalDataDBManager.getInstance(this).queryAllTemplateContemt();
                    if (null!=tTempPlateContents&&!tTempPlateContents.isEmpty()){
                        String content = tTempPlateContents.get(0).getContent();
                        for (TUser user:birthdayList) {
                            Person person=new Person();
                            person.setUserName(user.getName());
                            person.setPhoneNumber(user.getMobilePhone());
                            person.setMsgContent(user.getName()+","+content);
                            personList.add(person);
                        }

                        if (null!=personList&&!personList.isEmpty()){
                            for (Person item : personList){
                                sendSms(IDs,item.getPhoneNumber(),item.getMsgContent());
                                // 停顿1s
                            }
                        }

                    }else {
                        UIUtils.Toast(this,"请设置短信发送模板");
                    }

                }

                break;
            default:
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void sendSms(final int which,String phone,String context) {
        SubscriptionInfo sInfo = null;

        final SubscriptionManager sManager = (SubscriptionManager) getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);

        List<SubscriptionInfo> list = sManager.getActiveSubscriptionInfoList();

        if (list.size() == 2) {
            // 双卡
            sInfo = list.get(which);
        } else {
            // 单卡
            sInfo = list.get(0);
        }

        if (sInfo != null) {
            int subId = sInfo.getSubscriptionId();
            SmsManager manager = SmsManager.getSmsManagerForSubscriptionId(subId);

            if (!TextUtils.isEmpty(phone)) {
                ArrayList<String> messageList =manager.divideMessage(context);
                for(String text:messageList){
                    manager.sendTextMessage(phone, null, text, null, null);
                }
                Toast.makeText(this, "信息正在发送，请稍候", Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(this, "无法正确的获取SIM卡信息，请稍候重试",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_ADD_USER_CODE && null != data) {
                String resultStr = data.getStringExtra("addUser");
                if ("OK".equals(resultStr)) {
                    initData();
                }
            }
        }
    }

    public String getDate() {
        Calendar calendar = Calendar.getInstance();
        //月
        int month = calendar.get(Calendar.MONTH) + 1;
        //日
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String birthday = month + "月" + day + "日";
        return birthday;
    }

    public void requestPower() {
        //判断是否已经赋予权限
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED||ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED||ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true。
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {//这里可以写个对话框之类的项向用户解释为什么要申请权限，并在对话框的确认键后续再次申请权限
            } else {
                //申请权限，字符串数组内是一个或多个要申请的权限，1是申请权限结果的返回参数，在onRequestPermissionsResult可以得知申请结果
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_PHONE_STATE,Manifest.permission.SEND_SMS,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }
}
