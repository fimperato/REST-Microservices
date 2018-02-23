package it.imperato.test.ms.services.impl;

import it.imperato.test.ms.daos.UserDao;
import it.imperato.test.ms.model.entities.User;
import it.imperato.test.ms.model.pojo.UserInMem;
import it.imperato.test.ms.services.LoginService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    UserDao userDao;

    @Override
    public Optional<UserInMem> checkCredentials(User user, HttpHeaders headers) {

        Optional<UserInMem> optionalUser = null;

        return optionalUser;
    }


}
