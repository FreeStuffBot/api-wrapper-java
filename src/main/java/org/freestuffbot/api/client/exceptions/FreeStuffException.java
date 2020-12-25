package org.freestuffbot.api.client.exceptions;

public class FreeStuffException extends Exception {
    public FreeStuffException(String error, String message) {
        super(error + ": " + message);
    }
}
