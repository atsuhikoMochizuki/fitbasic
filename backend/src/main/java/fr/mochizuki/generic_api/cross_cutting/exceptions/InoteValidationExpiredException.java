package fr.mochizuki.generic_api.cross_cutting.exceptions;

import static fr.mochizuki.generic_api.cross_cutting.constants.MessagesEn.VALIDATION_ERROR_VALIDATION_IS_EXPIRED;

public class InoteValidationExpiredException extends Exception {
   
    public InoteValidationExpiredException() {
        super(VALIDATION_ERROR_VALIDATION_IS_EXPIRED);
    }

}
