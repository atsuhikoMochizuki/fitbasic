package fr.mochizuki.generic_api.service.impl;

import org.springframework.mail.MailException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import fr.mochizuki.generic_api.cross_cutting.enums.RoleEnum;
import fr.mochizuki.generic_api.cross_cutting.exceptions.*;
import fr.mochizuki.generic_api.entity.Role;
import fr.mochizuki.generic_api.entity.User;
import fr.mochizuki.generic_api.entity.Validation;
import fr.mochizuki.generic_api.repository.RoleRepository;
import fr.mochizuki.generic_api.repository.UserRepository;
import fr.mochizuki.generic_api.repository.ValidationRepository;
import fr.mochizuki.generic_api.service.UserService;
import fr.mochizuki.generic_api.service.ValidationService;

import static fr.mochizuki.generic_api.cross_cutting.constants.RegexPatterns.REGEX_EMAIL_PATTERN;
import static fr.mochizuki.generic_api.cross_cutting.constants.RegexPatterns.REGEX_PASSWORD_FORMAT;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Services related to User
 *
 * @author A.Mochizuki
 * @date 26/03/2024
 */
@Service
public class UserServiceImpl implements UserService {

    /* DEPENDENCIES INJECTION */
    /* ============================================================ */
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private ValidationService validationService;
    private RoleRepository roleRepository;
    private ValidationRepository validationRepository;

    public UserServiceImpl(
            UserRepository userRepository,
            BCryptPasswordEncoder passwordEncoder,
            ValidationService validationService,
            RoleRepository roleRepository,
            ValidationRepository validationRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.validationService = validationService;
        this.roleRepository = roleRepository;
        this.validationRepository = validationRepository;
    }

    /* PUBLIC METHODS */
    /* ============================================================ */

    /**
     * Ensure that password is 8 to 64 characters long and contains a mix of upper
     * and lower case
     * characters, one numeric and one special character
     * 
     * @param password
     * @throws InoteInvalidPasswordFormatException
     */
    public void checkPasswordSecurityRequirements(String password) throws InoteInvalidPasswordFormatException {
        Pattern compiledPattern;
        Matcher matcher;

        compiledPattern = Pattern.compile(REGEX_PASSWORD_FORMAT);
        matcher = compiledPattern.matcher(password);
        if (!matcher.matches()) {
            throw new InoteInvalidPasswordFormatException();
        }
    }

