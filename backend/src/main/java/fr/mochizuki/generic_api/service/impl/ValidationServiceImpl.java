package fr.mochizuki.generic_api.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import fr.mochizuki.generic_api.cross_cutting.exceptions.InoteInvalidEmailException;
import fr.mochizuki.generic_api.cross_cutting.exceptions.InoteMailException;
import fr.mochizuki.generic_api.cross_cutting.exceptions.InoteValidationNotFoundException;
import fr.mochizuki.generic_api.entity.User;
import fr.mochizuki.generic_api.entity.Validation;
import fr.mochizuki.generic_api.repository.ValidationRepository;
import fr.mochizuki.generic_api.service.NotificationService;
import fr.mochizuki.generic_api.service.ValidationService;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;

/**
 * The Service ValidationServiceImpl
 * 
 * @author A.Mochizuki
 * @date 11/04/2024
 */
@Transactional
@Service
public class ValidationServiceImpl implements ValidationService {

    /* DEPENDENCIES INJECTION */
    /* ============================================================ */
    private ValidationRepository validationRepository;
    private NotificationService notificationService;

    public ValidationServiceImpl(ValidationRepository validationRepository, NotificationService notificationService) {
        this.validationRepository = validationRepository;
        this.notificationService = notificationService;
    }

    /* PUBLIC METHODS */
    /* ============================================================ */

    /**
     * Create and save validation in database
     *
     * @param user the user to save
     * @author A.Mochizuki
     * @throws InoteMailException 
     * @throws MailException 
     * @date 2024-03-26
     */
    @Override
    public Validation createAndSave(User user) throws InoteInvalidEmailException, MailException, InoteMailException {
        Validation validation = Validation.builder()
                .user(user)
                .creation(Instant.now())
                .expiration(Instant.now().plus(5, ChronoUnit.MINUTES))
                .code(String.format("%06d", new Random().nextInt(999999)))
                .build();
        this.validationRepository.save(validation);

        this.notificationService.sendValidation_byEmail(validation);

        return validation;

    }

    /**
     * Get the validation in database from code
     *
     * @param code Sended code to email
     * @return the validation
     */
    @Override
    public Validation getValidationFromCode(String code) throws InoteValidationNotFoundException {
        return this.validationRepository.findByCode(code)
                .orElseThrow(InoteValidationNotFoundException::new);
    }
}
