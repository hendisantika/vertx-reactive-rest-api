package id.my.hendisantika.vertx_reactive_rest_api.verticle;

import id.my.hendisantika.vertx_reactive_rest_api.api.handler.BookHandler;
import id.my.hendisantika.vertx_reactive_rest_api.api.handler.BookValidationHandler;
import id.my.hendisantika.vertx_reactive_rest_api.api.handler.ErrorHandler;
import id.my.hendisantika.vertx_reactive_rest_api.api.repository.BookRepository;
import id.my.hendisantika.vertx_reactive_rest_api.api.router.BookRouter;
import id.my.hendisantika.vertx_reactive_rest_api.api.router.HealthCheckRouter;
import id.my.hendisantika.vertx_reactive_rest_api.api.router.MetricsRouter;
import id.my.hendisantika.vertx_reactive_rest_api.api.service.BookService;
import id.my.hendisantika.vertx_reactive_rest_api.utils.DbUtils;
import id.my.hendisantika.vertx_reactive_rest_api.utils.LogUtils;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.pgclient.PgPool;

/**
 * Created by IntelliJ IDEA.
 * Project : vertx-reactive-rest-api
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 17/01/25
 * Time: 05.50
 * To change this template use File | Settings | File Templates.
 */
public class ApiVerticle extends AbstractVerticle {

  private static final Logger LOGGER = LoggerFactory.getLogger(ApiVerticle.class);

  @Override
  public void start(Promise<Void> promise) {
    final PgPool dbClient = DbUtils.buildDbClient(vertx);

    final BookRepository bookRepository = new BookRepository();
    final BookService bookService = new BookService(dbClient, bookRepository);
    final BookHandler bookHandler = new BookHandler(bookService);
    final BookValidationHandler bookValidationHandler = new BookValidationHandler(vertx);
    final BookRouter bookRouter = new BookRouter(vertx, bookHandler, bookValidationHandler);

    final Router router = Router.router(vertx);
    ErrorHandler.buildHandler(router);
    HealthCheckRouter.setRouter(vertx, router, dbClient);
    MetricsRouter.setRouter(router);
    bookRouter.setRouter(router);

    buildHttpServer(vertx, promise, router);
  }

  /**
   * Run HTTP server on port 8888 with specified routes
   *
   * @param vertx   Vertx context
   * @param promise Callback
   * @param router  Router
   */
  private void buildHttpServer(Vertx vertx,
                               Promise<Void> promise,
                               Router router) {
    final int port = 8888;

    vertx.createHttpServer()
      .requestHandler(router)
      .listen(port, http -> {
        if (http.succeeded()) {
          promise.complete();
          LOGGER.info(LogUtils.RUN_HTTP_SERVER_SUCCESS_MESSAGE.buildMessage(port));
        } else {
          promise.fail(http.cause());
          LOGGER.info(LogUtils.RUN_HTTP_SERVER_ERROR_MESSAGE.buildMessage());
        }
      });
  }

}
