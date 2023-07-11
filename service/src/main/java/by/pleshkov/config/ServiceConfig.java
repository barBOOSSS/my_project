package by.pleshkov.config;

import by.pleshkov.database.config.DatabaseConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import(DatabaseConfig.class)
@ComponentScan("by.pleshkov.service")
@Configuration
public class ServiceConfig {
}
