ALTER TABLE purchase_records
    DROP COLUMN sku, DROP COLUMN name, DROP COLUMN price,
    ADD COLUMN product_version_id INT NOT NULL,
    ADD CONSTRAINT
    fk_product_version_id FOREIGN KEY(product_version_id)
    REFERENCES product_versions(id);
    