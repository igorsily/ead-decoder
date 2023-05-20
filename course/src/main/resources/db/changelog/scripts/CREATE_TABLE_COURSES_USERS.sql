CREATE TABLE courses_users
(
    id        UUID NOT NULL,
    user_id   UUID NOT NULL,
    course_id UUID,
    CONSTRAINT pk_courses_users PRIMARY KEY (id)
);

-- ALTER TABLE courses_users
--     ADD CONSTRAINT FK_COURSES_USERS_ON_COURSE FOREIGN KEY (course_id) REFERENCES courses (id);