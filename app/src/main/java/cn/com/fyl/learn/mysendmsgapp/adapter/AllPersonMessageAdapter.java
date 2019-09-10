package cn.com.fyl.learn.mysendmsgapp.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.com.fyl.learn.mysendmsgapp.R;
import cn.com.fyl.learn.mysendmsgapp.databinding.ItemAllPersonMessageBinding;
import cn.com.fyl.learn.mysendmsgapp.interfaces.IOnDelClickListener;
import cn.com.fyl.learn.mysendmsgapp.localdata.TUser;

/**
 * Created by Administrator on 2019/8/2 0002.
 */

public class AllPersonMessageAdapter extends BaseAdapter {
    private Context context;
    private List<TUser> list;
    private ItemAllPersonMessageBinding binding;
    private IOnDelClickListener iOnDelClickListener;
    public AllPersonMessageAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public void setList(List<TUser> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setiOnDelClickListener(IOnDelClickListener iOnDelClickListener) {
        this.iOnDelClickListener = iOnDelClickListener;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_all_person_message, parent, false);
        } else {
            binding = DataBindingUtil.getBinding(convertView);
        }
       binding.name.setText(list.get(position).getName());
       binding.mobilePhone.setText(list.get(position).getMobilePhone());
       binding.cardId.setText(String.valueOf(list.get(position).getId()));
        binding.delete.setOnClickListener(new MyOnClickListener(position) {
            @Override
            public void onClick(View v) {
              if (null!=iOnDelClickListener){
                  try {
                      iOnDelClickListener.delListener(list.get(myPosition));
                  } catch (Exception e) {
                      e.printStackTrace();
                  }
              }
            }
        });
        return binding.getRoot();
    }

    abstract class MyOnClickListener implements View.OnClickListener {
        int myPosition = -1;

        MyOnClickListener(int position) {
            this.myPosition = position;
        }
    }
}
