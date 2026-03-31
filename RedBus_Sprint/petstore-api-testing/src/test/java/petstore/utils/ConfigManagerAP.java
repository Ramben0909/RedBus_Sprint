package petstore.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class ConfigManagerAP {

    private static final Properties props = new Properties();

    static {
        try (InputStream is = ConfigManagerAP.class
                .getClassLoader()
                .getResourceAsStream("configAP.properties")) {

            if (is == null) {
                throw new RuntimeException("config.properties not found on classpath!");
            }
            props.load(is);
            System.out.println("Config loaded successfully.");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    
    
    public static String get(String key) {
        String value = props.getProperty(key);
        if (value == null) {
            System.err.println("Config key not found: " + key);
        }
        return value;
    }

   
    public static String get(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }
}
