package com.br.igorsily.ead.course.clients;

import com.br.igorsily.ead.course.dtos.CourseUserDTO;
import com.br.igorsily.ead.course.dtos.ResponsePageDTO;
import com.br.igorsily.ead.course.dtos.UserDTO;
import com.br.igorsily.ead.course.service.UtilService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Log4j2
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthUserClient {

    @Value("${ead.api.url.authuser}")
    private String REQUEST_URL_AUTHUSER;

    private final RestTemplate restTemplate;

    private final UtilService utilsService;

    public Page<UserDTO> getAllUsersByCourse(UUID courseId, Pageable pageable) {
        List<UserDTO> searchResult = null;
        ResponseEntity<ResponsePageDTO<UserDTO>> result = null;

        String url = REQUEST_URL_AUTHUSER + utilsService.createUrlGetAllUsersByCourse(courseId, pageable);

        log.debug("Request URL: {} ", url);
        log.info("Request URL: {} ", url);
        try {
            ParameterizedTypeReference<ResponsePageDTO<UserDTO>> responseType = new ParameterizedTypeReference<ResponsePageDTO<UserDTO>>() {
            };
            result = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
            searchResult = Objects.requireNonNull(result.getBody()).getContent();
            log.debug("Response Number of Elements: {} ", searchResult.size());
        } catch (HttpStatusCodeException e) {
            log.error("Error request /courses {} ", e);
        }
        log.info("Ending request /users courseId {} ", courseId);
        assert result != null;
        return result.getBody();
    }


    public ResponseEntity<UserDTO> getUserById(UUID userId) {
        String url = REQUEST_URL_AUTHUSER + "/users/" + userId;
        return restTemplate.exchange(url, HttpMethod.GET, null, UserDTO.class);
    }

    public void postSubscriptionUserInCourse(UUID courseId, UUID userId) {

        String url = REQUEST_URL_AUTHUSER + "/users/" + userId + "/courses/subscription";

        var courseUserDTO = new CourseUserDTO(
                userId,
                courseId
        );

        restTemplate.postForObject(url, courseUserDTO, String.class);
    }
}
