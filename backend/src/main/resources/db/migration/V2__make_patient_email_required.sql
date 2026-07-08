-- Fill missing emails first
UPDATE hospital.patients
SET email = CONCAT('unknown_', id, '@example.com')
WHERE email IS NULL;

-- Now enforce NOT NULL
ALTER TABLE hospital.patients
ALTER COLUMN email SET NOT NULL;