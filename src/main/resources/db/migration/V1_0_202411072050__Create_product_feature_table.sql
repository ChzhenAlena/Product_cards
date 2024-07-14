CREATE TABLE product_feature (
    product_id UUID NOT NULL,
    feature VARCHAR(255) NOT NULL,
    FOREIGN KEY (product_id) REFERENCES product(id)
);