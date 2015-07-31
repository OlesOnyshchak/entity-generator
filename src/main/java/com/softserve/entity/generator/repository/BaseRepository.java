package com.softserve.entity.generator.repository;

import com.softserve.entity.generator.entity.DatabaseObject;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseRepository<T extends DatabaseObject>
{
    void save(T object);

    void delete(T object);

    T merge(T object);
}
