package fr.mochizuki.generic_api.cross_cutting.exceptions;

import fr.mochizuki.generic_api.cross_cutting.constants.MessagesEn;

public class InoteEmptyMessageCommentException extends Exception {
    public InoteEmptyMessageCommentException() {
        super(MessagesEn.COMMENT_ERROR_MESSAGE_IS_EMPTY);
    }
}
