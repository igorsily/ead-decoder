CREATE TABLE IF NOT EXISTS users_courses
(
    id        UUID NOT NULL,
    course_id UUID NOT NULL,
    user_id   UUID NOT NULL,
    CONSTRAINT pk_users_courses PRIMARY KEY (id)
);

