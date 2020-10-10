package org.rjansen.common.repository;

import com.querydsl.core.types.dsl.ComparableExpression;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import lombok.Getter;

@Getter
public class PredicateContent<T extends Comparable> {
    private final T value;
    private final ComparableExpressionBase<T> conditionPath;

    public PredicateContent(T value, ComparableExpressionBase<T> conditionPath) {
        this.value = value;
        this.conditionPath = conditionPath;
    }
}
