package id.my.hendisantika.vertx_reactive_rest_api.api.service;

import id.my.hendisantika.vertx_reactive_rest_api.api.repository.BookRepository;
import io.vertx.pgclient.PgPool;

/**
 * Created by IntelliJ IDEA.
 * Project : vertx-reactive-rest-api
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 17/01/25
 * Time: 05.37
 * To change this template use File | Settings | File Templates.
 */
public class BookService {
  private static final Logger LOGGER = LoggerFactory.getLogger(BookService.class);

  private final PgPool dbClient;
  private final BookRepository bookRepository;

  public BookService(PgPool dbClient,
                     BookRepository bookRepository) {
    this.dbClient = dbClient;
    this.bookRepository = bookRepository;
  }
}
