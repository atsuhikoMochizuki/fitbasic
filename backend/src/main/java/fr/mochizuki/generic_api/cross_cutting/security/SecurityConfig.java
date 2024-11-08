package fr.mochizuki.generic_api.cross_cutting.security;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import fr.mochizuki.generic_api.cross_cutting.constants.Endpoint;

import java.util.Arrays;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;



/*
 * The @Configuration annotation in Spring is used to denote a class that
 * declares one or more @Bean methods and may be processed by the Spring IoC
 * container to generate bean definitions and service requests for those beans
 * at runtime.
 * Nota : The @Bean annotation on a method in Spring indicates that the method
 * is a factory method for a Spring bean. When the application context loads, it
 * will call the method and register the returned object as a bean in the
 * context.
 */
@Configuration /*
                * The class declares one or more @Bean methods and may be processed by the
                * Spring IoC container to generate bean definitions
                */
@EnableMethodSecurity /*
                       * Indicates that part of the configuration is contained in methods elsewhere in
                       * the code
                       */
@EnableWebSecurity /* Activation of Security */
public class SecurityConfig {

    /* DEPENDENCIES INJECTION */
    /* ============================================================ */

    /*
     * BCryptPasswordEncoder is a class provided by Spring Security that implements
     * the PasswordEncoder
     * interface. It uses the BCrypt strong hashing function to hash passwords,
     * making them secure
     */
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /* Make validation on token(jwt) in the HTTP header request */
    @SuppressWarnings("unused")
    private final JwtFilter jwtFilter;

    /*
     * UserDetailsService is a core interface in Spring Security used to retrieve
     * user authentication
     * and authorization information. It has one method named loadUserByUsername(),
     * which can be
     * overridden to customize the process of finding the user.
     * When we replace the default implementation of UserDetailsService, we must
     * also specify a
     * PasswordEncoder.
     */
    private final UserDetailsService userDetailsService;

    /* For Swagger */
    @SuppressWarnings("unused")
    private final String[] AUTH_WHITELIST = {
            "/api/v1/auth/**",
            "/v3/api-docs/**",
            "/v3/api-docs.yaml",
            "/swagger-ui/**",
            "swagger-ui.html"
    };

