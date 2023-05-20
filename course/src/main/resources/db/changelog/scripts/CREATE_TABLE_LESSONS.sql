CREATE TABLE lessons
(
    id          UUID         NOT NULL,
    title       VARCHAR(150) NOT NULL,
    description VARCHAR(500) NOT NULL,
    video_url   VARCHAR(255) NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_lessons PRIMARY KEY (id)
);

ALTER TABLE lessons
    ADD CONSTRAINT uc_lessons_title UNIQUE (title);