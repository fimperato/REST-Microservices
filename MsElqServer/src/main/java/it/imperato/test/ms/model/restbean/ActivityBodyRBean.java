package it.imperato.test.ms.model.restbean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActivityBodyRBean {

    @Getter @Setter
    private String name;
    @Getter @Setter
    private String assetType;
    @Getter @Setter
    private String activityType;
    @Getter @Setter
    private Integer contactId;

}
