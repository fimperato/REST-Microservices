package it.imperato.test.ms.repository;

import it.imperato.test.ms.model.entities.Domain;
import it.imperato.test.ms.repository.custom.DomainRepositoryCustom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DomainRepository extends MongoRepository<Domain, Long>, DomainRepositoryCustom {

    Domain findFirstByDomain(String domain);

    Domain findByDomainAndVisible(String domain, boolean visible);

    //Supports native JSON query string
    @Query("{domain:'?0'}")
    Domain findCustomByDomain(String domain);

    @Query("{domain: { $regex: ?0 } })")
    List<Domain> findCustomByRegExDomain(String domain);

}
