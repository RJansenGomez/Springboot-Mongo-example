package org.rjansen.mongo.repository;

import com.querydsl.core.types.Predicate;
import org.rjansen.common.repository.FieldFilter;
import org.rjansen.common.repository.PredicateLogicalBuilder;
import org.rjansen.common.repository.PredicateStringBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class MongoTableRepositoryImpl {

    private final MongoTableJpaRepository repositoryJpa;
    private final PredicateStringBuilder predicateStringBuilder;
    private final PredicateLogicalBuilder predicateLogicalBuilder;

    public MongoTableRepositoryImpl(final MongoTableJpaRepository repository,
                                    final PredicateStringBuilder predicateStringBuilder,
                                    final PredicateLogicalBuilder predicateLogicalBuilder) {
        this.repositoryJpa = repository;
        this.predicateLogicalBuilder = predicateLogicalBuilder;
        this.predicateStringBuilder = predicateStringBuilder;
    }

    public Page<MongoTable> search(MongoSearch search) {
        QMongoTable query = QMongoTable.testTable;
        Pageable page = search.getPageable();
        Predicate predicate = query.buildPredicates(search.getFilters(),
                predicateStringBuilder,
                predicateLogicalBuilder
        );
        if(predicate!=null) {
            return repositoryJpa.findAll(predicate, page);
        }else{
            return repositoryJpa.findAll(page);
        }
    }
}
