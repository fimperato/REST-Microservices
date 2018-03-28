package it.imperato.test.ms.repository.postgres;

import it.imperato.test.ms.model.postgres.AppUser;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<AppUser, Long> {
}
