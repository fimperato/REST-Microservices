package it.imperato.test.ms.daos;

import it.imperato.test.ms.model.pojo.UserInMem;

import java.util.Optional;


public interface UserDao /*extends JpaRepository<User, String>*/ extends UserDaoCustom {

    // Optional<User> findById(String id);

}
