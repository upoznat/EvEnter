package com.eventer.wallet.exception;

import org.springframework.dao.OptimisticLockingFailureException;

public class OptimisticLockingFailureEntityException extends OptimisticLockingFailureException {

    private final Object entity;

    /**
     * Use one of the factory methods below.
     */
    OptimisticLockingFailureEntityException(String msg, Throwable cause, Object entity) {
        super(msg, cause);
        this.entity = entity;
    }

    public Object getEntity() {
        return entity;
    }

    //

    public static OptimisticLockingFailureEntityException lockingFailure(String msg, Throwable cause, Object entity) {
        throw new OptimisticLockingFailureEntityException(msg, cause, entity);
    }

    public static OptimisticLockingFailureEntityException lockingFailure(boolean mustBeTrue, String msg, Object entity) {
        OptimisticLockingFailureEntityException e = new OptimisticLockingFailureEntityException(msg, null, entity);
        if (!mustBeTrue) {
            throw e;
        }
        return e;
    }

}
