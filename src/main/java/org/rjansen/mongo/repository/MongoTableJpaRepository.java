package org.rjansen.mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface MongoTableJpaRepository extends MongoRepository<MongoTable,String>, QuerydslPredicateExecutor<MongoTable> {
}
