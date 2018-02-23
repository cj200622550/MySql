package com.cj.tcp;

import android.util.Log;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by 陈骏 on 2017/11/20.
 */

public class MySqlUtil {
    public static Connection openConnection(String url, String user, String password) {
        Connection conn = null;
        try {
            final String DRIVER_NAME = "com.mysql.jdbc.Driver";
            Class.forName(DRIVER_NAME).getInterfaces();
            conn = DriverManager.getConnection(url, user, password);
            Log.e("MySqlUtil","");
        } catch (ClassNotFoundException e) {
            Log.e("MySqlUtil",e.getMessage());
            conn = null;
        } catch (SQLException e) {
            Log.e("MySqlUtil",e.getMessage());
            conn = null;
        }catch (Exception e){
            Log.e("MySqlUtil",e.getMessage());
            conn = null;
        }
        return conn;
    }

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
                while (!result.isAfterLast()) {
                    System.out.println("------------------");
                    System.out.print("id " + result.getString(idColumnIndex) + "\t");
                    System.out.println("name " + result.getString(nameColumnIndex));
                    result.next();
                }
            }
        } catch (SQLException e) {
            Log.e("MySqlUtil",e.getMessage());
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
                Log.e("MySqlUtil",sqle.getMessage());
            }
        }
    }
    public static boolean execSQL(Connection conn, String sql) {
        boolean execResult = false;
        if (conn == null) {
            return execResult;
        }
        Statement statement = null;
        try {
            statement = conn.createStatement();
            if (statement != null) {
                execResult = statement.execute(sql);
            }
        } catch (SQLException e) {
            Log.e("MySqlUtil",e.getMessage());
            execResult = false;
        }
        return execResult;
    }
}
