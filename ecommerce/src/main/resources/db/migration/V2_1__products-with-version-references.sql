ALTER TABLE products ADD COLUMN product_version_id INT NOT NULL,
    ADD CONSTRAINT fk_product_version 
    FOREIGN KEY(product_version_id) REFERENCES product_versions(id);