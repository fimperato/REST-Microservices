package it.imperato.test.ms.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

// use msAppClientDB
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "jwtTokenInfo")
public class JwtTokenInfo {

    @Id
    @Getter @Setter
    private String jwt;

    @Getter @Setter
    private Date createdAt;

    @Getter @Setter
    private Date expireDate;

    @Getter @Setter
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
