package it.imperato.test.ms.services;

import it.imperato.test.ms.exceptions.UserNotLoggedException;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

public interface JwtAuthService {

    String createJwt(String subject, String name, String permission, Date datenow) throws UnsupportedEncodingException;

    Map<String, Object> retrieveInfoByJwt(HttpServletRequest request) throws UserNotLoggedException, UnsupportedEncodingException;

    Map<String, Object> retrieveInfoByJwt(String jwt) throws UserNotLoggedException, UnsupportedEncodingException;

    String checkJwt(HttpHeaders headers) throws UserNotLoggedException;

}
