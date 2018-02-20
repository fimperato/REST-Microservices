package it.imperato.test.ms.model.restbean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Contact {

    @Getter @Setter
    private String id;
    @Getter @Setter
    private String nome;
    @Getter @Setter
    private String cognome;

}
