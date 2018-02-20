package it.imperato.test.ms.services;

import it.imperato.test.ms.exceptions.UserNotLoggedException;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

public interface MyAuthService {

    String createJwt(String subject, String name, String permission, Date datenow)
            throws UnsupportedEncodingException;

    Map<String, Object> verifyJwtAndDoSomeOperation(HttpServletRequest request)
            throws UserNotLoggedException, UnsupportedEncodingException;

    Map<String, Object> checkAndReadJwtByClient(HttpServletRequest request) throws UserNotLoggedException, UnsupportedEncodingException;

}
