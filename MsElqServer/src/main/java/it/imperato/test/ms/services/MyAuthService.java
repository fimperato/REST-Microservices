package it.imperato.test.ms.services;

import it.imperato.test.ms.exceptions.UserNotLoggedException;
import it.imperato.test.ms.model.entities.User;
import it.imperato.test.ms.model.pojo.UserInMem;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

public interface MyAuthService {

    String createJwt(String subject, String name, String permission, Date datenow)
            throws UnsupportedEncodingException;

    Map<String, Object> verifyJwtAndDoSomeOperation(HttpServletRequest request)
            throws UserNotLoggedException, UnsupportedEncodingException;

    Optional<UserInMem> checkUserForTest(String userId, String userPsw);

    Optional<User> checkUser(String userId, String userPsw);

    Map<String, Object> checkAndReadJwtByClient(HttpServletRequest request) throws UserNotLoggedException, UnsupportedEncodingException;

}
