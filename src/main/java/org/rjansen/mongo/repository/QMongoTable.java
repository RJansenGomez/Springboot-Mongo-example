package org.rjansen.mongo.repository;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import org.rjansen.common.repository.FieldFilter;
import org.rjansen.common.repository.PredicateContent;
import org.rjansen.common.repository.PredicateLogicalBuilder;
import org.rjansen.common.repository.PredicateStringBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

public class QMongoTable extends EntityPathBase<MongoTable> {


    public static final String ID_FIELD = "id";
    public static final String FIELD1_FIELD = "field1";
    public static final String FIELD2_FIELD = "field2";
    public static final String FIELD3_FIELD = "field3";
    public static final String FIELD4_FIELD = "field4";
    public static final String FIELD5_FIELD = "field5";
    public static final String FIELD6_FIELD = "field6";
    private static final String VARIABLE = "table_test";

    public static final QMongoTable testTable = new QMongoTable();

    public final StringPath id = createString(ID_FIELD);
    public final StringPath field1 = createString(FIELD1_FIELD);
    public final NumberPath<Long> field2 = createNumber(FIELD2_FIELD, Long.class);
    public final NumberPath<Float> field3 = createNumber(FIELD3_FIELD, Float.class);
    public final StringPath field4 = createString(FIELD4_FIELD);
    public final NumberPath<Double> field5 = createNumber(FIELD5_FIELD, Double.class);
    public final DateTimePath<LocalDateTime> field6 = createDateTime(FIELD6_FIELD, LocalDateTime.class);

    public QMongoTable() {
        super(MongoTable.class, forVariable(VARIABLE));
    }

    private PredicateStringBuilder predicateStringBuilder;
    private PredicateLogicalBuilder predicateLogicalBuilder;

    public Predicate buildPredicates(List<FieldFilter<?>> filters,
                                     PredicateStringBuilder predicateStringBuilder,
                                     PredicateLogicalBuilder predicateLogicalBuilder) {
        this.predicateStringBuilder = predicateStringBuilder;
        this.predicateLogicalBuilder = predicateLogicalBuilder;
        List<Predicate> predicates = filters.stream().map(this::mapFilter)
                .collect(Collectors.toList());
        return ExpressionUtils.allOf(predicates);
    }

    private Predicate mapFilter(FieldFilter fieldFilter) {

        switch (fieldFilter.getFieldName()) {
            case ID_FIELD:
                return createStringFilter(id, fieldFilter);
            case FIELD1_FIELD:
                return createStringFilter(field1, fieldFilter);
            case FIELD2_FIELD:
                return createLongFilter(field2, fieldFilter);
            case FIELD3_FIELD:
                return createFloatFilter(field3, fieldFilter);
            case FIELD4_FIELD:
                return createStringFilter(field4, fieldFilter);
            case FIELD5_FIELD:
                return createDoubleFilter(field5, fieldFilter);
            case FIELD6_FIELD:
                return createDateTimeFilter(field6, fieldFilter);
            default:
                throw new IllegalArgumentException("Field not supported");
        }
    }

    private Predicate createStringFilter(StringPath field, FieldFilter fieldFilter) {
        FieldFilter<String> stringFilter = (FieldFilter<String>) fieldFilter;
        return fieldFilter.getCondition().predicate.apply(predicateStringBuilder,
                new PredicateContent<>(stringFilter.getFieldValue(), field)
        );
    }

    private Predicate createLongFilter(NumberPath<Long> field, FieldFilter fieldFilter) {
        FieldFilter<Long> filter = (FieldFilter<Long>) fieldFilter;
        return fieldFilter.getCondition().predicate.apply(predicateLogicalBuilder,
                new PredicateContent<>(filter.getFieldValue(), field)
        );
    }

    private Predicate createFloatFilter(NumberPath<Float> field, FieldFilter fieldFilter) {
        FieldFilter<Float> filter = (FieldFilter<Float>) fieldFilter;
        return fieldFilter.getCondition().predicate.apply(predicateLogicalBuilder,
                new PredicateContent<>(filter.getFieldValue(), field)
        );
    }

    private Predicate createDoubleFilter(NumberPath<Double> field, FieldFilter fieldFilter) {
        FieldFilter<Double> filter = (FieldFilter<Double>) fieldFilter;
        return fieldFilter.getCondition().predicate.apply(predicateLogicalBuilder,
                new PredicateContent<>(filter.getFieldValue(), field)
        );
    }

    private Predicate createDateTimeFilter(DateTimePath<LocalDateTime> field, FieldFilter fieldFilter) {
        FieldFilter<LocalDateTime> filter = (FieldFilter<LocalDateTime>) fieldFilter;
        return fieldFilter.getCondition().predicate.apply(predicateLogicalBuilder,
                new PredicateContent<>(filter.getFieldValue(), field)
        );
    }
}
