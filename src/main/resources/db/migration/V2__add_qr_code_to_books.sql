ALTER TABLE books
ADD COLUMN qr_code VARCHAR(8) UNIQUE;

-- Update existing books with random QR codes
UPDATE books
SET qr_code = UPPER(SUBSTRING(MD5(RANDOM()::TEXT), 1, 8))
WHERE qr_code IS NULL;

-- Make the column not nullable after populating existing records
ALTER TABLE books
ALTER COLUMN qr_code SET NOT NULL;