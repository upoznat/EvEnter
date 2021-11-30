package com.eventer.paymentservice.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Throwables.propagate;

public class ToStringHelper {

    private ToStringHelper() {}

    public static final String toString(Object obj) {
        return toString(obj, new CustomObjectMapper());
    }

    public static final String toString(Object obj, ObjectMapper mapper) {
        if (obj == null) {
            return "<null>";
        }
        try {
            return checkNotNull(mapper).writer().withDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw propagate(e);
        }
    }

}
