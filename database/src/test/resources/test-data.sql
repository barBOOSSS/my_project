INSERT INTO users(name, surname, password, email, role, created_at)
VALUES ('Bred', 'Pit', '111', 'pit@gmail.com', 'USER', now()),
       ('Vin', 'Diesel', '222', 'vin@gmail.com', 'USER', now());


INSERT INTO rooms (number, places, price, class_room, status_room)
VALUES (1, 1, 100, 'STANDARD', 'FREE'),
       (2, 2, 200, 'STANDARD', 'FREE'),
       (3, 3, 300, 'SUITE', 'FREE'),
       (4, 4, 400, 'SUITE', 'FREE');

INSERT INTO room_user(room_id, user_id)
SELECT r.id, u.id
FROM rooms r
         JOIN users u ON r.number = 1 AND u.name = 'Bred';

INSERT INTO room_user(room_id, user_id)
SELECT r.id, u.id
FROM rooms r
         JOIN users u ON r.number = 2 AND u.name = 'Bred';

INSERT INTO room_user(room_id, user_id)
SELECT r.id, u.id
FROM rooms r
         JOIN users u ON r.number = 3 AND u.name = 'Vin';

INSERT INTO orders(user_id, price, status_order, solution, created_at)
VALUES ((SELECT users.id FROM users WHERE users.name = 'Bred'), 200, 'NEW', 'DENIED', now()),
       ((SELECT users.id FROM users WHERE users.name = 'Vin'), 300, 'NEW', 'UNPROCESSED', now()),
       ((SELECT users.id FROM users WHERE users.name = 'Vin'), 400, 'CLOSE', 'DENIED', now());

