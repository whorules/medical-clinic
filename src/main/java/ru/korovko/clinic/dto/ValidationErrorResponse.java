package ru.korovko.clinic.dto;

import lombok.Data;

@Data
public class ValidationErrorResponse {

    private ValidationError error;

    @Data
    private static class ValidationError {

        private String fieldName;
        private String message;
    }
}
