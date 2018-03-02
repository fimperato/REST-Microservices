package it.imperato.test.ms.repository;

import it.imperato.test.ms.model.entities.OAuthInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OAuthInfoRepository extends MongoRepository<OAuthInfo, String> {

    OAuthInfo findFirstByValidAndSystem(boolean valid, String system);

    void deleteBySystem(String system);

}
