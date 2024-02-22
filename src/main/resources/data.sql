

INSERT INTO province_table (name)
SELECT DISTINCT 법정동명
FROM data
WHERE 법정동명 IN ('서울특별시', '부산광역시', '대구광역시', '인천광역시', '광주광역시', '대전광역시', '울산광역시', '세종특별자치시', '경기도', '강원특별자치도', '충청북도', '충청남도', '전북특별자치도', '전라남도', '경상북도', '제주특별자치도');


-- province_table에서 각 광역시, 도에 해당하는 id 값을 가져오기
SET @서울특별시_id := (SELECT id FROM province_table WHERE name = '서울특별시');
SET @부산광역시_id := (SELECT id FROM province_table WHERE name = '부산광역시');
SET @대구광역시_id := (SELECT id FROM province_table WHERE name = '대구광역시');
SET @인천광역시_id := (SELECT id FROM province_table WHERE name = '인천광역시');
SET @광주광역시_id := (SELECT id FROM province_table WHERE name = '광주광역시');
SET @대전광역시_id := (SELECT id FROM province_table WHERE name = '대전광역시');
SET @울산광역시_id := (SELECT id FROM province_table WHERE name = '울산광역시');
SET @세종특별자치시_id := (SELECT id FROM province_table WHERE name = '세종특별자치시');
SET @경기도_id := (SELECT id FROM province_table WHERE name = '경기도');
SET @강원특별자치도_id := (SELECT id FROM province_table WHERE name = '강원특별자치도');
SET @충청북도_id := (SELECT id FROM province_table WHERE name = '충청북도');
SET @충청남도_id := (SELECT id FROM province_table WHERE name = '충청남도');
SET @전북특별자치도_id := (SELECT id FROM province_table WHERE name = '전북특별자치도');
SET @전라남도_id := (SELECT id FROM province_table WHERE name = '전라남도');
SET @경상북도_id := (SELECT id FROM province_table WHERE name = '경상북도');
SET @제주특별자치도_id := (SELECT id FROM province_table WHERE name = '제주특별자치도');

-- district_table에 데이터 삽입
INSERT INTO district_table (province_id, name)
SELECT @서울특별시_id, SUBSTRING_INDEX(법정동명, ' ', -1) AS name
FROM data
WHERE 법정동명 LIKE '서울특별시%' AND 법정동명 != '서울특별시'
UNION
SELECT @부산광역시_id, SUBSTRING_INDEX(법정동명, ' ', -1) AS name
FROM data
WHERE 법정동명 LIKE '부산광역시%' AND 법정동명 != '부산광역시'
UNION
SELECT @대구광역시_id, SUBSTRING_INDEX(법정동명, ' ', -1) AS name
FROM data
WHERE 법정동명 LIKE '대구광역시%' AND 법정동명 != '대구광역시'
UNION
SELECT @인천광역시_id, SUBSTRING_INDEX(법정동명, ' ', -1) AS name
FROM data
WHERE 법정동명 LIKE '인천광역시%' AND 법정동명 != '인천광역시'
UNION
SELECT @광주광역시_id, SUBSTRING_INDEX(법정동명, ' ', -1) AS name
FROM data
WHERE 법정동명 LIKE '광주광역시%' AND 법정동명 != '광주광역시'
UNION
SELECT @대전광역시_id, SUBSTRING_INDEX(법정동명, ' ', -1) AS name
FROM data
WHERE 법정동명 LIKE '대전광역시%' AND 법정동명 != '대전광역시'
UNION
SELECT @울산광역시_id, SUBSTRING_INDEX(법정동명, ' ', -1) AS name
FROM data
WHERE 법정동명 LIKE '울산광역시%' AND 법정동명 != '울산광역시'
UNION
SELECT @세종특별자치시_id, SUBSTRING_INDEX(법정동명, ' ', -1) AS name
FROM data
WHERE 법정동명 LIKE '세종특별자치시%' AND 법정동명 != '세종특별자치시'
UNION
SELECT @경기도_id, SUBSTRING_INDEX(법정동명, ' ', -1) AS name
FROM data
WHERE 법정동명 LIKE '경기도%' AND 법정동명 != '경기도'
UNION
SELECT @강원특별자치도_id, SUBSTRING_INDEX(법정동명, ' ', -1) AS name
FROM data
WHERE 법정동명 LIKE '강원특별자치도%' AND 법정동명 != '강원특별자치도'
UNION
SELECT @충청북도_id, SUBSTRING_INDEX(법정동명, ' ', -1) AS name
FROM data
WHERE 법정동명 LIKE '충청북도%' AND 법정동명 != '충청북도'
UNION
SELECT @충청남도_id, SUBSTRING_INDEX(법정동명, ' ', -1) AS name
FROM data
WHERE 법정동명 LIKE '충청남도%' AND 법정동명 != '충청남도'
UNION
SELECT @전북특별자치도_id, SUBSTRING_INDEX(법정동명, ' ', -1) AS name
FROM data
WHERE 법정동명 LIKE '전북특별자치도%' AND 법정동명 != '전북특별자치도'
UNION
SELECT @전라남도_id, SUBSTRING_INDEX(법정동명, ' ', -1) AS name
FROM data
WHERE 법정동명 LIKE '전라남도%' AND 법정동명 != '전라남도'
UNION
SELECT @경상북도_id, SUBSTRING_INDEX(법정동명, ' ', -1) AS name
FROM data
WHERE 법정동명 LIKE '경상북도%' AND 법정동명 != '경상북도'
UNION
SELECT @제주특별자치도_id, SUBSTRING_INDEX(법정동명, ' ', -1) AS name
FROM data
WHERE 법정동명 LIKE '제주특별자치도%' AND 법정동명 != '제주특별자치도';


INSERT INTO chat_room_province (province_id, province_name, chat_room_name)
SELECT id, name, CONCAT(name, ' 채팅방')
FROM province_table;

INSERT INTO chat_room_district (province_id, district_id, district_name, chat_room_name)
SELECT pt.id, dt.id, dt.name, CONCAT(pt.name, ' ', dt.name, ' 채팅방')
FROM district_table dt
         JOIN province_table pt ON dt.province_id = pt.id;
