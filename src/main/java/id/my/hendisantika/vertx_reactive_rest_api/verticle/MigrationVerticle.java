package id.my.hendisantika.vertx_reactive_rest_api.verticle;

import id.my.hendisantika.vertx_reactive_rest_api.utils.DbUtils;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.Configuration;

/**
 * Created by IntelliJ IDEA.
 * Project : vertx-reactive-rest-api
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 17/01/25
 * Time: 05.51
 * To change this template use File | Settings | File Templates.
 */
public class MigrationVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> promise) {
    final Configuration config = DbUtils.buildMigrationsConfiguration();
    final Flyway flyway = new Flyway(config);

    flyway.migrate();

    promise.complete();
  }
}
