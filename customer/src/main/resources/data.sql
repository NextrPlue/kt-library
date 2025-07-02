-- Customer 테스트 데이터
INSERT INTO customer_table (id, name, email, password, is_kt_user, created_at, updated_at) 
VALUES (1, '고객', 'customer@kt.com', 'password', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO read_book_table (id,book_id, book_shelf_id, title, price) 
VALUES (1,3, 101, 'Sample Book Title', 15000);


 INSERT INTO SUBSCIPTION_TABLE (id,is_valid, start_date, end_date, created_at, updated_at, customer_id) 
 VALUES (
    1,false,'2025-07-01', '2025-08-01', NOW(), NOW(), 1);