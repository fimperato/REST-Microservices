package it.imperato.test.ms.services;

import it.imperato.test.ms.model.entities.OAuthInfo;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

public interface MyAuthService {

    OAuthInfo findValidToken();

    void saveOAuth2TokenInfo(OAuth2AccessToken oAuth2AccessToken);

}
