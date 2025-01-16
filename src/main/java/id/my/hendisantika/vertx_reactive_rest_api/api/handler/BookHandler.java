package id.my.hendisantika.vertx_reactive_rest_api.api.handler;

import id.my.hendisantika.vertx_reactive_rest_api.api.service.BookService;

/**
 * Created by IntelliJ IDEA.
 * Project : vertx-reactive-rest-api
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 17/01/25
 * Time: 05.40
 * To change this template use File | Settings | File Templates.
 */
public class BookHandler {
  private static final String ID_PARAMETER = "id";
  private static final String PAGE_PARAMETER = "page";
  private static final String LIMIT_PARAMETER = "limit";

  private final BookService bookService;

  public BookHandler(BookService bookService) {
    this.bookService = bookService;
  }
}
