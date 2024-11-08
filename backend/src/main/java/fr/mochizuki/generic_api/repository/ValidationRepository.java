package fr.mochizuki.generic_api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.mochizuki.generic_api.entity.Validation;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface ValidationRepository extends CrudRepository<Validation, Integer> {

    /**
     * Find by code optional.
     *
     * @param code the code
     * @return the optional
     * @author A.Mochizuki
     */
    Optional<Validation> findByCode(String code);

    /**
     * Deletes from the database all validations whose expiry date has passed at the
     * time passed in parameters
     *
     * @param now actualInstant of call method
     * @author A.Mochizuki
     */
    void deleteAllByExpirationBefore(Instant now);
}
