
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
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "elqActivity")
public class Activity {

    @Id
    @Getter @Setter
    @JsonProperty("id")
    private String id;

    @Getter @Setter
    @JsonProperty("activityDate")
    private String activityDate;

    @Getter @Setter
    @JsonProperty("activityType")
    private String activityType;

    @Getter @Setter
    @JsonProperty("assetType")
    private String assetType;

    @Getter @Setter
    @JsonProperty("contact")
    private String contact;

    @Getter @Setter
    @JsonProperty("details")
    private List<Detail> details = null;

    @Override
    public String toString() {
        return "Activity{" +
                "id='" + id + '\'' +
                ", activityDate='" + activityDate + '\'' +
                ", activityType='" + activityType + '\'' +
                ", assetType='" + assetType + '\'' +
                ", contact='" + contact + '\'' +
                ", details=" + details +
                '}';
    }
}
