package com.volka.threadpool.exception;

/**
 * 런타임 예외
 *
 * @author volka
 */
public class BizException extends RuntimeException {
    private String code;
    private String msg;
    private Throwable cause;

    public BizException(String code) {
        this.code = code;
    }

    public BizException(String code, Throwable t) {
        this.code = code;
        this.cause = t;
    }
}
