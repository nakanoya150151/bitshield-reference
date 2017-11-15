package com.fressets.bitshield.config;

import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class FlywayConfig {

	// ymlで管理
	private boolean clearDb = true;

	@Bean
	@Profile({ "dev" })
	public FlywayMigrationStrategy strategy() {
		return new FlywayMigrationStrategy() {
			@Override
			public void migrate(Flyway flyway) {
				if (FlywayConfig.this.clearDb) {
					flyway.clean();
				}
				flyway.migrate();
			}
		};
	}
}
