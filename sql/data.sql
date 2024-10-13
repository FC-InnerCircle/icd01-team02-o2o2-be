INSERT INTO account (id, account_id, password, name, contact_number, email, role, status)
VALUES (1, 'admin', '$2a$10$ejxYYyp384wWKU2jQDjOnO/lYSumEMBpo3BZL9OsIOnpb2jDVTEMi', '어드민', '01011111111',
        'admin@test.com',
        'ADMIN', 'ACTIVE'),
       (2, 'owner', '$2a$10$ejxYYyp384wWKU2jQDjOnO/lYSumEMBpo3BZL9OsIOnpb2jDVTEMi', '점주', '01022222222',
        'owner@test.com',
        'OWNER', 'ACTIVE');

INSERT INTO file_group (id, created_at, updated_at, group_type)
VALUES (1, '2024-10-07 10:40:12.869157000', '2024-10-07 10:40:12.869157000', 'STORE'),
       (2, '2024-10-07 11:38:34.320748000', '2024-10-07 11:38:34.320748000', 'STORE'),
       (3, '2024-10-07 11:38:34.320748000', '2024-10-07 11:38:34.320748000', 'MENU'),
       (4, '2024-10-07 11:38:34.320748000', '2024-10-07 11:38:34.320748000', 'MENU');

INSERT INTO file_detail (id, created_at, updated_at, extension, ordering, original_file_name, path, size,
                         stored_file_name, file_group_id, resource_location)
VALUES (1, NULL, NULL, 'jpg', 1, 'ICD 김효준.jpg', 'https://o2o-admin.s3.ap-northeast-2.amazonaws.com', 122097,
        '7f384e25-62f2-46d6-85a3-fd5598b34ebb.jpg', 1, 0),
       (2, NULL, NULL, 'jpg', 1, 'ICD 김효준.jpg', 'https://o2o-admin.s3.ap-northeast-2.amazonaws.com', 122097,
        'bb161eea-2cd5-4191-b45f-865bcd587dd9.jpg', 2, 0),
       (3, NULL, NULL, 'jpg', 1, 'ICD 김효준.jpg', 'https://o2o-admin.s3.ap-northeast-2.amazonaws.com', 122097,
        'bb161eea-2cd5-4191-b45f-865bcd587dd9.jpg', 3, 0),
       (4, NULL, NULL, 'jpg', 1, 'ICD 김효준.jpg', 'https://o2o-admin.s3.ap-northeast-2.amazonaws.com', 122097,
        'bb161eea-2cd5-4191-b45f-865bcd587dd9.jpg', 4, 0);


INSERT INTO store (id, created_at, updated_at, address, address_detail, category, close_time, contact_number,
                   delivery_area, latitude, longitude, minimum_order_amount, name, open_time, zip_code,
                   thumbnail_file_group_id, status)
VALUES (1, '2024-10-07 10:40:12.718351000', '2024-10-07 10:40:12.885151000', '서울 금천구 벚꽃로 309', '상세주소', 'CAFE,CHICKEN',
        '23:00', '01012345678', NULL, 37.54, 127.18, 10000, '상점', '12:00', '12345', 1, 'ACTIVE');

INSERT INTO store_menu (id, created_at, updated_at, description, name, ordering, price, status, image_file_group_id,
                        store_id)
VALUES (1, NULL, NULL, '고추장 쌀 떡볶이', '떡볶이', 1, 14000, 'ENABLED', 3, 1),
       (2, NULL, NULL, '치킨', '치킨', 1, 14000, 'ENABLED', 4, 1);

INSERT INTO store_menu_option_group (id, is_deleted, is_required, ordering, title, menu_id, is_multiple)
VALUES (1, 0, 0, 1, '양', 1, 0),
       (2, 0, 0, 1, '맵기', 1, 0),
       (3, 0, 0, 1, '양', 2, 0),
       (4, 0, 0, 1, '맵기', 2, 0);


INSERT INTO store_menu_option (name, ordering, price, option_group_id)
VALUES ('떡 많이', 1, 1000, 1),
       ('떡 조금', 2, 500, 1),
       ('맵게', 1, 1000, 2),
       ('덜 맵게', 2, 500, 2),
       ('치킨 많이', 1, 1000, 3),
       ('치킨 조금', 2, 500, 3),
       ('맵게', 1, 1000, 4),
       ('덜 맵게', 2, 500, 4);

