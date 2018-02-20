package it.imperato.test.ms.services.impl;

import it.imperato.test.ms.daos.UserDao;
import it.imperato.test.ms.exceptions.UserNotLoggedException;
import it.imperato.test.ms.model.entities.User;
import it.imperato.test.ms.model.pojo.UserInMem;
import it.imperato.test.ms.services.JwtAuthService;
import it.imperato.test.ms.services.LoginService;
import it.imperato.test.ms.utils.EncryptionUtils;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Optional;

@Log
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    UserDao userDao;

    @Autowired
    EncryptionUtils encryptionUtils;

    @Autowired
    JwtAuthService jwtAuthService;

    @Override
    public Optional<User> checkCredentials(String id, String password) throws UserNotLoggedException {

        // TODO

        return Optional.empty();
    }

    /**
     * Only mock version
     *
     * @param user
     * @param headers
     * @return
     * @throws UserNotLoggedException
     */
    @Override
    public Optional<UserInMem> checkCredentialsMock(User user, HttpHeaders headers)
            throws UserNotLoggedException {

        // Verifica jwt già presente e valido
        String jwtVal = headers!=null && headers.get("jwt")!=null?headers.get("jwt").get(0):null;
        if(jwtVal!=null) {
            // check jwt val (se già valido, ritorno le info user)
            Optional<UserInMem> optionalUser = null;
            try {
                Map<String, Object> mapInfo = jwtAuthService.retrieveInfoByJwt(jwtVal);
                if(mapInfo!=null) {
                    UserInMem userInMem = new UserInMem(
                            (String)mapInfo.get("subject"),
                            (String)mapInfo.get("name"),
                            (String)mapInfo.get("password"), // not present
                            (String)mapInfo.get("scope"));
                    optionalUser = Optional.of(userInMem);
                    return optionalUser;
                }
            } catch (UnsupportedEncodingException e) {
                log.info("JWT presente ma non valido: " + jwtVal
                        + " (rieseguo il login con le credenziali fornite).");
            }
        }

        // Verifica user su DB (mock version)
        if(user==null || user.getId()==null || user.getPassword()==null) {
            throw new UserNotLoggedException("User not correctly logged in! (userId or pass is null)");
        }
        else {
            Optional<UserInMem> optionalUser = this.checkCredentialsMock(user.getId(), user.getPassword());
            return optionalUser;
        }
    }

    @Override
    public Optional<UserInMem> checkCredentialsMock(String id, String password)
            throws UserNotLoggedException {

        Optional<UserInMem> user = userDao.findByIdMock(id);
        if(user.isPresent()) {
            UserInMem userFound = user.get();
            if(encryptionUtils.decrypt(userFound.getPassword()).equals(password)){
                log.info("Check: UserID, Password: OK.");
            }else{
                log.info("Check: UserID, Password: Password check KO.");
                throw new UserNotLoggedException("User not correctly logged in!");
            }
        }
        return user;
    }

}
