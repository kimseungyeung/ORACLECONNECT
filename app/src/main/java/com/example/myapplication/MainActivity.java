package com.example.myapplication;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {
    TextView tv_name;
    EditText edt_id,edt_passowrd,edt_name,edt_age;
    Button btn_add;
    PreparedStatement pstmt = null;

    String SQL = "insert into test(id, name,password,age) values(?, ?, ?, ?)";
    private static final String DEFAULT_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DEFAULT_URL = "jdbc:oracle:thin:@192.168.42.172:1521:orcl";
    private static final String DEFAULT_USERNAME = "seungyeung";
    private static final String DEFAULT_PASSWORD = "tmddud";
    private Connection connection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        component();
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }



        try {
            this.connection = createConnection();
            //e.Log("Connected");
            Statement stmt=connection.createStatement();

            ResultSet rs=stmt.executeQuery("select * from test");
            while(rs.next()) {
                System.out.println("hello : " + rs.getString(1));
                tv_name.append(rs.getString("Name"));
            }
            connection.close();
        }
        catch (Exception e) {
            //e.Log(""+e);
            e.printStackTrace();
        }
    }
public void component(){
    tv_name =(TextView)findViewById(R.id.tv_name);
    edt_id =(EditText)findViewById(R.id.edt_id);
    edt_name=(EditText)findViewById(R.id.edt_name);
    edt_passowrd =(EditText)findViewById(R.id.edt_password);
    edt_age =(EditText)findViewById(R.id.edt_age);
    btn_add =(Button)findViewById(R.id.btn_add);
    btn_add.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          String id=  edt_id.getText().toString();
          String name =edt_name.getText().toString();
          String password =edt_passowrd.getText().toString();
          String age =edt_age.getText().toString();
            insert(id,name,password,age);
        }
    });
}
    public static Connection createConnection(String driver, String url, String username, String password) throws ClassNotFoundException, SQLException {

        Class.forName(driver);
        return DriverManager.getConnection(url, username, password);
    }

    public static Connection createConnection() throws ClassNotFoundException, SQLException {
        return createConnection(DEFAULT_DRIVER, DEFAULT_URL, DEFAULT_USERNAME, DEFAULT_PASSWORD);
    }
    public void insert (String id,String name,String password,String age){
        if(connection!=null){
            try {
            if(connection.isClosed()) {
            connection =createConnection();
            }
                pstmt = connection.prepareStatement(SQL);
                pstmt.setString(1, edt_id.getText().toString());
                pstmt.setString(2, edt_name.getText().toString());
                pstmt.setString(3, edt_passowrd.getText().toString());
                pstmt.setString(4, edt_age.getText().toString());
                int r = pstmt.executeUpdate();

            }catch (Exception e){
                Log.e("실패",e.getMessage().toString());
            }
        }
    }
    }

