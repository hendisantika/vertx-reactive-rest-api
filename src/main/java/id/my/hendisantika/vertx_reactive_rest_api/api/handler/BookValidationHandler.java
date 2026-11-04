package id.my.hendisantika.vertx_reactive_rest_api.api.handler;

import io.vertx.core.Vertx;
import io.vertx.ext.web.validation.RequestPredicate;
import io.vertx.ext.web.validation.ValidationHandler;
import io.vertx.ext.web.validation.builder.Bodies;
import io.vertx.ext.web.validation.builder.ParameterProcessorFactory;
import io.vertx.ext.web.validation.builder.Parameters;
import io.vertx.ext.web.validation.builder.ValidationHandlerBuilder;
import io.vertx.json.schema.Draft;
import io.vertx.json.schema.JsonSchemaOptions;
import io.vertx.json.schema.SchemaRepository;
import io.vertx.json.schema.common.dsl.ObjectSchemaBuilder;

import static io.vertx.json.schema.common.dsl.Keywords.maxLength;
import static io.vertx.json.schema.common.dsl.Keywords.maximum;
import static io.vertx.json.schema.common.dsl.Keywords.minLength;
import static io.vertx.json.schema.common.dsl.Schemas.intSchema;
import static io.vertx.json.schema.common.dsl.Schemas.objectSchema;
import static io.vertx.json.schema.common.dsl.Schemas.stringSchema;

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
    final SchemaRepository schemaRepository = buildSchemaRepository();

    return ValidationHandlerBuilder
      .create(schemaRepository)
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
    final SchemaRepository schemaRepository = buildSchemaRepository();

    return ValidationHandlerBuilder
      .create(schemaRepository)
      .pathParameter(buildIdPathParameter())
      .build();
  }

  /**
   * Build create one book request validation
   *
   * @return ValidationHandler
   */
  public ValidationHandler create() {
    final SchemaRepository schemaRepository = buildSchemaRepository();
    final ObjectSchemaBuilder schemaBuilder = buildBodySchemaBuilder();

    return ValidationHandlerBuilder
      .create(schemaRepository)
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
    final SchemaRepository schemaRepository = buildSchemaRepository();
    final ObjectSchemaBuilder schemaBuilder = buildBodySchemaBuilder();

    return ValidationHandlerBuilder
      .create(schemaRepository)
      .predicate(RequestPredicate.BODY_REQUIRED)
      .body(Bodies.json(schemaBuilder))
      .pathParameter(buildIdPathParameter())
      .build();
  }

  /**
   * Build delete one book request validation
   *
   * @return ValidationHandler
   */
  public ValidationHandler delete() {
    final SchemaRepository schemaRepository = buildSchemaRepository();

    return ValidationHandlerBuilder
      .create(schemaRepository)
      .pathParameter(buildIdPathParameter())
      .build();
  }

  private SchemaRepository buildSchemaRepository() {
    return SchemaRepository.create(new JsonSchemaOptions()
      .setDraft(Draft.DRAFT7)
      .setBaseUri("app://"));
  }

  private ObjectSchemaBuilder buildBodySchemaBuilder() {
    return objectSchema()
      .requiredProperty("author", stringSchema().with(minLength(1)).with(maxLength(255)))
      .requiredProperty("country", stringSchema().with(minLength(1)).with(maxLength(255)).nullable())
      .requiredProperty("image_link", stringSchema().with(minLength(1)).with(maxLength(255)).nullable())
      .requiredProperty("language", stringSchema().with(minLength(1)).with(maxLength(255)).nullable())
      .requiredProperty("link", stringSchema().with(minLength(1)).with(maxLength(255)).nullable())
      .requiredProperty("pages", intSchema().with(maximum(10000)).nullable())
      .requiredProperty("title", stringSchema().with(minLength(1)).with(maxLength(255)))
      .requiredProperty("year", intSchema().with(maximum(10000)).nullable());
  }

  private ParameterProcessorFactory buildIdPathParameter() {
    return Parameters.param("id", intSchema());
  }

  private ParameterProcessorFactory buildPageQueryParameter() {
    return Parameters.optionalParam("page", intSchema());
  }

  private ParameterProcessorFactory buildLimitQueryParameter() {
    return Parameters.optionalParam("limit", intSchema());
  }

}
