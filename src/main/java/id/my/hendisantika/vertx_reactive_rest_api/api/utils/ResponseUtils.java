package id.my.hendisantika.vertx_reactive_rest_api.api.utils;

import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;

/**
 * Created by IntelliJ IDEA.
 * Project : vertx-reactive-rest-api
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 17/01/25
 * Time: 05.35
 * To change this template use File | Settings | File Templates.
 */
public class ResponseUtils {
  private static final String CONTENT_TYPE_HEADER = "Content-Type";
  private static final String APPLICATION_JSON = "application/json";

  private ResponseUtils() {

  }

  /**
   * Build success response using 200 OK as its status code and response as its body
   *
   * @param rc       Routing context
   * @param response Response body
   */
  public static void buildOkResponse(RoutingContext rc,
                                     Object response) {
    rc.response()
      .setStatusCode(200)
      .putHeader(CONTENT_TYPE_HEADER, APPLICATION_JSON)
      .end(Json.encodePrettily(response));
  }

  /**
   * Build success response using 201 Created as its status code and response as its body
   *
   * @param rc       Routing context
   * @param response Response body
   */
  public static void buildCreatedResponse(RoutingContext rc,
                                          Object response) {
    rc.response()
      .setStatusCode(201)
      .putHeader(CONTENT_TYPE_HEADER, APPLICATION_JSON)
      .end(Json.encodePrettily(response));
  }

  /**
   * Build success response using 204 No Content as its status code and no body
   *
   * @param rc Routing context
   */
  public static void buildNoContentResponse(RoutingContext rc) {
    rc.response()
      .setStatusCode(204)
      .end();
  }
}
