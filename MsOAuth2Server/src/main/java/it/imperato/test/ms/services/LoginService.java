package it.imperato.test.ms.services;

import it.imperato.test.ms.model.entities.User;
import org.springframework.http.HttpHeaders;

public interface LoginService {

    boolean checkCredentials(User user, HttpHeaders headers);

}
