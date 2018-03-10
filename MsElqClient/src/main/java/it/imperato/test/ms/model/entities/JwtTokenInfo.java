package it.imperato.test.ms.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

// use msAppClientDB
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "jwtTokenInfo")
public class JwtTokenInfo {

    @Id
    @Getter @Setter
    @NotEmpty @NotBlank @NotNull
    private String jwt;

    @Getter @Setter
    @NotNull
    private Date createdAt;

    @Getter @Setter
    private Date expireDate;

    @Getter @Setter
    @NotEmpty @NotBlank @NotNull
    private String system;

    @Getter @Setter
    private boolean valid;

    @Override
    public String toString() {
        return "JwtTokenInfo{" +
                "jwt='" + jwt + '\'' +
                ", createdAt=" + createdAt +
                ", expireDate=" + expireDate +
                ", system='" + system + '\'' +
                ", valid=" + valid +
                '}';
    }
}
