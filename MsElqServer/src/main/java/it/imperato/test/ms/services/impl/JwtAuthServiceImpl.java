package it.imperato.test.ms.services.impl;

import it.imperato.test.ms.exceptions.UserNotLoggedException;
import it.imperato.test.ms.services.JwtAuthService;
import it.imperato.test.ms.utils.EncryptionUtils;
import it.imperato.test.ms.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Map;

@Service
public class JwtAuthServiceImpl implements JwtAuthService {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthServiceImpl.class);

    @Autowired
    EncryptionUtils encryptionUtils;

    @Override
    public Map<String, Object> retrieveInfoByJwt(HttpServletRequest request)
            throws UserNotLoggedException, UnsupportedEncodingException {
        String jwt = JwtUtils.getJwtFromHttpRequest(request);
        if(jwt == null){
            throw new UserNotLoggedException("Authentication token not found in the request");
        }
        Map<String, Object> userData = JwtUtils.jwt2Map(jwt);
        return userData;
    }

    public void checkJwt(HttpHeaders headers)
            throws UserNotLoggedException, UnsupportedEncodingException {
        String jwt = JwtUtils.getJwtFromHttpHeaders(headers);
        if(jwt == null){
            throw new UserNotLoggedException("Authentication token not found in the request header");
        }
        JwtUtils.jwt2Map(jwt);
    }
}
