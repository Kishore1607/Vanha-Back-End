use kishore_sugumar_corejava_project;
CREATE TABLE users (  
   user_id INT AUTO_INCREMENT PRIMARY KEY,  
   username VARCHAR(100) NOT NULL,  
   email VARCHAR(100) NOT NULL,  
   number BIGINT NOT NULL,  
   password VARCHAR(100) NOT NULL,  
   location VARCHAR(100) NOT NULL,  
   status BOOLEAN DEFAULT 1,  
   created_at DATETIME,  
   modified_at DATETIME  
);
CREATE TABLE products (  
   id INT AUTO_INCREMENT PRIMARY KEY,  
   product_id VARCHAR(100) UNIQUE NOT NULL,  
   category INT,  
   used_period INT,  
   used_duration INT,  
   description TEXT,  
   name VARCHAR(255) NOT NULL,  
   price INT(10),  
   min_price INT(10),
   seller_id INT,  
   status INT,  
   created_at DATETIME,  
   modified_at DATETIME  
);
CREATE TABLE bid_history (  
   bid_id INT AUTO_INCREMENT PRIMARY KEY,  
   bid_amount INT,  
   bid_date DATETIME NOT NULL,  
   buyer_id INT,  
   product_id INT,  
   status BOOLEAN DEFAULT 1  
);