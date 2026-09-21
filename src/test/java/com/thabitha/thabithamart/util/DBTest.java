package com.thabitha.thabithamart.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DBTest {

    @Test
    void resolveJdbcUrlConvertsRenderPostgresUrl() {
        String renderUrl = "postgres://user:pass@dpg-example:5432/thabithamart";

        assertEquals(
                "jdbc:postgresql://dpg-example:5432/thabithamart",
                DB.resolveJdbcUrl(renderUrl)
        );
    }

    @Test
    void resolveJdbcUrlFallsBackToLocalH2() {
        assertEquals(
                "jdbc:h2:~/thabithamart;MODE=PostgreSQL",
                DB.resolveJdbcUrl(null)
        );
    }
}
