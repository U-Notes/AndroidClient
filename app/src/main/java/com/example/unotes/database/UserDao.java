package com.example.unotes.database;

import android.util.Log;

import com.example.unotes.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 用户数据库
 *
 * @author witer
 * @date 2023/07/30
 */
public class UserDao {
    JdbcUtils jdbcUtil = JdbcUtils.getInstance();
    Connection conn = jdbcUtil.getConnection();
//TODO 后续需要增加一个中间层，防止被逆向
    public boolean login(String account, String password) throws SQLException {
        String sql = "SELECT * from userinfo WHERE account=? AND password=?";
        if (conn == null) {
            Log.i("error", "无数据链接");
            return false;
        } else {
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, account);
                ps.setString(2, password);
                ResultSet rs = ps.executeQuery();
                Log.i("rs", "result:" + rs.toString());
                return rs.next();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }

        }
    }
}