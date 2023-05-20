package com.br.igorsily.ead.course.service;

import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UtilService {

    String createUrlGetAllUsersByCourse(UUID courseId, Pageable pageable);

}
