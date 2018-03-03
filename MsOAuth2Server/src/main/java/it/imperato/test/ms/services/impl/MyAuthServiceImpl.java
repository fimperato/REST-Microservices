package it.imperato.test.ms.services.impl;

import it.imperato.test.ms.model.entities.OAuthInfo;
import it.imperato.test.ms.repository.OAuthInfoRepository;
import it.imperato.test.ms.services.MyAuthService;
import it.imperato.test.ms.utils.ConstantsApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;

@Service
public class MyAuthServiceImpl implements MyAuthService {

    private static final Logger log = LoggerFactory.getLogger(MyAuthServiceImpl.class);

    @Autowired
    OAuthInfoRepository oAuthInfoRepository;

    /**
     * Uso del Sistema: ConstantsApp.SISTEMA_MS_OAUTH2_CLIENT, per default
     *
     * @return
     */
    @Override
    public OAuthInfo findValidToken() {
        OAuthInfo oAuthInfo = oAuthInfoRepository
                .findFirstByValidAndSystem(true, ConstantsApp.SISTEMA_MS_OAUTH2_CLIENT);
        return oAuthInfo;
    }

    @Override
    public void saveOAuth2TokenInfo(OAuth2AccessToken oAuth2AccessToken) {
        OAuthInfo oAuthInfo = new OAuthInfo();
        updateTokenInfo(oAuth2AccessToken, oAuthInfo);
        oAuthInfo.setSystem(ConstantsApp.SISTEMA_MS_OAUTH2_CLIENT);
        oAuthInfo.setValid(true);
        oAuthInfoRepository.save(oAuthInfo);
    }

    @Override
    public void saveOrUpdateOAuth2TokenInfo(OAuth2AccessToken oAuth2AccessToken) {
        // token valido per il sistema presente
        OAuthInfo oAuthInfo = findValidToken();
        if(oAuthInfo==null) {
            oAuthInfo = new OAuthInfo();
        }
        updateTokenInfo(oAuth2AccessToken, oAuthInfo);
        oAuthInfoRepository.save(oAuthInfo);
    }

    private void updateTokenInfo(OAuth2AccessToken oAuth2AccessToken, OAuthInfo oAuthInfo) {
        oAuthInfo.setAccessToken(oAuth2AccessToken.getValue());
        oAuthInfo.setAccessTokenExpirationDate(oAuth2AccessToken.getExpiration());
        oAuthInfo.setAccessTokenExpiresIn(oAuth2AccessToken.getExpiresIn());
        oAuthInfo.setRefreshToken(oAuth2AccessToken.getRefreshToken()!=null?oAuth2AccessToken.getRefreshToken().getValue():null);
        oAuthInfo.setScope(oAuth2AccessToken.getScope());
        oAuthInfo.setTokenType(oAuth2AccessToken.getTokenType());
    }
}
