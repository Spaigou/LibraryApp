package for_bd;

import java.io.InputStream;

public class Properties {
    public static final String DB_URL = "db.url";
    public static final String DB_LOGIN = "db.login";
    public static final String DB_PASSWORD = "db.password";

    private static final java.util.Properties properties = new java.util.Properties();

    public static String get_property(String name) {
        if (properties.isEmpty()){
            try (InputStream is = Properties.class.getClassLoader().getResourceAsStream("DB.properties")) {
                properties.load(is);
            } catch (Exception ex){
                throw new RuntimeException("Error reading database settings file.");
            }
        }
        return properties.getProperty(name);
    }
}
