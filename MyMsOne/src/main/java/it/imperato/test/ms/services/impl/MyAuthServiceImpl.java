package it.imperato.test.ms.services.impl;

import it.imperato.test.ms.exceptions.UserNotLoggedException;
import it.imperato.test.ms.model.entities.User;
import it.imperato.test.ms.model.pojo.UserInMem;
import it.imperato.test.ms.services.MyAuthService;
import it.imperato.test.ms.utils.EncryptionUtils;
import it.imperato.test.ms.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Service
public class MyAuthServiceImpl implements MyAuthService {

    private static final Logger log = LoggerFactory.getLogger(MyAuthServiceImpl.class);

    @Autowired
    EncryptionUtils encryptionUtils;

    @Override
    public String createJwt(String subject, String name, String permission, Date now)
            throws UnsupportedEncodingException {
        Date expDate = now;
        expDate.setTime(now.getTime() + (300*1000));
        log.info("JWT Creation. Expiration time: " + expDate.getTime());
        String token = JwtUtils.generateJwt(subject, expDate, name, permission);
        return token;
    }

    @Override
    public Map<String, Object> verifyJwtAndDoSomeOperation(HttpServletRequest request)
            throws UserNotLoggedException, UnsupportedEncodingException {
        String jwt = JwtUtils.getJwtFromHttpRequest(request);
        if(jwt == null){
            throw new UserNotLoggedException("Authentication token not found in the request");
        }
        Map<String, Object> userData = JwtUtils.jwt2Map(jwt);
        return userData;
    }

    @Override
    public Optional<UserInMem> checkUserForTest(String userId, String userPsw) {
        String test123 = encryptionUtils.encrypt("data123");
        // User fake di test:
        UserInMem u = new UserInMem(userId, userId, userPsw, null);
        return Optional.of(u);
    }

    @Override
    public Optional<User> checkUser(String userId, String userPsw) {

        // TODO

        return Optional.empty();
    }

    @Override
    public Map<String, Object> checkAndReadJwtByClient(HttpServletRequest request)
            throws UserNotLoggedException, UnsupportedEncodingException {
        String jwt = JwtUtils.getJwtFromHttpRequest(request);
        if(jwt == null){
            throw new UserNotLoggedException("Authentication token not found in the request");
        }
        Map<String, Object> userData = JwtUtils.jwt2Map(jwt);
        return userData;
    }

}
