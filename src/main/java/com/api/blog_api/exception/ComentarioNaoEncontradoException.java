package com.api.blog_api.exception;

public class ComentarioNaoEncontradoException extends RuntimeException {
    public ComentarioNaoEncontradoException(String message) {
        super(message);
    }
}
