CREATE TABLE users(
id int NOT NULL AUTO_INCREMENT,
first_name varchar NOT NULL,
last_name varchar NOT NULL,
email varchar UNIQUE NOT NULL,
phone varchar,
address varchar NOT NULL,
username varchar UNIQUE NOT NULL,
password varchar NOT NULL,
--role varchar NOT NULL DEFAULT 'USER',
PRIMARY KEY (id)
);

CREATE TABLE books(
id int NOT NULL AUTO_INCREMENT,
title varchar NOT NULL,
author varchar NOT NULL,
description varchar NOT NULL,
usd_price double NOT NULL,
amount int NOT NULL CHECK (amount >= 0),
PRIMARY KEY (id)
);

INSERT INTO users (first_name, last_name, email, phone, address, username, password)
VALUES('moriya', 'ariel', 'moriyaariel4@gmail.com', '0535576037', 'hehoda nachshony 40', 'Moriya', 'Moriya@1'),
('b', 'bb', 'b@gmail.com', '0504380333', 'ben eliezer 45, netanya', 'Bmitay', 'Bmitay@2'),
('c', 'cc', 'c@gmail.com', '0524125553', 'cen eliezer 45, netanya', 'Cmitay', 'Cmitay@3'),
('d', 'dd', 'd@gmail.com', '0523715940', 'den eliezer 45, netanya', 'Dmitay', 'Dmitay@4'),
('e', 'ee', 'e@gmail.com', '0548976525', 'een eliezer 45, netanya', 'Emitay', 'Emitay@5'),
('f', 'ff', 'f@gmail.com', '0527468720', 'fen eliezer 45, netanya', 'Fmitay', 'Fmitay@6'),
('g', 'gg', 'g@gmail.com', '0554809620', 'gen eliezer 45, netanya', 'Gmitay', 'Gmitay@7'),
('h', 'hh', 'h@gmail.com', '0508887777', 'hen eliezer 45, netanya', 'Hmitay', 'Hmitay@8'),
('i', 'ii', 'i@gmail.com', '0556632241', 'ien eliezer 45, netanya', 'Imitay', 'Imitay@9'),
('j', 'jj', 'j@gmail.com', '0536645528', 'jen eliezer 45, netanya', 'Jmitay', 'Jmitay@10');

INSERT INTO books (title, author, description, usd_price, amount) VALUES
('To Kill a Mockingbird', 'Harper Lee', 'A classic novel about racial injustice in the 1930s', 12.99, 10),
('1984', 'George Orwell', 'A dystopian novel depicting a totalitarian society', 10.99, 8),
('Pride and Prejudice', 'Jane Austen', 'A romance novel set in 19th-century England', 9.99, 12),
('The Great Gatsby', 'F. Scott Fitzgerald', 'A story of wealth, love, and the American Dream', 11.99, 6),
('The Catcher in the Rye', 'J.D. Salinger', 'A coming-of-age novel following the protagonist Holden Caulfield', 10.99, 9),
('To the Lighthouse', 'Virginia Woolf', 'An experimental novel exploring the inner thoughts and experiences of characters', 13.99, 7),
('The Lord of the Rings', 'J.R.R. Tolkien', 'An epic fantasy trilogy set in the world of Middle-earth', 14.99, 11),
('Harry Potter and the Philosopher' , 'J.K. Rowling', 'The first book in the popular Harry Potter series', 12.99, 15),
('The Hobbit', 'J.R.R. Tolkien', 'A fantasy novel preceding The Lord of the Rings', 10.99, 10),
('Jane Eyre', 'Charlotte Bronte', 'A Gothic romance novel featuring a strong-willed heroine', 9.99, 13);

CREATE TABLE orders(
id int NOT NULL AUTO_INCREMENT,
user_id int NOT NULL,
order_date date DEFAULT current_date,
shipping_address varchar NOT NULL,
total_price double NOT NULL default 0,
status varchar NOT NULL DEFAULT 'TEMP',
PRIMARY KEY (id),
FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE book_to_user(
book_id int NOT NULL,
user_id int NOT NULL,
FOREIGN KEY (book_id) REFERENCES books (id),
FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE book_to_order(
book_id int NOT NULL,
order_id int NOT NULL,
FOREIGN KEY (book_id) REFERENCES books (id),
FOREIGN KEY (order_id) REFERENCES orders (id)
);

