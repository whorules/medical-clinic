package ru.korovko.clinic.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ValidationResult {

    private String field;
    private String message;

    @Override
    public String toString() {
        return "{" +
                "field='" + field + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
