CREATE TABLE IF NOT EXISTS roles
(
    id VARCHAR(50)
        CONSTRAINT role_pk PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS users
(
    id VARCHAR(50)
        CONSTRAINT user_pk PRIMARY KEY,
    email VARCHAR(60)  NOT NULL,
    first_name VARCHAR(60),
    last_name VARCHAR(60),
    password VARCHAR(60),
    UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS user_role_relation
(
    role_id VARCHAR(50) REFERENCES roles (id) ON DELETE CASCADE,
    user_id VARCHAR(50) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT user_role_pk PRIMARY KEY (role_id, user_id)
);

CREATE TABLE IF NOT EXISTS products
(
    id VARCHAR(50)
        CONSTRAINT product_pk PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    price BIGINT
);

CREATE TABLE IF NOT EXISTS manufacturers
(
    id VARCHAR(50)
        CONSTRAINT manufacturer_pk PRIMARY KEY,
    name VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS product_manufacturer_relation
(
    product_id VARCHAR(50) REFERENCES products (id) ON DELETE CASCADE,
    manufacturer_id VARCHAR(50) REFERENCES manufacturers (id) ON DELETE CASCADE,
    CONSTRAINT product_manufacturer_pk PRIMARY KEY (product_id, manufacturer_id)
);


