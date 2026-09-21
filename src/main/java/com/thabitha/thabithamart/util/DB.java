package com.thabitha.thabithamart.util;

import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class DB {

    private static final String DEFAULT_LOCAL_URL = "jdbc:h2:~/thabithamart;MODE=PostgreSQL";

    private DB() {
    }

    public static String resolveJdbcUrl(String rawUrl) {
        if (rawUrl == null || rawUrl.isBlank()) {
            return DEFAULT_LOCAL_URL;
        }

        if (rawUrl.startsWith("postgres://") || rawUrl.startsWith("postgresql://")) {
            URI uri = URI.create(rawUrl);
            String host = uri.getHost();
            int port = uri.getPort() == -1 ? 5432 : uri.getPort();
            String path = uri.getPath() == null ? "" : uri.getPath();
            String query = uri.getQuery();

            StringBuilder jdbcUrl = new StringBuilder("jdbc:postgresql://")
                    .append(host)
                    .append(":")
                    .append(port)
                    .append(path);

            if (query != null && !query.isBlank()) {
                jdbcUrl.append("?").append(query);
            }

            return jdbcUrl.toString();
        }

        return rawUrl;
    }

    public static Connection get() throws SQLException {
        String jdbcUrl = resolveJdbcUrl(System.getenv("DATABASE_URL"));
        String username = System.getenv("DB_USERNAME");
        String password = System.getenv("DB_PASSWORD");

        if (jdbcUrl.startsWith("jdbc:postgresql://") || jdbcUrl.startsWith("jdbc:postgresql:uid=")) {
            if (username == null || username.isBlank()) {
                username = System.getenv("PGUSER");
            }
            if (password == null || password.isBlank()) {
                password = System.getenv("PGPASSWORD");
            }
            return DriverManager.getConnection(jdbcUrl, username, password);
        }

        return DriverManager.getConnection(jdbcUrl, "sa", "");
    }

    public static void init() {
        try (Connection c = get(); Statement s = c.createStatement()) {
            if (c.getMetaData().getURL().startsWith("jdbc:h2:")) {
                s.execute("RUNSCRIPT FROM 'classpath:schema.sql'");
            } else {
                s.execute("CREATE TABLE IF NOT EXISTS users ("
                        + "id BIGSERIAL PRIMARY KEY, "
                        + "username VARCHAR(80) UNIQUE NOT NULL, "
                        + "password_hash VARCHAR(100) NOT NULL, "
                        + "email VARCHAR(100) UNIQUE, "
                        + "role VARCHAR(20) NOT NULL DEFAULT 'BUYER', "
                        + "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)"
                );
                s.execute("CREATE TABLE IF NOT EXISTS products ("
                        + "id BIGSERIAL PRIMARY KEY, "
                        + "name VARCHAR(150) NOT NULL, "
                        + "category VARCHAR(50) NOT NULL, "
                        + "price DECIMAL(10,2) NOT NULL, "
                        + "stock INT NOT NULL, "
                        + "image_url VARCHAR(500), "
                        + "description VARCHAR(500), "
                        + "seller_id BIGINT)"
                );
                s.execute("INSERT INTO users (username, password_hash, email, role) VALUES "
                        + "('admin', '$2a$12$t7S16S/Gz8onn9Bo31LlyeIrXWMJJlE0F2HaSkDv22hBylqyToPE6', 'admin@thabithamart.com', 'ADMIN') "
                        + "ON CONFLICT (username) DO NOTHING");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}