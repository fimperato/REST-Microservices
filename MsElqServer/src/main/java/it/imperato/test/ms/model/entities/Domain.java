package it.imperato.test.ms.model.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;

@NoArgsConstructor
@Document(collection = "domain")
public class Domain {

    @Id
    @Getter @Setter
    private BigInteger domainId;

    @Getter @Setter
    private Long domainCode;

    @Getter @Setter
    @Indexed(unique = true)
    private String domain;

    @Getter @Setter
    private boolean visible;

    @Override
    public String toString() {
        return "Domain{" +
                "domainId=" + domainId +
                ", domainCode=" + domainCode +
                ", domain='" + domain + '\'' +
                ", visible=" + visible +
                '}';
    }
}
