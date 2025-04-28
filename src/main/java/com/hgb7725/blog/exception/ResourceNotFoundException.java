package com.hgb7725.blog.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {
    private final String resourceName;
    private final String nameField;
    private final long valueField;

    public ResourceNotFoundException(String resourceName, String nameField, long valueField) {
        super(String.format("%s not found with %s : '%s'", resourceName, nameField, valueField));
        this.resourceName = resourceName;
        this.nameField = nameField;
        this.valueField = valueField;
    }
}
