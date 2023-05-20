CREATE TABLE IF NOT EXISTS users(
    id UUID NOT NULL ,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name  VARCHAR(150) NOT NULL,
    status VARCHAR(50) NOT NULL,
    "type" VARCHAR(50) NOT NULL,
    phone_number VARCHAR(20),
    document VARCHAR(20),
    image_url VARCHAR(255),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    PRIMARY KEY (id)
);

