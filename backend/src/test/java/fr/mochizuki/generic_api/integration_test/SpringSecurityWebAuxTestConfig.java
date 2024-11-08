package fr.mochizuki.generic_api.integration_test;

import static fr.mochizuki.generic_api.ConstantsForTests.*;

import java.util.List;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import fr.mochizuki.generic_api.cross_cutting.enums.RoleEnum;
import fr.mochizuki.generic_api.entity.Role;
@TestConfiguration
public class SpringSecurityWebAuxTestConfig {

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.builder()
                .username(REFERENCE_USER_NAME)
                .password(REFERENCE_USER_PASSWORD)
                .roles(String.valueOf(Role.builder().name(RoleEnum.ADMIN).build()))
                .build();

        return new InMemoryUserDetailsManager(List.of(
                admin
        ));
    }
}
