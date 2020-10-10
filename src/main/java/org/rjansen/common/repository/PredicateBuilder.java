package org.rjansen.common.repository;

import com.querydsl.core.types.Predicate;


public interface PredicateBuilder<T extends Comparable> {

    Predicate generateGreaterThan(PredicateContent<T> predicateContent);
    Predicate generateLessThan(PredicateContent<T> predicateContent);
    Predicate generateGreaterThanOrEquals(PredicateContent<T> predicateContent);
    Predicate generateLessThanOrEquals(PredicateContent<T> predicateContent);
    Predicate generateEquals(PredicateContent<T> predicateContent);
    Predicate generateLike(PredicateContent<T> predicateContent);
}
