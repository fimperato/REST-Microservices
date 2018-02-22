package it.imperato.test.ms.services.impl;

import it.imperato.test.ms.exceptions.UserNotLoggedException;
import it.imperato.test.ms.model.entities.JwtTokenInfo;
import it.imperato.test.ms.model.pojo.UserJwtInfoResponse;
import it.imperato.test.ms.repository.JwtTokenRepository;
import it.imperato.test.ms.services.MyAuthService;
import it.imperato.test.ms.utils.ConstantsApp;
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

@Service
public class MyAuthServiceImpl implements MyAuthService {

    private static final Logger log = LoggerFactory.getLogger(MyAuthServiceImpl.class);

    @Autowired
    EncryptionUtils encryptionUtils;

    @Autowired
    JwtTokenRepository jwtTokenRepository;

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
    public Map<String, Object> checkAndReadJwtByClient(HttpServletRequest request)
            throws UserNotLoggedException, UnsupportedEncodingException {
        String jwt = JwtUtils.getJwtFromHttpRequest(request);
        if(jwt == null){
            throw new UserNotLoggedException("Authentication token not found in the request");
        }
        Map<String, Object> userData = JwtUtils.jwt2Map(jwt);
        return userData;
    }

    @Override
    public JwtTokenInfo updateToken(UserJwtInfoResponse userJwtInfoResponse) {
        jwtTokenRepository.deleteBySystem(ConstantsApp.SISTEMA_MS_APP_CLIENT);
        String jwt = userJwtInfoResponse.getJwt();
        Date expireDate = null;
        try {
            Map<String, Object> mapInfo = JwtUtils.jwt2Map(jwt);
            expireDate = mapInfo.get("exp_date")!=null?(Date)mapInfo.get("exp_date"):null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        JwtTokenInfo jwtTokenInfo = new JwtTokenInfo(
                jwt,
                new Date(),
                expireDate,
                ConstantsApp.SISTEMA_MS_APP_CLIENT,true);
        jwtTokenInfo = jwtTokenRepository.insert(jwtTokenInfo);
        return jwtTokenInfo;
    }

    /**
     * Uso del Sistema: ConstantsApp.SISTEMA_MS_APP_CLIENT, per default
     *
     * @return
     */
    @Override
    public JwtTokenInfo findValidToken() {
        JwtTokenInfo jwtTokenInfo = jwtTokenRepository
                .findFirstByValidAndSystem(true, ConstantsApp.SISTEMA_MS_APP_CLIENT);
        return jwtTokenInfo;
    }
}
