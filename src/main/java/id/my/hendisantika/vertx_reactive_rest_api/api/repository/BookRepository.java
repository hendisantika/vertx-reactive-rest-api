package id.my.hendisantika.vertx_reactive_rest_api.api.repository;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * Project : vertx-reactive-rest-api
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 17/01/25
 * Time: 05.29
 * To change this template use File | Settings | File Templates.
 */
public class BookRepository {
  private static final Logger LOGGER = LoggerFactory.getLogger(BookRepository.class);

  private static final String SQL_SELECT_ALL = "SELECT * FROM books LIMIT #{limit} OFFSET #{offset}";
  private static final String SQL_SELECT_BY_ID = "SELECT * FROM books WHERE id = #{id}";
  private static final String SQL_INSERT = "INSERT INTO books (author, country, image_link, language, link, pages, title, year) " +
    "VALUES (#{author}, #{country}, #{image_link}, #{language}, #{link}, #{pages}, #{title}, #{year}) RETURNING id";
  private static final String SQL_UPDATE = "UPDATE books SET author = #{author}, country = #{country}, image_link = #{image_link}, " +
    "language = #{language}, link = #{link}, pages = #{pages}, title = #{title}, year = #{year} WHERE id = #{id}";
  private static final String SQL_DELETE = "DELETE FROM books WHERE id = #{id}";
  private static final String SQL_COUNT = "SELECT COUNT(*) AS total FROM books";

  public BookRepository() {
  }
}
