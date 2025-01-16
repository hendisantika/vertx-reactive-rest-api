package id.my.hendisantika.vertx_reactive_rest_api.api.router;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.LoggerFormat;
import io.vertx.ext.web.handler.LoggerHandler;

/**
 * Created by IntelliJ IDEA.
 * Project : vertx-reactive-rest-api
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 17/01/25
 * Time: 05.39
 * To change this template use File | Settings | File Templates.
 */
public class BookRouter {
  private final Vertx vertx;
  private final BookHandler bookHandler;
  private final BookValidationHandler bookValidationHandler;

  public BookRouter(Vertx vertx,
                    BookHandler bookHandler,
                    BookValidationHandler bookValidationHandler) {
    this.vertx = vertx;
    this.bookHandler = bookHandler;
    this.bookValidationHandler = bookValidationHandler;
  }

  /**
   * Set books API routes
   *
   * @param router Router
   */
  public void setRouter(Router router) {
    router.mountSubRouter("/api/v1", buildBookRouter());
  }

  /**
   * Build books API
   * All routes are composed by an error handler, a validation handler and the actual business logic handler
   */
  private Router buildBookRouter() {
    final Router bookRouter = Router.router(vertx);

    bookRouter.route("/books*").handler(BodyHandler.create());
    bookRouter.get("/books").handler(LoggerHandler.create(LoggerFormat.DEFAULT)).handler(bookValidationHandler.readAll()).handler(bookHandler::readAll);
    bookRouter.get("/books/:id").handler(LoggerHandler.create(LoggerFormat.DEFAULT)).handler(bookValidationHandler.readOne()).handler(bookHandler::readOne);
    bookRouter.post("/books").handler(LoggerHandler.create(LoggerFormat.DEFAULT)).handler(bookValidationHandler.create()).handler(bookHandler::create);
    bookRouter.put("/books/:id").handler(LoggerHandler.create(LoggerFormat.DEFAULT)).handler(bookValidationHandler.update()).handler(bookHandler::update);
    bookRouter.delete("/books/:id").handler(LoggerHandler.create(LoggerFormat.DEFAULT)).handler(bookValidationHandler.delete()).handler(bookHandler::delete);

    return bookRouter;
  }
}
