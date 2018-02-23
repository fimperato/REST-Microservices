package it.imperato.test.ms.daos;

import it.imperato.test.ms.model.pojo.UserInMem;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
//@Transactional
public class UserDaoImpl implements UserDao {

    public static final String miapassword = "miapassword";

    @Override
    public Optional<UserInMem> findByIdMock(String id) {
        return null;
    }

}
