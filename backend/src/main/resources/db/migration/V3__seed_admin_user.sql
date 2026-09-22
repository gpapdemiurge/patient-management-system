INSERT INTO hospital.users (username, email, password, role)
SELECT 'admin', 'info@demiurge.gr', '$2a$10$nljcBpp1ktLXRLGi3vFRBellXqoYGjO/rqXgTx1X/CynfTJya5Uza', 'ADMIN'
WHERE NOT EXISTS (
    SELECT 1
    FROM hospital.users
    WHERE username = 'admin'
       OR email = 'info@demiurge.gr'
);
