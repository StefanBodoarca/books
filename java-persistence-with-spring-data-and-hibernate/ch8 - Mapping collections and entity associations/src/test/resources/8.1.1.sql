create table item (
  id bigint primary key,
  name varchar(256)
);

CREATE TABLE image (
   item_id BIGINT NOT NULL,
   filename VARCHAR(256) NOT NULL,
   PRIMARY KEY (item_id, filename),
   FOREIGN KEY (item_id) REFERENCES item(id) on delete cascade
)

