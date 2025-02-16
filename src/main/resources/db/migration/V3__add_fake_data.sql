-- Insert authors with realistic names
INSERT INTO authors (first_name, last_name) VALUES
    ('John', 'Smith'),
    ('Emily', 'Johnson'),
    ('Michael', 'Brown'),
    ('Sarah', 'Davis'),
    ('David', 'Wilson'),
    ('Jennifer', 'Martinez'),
    ('William', 'Anderson'),
    ('Elizabeth', 'Taylor'),
    ('James', 'Thomas'),
    ('Maria', 'Garcia'),
    ('Robert', 'Moore'),
    ('Patricia', 'Lee'),
    ('Richard', 'White'),
    ('Linda', 'Clark'),
    ('Joseph', 'Walker'),
    ('Margaret', 'Hall'),
    ('Daniel', 'Young'),
    ('Barbara', 'King'),
    ('Charles', 'Wright'),
    ('Susan', 'Lopez');

-- Insert books with realistic titles, ISBNs, and varying publication years
INSERT INTO books (title, isbn, publication_year, total_copies, available_copies, qr_code, author_id)
SELECT
    CASE (random() * 4)::int
        WHEN 0 THEN 'The ' || 
            (ARRAY['Secret', 'Hidden', 'Lost', 'Last', 'First', 'New'])[1 + (random() * 6)::int] || ' ' ||
            (ARRAY['Garden', 'Mountain', 'River', 'City', 'World', 'Journey'])[1 + (random() * 6)::int]
        WHEN 1 THEN 
            (ARRAY['Beyond', 'Under', 'After', 'Before', 'Through'])[1 + (random() * 5)::int] || ' the ' ||
            (ARRAY['Stars', 'Storm', 'Dawn', 'Sunset', 'Shadows'])[1 + (random() * 5)::int]
        WHEN 2 THEN
            (ARRAY['Modern', 'Advanced', 'Practical', 'Essential', 'Complete'])[1 + (random() * 5)::int] || ' ' ||
            (ARRAY['Programming', 'Mathematics', 'Physics', 'Chemistry', 'Biology'])[1 + (random() * 5)::int]
        ELSE
            (ARRAY['Life of', 'History of', 'Guide to', 'Art of', 'Science of'])[1 + (random() * 5)::int] || ' ' ||
            (ARRAY['Nature', 'Humanity', 'Technology', 'Society', 'Culture'])[1 + (random() * 5)::int]
    END,
    '978' || 
    (ARRAY['0', '1', '2', '3'])[1 + (random() * 4)::int] || 
    LPAD((random() * 99999)::text, 5, '0') || 
    LPAD((random() * 99999)::text, 5, '0'),
    2000 + (random() * 23)::int,
    3 + (random() * 7)::int,
    3 + (random() * 7)::int,
    UPPER(SUBSTRING(MD5(RANDOM()::TEXT), 1, 8)),
    (SELECT id FROM authors ORDER BY random() LIMIT 1)
FROM generate_series(1, 100) n;

-- Ensure available_copies doesn't exceed total_copies
UPDATE books 
SET available_copies = total_copies 
WHERE available_copies > total_copies;