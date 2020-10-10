package org.rjansen.common.repository;

import com.querydsl.core.types.Predicate;

import java.util.function.BiFunction;


public enum Condition {
    GREATER_THAN(PredicateBuilder::generateGreaterThan, ">"),
    LESS_THAN(PredicateBuilder::generateLessThan, "<"),
    GREATER_THAN_OR_EQ(PredicateBuilder::generateGreaterThanOrEquals, ">="),
    LESS_THAN_OR_EQ(PredicateBuilder::generateLessThanOrEquals, "<="),
    EQ(PredicateBuilder::generateEquals, "="),
    LK(PredicateBuilder::generateLike, "lk");
    public final String operator;
    public final BiFunction<PredicateBuilder, PredicateContent<?>, Predicate> predicate;

    private Condition(BiFunction<PredicateBuilder, PredicateContent<?>, Predicate> predicate, String operator) {
        this.operator = operator;
        this.predicate = predicate;
    }

    public static Condition getConditionByOperator(String operator) {
        for (Condition con : values()) {
            if (con.operator.equals(operator)) {
                return con;
            }
        }
        throw new IllegalArgumentException("Operation not supported");
    }
}
