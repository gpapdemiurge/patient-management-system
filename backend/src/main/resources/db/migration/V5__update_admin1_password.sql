-- Update admin1 password to NewPassword1337 (bcrypt)

UPDATE hospital.users
SET password = '$2b$10$mzOlmBEngwkWMw38bGMDUeOYF1qPw4kDJGZjFWQp7KF6VKAkckrnq'
WHERE username = 'admin1';
