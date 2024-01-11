INSERT INTO province_table(name)
VALUES ('인천시'),('서울시'), ('부산시');

INSERT INTO district_table(province_id ,name)
VALUES (1, '동구'), (1, '서구'),(1, '남구'),
       (2, '서구'),(2, '남구'),(2, '동구'),
       (3, '남구'),(3, '동구'),(3, '서구');