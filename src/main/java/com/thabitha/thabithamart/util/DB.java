package com.thabitha.thabithamart.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class DB {

    private DB() {
    }

    public static Connection get() throws SQLException {

        return DriverManager.getConnection(
                "jdbc:h2:~/thabithamart;MODE=PostgreSQL",
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