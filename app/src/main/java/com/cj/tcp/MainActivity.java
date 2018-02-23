package com.cj.tcp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.mysql.jdbc.Connection;

import java.sql.*;


public class MainActivity extends Activity {
    private static final String URL = "jdbc:mysql://192.168.1.102/fxzx";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread(runnable).start();
    }

    private Connection connection = null;
    Runnable runnable = new Runnable() {

        @Override
        public void run() {
//            connection = MySqlUtil.openConnection(URL, USER, PASSWORD);
//            Log.e("main", connection == null ? "null" : "有值");

            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = (Connection) DriverManager.getConnection(
                        "jdbc:mysql://192.168.1.101:3306/fxzx", "root", "root");
                //连接URL为   jdbc:mysql//服务器地址/数据库名  ，后面的2个参数分别是登陆用户名和密码
//                connection = DriverManager.getConnection("jdbc:mysql://192.168.1.101:3306/fxzx", "root", "root1");
                query(connection, "select * from mytable");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e1) {
                e1.printStackTrace();
            } catch (Exception e) {
                Log.e("MySqlUtil", e.getMessage());
            }

        }
    };

    public static void query(Connection conn, String sql) {
        if (conn == null) {
            return;
        }
        Statement statement = null;
        ResultSet result = null;
        try {
            statement = conn.createStatement();
            result = statement.executeQuery(sql);
            if (result != null && result.first()) {
                int idColumnIndex = result.findColumn("id");
                int nameColumnIndex = result.findColumn("name");
                System.out.println("id\t\t" + "name");
                while (!result.isAfterLast()) {
                    System.out.print(result.getString(idColumnIndex) + "\t\t");
                    System.out.println(result.getString(nameColumnIndex));
                    result.next();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (result != null) {
                    result.close();
                    result = null;
                }
                if (statement != null) {
                    statement.close();
                    statement = null;
                }

            } catch (SQLException sqle) {

            }
        }
    }
}
