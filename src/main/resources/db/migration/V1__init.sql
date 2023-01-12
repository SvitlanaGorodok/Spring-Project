create table roles
(
    id VARCHAR(50)
        constraint role_pk PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    UNIQUE (name)
);

create table users
(
    id VARCHAR(50)
        constraint user_pk PRIMARY KEY,
    email VARCHAR(60)  NOT NULL,
    first_name VARCHAR(60)  NOT NULL,
    last_name VARCHAR(60)  NOT NULL,
    password VARCHAR(60) NOT NULL,
    UNIQUE (email)
);

create table user_role_relation
(
    role_id VARCHAR(50) REFERENCES roles (id) ON DELETE CASCADE,
    user_id VARCHAR(50) REFERENCES users (id) ON DELETE CASCADE,
    constraint user_role_pk PRIMARY KEY (role_id, user_id)
);



