CREATE TABLE courses
(
    id          UUID         NOT NULL,
    name        VARCHAR(150) NOT NULL,
    description VARCHAR(500) NOT NULL,
    image_url   VARCHAR(255),
    created_at  TIMESTAMP WITHOUT TIME ZONE,
    updated_at  TIMESTAMP WITHOUT TIME ZONE,
    status      VARCHAR(255) NOT NULL,
    level       VARCHAR(255) NOT NULL,
    instructor  UUID         NOT NULL,
    CONSTRAINT pk_courses PRIMARY KEY (id)
);

ALTER TABLE courses
    ADD CONSTRAINT uc_courses_name UNIQUE (name);