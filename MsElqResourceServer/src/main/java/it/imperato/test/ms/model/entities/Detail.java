
package it.imperato.test.ms.model.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@Document(collection = "elqDetail")
public class Detail {

    @Getter @Setter
    private String key;

    @Getter @Setter
    private String value;

    @Override
    public String toString() {
        return "Detail{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
