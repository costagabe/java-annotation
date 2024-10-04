package com.annotation.domain.entity;

import java.util.List;
import java.util.Map;

public abstract class BaseEntity<T> {
    private T id;

    protected Map<String, List<String>> validationErrors;

    public void validate() {
    boolean isValid = getValidationErrors().values().stream().allMatch(List::isEmpty);
    if (!isValid) {
        throw new RuntimeException("Validations failed");
    }
    }

    protected abstract Map<String, List<String>> getValidationErrors();

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }
}
