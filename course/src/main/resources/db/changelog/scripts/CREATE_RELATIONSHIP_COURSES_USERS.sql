ALTER TABLE courses_users
    ADD CONSTRAINT FK_COURSES_USERS_ON_COURSE FOREIGN KEY (course_id) REFERENCES courses (id);