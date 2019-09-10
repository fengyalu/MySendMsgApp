package cn.com.fyl.learn.mysendmsgapp.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;

import cn.com.fyl.learn.mysendmsgapp.R;
import cn.com.fyl.learn.mysendmsgapp.databinding.ActivityAddUserMessgaeBinding;
import cn.com.fyl.learn.mysendmsgapp.localdata.LocalDataDBManager;
import cn.com.fyl.learn.mysendmsgapp.localdata.TTemplate;
import cn.com.fyl.learn.mysendmsgapp.localdata.TUser;
import cn.com.fyl.learn.mysendmsgapp.utils.UIUtils;
import cn.com.fyl.learn.mysendmsgapp.utils.ValidateUtil;

public class AddUserMessgaeActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "AddUserMessgaeActivity";

    private static final int REQUEST_SEARCH_USER_CODE = 1001;

    private ActivityAddUserMessgaeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_add_user_messgae);
        initView();
    }

    private void initView() {
        binding.add.setOnClickListener(this);
        binding.title.setOnClickListener(this);
        binding.search.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()){
                case R.id.title:
                    finish();
                    break;
                case R.id.add:
                    String name = binding.editName.getText().toString().trim();
                    String mobilePhone = binding.editMobilePhone.getText().toString().trim();
                    String idStr = binding.editId.getText().toString().trim();
                    if (TextUtils.isEmpty(name)){
                        UIUtils.Toast(this,"姓名不能为空！");
                        return;
                    }
                    if (TextUtils.isEmpty(mobilePhone)){
                        UIUtils.Toast(this,"手机号码不能为空！");
                        return;
                    }

                    if (TextUtils.isEmpty(idStr)){
                        UIUtils.Toast(this,"身份证号码不能为空！");
                        return;
                    }

                    if (!ValidateUtil.isLegalId(idStr)){
                        UIUtils.Toast(this,"身份证格式有误！");
                        return;
                    }

                    TUser user=new TUser();
                    user.setName(name);
                    user.setMobilePhone(mobilePhone);

                    long id=Long.parseLong(idStr);
                    user.setId(id);

                    boolean oneUser = LocalDataDBManager.getInstance(this).saveOneUser(user);
                    if (oneUser){
                        UIUtils.Toast(this,"保存成功！");
                        Intent intent=new Intent();
                        intent.putExtra("addUser","OK");
                        setResult(RESULT_OK,intent);
                        binding.editName.getText().clear();
                        binding.editMobilePhone.getText().clear();
                        binding.editId.getText().clear();
                    }
                    break;

                case R.id.search:
                    Intent intent=new Intent(this,AllPersonMessageActivity.class);
                    startActivityForResult(intent,REQUEST_SEARCH_USER_CODE);
                    break;

                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_SEARCH_USER_CODE && null != data) {
                String resultStr = data.getStringExtra("delUser");
                if ("OK".equals(resultStr)) {
                    Intent intent=new Intent();
                    intent.putExtra("addUser","OK");
                    setResult(RESULT_OK,intent);
                }
            }
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
          finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
