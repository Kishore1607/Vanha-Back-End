CREATE TABLE users (  
           user_id INT AUTO_INCREMENT PRIMARY KEY,  
           username VARCHAR(100) NOT NULL,  
           email VARCHAR(100) NOT NULL,  
           number BIGINT NOT NULL,  
           password VARCHAR(100) NOT NULL,  
           location VARCHAR(100) NOT NULL,  
           status BOOLEAN DEFAULT 1,  
           created_at DATE,  
           modified_at DATE  
);
CREATE TABLE products (  
            id INT AUTO_INCREMENT PRIMARY KEY,  
            product_id VARCHAR(100) UNIQUE NOT NULL,  
            category VARCHAR(50) NOT NULL,  
            used_period INT NOT NULL,  
            used_duration ENUM('year', 'month') NOT NULL,  
            description TEXT,  
            name VARCHAR(255) NOT NULL,  
            price INT(10) NOT NULL,  
            min price INT(10)NOT NULL,x
            seller_id INT NOT NULL,  
            status ENUM('active', 'sold', 'deleted') DEFAULT 'active',  
            created_at DATETIME,  
            modified_at DATETIME  
);
CREATE TABLE bid_history (  
            bid_id INT AUTO_INCREMENT PRIMARY KEY,  
            bid_amount INT NOT NULL,  
            bid_date DATE NOT NULL,  
            buyer_id INT NOT NULL,  
            product_id VARCHAR(100) NOT NULL,  
            status BOOLEAN DEFAULT 1  
);