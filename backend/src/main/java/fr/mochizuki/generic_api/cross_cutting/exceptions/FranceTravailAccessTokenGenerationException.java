package fr.mochizuki.generic_api.cross_cutting.exceptions;

import fr.mochizuki.generic_api.cross_cutting.constants.MessagesEn;

public class FranceTravailAccessTokenGenerationException extends Exception {
    public FranceTravailAccessTokenGenerationException(){
        super(MessagesEn.ERROR_OCCURED_DURING_ACCESS_TOKEN_GENERATION);
    }

}
