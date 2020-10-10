package org.rjansen.mongo.repository;

import lombok.Getter;
import org.rjansen.common.repository.FieldFilter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static org.rjansen.mongo.repository.QMongoTable.*;

@Getter
public class MongoSearch {
    private final List<FieldFilter<?>> filters;
    private final Pageable pageable;

    private MongoSearch(final List<FieldFilter<?>> filters, final Pageable pageable) {
        this.filters = filters;
        this.pageable = pageable;
    }

    public static MongoSearch convertParamsToSearch(HttpServletRequest request) {
        List<FieldFilter<?>> filters = new ArrayList<>();

        String id = request.getParameter("id");
        String field1 = request.getParameter("field1");
        List<String> field2 = convertToList(request.getParameterValues("field2"));
        List<String> field3 = convertToList(request.getParameterValues("field3"));
        List<String> field4 = convertToList(request.getParameterValues("field4"));
        List<String> field5 = convertToList(request.getParameterValues("field5"));
        List<String> field6 = convertToList(request.getParameterValues("field6"));

        filters.add(createFilter(ID_FIELD, id, String::new));
        filters.add(createFilter(FIELD1_FIELD, field1, String::new));
        filters.addAll(createFilter(FIELD2_FIELD, field2, () -> 0L));
        filters.addAll(createFilter(FIELD3_FIELD, field3, () -> 0F));
        filters.addAll(createFilter(FIELD4_FIELD, field4, String::new));
        filters.addAll(createFilter(FIELD5_FIELD, field5, () -> 0D));
        filters.addAll(createFilter(FIELD6_FIELD, field6, LocalDateTime::now));

        filters = filters.stream().filter(Objects::nonNull)
                .collect(Collectors.toList());
        return new MongoSearch(filters, getPageable(request));
    }

    private static List<String> convertToList(String[] content) {
        if (content != null) {
            return Arrays.asList(content);
        }
        return new ArrayList<>();
    }

    private static Pageable getPageable(HttpServletRequest request) {
        String currentPage = request.getParameter("currentPage");
        String pageSize = request.getParameter("pageSize");
        if (currentPage == null) {
            currentPage = "0";
        }
        if (pageSize == null) {
            pageSize = "10";
        }
        return PageRequest.of(Integer.parseInt(currentPage), Integer.parseInt(pageSize));
    }

    private static <T> List<FieldFilter<T>> createFilter(String fieldName, List<String> fieldValue, Supplier<T> listType) {
        if (fieldValue != null) {
            return fieldValue.stream()
                    .map(filterValue -> createFilter(fieldName, filterValue, listType))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        }
        return null;
    }

    private static <V> FieldFilter<V> createFilter(String fieldName, String fieldValue, Supplier<V> type) {
        if (fieldValue != null) {
            return FieldFilter.createFilter(fieldValue, fieldName, type);
        }
        return null;
    }
}
