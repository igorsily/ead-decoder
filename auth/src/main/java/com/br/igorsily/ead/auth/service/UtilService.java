package com.br.igorsily.ead.auth.service;

import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UtilService {

    String createUrlGetAllCoursesByUser(UUID userId, Pageable pageable);

}
