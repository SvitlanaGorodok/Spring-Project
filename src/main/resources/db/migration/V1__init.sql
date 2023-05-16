CREATE TABLE IF NOT EXISTS roles
(
    id UUID
        CONSTRAINT role_pk PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS users
(
    id UUID
        CONSTRAINT user_pk PRIMARY KEY,
    email VARCHAR(50)  NOT NULL,
    UNIQUE (email),
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    role_id UUID REFERENCES roles(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS manufacturers
(
    id UUID
        CONSTRAINT manufacturer_pk PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS products
(
    id UUID
        CONSTRAINT product_pk PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    price BIGINT NOT NULL,
    manufacturer_id UUID REFERENCES manufacturers(id) ON DELETE CASCADE
);


