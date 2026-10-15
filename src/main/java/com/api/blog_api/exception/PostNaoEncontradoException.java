package com.api.blog_api.exception;

public class PostNaoEncontradoException extends RuntimeException {

    public PostNaoEncontradoException(String message) {
        super(message);
    }
}
