package id.my.hendisantika.vertx_reactive_rest_api.api.utils;

/**
 * Created by IntelliJ IDEA.
 * Project : vertx-reactive-rest-api
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 17/01/25
 * Time: 05.34
 * To change this template use File | Settings | File Templates.
 */
public class QueryUtils {
  private static final int DEFAULT_PAGE = 1;
  private static final int DEFAULT_LIMIT = 20;

  private QueryUtils() {

  }

  /**
   * Calculate page value
   *
   * @param page Page
   * @return Sanitized page
   */
  public static int getPage(String page) {
    return (page == null)
      ? DEFAULT_PAGE
      : Math.max(Integer.parseInt(page), DEFAULT_PAGE);
  }

  /**
   * Calculate limit value
   *
   * @param limit Limit
   * @return Sanitized limit
   */
  public static int getLimit(String limit) {
    return (limit == null)
      ? DEFAULT_LIMIT
      : Math.min(Integer.parseInt(limit), DEFAULT_LIMIT);
  }

  /**
   * Calculate offset
   *
   * @param page  Sanitized page
   * @param limit Sanitized limit
   * @return Offset
   */
  public static int getOffset(int page,
                              int limit) {
    if ((page - 1) * limit >= 0) {
      return (page - 1) * limit;
    } else {
      throw new NumberFormatException(LogUtils.NULL_OFFSET_ERROR_MESSAGE.buildMessage(page, limit));
    }
  }

}
