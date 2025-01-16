package id.my.hendisantika.vertx_reactive_rest_api.api.handler;

import id.my.hendisantika.vertx_reactive_rest_api.api.utils.ResponseUtils;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.validation.BadRequestException;
import io.vertx.ext.web.validation.BodyProcessorException;
import io.vertx.ext.web.validation.ParameterProcessorException;
import io.vertx.ext.web.validation.RequestPredicateException;

/**
 * Created by IntelliJ IDEA.
 * Project : vertx-reactive-rest-api
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 17/01/25
 * Time: 05.41
 * To change this template use File | Settings | File Templates.
 */
public class ErrorHandler {
  private ErrorHandler() {
  }

  /**
   * Build error handler
   * It's useful to handle errors thrown by Web Validation
   *
   * @param router Router
   */
  public static void buildHandler(Router router) {
    router.errorHandler(400, rc -> {
      if (rc.failure() instanceof BadRequestException) {
        if (rc.failure() instanceof ParameterProcessorException) {
          // Something went wrong while parsing/validating a parameter
          ResponseUtils.buildErrorResponse(rc, new IllegalArgumentException("Path parameter is invalid"));
        } else if (rc.failure() instanceof BodyProcessorException | rc.failure() instanceof RequestPredicateException) {
          // Something went wrong while parsing/validating the body
          ResponseUtils.buildErrorResponse(rc, new IllegalArgumentException("Request body is invalid"));
        }
      }
    });
  }
}
