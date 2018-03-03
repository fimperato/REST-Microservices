package it.imperato.test.ms.model.restbean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
public class MyResponseBody implements Serializable {
    @Getter @Setter
    private int codeResponse;
    @Getter @Setter
    private String msgResponse;
    @Getter @Setter
    private String category;
}