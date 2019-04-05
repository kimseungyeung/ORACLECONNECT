package com.example.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Data.UserData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Sub1Fragment extends Fragment implements View.OnClickListener {
    TextView tv_name;
    EditText edt_id, edt_passowrd, edt_name, edt_age;
    Button btn_add, btn_update, btn_delete, btn_function;
    PreparedStatement pstmt = null;
    Context mcontext;
   private Interfacecall incall;
    public int selectidx = 0;
    RecyclerView ll_recycle;
    Connection connection;
    ArrayList<UserData> ulist;
    CustomAdapter ca;
    MainActivity mm;
//    public Sub1Fragment newInstance() {
//        Sub1Fragment ss = new Sub1Fragment();
//        Bundle bb = new Bundle();
//        ss.setArguments(bb);
//        return ss;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.sub1_fragment, container, false);
        component(view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.incall=(Interfacecall)context;
    }

    public void component(View v) {
        mm = new MainActivity();
        ll_recycle = (RecyclerView) v.findViewById(R.id.lv_user);
        edt_id = (EditText) v.findViewById(R.id.edt_id);
        edt_name = (EditText) v.findViewById(R.id.edt_name);
        edt_passowrd = (EditText) v.findViewById(R.id.edt_password);
        edt_age = (EditText) v.findViewById(R.id.edt_age);
        btn_add = (Button) v.findViewById(R.id.btn_add);
        btn_update = (Button) v.findViewById(R.id.btn_update);
        btn_delete = (Button) v.findViewById(R.id.btn_delete);
        btn_function = (Button) v.findViewById(R.id.btn_funtion);
        btn_update.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
        btn_add.setOnClickListener(this);
        btn_function.setOnClickListener(this);

        initlist();
       ca = new CustomAdapter(ulist,R.layout.item_recycle_list);
      ll_recycle.setAdapter(ca);
      ll_recycle.setLayoutManager(new LinearLayoutManager(mcontext));
//      ll_recycle.setAnimation(new Defa);

    }

    public void initlist() {
        ulist =new ArrayList<>();
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        try {
            this.connection = MainActivity.createConnection();
            //e.Log("Connected");
            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery("select * from test");
            while (rs.next()) {
                System.out.println("hello : " + rs.getString(1));
                String id = rs.getString("id");
                String name = rs.getString("name");
                String password = rs.getString("password");
                int age = Integer.parseInt(rs.getString("age"));
                UserData ud = new UserData(id,name,password,age);
               ulist.add(ud);
            }
            connection.close();
        } catch (Exception e) {
            //e.Log(""+e);
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
//                String id = edt_id.getText().toString();
//                String name = edt_name.getText().toString();
//                String password = edt_passowrd.getText().toString();
//                String age = edt_age.getText().toString();
//            insert(id,name,password,age);
//            update(2);
//            delete(4);
                int ss = incall.returnva(ca.getSelectidx());
                Toast.makeText(getActivity(), String.valueOf(ss) + "입니다"+String.valueOf(ca.getSelectidx()), Toast.LENGTH_LONG).show();
                ca.notifyDataSetChanged();
                break;
            case R.id.btn_update:
                incall.update(ca.getSelectidx(), null);
                ca.notifyDataSetChanged();
                break;
            case R.id.btn_delete:
                incall.delete(ca.getSelectidx());
                initlist();
                ca.setUdatalist(ulist);
                ca.notifyDataSetChanged();
                break;
            case R.id.btn_funtion:
                insertdatadialog();
                break;
        }
    }

    public void insertdatadialog() {
        android.support.v7.app.AlertDialog.Builder dialogBuilder = new android.support.v7.app.AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.insert_dialog, null);
        dialogBuilder.setView(view);

        final android.support.v7.app.AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        EditText edt_id = (EditText) view.findViewById(R.id.edt_id);
        EditText edt_name = (EditText) view.findViewById(R.id.edt_name);
        EditText edt_password = (EditText) view.findViewById(R.id.edt_password);
        EditText edt_age = (EditText) view.findViewById(R.id.edt_age);
        Button btn_ok = (Button) view.findViewById(R.id.btn_ok);
        Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });
        alertDialog.show();
    }

}
