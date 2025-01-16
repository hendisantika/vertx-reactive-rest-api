package id.my.hendisantika.vertx_reactive_rest_api.utils;

/**
 * Created by IntelliJ IDEA.
 * Project : vertx-reactive-rest-api
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 17/01/25
 * Time: 05.31
 * To change this template use File | Settings | File Templates.
 */
public enum LogUtils {

  REGULAR_CALL_SUCCESS_MESSAGE("%s called w/ success - %s"),
  REGULAR_CALL_ERROR_MESSAGE("%s called w/ error - %s"),
  NO_BOOK_WITH_ID_MESSAGE("No book with id %d"),
  CANNOT_CREATE_BOOK_MESSAGE("Cannot create a new book"),
  RUN_HTTP_SERVER_SUCCESS_MESSAGE("HTTP server running on port %s"),
  RUN_HTTP_SERVER_ERROR_MESSAGE("Cannot run HTTP server"),
  NULL_OFFSET_ERROR_MESSAGE("Offset can't be null. Page %s and limit %s"),
  RUN_APP_SUCCESSFULLY_MESSAGE("vertx-4-reactive-rest-api started successfully in %d ms");

  private final String message;

  LogUtils(final String message) {
    this.message = message;
  }

  public String buildMessage(Object... argument) {
    return String.format(message, argument);
  }
}
