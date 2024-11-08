package fr.mochizuki.generic_api.cross_cutting.exceptions;

import static fr.mochizuki.generic_api.cross_cutting.constants.MessagesEn.EMAIL_ERROR_INVALID_EMAIL_FORMAT;

public class InoteInvalidEmailException extends Exception {
    public InoteInvalidEmailException(){
        super(EMAIL_ERROR_INVALID_EMAIL_FORMAT);
    }
}