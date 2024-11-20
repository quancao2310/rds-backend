package com.example.regionaldelicacy.serializers;

import java.io.IOException;
import java.time.Instant;

import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

@JsonComponent
public class InstantSerializer extends JsonSerializer<Instant> {
    @Override
    public void serialize(Instant instant, JsonGenerator generator, SerializerProvider serializers)
            throws IOException {
        if (instant != null) {
            generator.writeString(instant.toString());
        }
    }
}
