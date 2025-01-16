CREATE TABLE if NOT EXISTS books
(
  id
  SERIAL
  NOT
  NULL,
  author
  VARCHAR
(
  255
) NOT NULL
  , country VARCHAR
(
  255
)
  , image_link VARCHAR
(
  255
)
  , LANGUAGE VARCHAR
(
  255
)
  , link VARCHAR
(
  255
)
  , pages INT4
  , title VARCHAR
(
  255
) NOT NULL
  , YEAR INT4
  , PRIMARY KEY
(
  id
)
  );
