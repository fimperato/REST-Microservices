package it.imperato.test.ms.model.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@Document(collection = "domain")
public class Domain {

    @Id
    @Getter @Setter
    private String id;

    @Getter @Setter
    @Indexed(unique = true)
    private String domain;

    @Getter @Setter
    private boolean visible;

}
