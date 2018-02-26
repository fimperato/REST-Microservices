package it.imperato.test.ms.services.impl;

import it.imperato.test.ms.model.entities.User;
import it.imperato.test.ms.services.LoginService;
import lombok.extern.java.Log;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Log
@Service
public class LoginServiceImpl implements LoginService {

    @Override
    public boolean checkCredentials(User user, HttpHeaders headers) {

        return true;
    }


}
