package it.imperato.test.ms.services;

import it.imperato.test.ms.model.entities.User;
import it.imperato.test.ms.model.pojo.UserInMem;
import org.springframework.http.HttpHeaders;

import java.util.Optional;

public interface LoginService {

    Optional<UserInMem> checkCredentials(User user, HttpHeaders headers);

}
