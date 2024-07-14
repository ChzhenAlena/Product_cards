CREATE TABLE product (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    brand VARCHAR(255) NOT NULL,
    model VARCHAR(255),
    available_units INTEGER,
    weight DOUBLE NOT NULL,
    rating DOUBLE NOT NULL,
    category VARCHAR(255) NOT NULL,
    description VARCHAR(2000) NOT NULL,
    color VARCHAR(255) NOT NULL,
    price DOUBLE NOT NULL,
    warranty VARCHAR(255) NOT NULL
);