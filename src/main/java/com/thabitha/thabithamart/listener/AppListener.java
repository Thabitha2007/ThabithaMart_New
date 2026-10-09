package com.thabitha.thabithamart.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;                 // NEW
import org.slf4j.LoggerFactory;          // NEW

import com.thabitha.thabithamart.util.DB;

public class AppListener implements ServletContextListener {

    private static final Logger log = LoggerFactory.getLogger(AppListener.class); // NEW

    @Override
    public void contextInitialized(ServletContextEvent e) {

        try {
            DB.init();
            log.info("ThabithaMart started, database ready");   // NEW
        } catch (Exception x) {
            log.error("Database initialisation failed", x);     // CHANGED from printStackTrace
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent e) {
        DB.close();   // NEW: shuts down the connection pool
    }
}