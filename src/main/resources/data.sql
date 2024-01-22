INSERT INTO province_table(id, name)
VALUES (1, '인천시'),
       (2, '서울시'),
       (3, '부산시');

INSERT INTO district_table(province_id, name)
VALUES (1, '동구'),
       (1, '서구'),
       (1, '남구'),
       (2, '서구'),
       (2, '남구'),
       (2, '동구'),
       (3, '남구'),
       (3, '동구'),
       (3, '서구');

INSERT INTO chat_room_province(province_id, province_name, chat_room_name)
VALUES (1, '인천시', '인천시 채팅방'),
       (2, '서울시', '서울시 채팅방'),
       (3, '부산시', '부산시 채팅방');

INSERT INTO chat_room_district(province_id, district_id, district_name, chat_room_name)
VALUES (1, 1, '동구', '인천시 동구 채팅방'),
       (1, 2, '서구', '인천시 서구 채팅방'),
       (1, 3, '남구', '인천시 남구 채팅방'),
       (2, 4, '서구', '서울시 서구 채팅방'),
       (2, 5, '남구', '서울시 남구 채팅방'),
       (2, 6, '동구', '서울시 동구 채팅방'),
       (3, 7, '남구', '부산시 남구 채팅방'),
       (3, 8, '동구', '부산시 동구 채팅방'),
       (3, 9, '서구', '부산시 서구 채팅방');