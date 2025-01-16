package id.my.hendisantika.vertx_reactive_rest_api.api.handler;

import io.vertx.core.Vertx;
import io.vertx.ext.web.validation.RequestPredicate;
import io.vertx.ext.web.validation.ValidationHandler;
import io.vertx.ext.web.validation.builder.Bodies;
import io.vertx.json.schema.SchemaParser;
import io.vertx.json.schema.common.dsl.ObjectSchemaBuilder;

/**
 * Created by IntelliJ IDEA.
 * Project : vertx-reactive-rest-api
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 17/01/25
 * Time: 05.43
 * To change this template use File | Settings | File Templates.
 */
public class BookValidationHandler {
  private final Vertx vertx;

  public BookValidationHandler(Vertx vertx) {
    this.vertx = vertx;
  }

  /**
   * Build read all books request validation
   *
   * @return ValidationHandler
   */
  public ValidationHandler readAll() {
    final SchemaParser schemaParser = buildSchemaParser();

    return ValidationHandler
      .builder(schemaParser)
      .queryParameter(buildPageQueryParameter())
      .queryParameter(buildLimitQueryParameter())
      .build();
  }

  /**
   * Build read one book request validation
   *
   * @return ValidationHandler
   */
  public ValidationHandler readOne() {
    final SchemaParser schemaParser = buildSchemaParser();

    return ValidationHandler
      .builder(schemaParser)
      .pathParameter(buildIdPathParameter())
      .build();
  }

  /**
   * Build create one book request validation
   *
   * @return ValidationHandler
   */
  public ValidationHandler create() {
    final SchemaParser schemaParser = buildSchemaParser();
    final ObjectSchemaBuilder schemaBuilder = buildBodySchemaBuilder();

    return ValidationHandler
      .builder(schemaParser)
      .predicate(RequestPredicate.BODY_REQUIRED)
      .body(Bodies.json(schemaBuilder))
      .build();
  }

  /**
   * Build update one book request validation
   *
   * @return ValidationHandler
   */
  public ValidationHandler update() {
    final SchemaParser schemaParser = buildSchemaParser();
    final ObjectSchemaBuilder schemaBuilder = buildBodySchemaBuilder();

    return ValidationHandler
      .builder(schemaParser)
      .predicate(RequestPredicate.BODY_REQUIRED)
      .body(Bodies.json(schemaBuilder))
      .pathParameter(buildIdPathParameter())
      .build();
  }
}
