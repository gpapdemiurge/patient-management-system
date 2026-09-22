INSERT INTO hospital.users (username, email, password, role)
SELECT 'admin1', 'gkpapakotsis@gmail.com', '$2b$10$hFThU56kEe4bZUzNRZkCW.XxGsihAL/mJ3eS12Rx.Hd976sGRBQiq', 'ADMIN'
WHERE NOT EXISTS (
    SELECT 1
    FROM hospital.users
    WHERE username = 'admin1'
       OR email = 'gkpapakotsis@gmail.com'
);
