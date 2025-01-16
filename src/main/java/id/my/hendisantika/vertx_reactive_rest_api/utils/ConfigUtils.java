package id.my.hendisantika.vertx_reactive_rest_api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * Project : vertx-reactive-rest-api
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 17/01/25
 * Time: 05.32
 * To change this template use File | Settings | File Templates.
 */
public class ConfigUtils {
  private static ConfigUtils instance;

  private final Properties properties;

  private ConfigUtils() {
    this.properties = readProperties();
  }

  public static ConfigUtils getInstance() {
    if (instance == null) {
      instance = new ConfigUtils();
    }

    return instance;
  }

  public Properties getProperties() {
    return properties;
  }

  /**
   * Read application.properties file on resources folder
   *
   * @return Properties
   */
  private Properties readProperties() {
    Properties properties = new Properties();

    try {
      try (InputStream is = getClass().getClassLoader().getResourceAsStream("application.properties")) {
        try {
          properties.load(is);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return properties;
  }
}
