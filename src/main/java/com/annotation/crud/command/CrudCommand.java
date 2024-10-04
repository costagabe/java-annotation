package com.annotation.crud.command;

import com.annotation.domain.entity.BaseEntity;
import com.annotation.domain.entity.Page;
import com.annotation.domain.entity.Pageable;

public interface CrudCommand<T, K extends BaseEntity<T>> {
    K create(K entity);

    K update(T id, K entity);

    K getById(T id);

    Page<K> findAllByExample(Pageable pageable, K example);

    K findByExample(K example);

    void delete(K entity);

}