USE dvdvdeep;

INSERT INTO customers VALUES (0 , 'ADMIN' , '010-6669-0166' );
INSERT INTO movies VALUES ( 0 , 'ADMIN' , 'ADMIN' , 0 );
INSERT INTO orders VALUES ( 0 , 0 , 0 , 0 , '2025-01-01', '2025-01-02' );

-- INSERT INTO customers VALUES
-- (<{cust_id: }>,
-- <{cust_name: }>,
-- <{phone: }>);

-- INSERT INTO movies VALUES
-- (<{movie_id: }>,
-- <{movie_title: }>,
-- <{director: }>,
-- <{price: }>);

-- INSERT INTO orders VALUES
-- (<{order_id: }>,
-- <{cust_id: }>,
-- <{movie_id: }>,
-- <{saleprice: }>,
-- <{borrowdate: }>,
-- <{returndate: }>);