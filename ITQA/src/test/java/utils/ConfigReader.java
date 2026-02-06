package utils;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input =
                     ConfigReader.class
                             .getClassLoader()
                             .getResourceAsStream("config.properties")) {

            if (input == null) {
                throw new RuntimeException("config.properties not found in classpath");
            }

            properties.load(input);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    // Generic getter
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    // Typed helper methods
    public static String getBaseUrl() {
        return properties.getProperty("base.url", "http://localhost:8081");
    }

    public static String getBrowser() {
        return properties.getProperty("browser", "chrome");
    }

    public static int getImplicitWait() {
        return Integer.parseInt(
                properties.getProperty("implicit.wait", "10")
        );
    }

    public static int getPageLoadTimeout() {
        return Integer.parseInt(
                properties.getProperty("page.load.timeout", "30")
        );
    }

    public static int getExplicitWait() {
        return Integer.parseInt(
                properties.getProperty("explicit.wait", "15")
        );
    }
}
