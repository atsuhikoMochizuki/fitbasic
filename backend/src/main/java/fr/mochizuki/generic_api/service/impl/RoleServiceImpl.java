package fr.mochizuki.generic_api.service.impl;

import org.springframework.stereotype.Service;

import fr.mochizuki.generic_api.cross_cutting.enums.RoleEnum;
import fr.mochizuki.generic_api.cross_cutting.exceptions.InoteUserException;
import fr.mochizuki.generic_api.entity.Role;
import fr.mochizuki.generic_api.repository.RoleRepository;
import fr.mochizuki.generic_api.service.RoleService;

import java.util.ArrayList;
import java.util.List;

/**
 * The Service RoleServiceImpl
 * @author A.Mochizuki
 * @date   11/04/2024
 */
@Service
public class RoleServiceImpl implements RoleService {

    /* DEPENDENCIES INJECTION */
    /* ============================================================ */
    private RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /* PUBLIC METHODS */
    /* ============================================================ */
    
    /**
     * Populates the role table from the dedicated enumeration
     *
     * @return persisted roles in database
     * @author A.Mochizuki
     * @date 28/03/2024
     */
    @Override
    public List<Role> insertRolesInDb() {
        List<Role> rolesInApp = new ArrayList<>();
        for (RoleEnum role : RoleEnum.values()) {
            rolesInApp.add(this.roleRepository.save(
                    Role.builder().name(role).build()));
        }
        return rolesInApp;
    }

    /**
     * Load admin role
     *
     * @return Singleton of asked admin Role
     * @author A.Mochizuki
     * @date 28/03/2024
     */
    @Override
    public Role loadAdminRole() throws InoteUserException {
        return this.roleRepository.findByName(RoleEnum.ADMIN).orElseThrow(() -> new InoteUserException());
    }

    /**
     * Load Manager role
     *
     * @return Singleton of asked manager Role
     * @author A.Mochizuki
     * @date 28/03/2024
     */
    @Override
    public Role loadManagerRole() throws InoteUserException {
        return this.roleRepository.findByName(RoleEnum.MANAGER).orElseThrow(() -> new InoteUserException());
    }

    /**
     * Load User role
     *
     * @return Singleton of asked user Role
     * @author A.Mochizuki
     * @date 28/03/2024
     */
    @Override
    public Role loadUserRole() throws InoteUserException {
        return this.roleRepository.findByName(RoleEnum.USER).orElseThrow(() -> new InoteUserException());
    }
}
