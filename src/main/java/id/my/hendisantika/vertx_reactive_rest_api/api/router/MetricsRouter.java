package id.my.hendisantika.vertx_reactive_rest_api.api.router;

import io.vertx.ext.web.Router;
import io.vertx.micrometer.PrometheusScrapingHandler;

/**
 * Created by IntelliJ IDEA.
 * Project : vertx-reactive-rest-api
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 17/01/25
 * Time: 05.46
 * To change this template use File | Settings | File Templates.
 */
public class MetricsRouter {
  private MetricsRouter() {

  }

  /**
   * Set metrics routes
   *
   * @param router Router
   */
  public static void setRouter(Router router) {
    router.route("/metrics").handler(PrometheusScrapingHandler.create());
  }
}
