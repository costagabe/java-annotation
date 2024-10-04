package com.annotation.crud.usecase;

import com.annotation.crud.dataprovider.CrudDataProvider;
import com.annotation.domain.entity.BaseEntity;
import com.annotation.domain.entity.Page;
import com.annotation.domain.entity.Pageable;

public abstract class CrudUseCase<T, K extends BaseEntity<T>> {

    private final CrudDataProvider<T, K> dataProvider;

    protected CrudUseCase(CrudDataProvider<T, K> dataProvider) {
        this.dataProvider = dataProvider;
    }

    public K create(K entity) {
        entity.validate();
        return dataProvider.create(entity);
    }

    public K update(T id, K entity) {
        entity.validate();
        return dataProvider.update(id, entity);
    }

    public K getById(T id) {
        return dataProvider.getById(id);
    }

    public Page<K> findAllByExample(Pageable pageable, K example) {
        return dataProvider.findAllByExample(pageable, example);
    }

    public K findByExample(K example) {
        return dataProvider.findByExample(example);
    }

    public void delete(K entity) {
        dataProvider.delete(entity);
    }

}
