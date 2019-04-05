package com.example.myapplication;

import android.content.Context;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Data.UserData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends FragmentActivity implements  Interfacecall {


    String InsertSQL = "insert into test(id, name,password,age) values(?, ?, ?, ?)";
    String UpdateSQL = "update test set ";
    String DeleteSQL = "delete from test where id=";

    private static final String DEFAULT_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DEFAULT_URL = "jdbc:oracle:thin:@192.168.42.172:1521:orcl";
    private static final String DEFAULT_USERNAME = "seungyeung";
    private static final String DEFAULT_PASSWORD = "tmddud";
    private Connection connection;
    PreparedStatement pstmt = null;
    int selectidx = 0;
    FragmentTabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        component();

    }

    public void component() {
        tabHost =(FragmentTabHost)findViewById(R.id.tabhost);
        tabHost.setup(this,getSupportFragmentManager(),R.id.tabcontent);
        TabHost.TabSpec tabSpec1 =tabHost.newTabSpec("tab1");
//        tabSpec1.setIndicator("탭1");
        LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        View bv = li.inflate(R.layout.tabbutton, null);
//        tab1.setImageResource(R.drawable.common_full_open_on_phone);
//        TabSpec.setIndicator("tab1", getResources().getDrawable(R.drawable.common_full_open_on_phone));
        tabSpec1.setIndicator(bv);
        Sub1Fragment ss= new Sub1Fragment();
        tabHost.addTab(tabSpec1,Sub1Fragment.class,null);
        TabHost.TabSpec tabSpec2 =tabHost.newTabSpec("tab2");
        tabSpec2.setIndicator("탭2");
        tabHost.addTab(tabSpec2,Sub2Fragment.class,null);
        TabHost.TabSpec tabSpec3 =tabHost.newTabSpec("tab3");
        tabSpec3.setIndicator("탭3");
        tabHost.addTab(tabSpec3,Sub3Fragment.class,null);
        TabHost.TabSpec tabSpec4 =tabHost.newTabSpec("tab4");
        tabSpec4.setIndicator("탭4");
        tabHost.addTab(tabSpec4,Sub4Fragment.class,null);
        TabHost.TabSpec tabSpec5 =tabHost.newTabSpec("tab5");
        tabSpec5.setIndicator("탭5");
        tabHost.addTab(tabSpec5,Sub5Fragment.class,null);
        tabHost.setCurrentTab(0);

    }

    public static Connection createConnection(String driver, String url, String username, String password) throws ClassNotFoundException, SQLException {

        Class.forName(driver);
        return DriverManager.getConnection(url, username, password);
    }

    public static Connection createConnection() throws ClassNotFoundException, SQLException {
        return createConnection(DEFAULT_DRIVER, DEFAULT_URL, DEFAULT_USERNAME, DEFAULT_PASSWORD);
    }

    public void insert(UserData udata) {
        if (connection != null) {
            try {
                if (connection.isClosed()) {
                    connection = createConnection();
                }
                pstmt = connection.prepareStatement(InsertSQL);
                pstmt.setString(1, udata.getId());
                pstmt.setString(2, udata.getName());
                pstmt.setString(3, udata.getPassword());
                pstmt.setString(4, String.valueOf(udata.getAge()));
                int r = pstmt.executeUpdate();
                connection.close();
            } catch (Exception e) {
                Log.e("실패", e.getMessage().toString());

            }
        }

    }

    public void update(int index, UserData udata) {
        String id = udata.getId();
        String name = udata.getName();
        String password = udata.getPassword();
        String age = String.valueOf(udata.getAge());
        if (connection != null) {
            try {
                if (connection.isClosed()) {
                    connection = createConnection();
                }
                String df = UpdateSQL;
                if (!id.equals("")) {
                    df = df + "id=" + id + ",";
                }
                if (!name.equals("")) {
                    df = df + "name=" + name + ",";
                }
                if (!password.equals("")) {
                    df = df + "password=" + password + ",";
                }
                if (!age.equals("")) {
                    df = df + "age=" + age + ",";
                }
                String test = df.substring(df.length() - 1, df.length());
                if (test.equals(",")) {
                    df = df.substring(0, df.length() - 1);
                    df = df + " where id =" + "'" + String.valueOf(index) + "'";
//                    df=  df+" where name = 'jsh'";
                }
                pstmt = connection.prepareStatement(df);
//                if(!id.equals("")) {
//                    pstmt.setString(1, edt_id.getText().toString());
//                }
//
//                if(!name.equals("")) {
//                    pstmt.setString(2, edt_name.getText().toString());
//                }
//                if(!password.equals("")) {
//                    pstmt.setString(3, edt_passowrd.getText().toString());
//                }
//                if(!age.equals("")) {
//                    pstmt.setString(4, edt_age.getText().toString());
//                }
//                    pstmt.setString(2,"1");
                int r = pstmt.executeUpdate();
                connection.close();
            } catch (Exception e) {
                Log.e("실패", e.getMessage().toString());

            }
        }
    }

    public void delete(int index) {
        try {
        if (connection == null) {
            connection=createConnection();
        }


                if (connection.isClosed()) {
                    connection = createConnection();
                }
                String df = DeleteSQL;
                df = df + "'" + String.valueOf(index) + "'";
                pstmt = connection.prepareStatement(df);
//                pstmt.setString(1,"4");

                int r = pstmt.executeUpdate();
                connection.close();
            } catch (Exception e) {
                Log.e("실패", e.getMessage().toString());

            }

    }

    public int returnva(int idx) {
        int s = 0;
        try {
        if (connection == null) {
            connection=createConnection();
        }

                if (connection.isClosed()) {
                    connection = createConnection();
                }
                String df = "SELECT func_return(" + String.valueOf(idx) + ") FROM DUAL";
                pstmt = connection.prepareStatement(df);

                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    s = rs.getInt(1);
                }
                return s;

            } catch (Exception e) {
                Log.e("실패", e.getMessage().toString());
                return 0;
            }




    }

}

