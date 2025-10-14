DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `Order_Movie`(
	IN i_cust_name varchar(45),
	IN i_cust_phone varchar(45),
	IN i_movie_id int
)
BEGIN
	
	-- 고객 ID 저장 변수 (존재 확인용)
    DECLARE cust_id INT;
    -- 영화 name 저장 변수
    DECLARE movie_name INT;
    
    START transaction;
    -- 1. 입력받은 전화번호 -> 고객 조회
    SELECT cust_id INTO cust_id FROM customers WHERE cust_phone = i_cust_phone;
    -- 2. 조회된 고객ID X -> insert customers
    IF cust_id IS NULL THEN
		INSERT INTO customers(cust_name, cust_phone) VALUES (i_cust_name, i_cust_phone);
        -- insert하면서 생성된 cust_id 가져와 저장
        SET cust_id = LAST_INSERT_ID();
	END IF;
    -- 3. insert orders
    IF movie_name IS NOT NULL THEN
		INSERT INTO orders (cust_id, movie_id, saleprice, borrowdate, returndate)
        VALUES (cust_id, i_movie_id, 0, CURDATE(), NULL);
        COMMIT;
	ELSE
		ROLLBACK;
	END IF;
    
END$$
DELIMITER ;
