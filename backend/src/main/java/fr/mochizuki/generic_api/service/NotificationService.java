package fr.mochizuki.generic_api.service;

import org.springframework.mail.MailException;

import fr.mochizuki.generic_api.cross_cutting.exceptions.InoteInvalidEmailException;
import fr.mochizuki.generic_api.cross_cutting.exceptions.InoteMailException;
import fr.mochizuki.generic_api.entity.Validation;

/**
 * The interface Notification service.
 * @author A.Mochizuki
 * @date 26-03-2024
 */
public interface NotificationService {
    /**
     * Send validation by email.
     * @author A.Mochizuki
     *
     * @date 26-03-2024
     * @param validation the validation
     * @throws InoteMailException 
     */
    void sendValidation_byEmail(Validation validation) throws MailException, InoteInvalidEmailException, InoteMailException;

}
