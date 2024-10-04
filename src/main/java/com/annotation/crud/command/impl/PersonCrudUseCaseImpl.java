package com.annotation.crud.command.impl;

import com.annotation.crud.dataprovider.CrudDataProvider;
import com.annotation.crud.usecase.CrudUseCase;
import com.annotation.domain.entity.Person;

import java.util.UUID;

public class PersonCrudUseCaseImpl extends CrudUseCase<UUID, Person> {


    protected PersonCrudUseCaseImpl(CrudDataProvider<UUID, Person> dataProvider) {
        super(dataProvider);
    }
}
