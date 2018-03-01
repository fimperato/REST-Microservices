package it.imperato.test.ms.configurations;

import it.imperato.test.ms.app.GlobalProperties;
import it.imperato.test.ms.app.ProviderGlobalProperties;
import it.imperato.test.ms.configurations.CustomAccessDeniedHandler;
import it.imperato.test.ms.configurations.facebook.FbBasicAuthenticationEntryPoint;
import it.imperato.test.ms.configurations.google.CustomAuthenticationHandler;
import it.imperato.test.ms.configurations.google.GoogleBasicAuthenticationEntryPoint;
import it.imperato.test.ms.configurations.google.GoogleUserInfoTokenServices;
import it.imperato.test.ms.utils.ConstantsApp;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.filter.CompositeFilter;

import javax.servlet.Filter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Log
@Configuration
public class MsOAuth2ServerConfigSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    OAuth2ClientContext oauth2ClientContext;

    @Autowired
    OAuth2ClientContextFilter oAuth2ClientContextFilter;

    @Autowired
    GlobalProperties globalProperties;

    @Autowired
    ProviderGlobalProperties providerGlobalProperties;

    @Autowired
    CustomAuthenticationHandler customGoogleAuthenticationHandler;

    //private OAuth2ClientContext oauth2ClientContext;
    //private AuthorizationCodeResourceDetails authorizationCodeResourceDetails;

    /* This method is for overriding the default AuthenticationManagerBuilder.
     We can specify how the user details are kept in the application. It may
     be in a database, LDAP or in memory.*/
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    /* This method is for overriding some configuration of the WebSecurity
     If you want to ignore some request or request patterns then you can
     specify that inside this method.*/
    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if(globalProperties.getOauth2ProviderFacebookActive()) {
            configureFbSecurity(http);
        } else {
            configureGoogleSecurity(http);
        }
    }

    // Facebook provider config

    private void configureFbSecurity(HttpSecurity http) throws Exception {
        http.antMatcher("/**").authorizeRequests()
            .antMatchers("/login/oauth2_fb_login", "/test/send403")
            .permitAll()
            .antMatchers("/", "/login**", "/webjars/**")
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .logout().logoutSuccessUrl("/").permitAll()
            .and()
            .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            .and()
            .exceptionHandling().authenticationEntryPoint(new FbBasicAuthenticationEntryPoint())
            //.exceptionHandling().accessDeniedPage("/403")
            //.exceptionHandling().accessDeniedHandler(accessDeniedHandler())
            .and()
            .addFilterBefore(facebookSsoFilter(), BasicAuthenticationFilter.class);
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new CustomAccessDeniedHandler();
    }

    private Filter facebookSsoFilter() {
        CompositeFilter filter = new CompositeFilter();
        List<Filter> filters = new ArrayList<>();

        OAuth2ClientAuthenticationProcessingFilter facebookFilter =
                new OAuth2ClientAuthenticationProcessingFilter(ConstantsApp.FB_LOGIN_URI);
        OAuth2RestTemplate facebookTemplate = new OAuth2RestTemplate(facebook(), oauth2ClientContext);
        facebookFilter.setRestTemplate(facebookTemplate);
        UserInfoTokenServices tokenServices = new UserInfoTokenServices(
                facebookResource().getUserInfoUri(), facebook().getClientId());
        tokenServices.setRestTemplate(facebookTemplate);
        facebookFilter.setTokenServices(tokenServices);
        filters.add(facebookFilter);

        filter.setFilters(filters);
        return filter;
    }

    // Google provider config

    /*@Autowired
    public void setOauth2ClientContext(OAuth2ClientContext oauth2ClientContext) {
        this.oauth2ClientContext = oauth2ClientContext;
    }
    @Autowired
    public void setAuthorizationCodeResourceDetails(AuthorizationCodeResourceDetails authorizationCodeResourceDetails) {
        this.authorizationCodeResourceDetails = authorizationCodeResourceDetails;
    }*/

    private void configureGoogleSecurity(HttpSecurity http) throws Exception {
        http
                // Starts authorizing configurations.
                .authorizeRequests()
                // Ignore the "/" and "/index.html"
                .antMatchers("/", "/**.html", "/**.js", "/login/google**", "/login**")
                .permitAll()
                // Authenticate all remaining URLs.
                .anyRequest().fullyAuthenticated()
                .and()
                // Setting the logout URL "/logout" - default logout URL.
                .logout()
                // After successful logout the application will redirect to "/" path.
                .logoutSuccessUrl("/")
                .permitAll()
                .and()
                // Setting the filter for the URL "/login/google".
                .addFilterAfter(oAuth2ClientContextFilter, ExceptionTranslationFilter.class)
                .addFilterAt(googleFilter(), OAuth2ClientContextFilter.class)
                .anonymous()
                // anonymous login must be disabled,
                // otherwise an anonymous authentication will be created,
                // and the UserRedirectRequiredException will not be thrown,
                // and the user will not be redirected to the authorization server
                    .disable()
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                .exceptionHandling().authenticationEntryPoint(new GoogleBasicAuthenticationEntryPoint());
    }

    /*This method for creating filter for OAuth authentication.*/
    private OAuth2ClientAuthenticationProcessingFilter googleFilter() {
        //Creating the filter for "/login/google" url
        OAuth2ClientAuthenticationProcessingFilter googleOAuth2Filter =
                new OAuth2ClientAuthenticationProcessingFilter(providerGlobalProperties.getGAuthenticationPath());

        // Authentication HANDLER set:
        googleOAuth2Filter.setAuthenticationSuccessHandler(customGoogleAuthenticationHandler);
        /* Caso di uso: SimpleUrlAuthenticationSuccessHandler
        googleOAuth2Filter.setAuthenticationSuccessHandler(
                new SimpleUrlAuthenticationSuccessHandler() {
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                Authentication authentication) throws IOException, ServletException {
                        String authorizationCode = request.getParameter("code");
                        this.setDefaultTargetUrl(providerGlobalProperties.getGFilterCallbackPath());
                        super.onAuthenticationSuccess(request, response, authentication);
                    }
                });
        */

        //Creating the rest template for getting connected with OAuth service.
        //The configuration parameters will inject while creating the bean.
        OAuth2RestTemplate googleOAuth2RestTemplate = new OAuth2RestTemplate(google(),
                oauth2ClientContext);
        googleOAuth2Filter.setRestTemplate(googleOAuth2RestTemplate);

        // Setting the token service. It will help for getting the token and user details from the OAuth Service.
        // Use GoogleUserInfoTokenServices or UserInfoTokenServices
        // UserInfoTokenServices tokenServices = new UserInfoTokenServices(googleResource().getUserInfoUri(), google().getClientId());
        GoogleUserInfoTokenServices tokenServices = googleUserInfoTokenServices();

        tokenServices.setRestTemplate(googleOAuth2RestTemplate);
        //tokenServices.setTokenStore(tokenStore());
        //tokenServices.setSupportRefreshToken(true);
        googleOAuth2Filter.setTokenServices(tokenServices);

        return googleOAuth2Filter;
    }

    //@Bean
    public TokenStore tokenStore() {
        DataSource dataSource = null; // to add
        return new JdbcTokenStore(dataSource);
    }

    @Bean
    @Description("Google API UserInfo resource server")
    public GoogleUserInfoTokenServices googleUserInfoTokenServices() {
        GoogleUserInfoTokenServices userInfoTokenServices =
                new GoogleUserInfoTokenServices(googleResource().getUserInfoUri(), google().getClientId());
        // TODO Configure bean to use local database to read authorities
        // userInfoTokenServices.setAuthoritiesExtractor(authoritiesExtractor);
        return userInfoTokenServices;
    }



    // Prop. Bean definition

    @Bean
    @ConfigurationProperties("facebook.client")
    public AuthorizationCodeResourceDetails facebook() {
        return new AuthorizationCodeResourceDetails();
    }

    @Bean
    @ConfigurationProperties("facebook.resource")
    public ResourceServerProperties facebookResource() {
        return new ResourceServerProperties();
    }

    @Bean
    @ConfigurationProperties("google.client")
    public AuthorizationCodeResourceDetails google() {
        return new AuthorizationCodeResourceDetails();
    }

    @Bean
    @ConfigurationProperties("google.resource")
    public ResourceServerProperties googleResource() {
        return new ResourceServerProperties();
    }

    @Bean
    public FilterRegistrationBean oauth2ClientFilterRegistration(
            OAuth2ClientContextFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(filter);
        registration.setOrder(-100);
        return registration;
    }
}