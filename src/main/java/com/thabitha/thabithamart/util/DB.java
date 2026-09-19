package com.thabitha.thabithamart.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class DB {

    private DB() {
    }

    public static Connection get() throws SQLException {
        // H2 Database URL-லேயே schema.sql-ஐத் தானாக ரன் செய்ய INIT கட்டளை சேர்க்கப்பட்டுள்ளது
        return DriverManager.getConnection(
                "jdbc:h2:~/thabithamart;MODE=PostgreSQL;INIT=RUNSCRIPT FROM 'classpath:schema.sql'",
                "sa",
                ""
        );
    }

    public static void init() {
        try (
                Connection c = get();
                Statement s = c.createStatement()
        ) {
            s.execute(
                    "RUNSCRIPT FROM 'classpath:schema.sql'"
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}