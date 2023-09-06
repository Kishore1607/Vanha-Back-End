USE kishore_sugumar_corejava_project;

-- Create the "users" table
CREATE TABLE users (
   id INT AUTO_INCREMENT PRIMARY KEY,
   username VARCHAR(50) NOT NULL,
   email VARCHAR(200) NOT NULL UNIQUE,
   number BIGINT NOT NULL,
   password VARCHAR(50) NOT NULL,
   location VARCHAR(100) NOT NULL,
   status BOOLEAN DEFAULT 1,
   created_at DATETIME,
   modified_at DATETIME
);

-- Create the "products" table with foreign key to "users"
CREATE TABLE products (
   id INT AUTO_INCREMENT PRIMARY KEY,
   product_id VARCHAR(50) UNIQUE NOT NULL,
   category VARCHAR(2) NOT NULL,
   used_period INT,
   used_duration VARCHAR(2) NOT NULL,
   description TEXT,
   name VARCHAR(255) NOT NULL,
   price INT(10),
   min_price INT(10),
   seller_id INT,
   status CHAR,
   created_at DATETIME,
   modified_at DATETIME,
   FOREIGN KEY (seller_id) REFERENCES users(id)
);

-- Create the "bid_history" table with foreign keys to "users" and "products"
CREATE TABLE bid_history (
   id INT AUTO_INCREMENT PRIMARY KEY,
   bid_amount INT,
   bid_date DATETIME NOT NULL,
   buyer_id INT,
   product_id INT,
   status BOOLEAN DEFAULT 1,
   FOREIGN KEY (product_id) REFERENCES products(id)
);

-- Create the "asserts" table
CREATE TABLE assets(
   id INT AUTO_INCREMENT PRIMARY KEY,
   url VARCHAR(255) NOT NULL,
   status BOOLEAN DEFAULT 1
);

-- Create the "product_asserts" table with foreign keys to "products" and "asserts"
CREATE TABLE product_assets(
   product_id INT,
   asset_id INT,
   status BOOLEAN DEFAULT 1,
   FOREIGN KEY (product_id) REFERENCES products(id),
   FOREIGN KEY (asset_id) REFERENCES assets(id)
);
