package it.imperato.test.ms.repository;

import it.imperato.test.ms.model.entities.JwtTokenInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JwtTokenRepository extends MongoRepository<JwtTokenInfo, String> {

    JwtTokenInfo findFirstByValidAndSystem(boolean valid, String system);

}
