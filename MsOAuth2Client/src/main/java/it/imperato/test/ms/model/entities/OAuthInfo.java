
package it.imperato.test.ms.model.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.xml.soap.Detail;
import java.util.Date;
import java.util.List;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "accessToken",
        "refreshToken",
        "expirationDate",
        "expiresIn",
        "scope",
        "tokenType",
        "valid",
        "system"
})
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "authorization")
public class OAuthInfo {

    @Id
    @Getter @Setter
    @JsonProperty("id")
    private String id;

    @Getter @Setter
    @JsonProperty("accessToken")
    private String accessToken;

    @Getter @Setter
    @JsonProperty("refreshToken")
    private String refreshToken;

    @Getter @Setter
    @JsonProperty("expirationDate")
    private Date accessTokenExpirationDate;

    @Getter @Setter
    @JsonProperty("expiresIn")
    private int accessTokenExpiresIn;

    @Getter @Setter
    @JsonProperty("scope")
    private Set<String> scope;

    @Getter @Setter
    @JsonProperty("tokenType")
    private String tokenType;

    @Getter @Setter
    @JsonProperty("valid")
    boolean valid;

    @Getter @Setter
    @JsonProperty("system")
    String system;

}
