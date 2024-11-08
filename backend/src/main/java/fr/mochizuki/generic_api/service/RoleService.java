package fr.mochizuki.generic_api.service;

import java.util.List;

import fr.mochizuki.generic_api.cross_cutting.exceptions.InoteUserException;
import fr.mochizuki.generic_api.entity.Role;

public interface RoleService {

    /**
     * Populates the role table from the dedicated enumeration
     *
     * @return list of saved roles
     * @author A.Mochizuki
     * @date 28/03/2024
     */
    List<Role> insertRolesInDb();

    /**
     * Load admin role
     *
     * @return Singleton of asked admin Role
     * @author A.Mochizuki
     * @date 28/03/2024
     */
    Role loadAdminRole() throws InoteUserException;

    /**
     * Load Manager role
     *
     * @return Singleton of asked manager Role
     * @author A.Mochizuki
     * @date 28/03/2024
     */
    Role loadManagerRole() throws InoteUserException;

    /**
     * Load User role
     *
     * @return Singleton of asked user Role
     * @author A.Mochizuki
     * @date 28/03/2024
     */
    Role loadUserRole() throws InoteUserException;
}

