package id.my.hendisantika.vertx_reactive_rest_api.api.router;

import io.vertx.core.Vertx;

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
}
