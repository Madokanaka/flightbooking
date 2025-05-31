INSERT INTO users(email, name, password, is_frozen)
VALUES ('user@mail.com', 'user', '$2a$10$GJcJ9USsEch5l8yzAGZKbetBi/uUy.VvTynNC4s3Wac3jxWrWH45a', false);

INSERT INTO users(email, name, password, is_frozen)
VALUES ('admin', 'admin', '$2y$10$UgGstNUaojmMdugIPUBid.TETJzEXv51tmdqJoLyMfE.8BUhbMKeK', false);


INSERT INTO user_roles(user_id, role_id)
SELECT u.id, r.id
FROM users u
         JOIN roles r ON r.role_name = 'USER'
WHERE u.name = 'user';

INSERT INTO user_roles(user_id, role_id)
SELECT u.id, r.id
FROM users u
         JOIN roles r ON r.role_name = 'ADMIN'
WHERE u.name = 'admin';

