package it.imperato.test.ms.model.restbean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActivityResponseBody {

    @Getter @Setter
    private List<Activity> activities;
    @Getter @Setter
    private int responseCode;
    @Getter @Setter
    private String responseMessage;

}
