INSERT INTO users(email, name, password, is_frozen)
VALUES ('flight@mail.com', 'FLYWAY', '$2a$10$GJcJ9USsEch5l8yzAGZKbetBi/uUy.VvTynNC4s3Wac3jxWrWH45a', false);

INSERT INTO user_roles(user_id, role_id)
SELECT u.id, r.id
FROM users u
         JOIN roles r ON r.role_name = 'COMPANY'
WHERE u.name = 'FLYWAY';

INSERT INTO flight (flight_number, departure_city, arrival_city, departure_time, arrival_time, company_id)
SELECT
    f.flight_number, f.departure_city, f.arrival_city,
    f.departure_time, f.arrival_time, u.id
FROM (
         VALUES
             ('FL001', 'DUBLIN', 'LONDON', '2025-06-01 08:00:00', '2025-06-01 10:00:00'),
             ('FL002', 'LONDON', 'PARIS', '2025-06-02 09:00:00', '2025-06-02 11:00:00'),
             ('FL003', 'PARIS', 'TOKYO', '2025-06-03 10:00:00', '2025-06-03 22:00:00'),
             ('FL004', 'TOKYO', 'DUBAI', '2025-06-04 11:00:00', '2025-06-04 15:00:00'),
             ('FL005', 'DUBAI', 'NEW YORK', '2025-06-05 12:00:00', '2025-06-05 20:00:00')
     ) AS f(flight_number, departure_city, arrival_city, departure_time, arrival_time),
     users u
WHERE u.name = 'FLYWAY';

INSERT INTO ticket (seat_number, ticket_class, price, is_booked, flight_id)
SELECT v.seat_number, v.ticket_class, v.price, v.is_booked, f.id
FROM (
         VALUES
             ('1A', 'BUSINESS', 500.00, FALSE, 'FL001'), ('1B', 'BUSINESS', 500.00, FALSE, 'FL001'), ('1C', 'BUSINESS', 500.00, FALSE, 'FL001'),
             ('2A', 'ECONOMY', 200.00, FALSE, 'FL001'), ('2B', 'ECONOMY', 200.00, FALSE, 'FL001'), ('2C', 'ECONOMY', 200.00, FALSE, 'FL001'),
             ('3A', 'ECONOMY', 200.00, FALSE, 'FL001'), ('3B', 'ECONOMY', 200.00, FALSE, 'FL001'), ('3C', 'ECONOMY', 200.00, FALSE, 'FL001'),
             ('4A', 'ECONOMY', 200.00, FALSE, 'FL001'),

             ('1A', 'BUSINESS', 450.00, FALSE, 'FL002'), ('1B', 'BUSINESS', 450.00, FALSE, 'FL002'), ('1C', 'BUSINESS', 450.00, FALSE, 'FL002'),
             ('2A', 'ECONOMY', 180.00, FALSE, 'FL002'), ('2B', 'ECONOMY', 180.00, FALSE, 'FL002'), ('2C', 'ECONOMY', 180.00, FALSE, 'FL002'),
             ('3A', 'ECONOMY', 180.00, FALSE, 'FL002'), ('3B', 'ECONOMY', 180.00, FALSE, 'FL002'), ('3C', 'ECONOMY', 180.00, FALSE, 'FL002'),
             ('4A', 'ECONOMY', 180.00, FALSE, 'FL002'),

             ('1A', 'BUSINESS', 800.00, FALSE, 'FL003'), ('1B', 'BUSINESS', 800.00, FALSE, 'FL003'), ('1C', 'BUSINESS', 800.00, FALSE, 'FL003'),
             ('2A', 'ECONOMY', 350.00, FALSE, 'FL003'), ('2B', 'ECONOMY', 350.00, FALSE, 'FL003'), ('2C', 'ECONOMY', 350.00, FALSE, 'FL003'),
             ('3A', 'ECONOMY', 350.00, FALSE, 'FL003'), ('3B', 'ECONOMY', 350.00, FALSE, 'FL003'), ('3C', 'ECONOMY', 350.00, FALSE, 'FL003'),
             ('4A', 'ECONOMY', 350.00, FALSE, 'FL003'),

             ('1A', 'BUSINESS', 600.00, FALSE, 'FL004'), ('1B', 'BUSINESS', 600.00, FALSE, 'FL004'), ('1C', 'BUSINESS', 600.00, FALSE, 'FL004'),
             ('2A', 'ECONOMY', 250.00, FALSE, 'FL004'), ('2B', 'ECONOMY', 250.00, FALSE, 'FL004'), ('2C', 'ECONOMY', 250.00, FALSE, 'FL004'),
             ('3A', 'ECONOMY', 250.00, FALSE, 'FL004'), ('3B', 'ECONOMY', 250.00, FALSE, 'FL004'), ('3C', 'ECONOMY', 250.00, FALSE, 'FL004'),
             ('4A', 'ECONOMY', 250.00, FALSE, 'FL004'),

             ('1A', 'BUSINESS', 700.00, FALSE, 'FL005'), ('1B', 'BUSINESS', 700.00, FALSE, 'FL005'), ('1C', 'BUSINESS', 700.00, FALSE, 'FL005'),
             ('2A', 'ECONOMY', 300.00, FALSE, 'FL005'), ('2B', 'ECONOMY', 300.00, FALSE, 'FL005'), ('2C', 'ECONOMY', 300.00, FALSE, 'FL005'),
             ('3A', 'ECONOMY', 300.00, FALSE, 'FL005'), ('3B', 'ECONOMY', 300.00, FALSE, 'FL005'), ('3C', 'ECONOMY', 300.00, FALSE, 'FL005'),
             ('4A', 'ECONOMY', 300.00, FALSE, 'FL005')
     ) AS v(seat_number, ticket_class, price, is_booked, flight_number)
         JOIN flight f ON f.flight_number = v.flight_number;
