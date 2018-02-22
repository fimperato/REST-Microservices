package it.imperato.test.ms.services;

import it.imperato.test.ms.exceptions.UserNotLoggedException;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

public interface JwtAuthService {

    Map<String, Object> retrieveInfoByJwt(HttpServletRequest request) throws UserNotLoggedException, UnsupportedEncodingException;

    void checkJwt(HttpHeaders headers) throws UserNotLoggedException, UnsupportedEncodingException;

}
