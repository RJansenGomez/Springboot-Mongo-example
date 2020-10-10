package org.rjansen.common.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.NumberPath;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PredicateLogicalBuilder<V extends Comparable> implements PredicateBuilder<V> {

    @Override
    public Predicate generateGreaterThan(PredicateContent<V> predicateContent) {
        return predicateConversion(predicateContent,Condition.GREATER_THAN);
    }

    @Override
    public Predicate generateLessThan(PredicateContent<V> predicateContent) {
        return predicateConversion(predicateContent,Condition.LESS_THAN);
    }

    @Override
    public Predicate generateGreaterThanOrEquals(PredicateContent<V> predicateContent) {
        return predicateConversion(predicateContent,Condition.GREATER_THAN_OR_EQ);
    }

    @Override
    public Predicate generateLessThanOrEquals(PredicateContent<V> predicateContent) {
        return predicateConversion(predicateContent,Condition.LESS_THAN_OR_EQ);
    }

    @Override
    public Predicate generateEquals(PredicateContent<V> predicateContent) {
        return predicateConversion(predicateContent,Condition.EQ);
    }

    @Override
    public Predicate generateLike(PredicateContent<V> predicateContent) {
        throw new IllegalArgumentException("Not supported for strings");
    }

    private Predicate predicateConversion(PredicateContent<V> predicateContent, Condition condition) {
        if (predicateContent.getConditionPath() instanceof NumberPath) {
            NumberPath path = (NumberPath) predicateContent.getConditionPath();
            return generatePredicateOfNumberPath(path,(Number) predicateContent.getValue(), condition);

        }else if (predicateContent.getConditionPath() instanceof DateTimePath) {
            DateTimePath<LocalDateTime> path = (DateTimePath<LocalDateTime>) predicateContent.getConditionPath();
            return generatePredicateOfDateTimePath(path,(LocalDateTime)predicateContent.getValue(),condition);
        }
        throw new IllegalArgumentException("Condition path not supported");
    }

    private Predicate generatePredicateOfNumberPath(NumberPath path, Number value, Condition condition) {
        switch (condition){
            case GREATER_THAN: return path.gt(value);
            case LESS_THAN: return path.lt(value);
            case GREATER_THAN_OR_EQ: return path.goe(value);
            case LESS_THAN_OR_EQ: return path.loe(value);
            case EQ: return path.eq(value);
        }
        throw new IllegalArgumentException("Condition path not supported");
    }

    private Predicate generatePredicateOfDateTimePath(DateTimePath<LocalDateTime> path, LocalDateTime value, Condition condition) {
        switch (condition){
            case GREATER_THAN: return path.gt(value);
            case LESS_THAN: return path.lt(value);
            case GREATER_THAN_OR_EQ: return path.goe(value);
            case LESS_THAN_OR_EQ: return path.loe(value);
            case EQ: return path.eq(value);
        }
        throw new IllegalArgumentException("Condition path not supported");
    }
}
