package it.imperato.test.ms.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
public class UserJwtInfoResponse implements Serializable {

    @Getter @Setter
    private int serverCode;
    @Getter @Setter
    private String jwt;
    @Getter @Setter
    private User user;
    @Getter @Setter
    private String message;

}
