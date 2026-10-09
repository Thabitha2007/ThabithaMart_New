package com.thabitha.thabithamart.util;

import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.mindrot.jbcrypt.BCrypt;           // NEW
import org.slf4j.Logger;                     // NEW
import org.slf4j.LoggerFactory;              // NEW

import com.zaxxer.hikari.HikariConfig;       // NEW
import com.zaxxer.hikari.HikariDataSource;   // NEW

public final class DB {

    private static final Logger log = LoggerFactory.getLogger(DB.class); // NEW

    // Server/Docker environment-ல JDBC drivers தானா register ஆகாது —
    // அதனால இங்க explicit-ஆ load பண்றோம்.
    static {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("H2 driver not found on classpath: " + e.getMessage());
        }
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL driver not found on classpath: " + e.getMessage());
        }
    }

    private static final String DEFAULT_LOCAL_URL = "jdbc:h2:~/thabithamart;MODE=PostgreSQL";

    // NEW: HikariCP connection pool (created on first use)
    private static HikariDataSource dataSource;

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

    // NEW: builds the pool once, using the same URL and credential rules as before
    private static synchronized HikariDataSource pool() {
        if (dataSource == null || dataSource.isClosed()) {
            String jdbcUrl = resolveJdbcUrl(System.getenv("DATABASE_URL"));
            String username = System.getenv("DB_USERNAME");
            String password = System.getenv("DB_PASSWORD");

            boolean postgres = jdbcUrl.startsWith("jdbc:postgresql://")
                    || jdbcUrl.startsWith("jdbc:postgresql:uid=");
            if (postgres) {
                if (username == null || username.isBlank()) {
                    username = System.getenv("PGUSER");
                }
                if (password == null || password.isBlank()) {
                    password = System.getenv("PGPASSWORD");
                }
            } else {
                username = "sa";
                password = "";
            }

            HikariConfig cfg = new HikariConfig();
            cfg.setJdbcUrl(jdbcUrl);
            cfg.setUsername(username);
            cfg.setPassword(password);
            cfg.setMaximumPoolSize(10);
            cfg.setMinimumIdle(2);
            cfg.setConnectionTimeout(10000);
            cfg.setPoolName("ThabithaMartPool");
            dataSource = new HikariDataSource(cfg);
            log.info("Connection pool started for {}", jdbcUrl.startsWith("jdbc:h2:") ? "H2" : "PostgreSQL");
        }
        return dataSource;
    }

    // CHANGED: now returns a pooled connection. Callers still use try-with-resources.
    public static Connection get() throws SQLException {
        return pool().getConnection();
    }

    // NEW: called when the app stops
    public static synchronized void close() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            log.info("Connection pool closed");
        }
    }

    public static void init() {
        try (Connection c = get(); Statement s = c.createStatement()) {
            if (c.getMetaData().getURL().startsWith("jdbc:h2:")) {
                s.execute("RUNSCRIPT FROM 'classpath:schema.sql' CHARSET 'UTF-8'");
                fixDemoPasswords(c);   // NEW
                assignOrphanProducts(c); // NEW
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

    // NEW: if a demo account's stored hash does not match its documented password, replace it
    private static void fixDemoPasswords(Connection c) throws SQLException {
        String[][] demo = { {"seller1", "seller123"}, {"buyer1", "buyer123"} };
        for (String[] d : demo) {
            String hash = null;
            try (PreparedStatement ps = c.prepareStatement("SELECT password_hash FROM users WHERE username = ?")) {
                ps.setString(1, d[0]);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        hash = rs.getString(1);
                    }
                }
            }
            if (hash == null) {
                continue;
            }
            boolean ok;
            try {
                ok = BCrypt.checkpw(d[1], hash);
            } catch (IllegalArgumentException ex) {
                ok = false;
            }
            if (!ok) {
                try (PreparedStatement ps = c.prepareStatement("UPDATE users SET password_hash = ? WHERE username = ?")) {
                    ps.setString(1, BCrypt.hashpw(d[1], BCrypt.gensalt(10)));
                    ps.setString(2, d[0]);
                    ps.executeUpdate();
                }
                log.warn("Reset password hash for demo account {}", d[0]);
            }
        }
    }

    // NEW: seeded products with no seller become seller1's products
    private static void assignOrphanProducts(Connection c) throws SQLException {
        try (PreparedStatement ps = c.prepareStatement(
                "UPDATE products SET seller_id = (SELECT id FROM users WHERE username = ?) WHERE seller_id IS NULL")) {
            ps.setString(1, "seller1");
            ps.executeUpdate();
        }
    }
}