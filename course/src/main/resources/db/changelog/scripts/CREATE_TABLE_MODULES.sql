CREATE TABLE modules
(
    id          UUID         NOT NULL,
    title       VARCHAR(150) NOT NULL,
    description VARCHAR(500) NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_modules PRIMARY KEY (id)
);

ALTER TABLE modules
    ADD CONSTRAINT uc_modules_title UNIQUE (title);