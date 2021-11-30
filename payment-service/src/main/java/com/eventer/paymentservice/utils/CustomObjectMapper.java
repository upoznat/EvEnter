package com.eventer.paymentservice.utils;

import static com.fasterxml.jackson.core.JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN;
import static com.fasterxml.jackson.databind.DeserializationFeature.*;
import static com.fasterxml.jackson.databind.SerializationFeature.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public final class CustomObjectMapper extends ObjectMapper {

    private static final long serialVersionUID = 2322286789752923876L;

    public CustomObjectMapper() {
        configure(WRITE_BIGDECIMAL_AS_PLAIN, true);
        configure(WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
        configure(READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
        configure(INDENT_OUTPUT, true);

        configure(USE_BIG_DECIMAL_FOR_FLOATS, true);
        configure(FAIL_ON_UNKNOWN_PROPERTIES, false);

        registerModule(new JavaTimeModule());

    }

    public void setView(Class<?> view) {
        setConfig(getSerializationConfig().withView(view));
    }

}
