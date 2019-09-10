package cn.com.fyl.learn.mysendmsgapp.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import java.util.List;

import cn.com.fyl.learn.mysendmsgapp.R;
import cn.com.fyl.learn.mysendmsgapp.adapter.AllPersonMessageAdapter;
import cn.com.fyl.learn.mysendmsgapp.databinding.ActivityAllPersonMessageBinding;
import cn.com.fyl.learn.mysendmsgapp.interfaces.IOnDelClickListener;
import cn.com.fyl.learn.mysendmsgapp.localdata.LocalDataDBManager;
import cn.com.fyl.learn.mysendmsgapp.localdata.TUser;
import cn.com.fyl.learn.mysendmsgapp.popwindow.MyDialog;
import cn.com.fyl.learn.mysendmsgapp.utils.ProgressDialogTool;
import cn.com.fyl.learn.mysendmsgapp.utils.UIUtils;

public class AllPersonMessageActivity extends AppCompatActivity implements View.OnClickListener,IOnDelClickListener {
    private static final String TAG = "AllPersonMessageActivity";

    private ActivityAllPersonMessageBinding binding;
    private AllPersonMessageAdapter allMessageAdapter;
    private MyDialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_all_person_message);
        initView();
        initData();
    }

    private void initView() {
        myDialog = new MyDialog(this);
        binding.title.setOnClickListener(this);
        allMessageAdapter = new AllPersonMessageAdapter(this);
        allMessageAdapter.setiOnDelClickListener(this);
    }

    private void initData() {
        List<TUser> userList = LocalDataDBManager.getInstance(this).queryAllTUser();
            allMessageAdapter.setList(userList);
            binding.allListView.setAdapter(allMessageAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void delListener(final TUser user) {
        if (null!=user){
            ProgressDialogTool.showDialog(this);
            myDialog.setButtonStyle(MyDialog.TWO_BUTTON_STYLE)
                    .setTitle("删除提示")
                    .setContent("确定删除"+user.getName()+"的全部信息？")
                    .setTextColor(Color.RED)
                    .setBtnTwoLeftText("取消")
                    .setBtnTwoRightText("确定")
                    .setAnimation(true)
                    .setAnimationStyle(R.style.dialog_animation);
            myDialog.show();
            myDialog.setClickListener(new MyDialog.ClickListenerInterface() {
                @Override
                public void confirm() {
                    delUser(user);
                    myDialog.dismiss();
                    ProgressDialogTool.dismissDialog();
                    initData();
                }

                @Override
                public void cancel() {
                    myDialog.dismiss();
                    ProgressDialogTool.dismissDialog();
                }
            });
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void delUser(TUser user){
        boolean delUser = LocalDataDBManager.getInstance(this).deleteUser(user);
        if (delUser){
            UIUtils.Toast(this,"删除成功！");
            Intent intent=new Intent();
            intent.putExtra("delUser","OK");
            setResult(RESULT_OK,intent);
        }
    }
}
