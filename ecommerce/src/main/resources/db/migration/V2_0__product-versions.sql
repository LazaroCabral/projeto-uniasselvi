CREATE TABLE IF NOT EXISTS product_versions
    (id INT NOT NULL AUTO_INCREMENT,
    sku VARCHAR(15) NOT NULL,
    name VARCHAR(30) NOT NULL,
    price DECIMAL(5,2) NOT NULL,
    description varchar(255),
    updated_at DATETIME NOT NULL,
    PRIMARY KEY(Id));

