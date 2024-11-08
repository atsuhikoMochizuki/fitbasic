package fr.mochizuki.generic_api.cross_cutting.exceptions;

import static fr.mochizuki.generic_api.cross_cutting.constants.MessagesEn.EMAIL_ERROR_INVALID_PASSWORD_FORMAT;

public class InoteInvalidPasswordFormatException extends Exception {
    public InoteInvalidPasswordFormatException() {
        super(EMAIL_ERROR_INVALID_PASSWORD_FORMAT);
    }
}
