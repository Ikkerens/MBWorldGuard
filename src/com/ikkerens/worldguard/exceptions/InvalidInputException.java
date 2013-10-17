package com.ikkerens.worldguard.exceptions;

public class InvalidInputException extends RuntimeException {
    private static final long serialVersionUID = 7745659364559192517L;

    public InvalidInputException( final String msg ) {
        super( msg );
    }
}
