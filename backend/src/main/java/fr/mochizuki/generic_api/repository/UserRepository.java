package fr.mochizuki.generic_api.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.mochizuki.generic_api.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    /**
     * Find by email optional.
     *
     * @param email the email
     * @return the optional
     * @author A.Mochizuki
     */
    Optional<User> findByEmail(String email);
}
