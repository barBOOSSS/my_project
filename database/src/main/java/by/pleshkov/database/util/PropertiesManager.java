package by.pleshkov.database.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.io.InputStream;
import java.util.Properties;

@UtilityClass
public class PropertiesManager {

    private final Properties PROPERTIES = new Properties();

    static {
        loadProperties();
    }

    public String get(String key) {
        return PROPERTIES.getProperty(key);
    }

    @SneakyThrows
    private static void loadProperties() {
        try (InputStream inputStream = Thread.currentThread()
                .getContextClassLoader().getResourceAsStream("application.properties")) {
            PROPERTIES.load(inputStream);
        }
    }
}
