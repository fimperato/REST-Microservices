package it.imperato.test.ms.utils;

import io.jsonwebtoken.*;
import org.apache.tomcat.util.bcel.classfile.ConstantDouble;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {

    public static String generateJwt(String subject, Date date, String name, String scope)
            throws UnsupportedEncodingException {

        String jwt = Jwts.builder()
                .setSubject(subject)
                .setExpiration(date)
                .claim("name", name)
                .claim("scope", scope)
                .signWith(
                        SignatureAlgorithm.HS256,
                        ConstantsApp.SIGNIN_KEY.getBytes("UTF-8")
                )
                .compact();

        return jwt;
    }

    /**
     * This method converts the token into a map of Userdata checking it's validity
     *
     * @param jwt
     * @return HashMap<String, Object> of user data
     * @throws UnsupportedEncodingException
     */
    public static Map<String, Object> jwt2Map(String jwt) throws
            UnsupportedEncodingException, ExpiredJwtException {

        Jws<Claims> claim = Jwts.parser()
                .setSigningKey(ConstantsApp.SIGNIN_KEY.getBytes("UTF-8"))
                .parseClaimsJws(jwt);

        String name = claim.getBody().get("name", String.class);
        String scope = (String) claim.getBody().get("scope");

        Date expDate = claim.getBody().getExpiration();
        String subj = claim.getBody().getSubject();

        Map<String, Object> userData = new HashMap<>();
        userData.put("name", name);
        userData.put("scope", scope);
        userData.put("exp_date", expDate);
        userData.put("subject", subj);

        Date now = new Date();
        if(now.after(expDate)){
            throw new ExpiredJwtException(null, null, "Session expired!");
        }

        return userData;
    }


    /**
     * This method extracts the jwt from the header or the cookie in the Http request
     *
     * @param request
     * @return jwt
     */
    public static String getJwtFromHttpRequest(HttpServletRequest request){
        String jwt = null;
        if(request.getHeader("jwt") != null) {
            jwt = request.getHeader("jwt"); // token present in header
        }
        else if(request.getCookies() != null) {
            Cookie[] cookies = request.getCookies(); // token present in cookie
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("jwt")) {
                    jwt = cookie.getValue();
                }
            }
        }
        return jwt;
    }

    /**
     * This method extracts the jwt from the HttpHeaders
     *
     * @param headers
     * @return jwt
     */
    public static String getJwtFromHttpHeaders(HttpHeaders headers){
        String jwt = null;
        if(headers.get("jwt") != null) {
            jwt = headers.get("jwt").get(0); // token present in HttpHeaders
        }
        return jwt;
    }

}