    public SecurityConfig(
            BCryptPasswordEncoder bCryptPasswordEncoder,
            JwtFilter jwtFilter,
            UserDetailsService userDetailsService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtFilter = jwtFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    // Interface à implémenter par les classes (généralement les gestionnaires de
    // requêtes HTTP) qui fournit une instance de CorsConfiguration en fonction de
    // la requête fournie.
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST"));
        configuration.setAllowedHeaders(Arrays.asList("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Nous appliquons ici cette configuration à toutes les routes de l'application
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    

    /* SECURITY FILTERS CHAIN */
    /* ============================================================ */

    /**
     * Create security filter chain of application
     *
     * @param httpSecurity item allows configuring web based security for specific
     *                     http requests. By default it will be applied to all
     *                     requests, but can be
     *                     restricted using requestMatcher or other similar methods.
     *                     <p>
     * @return the security filter chain
     *         <p>
     * @author A.Mochizuki
     * @date 28/03/2024
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
        .cors(c -> c.configurationSource(corsConfigurationSource()))
                /********* CRSF PROTECTION *********/
                /*
                 * Deactivation of protection against the cross-site request forgery
                 * vulnerability, which consists in transmitting a falsified request to an
                 * unauthenticated user who will point to an internal site action so that he can
                 * execute it without being aware of it with his own rights.
                 */
                .csrf(AbstractHttpConfigurer::disable)

                /********* AUTHORIZATIONS *********/
                /*
                 * By default, Spring Security requires that every request be authenticated.
                 * That said, any time you use an HttpSecurity instance, it’s necessary to
                 * declare your authorization rules.
                 * So whenever you have an HttpSecurity instance, you should at least do:
                 * http.authorizeHttpRequests((authorize) ->
                 * authorize.anyRequest().authenticated());)
                 */
                .authorizeHttpRequests(
                        /********* ROUTES MANAGEMENT *********/
                        authorize -> authorize
                        // Swagger-ui
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        // -> Publics endpoints
                        .requestMatchers(POST, Endpoint.REGISTER).permitAll()
                        .requestMatchers(POST, Endpoint.ACTIVATION).permitAll()
                        .requestMatchers(POST, Endpoint.SIGN_IN).permitAll()
                        .requestMatchers(POST, Endpoint.CHANGE_PASSWORD).permitAll()
                        .requestMatchers(POST, Endpoint.NEW_PASSWORD).permitAll()
                        .requestMatchers(POST, Endpoint.REFRESH_TOKEN).permitAll()
                        // -> Secured endpoints
                        .requestMatchers(POST, Endpoint.GET_CURRENT_USER).authenticated()
                        .requestMatchers(POST, Endpoint.SIGN_OUT).authenticated()
                        .requestMatchers(POST, Endpoint.USER).hasAuthority("ROLE_ADMIN")
                        .requestMatchers(GET, Endpoint.GET_ALL_USERS).hasAuthority("ROLE_ADMIN")
                        
                        /* Examples */
                        // .requestMatchers(POST, Endpoint.CREATE_COMMENT).hasAnyAuthority("ROLE_USER",
                        // "ROLE_MANAGER", "ROLE_ADMIN") // Role level
                        // .requestMatchers(POST, Endpoint.CREATE_COMMENT).hasROLE("USER") // Role level
                        // .requestMatchers(POST,
                        // Endpoint.CREATE_COMMENT).hasAuthority("UTILISATEUR_CREATE_AVIS") // Role
                        // Permission level
                        .requestMatchers(POST, Endpoint.CREATE_COMMENT)
                        .hasAnyAuthority("ROLE_USER", "ROLE_MANAGER", "ROLE_ADMIN") // Role level
                        .requestMatchers(GET, Endpoint.COMMENT_GET_ALL)
                        .hasAnyAuthority("ROLE_USER", "ROLE_MANAGER", "ROLE_ADMIN") // Role level

                        // .requestMatchers(GET, "/comment").hasAnyAuthority("ROLE_ADMIN",
                        // "ROLE_MANAGER")

                        // ->  By default must be authenticated
                        .anyRequest().authenticated())
                                

                /*
                 * Session Management
                 * session is a period of interaction between a user and application.
                 * Furthermore, the website maintains state information about the user’s actions
                 * and preferences during a session.
                 * The server can initiate a session for a user when they browse through a
                 * website.
                 * The session remains active until the user logs out.
                 * Session can help to improve the security by allowing the server to
                 * authenticate
                 * users and prevent unauthorized access to sensitive datas.
                 * Here, we ask to spring security don't create session, because our application
                 * is stateless (REST)
                 */
                .sessionManagement(
                        httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer
                                /*
                                 * disable the creation and use of HTTP sessions. When this policy is set,
                                 * Spring Security will not create a session and will not rely on the session to
                                 * store the SecurityContext.
                                 * This policy is useful for stateless applications
                                 * where every request needs to be authenticated separately, without relying on
                                 * a previous session. However, note that this policy only applies to the Spring
                                 * Security context, and your application might still create its own sessions.
                                 * Reminder -> Browsers can generally handle authentication in one of two ways:
                                 * - Token authentication (stateless): the token already has the information
                                 * needed to validate the user, so there's no need to save session information
                                 * on the server.
                                 *
                                 * - Session-based authentication (stateful) using cookies: identifiers are
                                 * saved on the server and in the browser for comparison.
                                 */
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                /********* CUSTOMS FILTERS *********/
                /*
                 * Add a custom filter upstream of the security chain, inheriting from a filter
                 * class.
                 * The UsernamePasswordAuthenticationFilter is a Spring Security class that
                 * handles the authentication process for username and password credentials
                 */
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    // @Bean
    // // Création de la chaine des filtres de sécurité (Implémentation du Bean
    // // SecuriyFilterChain)
    // // Nota : l'annotation @Bean indique que la méthode produira un objet java, le
    // // bean, qui sera géré par le conteneur Spring
    // // Nota : L'objet HttpSecurity permet de configurer la sécurité basée sur le web
    // // pour des requêtes http spécifiques.
    // // Par défaut, la sécurité est appliquée à toutes les demandes, mais elle peut
    // // être restreinte à l'aide de requestMatcher(RequestMatcher) ou d'autres
    // // méthodes similaires.
    // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    //     http
    //             // ===> Utilisation de la méthode cors() de Spring Security
    //             .cors(c -> c.configurationSource(corsConfigurationSource()))

    //             // Permet de configurer la gestion des exceptions. Ceci est automatiquement
    //             // appliqué lors de l'utilisation de EnableWebSecurity.
    //             .exceptionHandling(customizer -> customizer
    //                     .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))

    //             // Désactivation de la sécurité crsf
    //             // Nota: La sécurité CSRF (Cross-Site Request Forgery) est un type d’attaque qui
    //             // vise à inciter un utilisateur authentifié à effectuer une action non
    //             // souhaitée sur un site web. Cette attaque repose sur la confiance que
    //             // l’utilisateur a dans l’authentification globale du site, ce qui permet à
    //             // l’attaquant de faire exécuter des requêtes HTTP à l’insu de l’utilisateur.
    //             .csrf(AbstractHttpConfigurer::disable)

    //             // Configuration d'une application stateless
    //             /*
    //              * Dans une configuration Spring Security, SessionCreationPolicy.STATELESS
    //              * indique qu'aucune session ne sera créée ou utilisée par Spring Security.
    //              * Cette politique est utile pour les applications sans état, où les décisions
    //              * d'authentification et d'autorisation sont prises sans s'appuyer sur des
    //              * sessions HTTP.
    //              * Caractéristiques principales :
    //              * - aucune session n'est créée ou stockée du côté du client (par exemple, des
    //              * cookies).
    //              * - Chaque demande nécessite une nouvelle authentification, car aucune
    //              * information de session n'est disponible.
    //              * - L'application ne s'appuie pas sur les sessions HTTP pour l'authentification
    //              * ou l'autorisation.
    //              */
    //             .sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    //             .authorizeHttpRequests((requests) -> requests
    //                     .anyRequest().permitAll());
    //     return http.build();
    // }

    /* AUTHENTIFICATION MANAGER */
    /* ============================================================ */

    /**
     * Create authentification manager
     * The Spring AuthenticationManager is a core component of
     * Spring Security responsible for validating user credentials.
     * It is typically implemented by ProviderManager, which delegates to a chain of
     * AuthenticationProvider instances.
     *
     * @param authenticationConfiguration who define the authentification process
     *                                    for you application
     * @return the authentification manager of application
     * @throws Exception when anomalie in identification occurs
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /* AUTHENTIFICAION PROVIDER */
    /* ============================================================ */

    /**
     * Create Spring authentification provider, who will be used by
     * AuthenticationManager,
     * using UserDetailsService and passwordEncoder
     * <p>
     * AuthenticationProvider is a key component of Spring Security’s
     * authentication and authorization. It is responsible for
     * authenticating a user’s credentials and returning an Authentication object
     * that represents the authenticated user.
     *
     * @return the authentification provider
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {

        /*
         * The DaoAuthenticationProvider is a AuthenticationProvider implementation in
         * Spring Security that uses a UserDetailsService and PasswordEncoder to
         * authenticate a username and password. It is responsible for loading user
         * details from the UserDetailsService and comparing the provided password with
         * the encoded password stored in the UserDetails object.
         */
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return daoAuthenticationProvider;
    }

}
