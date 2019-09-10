package cn.com.fyl.learn.mysendmsgapp.popwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.lang.reflect.Method;

import cn.com.fyl.learn.mysendmsgapp.R;


/**
 * Created by Administrator on 2019/6/11 0011.
 */

public class PopAddTemplate {
    private Context context;
    private PopupWindow popupWindow;

    private TextView title;
    private Button left;
    private Button right;
    private EditText ediText;
    private String txtTitle;
    private String txtLeft;
    private String txtRight;
    private boolean cancelable;
    private OnLeftButtonClickListener onLeftButtonClickListener;
    private OnRightButtonClickListener onRightButtonClickListener;

    /**
     * 位置
     */
    public enum Gravitys {
        TOP, LEFT, CENTER, RIGHT, BOTTOM
    }

    public PopAddTemplate(Context context) {
        this.context = context;
    }

    private void createDialog(Gravitys gravity) {
        View view = View.inflate(context, R.layout.pop_add_template, null);
        initView(view);
        setView();
        popupWindow = new PopupWindow(view, -1, -2);
        //先关闭
        dismiss();
        //设置一个背景
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //点击外边可关闭pw
        popupWindow.setOutsideTouchable(false);
        //pw内可获取焦点
        popupWindow.setFocusable(true);

        try {
            Method method = PopupWindow.class.getDeclaredMethod("setTouchModal", boolean.class);
            method.setAccessible(true);
            method.invoke(popupWindow, false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 设置背景颜色变暗
        WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
        lp.alpha = 0.7f;
        ((Activity) this.context).getWindow().setAttributes(lp);

        //退出时恢复透明度
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
                lp.alpha = 1f;
                ((Activity) context).getWindow().setAttributes(lp);
            }
        });
        //设置popwindow显示位置及动画
        setWindow(view, gravity);
    }

    private void initView(View view) {
        //标题
        title = (TextView) view.findViewById(R.id.dialog_title);
        //左边按钮
        left = (Button) view.findViewById(R.id.btn_two_left);
        //右边按钮
        right = (Button) view.findViewById(R.id.btn_two_right);
        ediText= (EditText) view.findViewById(R.id.text);
    }

    private void setView() {
        title.setText(txtTitle);
        left.setText(txtLeft);
        right.setText(txtRight);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onLeftButtonClickListener != null) {
                    onLeftButtonClickListener.onLeftClick();
                }
            }
        });

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRightButtonClickListener!=null){
                    String content = ediText.getText().toString().trim();
                    onRightButtonClickListener.onRightClick(content);
                }
            }
        });
    }

    private void setWindow(View view, Gravitys gravity) {
        switch (gravity) {
            case TOP:
                //显示。y轴距离底部100是因为部分手机有虚拟按键，所以实际项目中要动态设置
                popupWindow.showAtLocation(view, Gravity.TOP, 0, 0);
                break;
            case LEFT:
                popupWindow.showAtLocation(view, Gravity.LEFT, 0, 0);
                break;
            case CENTER:
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                break;
            case RIGHT:
                popupWindow.showAtLocation(view, Gravity.RIGHT, 0, 0);
                break;
            case BOTTOM:
                popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                break;
            default:
                break;
        }
    }

    /**
     * 设置标题
     *
     * @param txtTitle
     */
    public PopAddTemplate setTxtTitle(String txtTitle) {
        this.txtTitle = txtTitle;
        return this;
    }

    /**
     * 设置左按钮
     *
     * @param txtLeft
     * @return
     */
    public PopAddTemplate setTxtLeft(String txtLeft) {
        this.txtLeft = txtLeft;
        return this;
    }


    /**
     * 设置右按钮
     *
     * @param txtRight
     * @return
     */
    public PopAddTemplate setTxtRight(String txtRight) {
        this.txtRight = txtRight;
        return this;
    }

    /**
     * 设置是否点击外部使其消失
     *
     * @param cancelable
     * @return
     */
    public PopAddTemplate setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
        return this;
    }


    /**
     * 显示弹出框
     */
    public PopAddTemplate show(Gravitys gravity) {
        createDialog(gravity);
        return this;
    }

    /**
     * 取消弹出框
     */
    public void dismiss() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
        }
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public PopAddTemplate setOnLeftButtonClickListener(OnLeftButtonClickListener onLeftButtonClickListener) {
        this.onLeftButtonClickListener = onLeftButtonClickListener;
        return this;
    }

    public PopAddTemplate setOnRightButtonClickListener(OnRightButtonClickListener onRightButtonClickListener) {
        this.onRightButtonClickListener = onRightButtonClickListener;
        return this;
    }

    /**
     * 左按钮监听
     */
    public interface OnLeftButtonClickListener {
        //左按钮
        void onLeftClick();
    }

    /**
     * 右按钮监听
     */
    public interface OnRightButtonClickListener {
        //左按钮
        void onRightClick(String content);
    }

}
