package com.annotation.domain.entity;

import com.annotation.CrudUseCase;

import java.util.List;
import java.util.Map;
import java.util.UUID;


public class Person extends BaseEntity<UUID> {

    private int age;

    private String name;

    public int getAge() {
        return age;
    }

//    @BuilderProperty
    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

//    @BuilderProperty
    public void setName(String name) {
        this.name = name;
    }

    @Override
    protected Map<String, List<String>> getValidationErrors() {
        return Map.of();
    }
}