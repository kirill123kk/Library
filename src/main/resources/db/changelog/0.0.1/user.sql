
create table users
(
    id         serial primary key,
    username varchar not null check ( length(trim(username)) > 0 ) unique,
    password varchar,
    email varchar
);

create table roles
(
    id          serial primary key,
    role varchar not null check ( length(trim(role)) > 0 ) unique
);

create table users_roles
(
    id           serial primary key,
    id_user      int not null references users (id),
    id_role int not null references roles (id),
    constraint uk_user_authority unique (id_user, id_role)
);
create table if not exists book
(
    id                         serial primary key,
    book_name varchar not null check ( length(trim(book_name)) > 0 ) unique,
    genre                    varchar not null,
    author                  varchar,
    username                  varchar not null
    );

-- Вставка пользователей
INSERT INTO users (username, password, email)
VALUES ('admin', '$2a$10$/s/m0saqhE5/fFj1LO5ld.X1Hd.CQ/xvz6TGWqXeJ7GkSk.aubbde', 'admin@example.com'),
       ('string', '$2a$10$ILZ5m3.jYBEtPKyz.RQTL.vEt86OICk0OVACNH0AhKkf4SFyD2F5i', 'user1@example.com');

-- Добавляем роли
INSERT INTO roles (role)
VALUES ('ROLE_ADMIN'),
       ('ROLE_USER');

-- Связываем пользователей и роли
INSERT INTO users_roles (id_user, id_role)
VALUES ((SELECT id FROM users WHERE username = 'admin'), (SELECT id FROM roles WHERE role = 'ROLE_ADMIN')),
       ((SELECT id FROM users WHERE username = 'string'), (SELECT id FROM roles WHERE role = 'ROLE_USER'));


INSERT INTO book (book_name, genre, author, username)
VALUES ('1984', 'Dystopian Fiction', 'George Orwell', 'admin');