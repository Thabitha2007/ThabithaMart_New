package com.thabitha.thabithamart.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import com.thabitha.thabithamart.util.DB;

public class AppListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent e) {
        
        try {
            DB.init();
        } catch (Exception x) {
            x.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent e) {
    }
}