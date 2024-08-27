INSERT INTO account (account_id, password, name, contact_number, email, role, status)
VALUES ('admin', '$2a$10$ejxYYyp384wWKU2jQDjOnO/lYSumEMBpo3BZL9OsIOnpb2jDVTEMi', '어드민', '01011111111', 'admin@test.com',
        'ADMIN', 'ACTIVE'),
       ('owner', '$2a$10$ejxYYyp384wWKU2jQDjOnO/lYSumEMBpo3BZL9OsIOnpb2jDVTEMi', '점주', '01022222222', 'owner@test.com',
        'OWNER', 'ACTIVE');
