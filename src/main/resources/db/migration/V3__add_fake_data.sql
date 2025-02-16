-- Insert authors with realistic names
INSERT INTO authors (first_name, last_name) VALUES
    ('John', 'Smith'),
    ('Emily', 'Johnson'),
    ('Michael', 'Brown'),
    ('Sarah', 'Davis'),
    ('David', 'Wilson');

-- Insert specific books with realistic data
INSERT INTO books (title, isbn, publication_year, total_copies, available_copies, qr_code, author_id) VALUES
    ('The Art of Programming', '9780134685991', 2020, 5, 5, 'PROG1234', 1),
    ('Data Structures and Algorithms', '9780262033848', 2019, 3, 3, 'DATA5678', 1),
    ('Modern Web Development', '9781449331818', 2021, 4, 4, 'WEB12345', 2),
    ('Cloud Computing Basics', '9780135264354', 2022, 3, 3, 'CLOUD123', 2),
    ('Artificial Intelligence', '9780262043793', 2023, 5, 5, 'AI123456', 3),
    ('Machine Learning Fundamentals', '9781098107956', 2021, 4, 4, 'ML123456', 3),
    ('Database Design', '9780321884497', 2020, 3, 3, 'DB123456', 4),
    ('Software Architecture', '9781492043454', 2022, 4, 4, 'ARCH1234', 4),
    ('Clean Code', '9780132350884', 2019, 5, 5, 'CODE1234', 5),
    ('DevOps Handbook', '9781942788003', 2021, 3, 3, 'DEVS1234', 5),
    ('Python Programming', '9781449355739', 2022, 4, 4, 'PY123456', 1),
    ('Java Complete Reference', '9780071808552', 2020, 5, 5, 'JAVA1234', 2),
    ('JavaScript Deep Dive', '9781449331818', 2021, 3, 3, 'JS123456', 3),
    ('Microservices Patterns', '9781617294549', 2023, 4, 4, 'MICR1234', 4),
    ('System Design Interview', '9780578973838', 2022, 5, 5, 'SYS12345', 5),
    ('Coding Interview Guide', '9781984857606', 2021, 3, 3, 'INT12345', 1),
    ('Git Version Control', '9781484200773', 2020, 4, 4, 'GIT12345', 2),
    ('Agile Development', '9781449331924', 2022, 5, 5, 'AGIL1234', 3),
    ('Software Testing', '9781449365820', 2021, 3, 3, 'TEST1234', 4),
    ('Cybersecurity Basics', '9781119643777', 2023, 4, 4, 'SEC12345', 5);