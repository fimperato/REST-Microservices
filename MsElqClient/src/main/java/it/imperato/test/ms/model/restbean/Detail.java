
package it.imperato.test.ms.model.restbean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "key",
    "value"
})
public class Detail {

    @JsonProperty("key")
    @Getter @Setter
    private String key;
    @JsonProperty("value")
    @Getter @Setter
    private String value;

}
