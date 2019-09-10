package cn.com.fyl.learn.mysendmsgapp.utils;


import android.content.Context;

public class ProgressDialogTool {
    private static MyProgressDialog dialog = null;
    private static Context curContext;
    private static final String TAG = ProgressDialogTool.class.getSimpleName();
    private static MyProgressDialog myDialog;

    /**
     * 展示对话框
     */
    public static synchronized void showDialog(Context context) {
        if (context != curContext || dialog == null) {
            dialog = new MyProgressDialog(context, 0);
            curContext = context;
            dialog.setCancelable(false);

        }
         dialog.show();
    }
    public static synchronized void showDialog(Context context,String s) {
        if (context != curContext || dialog == null) {
            dialog = new MyProgressDialog(context, 0);
            curContext = context;
            dialog.setCancelable(false);
        }
        dialog.show();
        dialog.change(s);
    }
    /**
     * 对话框消失
     */
    public static synchronized void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }


    public static void showLoginDiaolog(Context context,String s){
        myDialog = new MyProgressDialog(context,0);
        myDialog.setCancelable(false);
        myDialog.show();
        myDialog.change(s);

    }

    public static void dismissLoginDialog(){
        if (myDialog!=null&& myDialog.isShowing()){
            myDialog.dismiss();
        }
    }
}
