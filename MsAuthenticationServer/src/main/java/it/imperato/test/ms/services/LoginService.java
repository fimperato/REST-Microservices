package it.imperato.test.ms.services;

import it.imperato.test.ms.exceptions.UserNotLoggedException;
import it.imperato.test.ms.model.entities.User;
import it.imperato.test.ms.model.pojo.UserInMem;
import org.springframework.http.HttpHeaders;

import java.util.Optional;

public interface LoginService {

    Optional<User> checkCredentials(String id, String password) throws UserNotLoggedException;

    Optional<UserInMem> checkCredentialsMock(User user, HttpHeaders headers) throws UserNotLoggedException;

    Optional<UserInMem> checkCredentialsMock(String id, String password) throws UserNotLoggedException;

}
