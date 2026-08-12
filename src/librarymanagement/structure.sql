CREATE DATABASE db_library_management;

CREATE TABLE borrow_cards(
    card_id SERIAL PRIMARY KEY,
    book_title VARCHAR(150) NOT NULL,
    borrower_name VARCHAR(100) NOT NULL,
    borrow_date TIMESTAMP NOT NULL,
    return_deadline TIMESTAMP NOT NULL,
    quantity INT NOT NULL,
    status VARCHAR(30) NOT NULL
);

CREATE OR REPLACE FUNCTION get_all_borrow_cards()
RETURNS TABLE(
    card_id INT,
    book_title VARCHAR,
    borrower_name VARCHAR,
    borrow_date TIMESTAMP,
    return_deadline TIMESTAMP,
    quatity INT,
    status VARCHAR
)
LANGUAGE sql
AS $$
    SELECT * FROM borrow_cards ORDER BY card_id;
$$;

CREATE OR REPLACE PROCEDURE insert_borrower_card(
    p_book_title VARCHAR,
    p_borrower_name VARCHAR,
    p_borrow_date TIMESTAMP,
    p_return_deadline TIMESTAMP,
    p_quantity INT,
    p_status VARCHAR
)
LANGUAGE sql
AS $$
    INSERT INTO borrow_cards(book_title, borrower_name, borrow_date, return_deadline, quantity, status)
    VALUES (p_book_title, p_borrower_name, p_borrow_date,p_return_deadline,p_quantity, p_status)
$$;

CREATE OR REPLACE FUNCTION search_by_borrower_name(p_name VARCHAR)
RETURNS TABLE (
    card_id INT, book_title VARCHAR, borrower_name VARCHAR, borrow_date TIMESTAMP, return_deadline TIMESTAMP, quantity INT, status VARCHAR
)
LANGUAGE sql
AS $$
    SELECT * FROM borrow_cards WHERE borrower_name ILIKE '%' || p_name || '$';
$$;

CREATE OR REPLACE PROCEDURE update_borrow_card(
    p_card_id INT,
    p_book_title VARCHAR,
    p_borrower_name VARCHAR,
    p_borrow_date TIMESTAMP,
    p_return_deadline TIMESTAMP,
    p_quantity INT,
    p_status VARCHAR
)
LANGUAGE sql
AS $$
    UPDATE borrow_cards
    SET book_title = p_book_title,
        borrower_name = p_borrower_name,
        borrow_date = p_borrow_date,
        return_deadline = p_return_deadline,
        quantity = p_quantity,
        status = p_status
    WHERE card_id = p_card_id;
$$;

CREATE OR REPLACE PROCEDURE delete_borrow_card(p_card_id INT)
LANGUAGE sql
AS $$
    DELETE FROM borrow_cards WHERE card_id = p_card_id;
$$;

CREATE OR REPLACE FUNCTION get_by_book_title(p_book_title VARCHAR)
RETURNS TABLE(
card_id INT, book_title VARCHAR, borrower_name VARCHAR, borrow_date TIMESTAMP, return_deadline TIMESTAMP, quantity INT, status VARCHAR
)
LANGUAGE sql
AS $$
    SELECT * FROM borrow_cards WHERE book_title ILIKE '%' || p_book_title || '%';
$$;