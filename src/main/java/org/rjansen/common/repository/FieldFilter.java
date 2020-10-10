package org.rjansen.common.repository;

import lombok.Getter;
import org.rjansen.common.repository.exception.FilterNotValidRuntimeException;
import org.rjansen.common.utils.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import static org.rjansen.common.repository.Condition.*;


@Getter
public class FieldFilter<V> {
    private final Supplier<V> type;
    private String filterValue;
    private String fieldName;
    private V fieldValue;
    private String operation;
    private Condition condition;

    private FieldFilter(Supplier<V> type,
                        String filterValue, String filterField) {
        this.type = type;
        this.filterValue = filterValue;
        this.fieldName = filterField;
    }

    public String getFieldName() {
        return fieldName;
    }

    public V getFieldValue() {
        return fieldValue;
    }

    public String getOperation() {
        return operation;
    }

    private final static List<String> acceptedStringOperations =
            Arrays.asList(
                    EQ.operator,
                    LK.operator
            );
    private final static List<String> acceptedLogicalOperations =
            Arrays.asList(
                    GREATER_THAN.operator,
                    LESS_THAN.operator,
                    GREATER_THAN_OR_EQ.operator,
                    LESS_THAN_OR_EQ.operator,
                    EQ.operator
            );


    public static <T> FieldFilter<T> createFilter(String filterValue, String filterField, Supplier<T> type) {
        return new FieldFilter<>(type, filterValue, filterField).buildFilter();
    }

    private FieldFilter<V> buildFilter() {
        if (type.get() instanceof String) {
            convertStringFilter();
        } else if (type.get() instanceof LocalDateTime) {
            convertLocalDateTimeFilter();
        } else if (type.get() instanceof Integer) {
            convertIntegerFilter();
        } else if (type.get() instanceof LocalDate) {
            convertDateFilter();
        } else if (type.get() instanceof Double) {
            convertDoubleFilter();
        } else if (type.get() instanceof Float) {
            convertFloatFilter();
        } else if (type.get() instanceof Long) {
            convertLongFilter();
        }
        return this;
    }

    private void convertStringFilter() {
        this.operation = parseStringOperation(this.filterValue);
        this.condition = Condition.getConditionByOperator(this.operation);
        this.fieldValue = (V) convertString();
    }

    private String convertString() {
        return this.filterValue.substring(this.operation.length());
    }

    private void logicalConversion() {
        this.operation = parseLogicalOperation(this.filterValue);
        this.condition = Condition.getConditionByOperator(this.operation);
    }

    private void convertLocalDateTimeFilter() {
        logicalConversion();
        this.fieldValue = (V) parseDateTime();
    }

    private LocalDateTime parseDateTime() {
        String date = this.filterValue.substring(this.operation.length());
        return DateTimeFormat.parseStringToLocalDateTime(date);
    }

    private void convertDateFilter() {
        logicalConversion();
        this.fieldValue = (V) convertDate();
    }

    private LocalDate convertDate() {
        String date = this.filterValue.substring(this.operation.length());
        return DateTimeFormat.parseStringToLocalDate(date);
    }

    private void convertIntegerFilter() {
        logicalConversion();
        this.fieldValue = (V) convertInteger();
    }


    private Integer convertInteger() {
        return Integer.parseInt(this.filterValue.substring(this.operation.length()));
    }

    private void convertLongFilter() {
        logicalConversion();
        this.fieldValue = (V) convertLong();
    }


    private Long convertLong() {
        return Long.parseLong(this.filterValue.substring(this.operation.length()));
    }

    private void convertDoubleFilter() {
        logicalConversion();
        this.fieldValue = (V) convertDouble();
    }


    private Double convertDouble() {
        return Double.parseDouble(this.filterValue.substring(this.operation.length()));
    }

    private void convertFloatFilter() {
        logicalConversion();
        this.fieldValue = (V) convertFloat();
    }


    private Float convertFloat() {
        return Float.parseFloat(this.filterValue.substring(this.operation.length()));
    }

    private static String parseLogicalOperation(String filter) {
        if (null != filter
                && filter.length() >= 3) {
            String composedFilter = filter.substring(0, 2);
            if (acceptedLogicalOperations.contains(composedFilter)) {
                return composedFilter;
            } else {
                String singleFilter = filter.substring(0, 1);
                if (acceptedLogicalOperations.contains(singleFilter)) {
                    return singleFilter;
                } else {
                    throw new FilterNotValidRuntimeException(filter);
                }
            }
        } else {
            throw new FilterNotValidRuntimeException(filter);
        }
    }

    private static String parseStringOperation(String filter) {
        if (null != filter
                && filter.length() >= 3) {
            String composedFilter = filter.substring(0, 2);
            if (acceptedStringOperations.contains(composedFilter)) {
                return composedFilter;
            } else {
                String singleFilter = filter.substring(0, 1);
                if (acceptedStringOperations.contains(singleFilter)) {
                    return singleFilter;
                } else {
                    throw new FilterNotValidRuntimeException(filter);
                }
            }
        } else {
            throw new FilterNotValidRuntimeException(filter);
        }
    }

    public String toParamUrl() {
        if (type.get() instanceof LocalDateTime){
            return fieldName + "=" + operation + DateTimeFormat.parseLocalDateTimeToString((LocalDateTime)fieldValue);
        }else if (type.get() instanceof LocalDate){
            return fieldName + "=" + operation + DateTimeFormat.parseLocalDateToString((LocalDate)fieldValue);
        }
        return fieldName + "=" + operation + fieldValue;
    }
}