    /**
     * Retrieve an identified user
     * <p>
     *
     * @param username the username identifying the user whose data is required.
     * @return
     * @throws UsernameNotFoundException
     * @author A.Mochizuki
     * @date 26/03/2024
     *       The loadUserByUsername() method is a part of the UserDetailsService
     *       interface
     *       in Spring Security, which is responsible for retrieving user-related
     *       data,
     *       particularly during the authentication process.
     */
    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("None user found"));
    }

    /**
     * Create an user in database with:<br>
     * <ul>
     * <li>validation of email format</li>
     * <li>validation of password format</li>
     * <li>checking is user is allready in database</li>
     * <li>encryption of password with Bcrypt</li>
     * </ul>
     * then create a validation and save it in database
     * <p>
     *
     * @param user the user to register
     *             <p>
     * @return the user
     * @throws InoteExistingEmailException
     * @author A.Mochizuki
     * @throws InoteMailException
     * @throws MailException
     * @date 26/03/2024
     */
    @Override
    public User register(User user) throws InoteExistingEmailException, InoteInvalidEmailException,
            InoteRoleNotFoundException, InoteInvalidPasswordFormatException, MailException, InoteMailException {
        User userToRegister = this.createUser(user);
        this.validationService.createAndSave(userToRegister);
        return userToRegister;
    }

    public User registerTester(User user) throws InoteExistingEmailException, InoteInvalidEmailException,
            InoteRoleNotFoundException, InoteInvalidPasswordFormatException, MailException, InoteMailException {
        User userToRegister = this.createTesterUser(user);
        this.validationService.createAndSave(userToRegister);
        return userToRegister;
    }

    /* PRIVATE METHODS */
    /* ============================================================ */

    /**
     * Create an user in database with:<br>
     * <ul>
     * <li>validation of email format</li>
     * <li>validation of password format</li>
     * <li>checking is user is allready in database</li>
     * <li>encryption of password with Bcrypt</li>
     * </ul>
     *
     * @return the saved user if success
     * @throws InoteExistingEmailException
     * @author A.Mochizuki
     * @date 26/03/2024
     */
    private User createUser(User user) throws InoteExistingEmailException, InoteInvalidEmailException,
            InoteInvalidPasswordFormatException, InoteRoleNotFoundException {

        Pattern compiledPattern;
        Matcher matcher;

        // Email format checking
        compiledPattern = Pattern.compile(REGEX_EMAIL_PATTERN);
        matcher = compiledPattern.matcher(user.getEmail());
        if (!matcher.matches()) {
            throw new InoteInvalidEmailException();
        }

        this.checkPasswordSecurityRequirements(user.getPassword());

        // Verification of any existing registration
        Optional<User> utilisateurOptional = this.userRepository.findByEmail(user.getEmail());
        if (utilisateurOptional.isPresent()) {
            throw new InoteExistingEmailException();
        }

        // Insert encrypted password in database
        String pass = user.getPassword();
        String mdpCrypte = this.passwordEncoder.encode(pass);
        user.setPassword(mdpCrypte);

        // Role affectation
        Role role = this.roleRepository.findByName(RoleEnum.USER).orElseThrow(InoteRoleNotFoundException::new);
        user.setRole(role);

        return this.userRepository.save(user);
    }

    /**
     * Create an user tester in database with:<br>
     * <ul>
     * <li>validation of email format</li>
     * <li>validation of password format</li>
     * <li>checking is user is allready in database</li>
     * <li>encryption of password with Bcrypt</li>
     * </ul>
     *
     * @return the saved user if success
     * @throws InoteExistingEmailException
     * @author A.Mochizuki
     * @date 26/03/2024
     */
    private User createTesterUser(User user) throws InoteExistingEmailException, InoteInvalidEmailException,
            InoteInvalidPasswordFormatException, InoteRoleNotFoundException {

        Pattern compiledPattern;
        Matcher matcher;

        // Email format checking
        compiledPattern = Pattern.compile(REGEX_EMAIL_PATTERN);
        matcher = compiledPattern.matcher(user.getEmail());
        if (!matcher.matches()) {
            throw new InoteInvalidEmailException();
        }

        this.checkPasswordSecurityRequirements(user.getPassword());

        // Verification of any existing registration
        Optional<User> utilisateurOptional = this.userRepository.findByEmail(user.getEmail());
        if (utilisateurOptional.isPresent()) {
            throw new InoteExistingEmailException();
        }

        // Insert encrypted password in database
        String pass = user.getPassword();
        String mdpCrypte = this.passwordEncoder.encode(pass);
        user.setPassword(mdpCrypte);

        // Role affectation
        Role role = this.roleRepository.findByName(RoleEnum.TESTER).orElseThrow(InoteRoleNotFoundException::new);
        user.setRole(role);

        return this.userRepository.save(user);
    }

    /**
     * Activate an user
     *
     * @param activation informations
     * @return
     */

    public User activation(String activationCode)
            throws InoteValidationNotFoundException, InoteValidationExpiredException, InoteUserNotFoundException {
        Validation validation = this.validationService.getValidationFromCode(activationCode);
        if (Instant.now().isAfter(validation.getExpiration())) {
            throw new InoteValidationExpiredException();
        }

        User activatedUser = this.userRepository.findById(validation.getUser().getId())
                .orElseThrow(InoteUserNotFoundException::new);
        activatedUser.setActif(true);

        validation.setActivation(Instant.now());
        validationRepository.save(validation);
        return this.userRepository.save(activatedUser);
    }

    /**
     * Change password user
     *
     * @param email
     * @throws InoteMailException
     * @throws MailException
     */
    public void changePassword(String email) throws InoteInvalidEmailException, MailException, InoteMailException {
        User user = this.loadUserByUsername(email);
        this.validationService.createAndSave(user);
    }

    /**
     * Update the new password in database
     * After receiving his activation code by email, the user sends his new
     * password, along with his email address and the code.
     * If the email corresponds to the validation referred to by the activation
     * code,
     * the user's new password, if it meets security requirements,
     * is encoded and replaces the previous one.
     *
     * @param email containing user email
     */
    public void newPassword(String email, String newPassword, String code)
            throws InoteValidationNotFoundException, InoteInvalidPasswordFormatException, UsernameNotFoundException {

        User user = this.loadUserByUsername(email);

        final Validation validation = validationService.getValidationFromCode(code);

        if (validation.getUser().getEmail().equals(user.getEmail())) {
            this.checkPasswordSecurityRequirements(newPassword);
            String EncrytedPassword = this.passwordEncoder.encode(newPassword);
            user.setPassword(EncrytedPassword);
            this.userRepository.save(user);
        }
    }

    /**
     * Get all users
     *
     * @return a list containing all users
     * 
     * @author A.Mochizuki
     * @since 2024-06-13
     */
    public List<User> list() {
        List<User> users = new ArrayList<>();
        Iterable<User> iterable = this.userRepository.findAll();
        for(User item:iterable){
            users.add(item);
        }
        return users;
    }

    

}
