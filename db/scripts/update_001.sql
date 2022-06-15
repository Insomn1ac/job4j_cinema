CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  username VARCHAR NOT NULL,
  email VARCHAR NOT NULL UNIQUE,
  phone VARCHAR NOT NULL UNIQUE
);

CREATE TABLE sessions (
  id SERIAL PRIMARY KEY,
  name text,
  description text,
  timeofsession text,
  photo bytea
);

CREATE TABLE ticket (
    id SERIAL PRIMARY KEY,
    session_id INT NOT NULL REFERENCES sessions(id),
    pos_row INT NOT NULL,
    cell INT NOT NULL,
    user_id INT NOT NULL REFERENCES users(id)
);

ALTER TABLE ticket ADD CONSTRAINT unique_seat UNIQUE (session_id, pos_row, cell)