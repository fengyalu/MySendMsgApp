package cn.com.fyl.learn.mysendmsgapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import cn.com.fyl.learn.mysendmsgapp.R;
import cn.com.fyl.learn.mysendmsgapp.localdata.TUser;

/**
 * Created by Administrator on 2018/3/10.
 */

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {
    private static final String TAG = "UserListAdapter";

    private Context context;
    private List<TUser> list;
    private LayoutInflater mLiLayoutInflater;

    public UserListAdapter(Context context, List<TUser> list) {
        this.context = context;
        this.list = list;
        this.mLiLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public UserListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLiLayoutInflater.inflate(R.layout.item_user_list, parent, false));
    }

    @Override
    public void onBindViewHolder(UserListAdapter.ViewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
        }
    }
}
