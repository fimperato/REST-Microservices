package it.imperato.test.ms.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class Company {

    @Getter @Setter
    private String name;

    @Getter @Setter
    private String city;

}
