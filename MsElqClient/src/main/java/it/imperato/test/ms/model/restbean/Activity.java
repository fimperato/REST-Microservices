
package it.imperato.test.ms.model.restbean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "activityDate",
        "activityType",
        "assetType",
        "contact",
        "details"
})
public class Activity {

    @JsonProperty("id")
    @Getter @Setter
    private String id;
    @JsonProperty("activityDate")
    @Getter @Setter
    private String activityDate;
    @JsonProperty("activityType")
    @Getter @Setter
    private String activityType;
    @JsonProperty("assetType")
    @Getter @Setter
    private String assetType;
    @JsonProperty("contact")
    @Getter @Setter
    private String contact;
    @JsonProperty("details")
    @Getter @Setter
    private List<Detail> details = null;

}
