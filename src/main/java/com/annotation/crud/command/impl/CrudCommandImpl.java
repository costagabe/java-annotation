package com.annotation.crud.command.impl;

import com.annotation.crud.command.CrudCommand;
import com.annotation.crud.usecase.CrudUseCase;
import com.annotation.domain.entity.BaseEntity;
import com.annotation.domain.entity.Page;
import com.annotation.domain.entity.Pageable;

public class CrudCommandImpl<T, K extends BaseEntity<T>> implements CrudCommand<T, K> {
    private final CrudUseCase<T, K> useCase;

    protected CrudCommandImpl(CrudUseCase<T, K> useCase) {
        this.useCase = useCase;
    }


    @Override
    public K create(K entity) {
        return useCase.create(entity);
    }

    @Override
    public K update(T id, K entity) {
        return useCase.update(id, entity);
    }

    @Override
    public K getById(T id) {
        return useCase.getById(id);
    }

    @Override
    public Page<K> findAllByExample(Pageable pageable, K example) {
        return useCase.findAllByExample(pageable, example);
    }

    @Override
    public K findByExample(K example) {
        return useCase.findByExample(example);
    }

    @Override
    public void delete(K entity) {
        useCase.delete(entity);
    }
}
