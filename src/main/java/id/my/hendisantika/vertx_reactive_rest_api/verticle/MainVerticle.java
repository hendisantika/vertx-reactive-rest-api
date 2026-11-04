package id.my.hendisantika.vertx_reactive_rest_api.verticle;

import id.my.hendisantika.vertx_reactive_rest_api.utils.LogUtils;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.ThreadingModel;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * Project : vertx-reactive-rest-api
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Link : s.id/hendisantika
 * Date: 05/11/26
 * Time: 05.46
 */
public class MainVerticle extends AbstractVerticle {

  private static final Logger LOGGER = LoggerFactory.getLogger(MainVerticle.class);

  @Override
  public void start() {
    final long start = System.currentTimeMillis();

    deployMigrationVerticle(vertx)
      .flatMap(migrationVerticleId -> deployApiVerticle(vertx))
      .onSuccess(success -> LOGGER.info(LogUtils.RUN_APP_SUCCESSFULLY_MESSAGE.buildMessage(System.currentTimeMillis() - start)))
      .onFailure(throwable -> LOGGER.error(throwable.getMessage()));
  }

  private Future<Void> deployMigrationVerticle(Vertx vertx) {
    final DeploymentOptions options = new DeploymentOptions()
      .setThreadingModel(ThreadingModel.WORKER)
      .setWorkerPoolName("migrations-worker-pool")
      .setInstances(1)
      .setWorkerPoolSize(1);

    return vertx.deployVerticle(MigrationVerticle.class.getName(), options)
      .flatMap(vertx::undeploy);
  }

  private Future<String> deployApiVerticle(Vertx vertx) {
    return vertx.deployVerticle(ApiVerticle.class.getName());
  }


}
