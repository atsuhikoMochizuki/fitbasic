package fr.mochizuki.generic_api.cross_cutting.exceptions;

import static fr.mochizuki.generic_api.cross_cutting.constants.MessagesEn.VALIDATION_ERROR_NOT_FOUND;

public class InoteValidationNotFoundException extends Exception{
   
    public InoteValidationNotFoundException() {
        super(VALIDATION_ERROR_NOT_FOUND);
    }
}
