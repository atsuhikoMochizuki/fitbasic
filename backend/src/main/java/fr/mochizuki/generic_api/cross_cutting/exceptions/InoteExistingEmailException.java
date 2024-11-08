package fr.mochizuki.generic_api.cross_cutting.exceptions;

import static fr.mochizuki.generic_api.cross_cutting.constants.MessagesEn.REGISTER_ERROR_USER_ALREADY_EXISTS;

public class InoteExistingEmailException extends Exception{
    public InoteExistingEmailException() {
        super(REGISTER_ERROR_USER_ALREADY_EXISTS);
    }

}
