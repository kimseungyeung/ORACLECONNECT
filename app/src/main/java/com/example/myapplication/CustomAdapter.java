package com.example.myapplication;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.Data.UserData;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.Holder> {
    private ArrayList<UserData> udatalist;
    private int itemlayout;
    private int selectidx=0;
    public  CustomAdapter(ArrayList<UserData> udl, int ilayout){
        this.udatalist=udl;
        this.itemlayout=ilayout;
    }
    //아이템을 셋팅
    @Override
    public Holder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(itemlayout,viewGroup,false);
        return new Holder(view);
    }
    //넘겨받은 데이터를 화면에 출력함
    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        UserData ud= udatalist.get(position);
        holder.tv_id.setText(ud.getId());
        holder.tv_name.setText(ud.getName());
        holder.tv_password.setText(ud.getPassword());

//        holder.tv_age.setText(String.valueOf(ud.getAge()));
        holder.ll_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectidx=position;
            }
        });
    }

    public int getSelectidx() {
        return this.selectidx;
    }

    @Override
    public int getItemCount() {
        return udatalist.size();
    }
   //홀더를 셋팅
    public static class Holder extends RecyclerView.ViewHolder{
        public TextView tv_id,tv_name,tv_password,tv_age;
        public LinearLayout ll_main;
        public Holder(View itemview){
            super(itemview);
            ll_main=(LinearLayout)itemview.findViewById(R.id.ll_main);
            tv_id =(TextView)itemview.findViewById(R.id.tv_id);
            tv_name =(TextView)itemview.findViewById(R.id.tv_name);
            tv_password =(TextView)itemview.findViewById(R.id.tv_password);
            tv_age =(TextView)itemview.findViewById(R.id.tv_age);
        }
    }
}
