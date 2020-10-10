package org.rjansen.common.repository;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.stereotype.Component;

@Component
public class PredicateStringBuilder implements PredicateBuilder<String> {

    @Override
    public Predicate generateGreaterThan(PredicateContent<String> predicateContent) {
        throw new RuntimeException("Not supported for strings");
    }

    @Override
    public Predicate generateLessThan(PredicateContent<String> predicateContent) {
        throw new RuntimeException("Not supported for strings");
    }

    @Override
    public Predicate generateGreaterThanOrEquals(PredicateContent<String> predicateContent) {
        throw new RuntimeException("Not supported for strings");
    }

    @Override
    public Predicate generateLessThanOrEquals(PredicateContent<String> predicateContent) {
        throw new RuntimeException("Not supported for strings");
    }

    @Override
    public Predicate generateEquals(PredicateContent<String> predicateContent) {
        StringPath stringPath = (StringPath) predicateContent.getConditionPath();
        return stringPath.equalsIgnoreCase(predicateContent.getValue());
    }

    @Override
    public Predicate generateLike(PredicateContent<String> predicateContent) {
        StringPath stringPath = (StringPath) predicateContent.getConditionPath();
        return ExpressionUtils.anyOf(stringPath.matches(predicateContent.getValue()),
                stringPath.matches(predicateContent.getValue().toLowerCase()),
                stringPath.matches(predicateContent.getValue().toUpperCase()));
    }
}
