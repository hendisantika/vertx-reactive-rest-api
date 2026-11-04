package id.my.hendisantika.vertx_reactive_rest_api.api.router;

import io.vertx.core.Vertx;
import io.vertx.ext.healthchecks.Status;
import io.vertx.ext.web.healthchecks.HealthCheckHandler;
import io.vertx.ext.web.Router;
import io.vertx.sqlclient.Pool;

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
public class HealthCheckRouter {
  private HealthCheckRouter() {
  }

  /**
   * Set health check routes
   *
   * @param vertx    Vertx context
   * @param router   Router
   * @param dbClient PostgreSQL pool
   */
  public static void setRouter(Vertx vertx,
                               Router router,
                               Pool dbClient) {
    final HealthCheckHandler healthCheckHandler = HealthCheckHandler.create(vertx);

    healthCheckHandler.register("database",
      promise ->
        dbClient.getConnection()
          .onSuccess(connection -> {
            connection.close();
            promise.complete(Status.OK());
          })
          .onFailure(promise::fail)
    );

    router.get("/health").handler(healthCheckHandler);
  }
}
