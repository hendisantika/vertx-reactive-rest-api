package id.my.hendisantika.vertx_reactive_rest_api.api.handler;

import id.my.hendisantika.vertx_reactive_rest_api.api.model.Book;
import id.my.hendisantika.vertx_reactive_rest_api.api.model.BookGetAllResponse;
import id.my.hendisantika.vertx_reactive_rest_api.api.model.BookGetByIdResponse;
import id.my.hendisantika.vertx_reactive_rest_api.api.service.BookService;
import id.my.hendisantika.vertx_reactive_rest_api.api.utils.ResponseUtils;
import io.vertx.core.Future;
import io.vertx.ext.web.RoutingContext;

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

  /**
   * Read all books
   * It should return 200 OK in case of success
   * It should return 400 Bad Request, 404 Not Found or 500 Internal Server Error in case of failure
   *
   * @param rc Routing context
   * @return BookGetAllResponse
   */
  public Future<BookGetAllResponse> readAll(RoutingContext rc) {
    final String page = rc.queryParams().get(PAGE_PARAMETER);
    final String limit = rc.queryParams().get(LIMIT_PARAMETER);

    return bookService.readAll(page, limit)
      .onSuccess(success -> ResponseUtils.buildOkResponse(rc, success))
      .onFailure(throwable -> ResponseUtils.buildErrorResponse(rc, throwable));
  }

  /**
   * Read one book
   * It should return 200 OK in case of success
   * It should return 400 Bad Request, 404 Not Found or 500 Internal Server Error in case of failure
   *
   * @param rc Routing context
   * @return BookGetByIdResponse
   */
  public Future<BookGetByIdResponse> readOne(RoutingContext rc) {
    final String id = rc.pathParam(ID_PARAMETER);

    return bookService.readOne(Integer.parseInt(id))
      .onSuccess(success -> ResponseUtils.buildOkResponse(rc, success))
      .onFailure(throwable -> ResponseUtils.buildErrorResponse(rc, throwable));
  }

  /**
   * Create one book
   * It should return 201 Created in case of success
   * It should return 400 Bad Request, 404 Not Found or 500 Internal Server Error in case of failure
   *
   * @param rc Routing context
   * @return BookGetByIdResponse
   */
  public Future<BookGetByIdResponse> create(RoutingContext rc) {
    final Book book = rc.getBodyAsJson().mapTo(Book.class);

    return bookService.create(book)
      .onSuccess(success -> ResponseUtils.buildCreatedResponse(rc, success))
      .onFailure(throwable -> ResponseUtils.buildErrorResponse(rc, throwable));
  }

  /**
   * Update one book
   * It should return 200 OK in case of success
   * It should return 400 Bad Request, 404 Not Found or 500 Internal Server Error in case of failure
   *
   * @param rc Routing context
   * @return BookGetByIdResponse
   */
  public Future<BookGetByIdResponse> update(RoutingContext rc) {
    final String id = rc.pathParam(ID_PARAMETER);
    final Book book = rc.getBodyAsJson().mapTo(Book.class);

    return bookService.update(Integer.parseInt(id), book)
      .onSuccess(success -> ResponseUtils.buildOkResponse(rc, success))
      .onFailure(throwable -> ResponseUtils.buildErrorResponse(rc, throwable));
  }
}
