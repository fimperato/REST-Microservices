package it.imperato.test.ms.daos;

import it.imperato.test.ms.model.pojo.UserInMem;

import java.util.Optional;

public interface UserDaoCustom {

    Optional<UserInMem> findByIdMock(String id);

}
