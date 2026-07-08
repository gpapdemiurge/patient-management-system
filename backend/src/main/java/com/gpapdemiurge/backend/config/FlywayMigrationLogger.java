package com.gpapdemiurge.backend.config;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;
import org.flywaydb.core.api.MigrationInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

/**
 * Logs Flyway migration information on application startup.
 * This helps verify that Flyway is correctly picking up migration scripts
 * and applying them to the configured schema.
 */
@Configuration
public class FlywayMigrationLogger implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(FlywayMigrationLogger.class);

    private final Flyway flyway;

    public FlywayMigrationLogger(Flyway flyway) {
        this.flyway = flyway;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            MigrationInfoService info = flyway.info();
            MigrationInfo[] pending = info.pending();
            MigrationInfo[] applied = info.applied();

            logger.info("=== Flyway Migration Summary ===");
            logger.info("Applied migrations: {}", applied.length);
            for (MigrationInfo mi : applied) {
                logger.info("  Applied: {} - {}", mi.getVersion(), mi.getDescription());
            }
            logger.info("Pending migrations: {}", pending.length);
            for (MigrationInfo mi : pending) {
                logger.info("  Pending: {} - {}", mi.getVersion(), mi.getDescription());
            }
        } catch (Exception e) {
            logger.error("Error while retrieving Flyway migration info", e);
        }
    }
}
