package com.thabitha.thabithamart.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import com.thabitha.thabithamart.util.DB;
import com.thabitha.thabithamart.util.PasswordUtil;

public class AppListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent e) {

        DB.init();

        String sql = "MERGE INTO users(username, password_hash, role) " +
                     "KEY(username) VALUES(?,?,?)";

        try (
            Connection c = DB.get();
            PreparedStatement p = c.prepareStatement(sql)
        ) {

            p.setString(1, "admin");
            p.setString(2, PasswordUtil.hash("Admin@123"));
            p.setString(3, "ADMIN");

            p.executeUpdate();

        } catch (Exception x) {
            throw new RuntimeException(x);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent e) {
    }
}