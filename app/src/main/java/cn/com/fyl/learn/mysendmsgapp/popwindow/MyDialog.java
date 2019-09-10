package cn.com.fyl.learn.mysendmsgapp.popwindow;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.com.fyl.learn.mysendmsgapp.R;

/**
 * 自定义Dialog
 */

public class MyDialog extends Dialog {
    //成功标题颜色
    public static final int SUCCESS_COLOR = Color.parseColor("#FF00C087");
    //失败标题颜色
    public static final int FAILD_COLOR = Color.parseColor("#FFE0463C");
    //一个button的style
    public static final int ONE_BUTTON_STYLE = 1;
    //两个button的style
    public static final int TWO_BUTTON_STYLE = 2;
    private String title;
    private String content;
    private int titleColor;
    private String btnOneText;
    private String btnTwoLeftText;
    private String btnTwoRightText;
    private int what;
    private boolean isAnimation;
    private int animationStyle;
    private TextView myTitle;
    private TextView myContent;

    private LinearLayout llOneBtn;
    private LinearLayout llTwoBtn;
    private Button btnOne;
    private Button btnTwoLeft;
    private Button btnTwoRight;
    private RelativeLayout all;

    public MyDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.mydialog, null);
        setContentView(view);
        myTitle = (TextView) view.findViewById(R.id.dialog_title);
        myContent = (TextView) view.findViewById(R.id.dialog_content);
        llOneBtn= (LinearLayout) view.findViewById(R.id.ll_btn_one);
        llTwoBtn= (LinearLayout) view.findViewById(R.id.ll_btn_two);
        btnOne= (Button) view.findViewById(R.id.btn_one);
        btnTwoLeft= (Button) view.findViewById(R.id.btn_two_left);
        btnTwoRight= (Button) view.findViewById(R.id.btn_two_right);
        all = (RelativeLayout) view.findViewById(R.id.all);
    }

    @Override
    public void show() {
        super.show();
        show(this);
    }

    /**
     * 显示
     * @param myDialog
     */
    private void show(MyDialog myDialog){
        //判断显示哪个dialog
        switch (what) {
            case 1 :
                llOneBtn.setVisibility(View.VISIBLE);
                llTwoBtn.setVisibility(View.GONE);
                break;
            case 2 :
                llOneBtn.setVisibility(View.GONE);
                llTwoBtn.setVisibility(View.VISIBLE);
                break;
            default:
                all.setVisibility(View.GONE);
                Toast.makeText(getContext(),"传入参数错误",Toast.LENGTH_SHORT).show();
                break;
        }

        myTitle.setText(title);
        myTitle.setTextColor(titleColor);
        myContent.setText(content);



        btnOne.setText(btnOneText);
        btnTwoLeft.setText(btnTwoLeftText);
        btnTwoRight.setText(btnTwoRightText);

        btnOne.setOnClickListener(new ClickListener());
        btnTwoLeft.setOnClickListener(new ClickListener());
        btnTwoRight.setOnClickListener(new ClickListener());

        //设置dialog宽高位置
        Window window = getWindow();
//        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setBackgroundDrawableResource(R.drawable.background_dialog);
        WindowManager.LayoutParams lp = window.getAttributes();
        DisplayMetrics d = getContext().getResources().getDisplayMetrics();
        if(isAnimation == true) {
            //设置动画
            window.setWindowAnimations(animationStyle);
        }else{
            window.setWindowAnimations(android.R.style.Animation);
        }
        lp.width = d.widthPixels/5*4;
        lp.height = d.heightPixels/3;
        //设置位置在中间
        lp.gravity = Gravity.CENTER;
        window.setAttributes(lp);
    }

    /**
     * 设置Button类型
     */
    public MyDialog setButtonStyle(int what){
        this.what = what;
        return this;
    }

    /**
     * 设置动画类型
     * @param styleId
     */
    public MyDialog setAnimationStyle(int styleId){
        this.animationStyle=styleId;
        return this;
    }
    /**
     * 是否设置动画
     * @param isAnimation
     */
    public MyDialog setAnimation(boolean isAnimation){
        this.isAnimation = isAnimation;
        return this;
    }
    /**
     * 设置内容
     * @param content
     */
    public MyDialog setContent(String content){
        this.content = content;
        return this;
    }



    /**
     * 设置标题
     * @param title
     */
    public MyDialog setTitle(String title){
        this.title = title;
        return this;
    }
    /**
     * 设置标题颜色
     * @param color
     */
    public MyDialog setTextColor(int color){
        this.titleColor = color;
        return this;
    }

    /**
     * 设置btnOne的Text
     * @param text
     */
    public MyDialog setBtnOneText(String text){
        this.btnOneText = text;
        return this;
    }

    /**
     * 设置btnTwoLeft的Text
     * @param text
     */
    public MyDialog setBtnTwoLeftText(String text){
        this.btnTwoLeftText = text;
        return this;
    }

    /**
     * 设置btnTwoRight的Text
     * @param text
     */
    public MyDialog setBtnTwoRightText(String text){
        this.btnTwoRightText = text;
        return this;
    }

    //设置回调
    private ClickListenerInterface clickListenerInterface;

    public interface ClickListenerInterface {
        /**
         * 确认
         */
        void confirm();

        /**
         * 取消
         */
        void cancel();
    }

    public void setClickListener(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }

    /**
     * 初始化数据
     */

    private class ClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_one :
                    clickListenerInterface.confirm();
                    break;
                case R.id.btn_two_left :
                    clickListenerInterface.cancel();
                    break;
                case R.id.btn_two_right :
                    clickListenerInterface.confirm();
                    break;
                default:
                    break;
            }
        }
    }
}
