CREATE TABLE product_photo (
    product_id UUID NOT NULL,
    photo_name VARCHAR(255) NOT NULL,
    FOREIGN KEY (product_id) REFERENCES product(id)
);