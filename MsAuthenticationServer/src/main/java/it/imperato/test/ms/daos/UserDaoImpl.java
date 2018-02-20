package it.imperato.test.ms.daos;

import it.imperato.test.ms.model.pojo.UserInMem;
import it.imperato.test.ms.utils.EncryptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
//@Transactional
public class UserDaoImpl implements UserDao {

    public static final String miapassword = "miapassword";
    @Autowired
    EncryptionUtils encryptionUtils;

    @Override
    public Optional<UserInMem> findByIdMock(String id) {
        // User fake di test:
        String passwordEncrypted = encryptionUtils.encrypt(miapassword);
        UserInMem u = new UserInMem("35", "miousername", passwordEncrypted, null);
        return Optional.of(u);
    }

}
