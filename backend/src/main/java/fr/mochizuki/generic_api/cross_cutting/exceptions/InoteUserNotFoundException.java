package fr.mochizuki.generic_api.cross_cutting.exceptions;

import static fr.mochizuki.generic_api.cross_cutting.constants.MessagesEn.USER_ERROR_USER_NOT_FOUND;

public class InoteUserNotFoundException extends Exception {
    public InoteUserNotFoundException() {
        super(USER_ERROR_USER_NOT_FOUND);
    }
}
