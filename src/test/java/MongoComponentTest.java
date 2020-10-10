import com.querydsl.core.types.Predicate;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.rjansen.Mongotest;
import org.rjansen.common.repository.FieldFilter;
import org.rjansen.common.repository.PredicateLogicalBuilder;
import org.rjansen.common.repository.PredicateStringBuilder;
import org.rjansen.common.utils.DateTimeFormat;
import org.rjansen.mongo.repository.MongoTable;
import org.rjansen.mongo.repository.MongoTableJpaRepository;
import org.rjansen.mongo.repository.QMongoTable;
import org.rjansen.rest.MongoTableSearchJsonResponse;
import org.rjansen.rest.TestJsonRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import testcontainers.MongoDBTestContainer;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Mongotest.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(initializers = MongoDBTestContainer.MongoDBTestContainerInitializer.class)
class MongoComponentTest {
    private TestRestTemplate restTemplate = new TestRestTemplate();
    private HttpHeaders headers = new HttpHeaders();
    private final int port;
    private final MongoTableJpaRepository jpaRepository;
    private final PredicateStringBuilder predicateStringBuilder;
    private final PredicateLogicalBuilder predicateLogicalBuilder;

    @Autowired
    MongoComponentTest(@LocalServerPort int port,
                       final MongoTableJpaRepository jpaRepository,
                       final PredicateStringBuilder predicateStringBuilder,
                       final PredicateLogicalBuilder predicateLogicalBuilder) {
        this.port = port;
        this.jpaRepository = jpaRepository;
        this.predicateStringBuilder = predicateStringBuilder;
        this.predicateLogicalBuilder = predicateLogicalBuilder;
    }

    @Before
    void setup() {
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    @DisplayName("Example Test ok")
    void test_ok() {
        String url = "http://localhost:" + port + "/test/create";
        TestJsonRequest request = new TestJsonRequest();
        request.setId("My Own Id");
        HttpEntity<TestJsonRequest> entity = new HttpEntity<>(request, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST, entity, String.class);

        //Assertions
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals("OK", response.getBody());
    }


    @Test
    @DisplayName("Example Test ok")
    void test_search_ok() {
        String url = "http://localhost:" + port + "/test";

        ResponseEntity<MongoTableSearchJsonResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET, null, MongoTableSearchJsonResponse.class);

        //Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        validateResult(Collections.EMPTY_LIST, response.getBody(), 0, 10);
    }
    @Test
    @DisplayName("Example Test ok")
    void test_search_ok_id_filter() {
        String url = "http://localhost:" + port + "/test";
        FieldFilter<String> idFilter = FieldFilter.createFilter("lkid0", "id", String::new);
        url = url.concat("?").concat(idFilter.toParamUrl());
        ResponseEntity<MongoTableSearchJsonResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET, null, MongoTableSearchJsonResponse.class);

        //Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        validateResult(Collections.singletonList(idFilter), response.getBody(), 0, 1);
    }
    @Test
    @DisplayName("Example Test ok")
    void test_search_ok_field1_filter() {
        String url = "http://localhost:" + port + "/test";
        FieldFilter<String> idFilter = FieldFilter.createFilter("lk0f", "field1", String::new);
        url = url.concat("?").concat(idFilter.toParamUrl());
        ResponseEntity<MongoTableSearchJsonResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET, null, MongoTableSearchJsonResponse.class);

        //Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        validateResult(Collections.singletonList(idFilter), response.getBody(), 0, 3);
    }

    @Test
    @DisplayName("Example Test ok")
    void test_search_ok_number_filter() {
        String url = "http://localhost:" + port + "/test";
        FieldFilter<Float> idFilter = FieldFilter.createFilter(">=2423430", "field3", () -> 0F);
        url = url.concat("?").concat(idFilter.toParamUrl());
        ResponseEntity<MongoTableSearchJsonResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET, null, MongoTableSearchJsonResponse.class);

        //Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        validateResult(Collections.singletonList(idFilter), response.getBody(), 0, 10);
    }

    @Test
    @DisplayName("Example Test ok")
    void test_search_ok_number_page2_filter() {
        String url = "http://localhost:" + port + "/test";
        FieldFilter<Float> idFilter = FieldFilter.createFilter(">=2423430", "field3", () -> 0F);
        url = url.concat("?").concat(idFilter.toParamUrl());
        url = url.concat("&").concat("currentPage=2");
        ResponseEntity<MongoTableSearchJsonResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET, null, MongoTableSearchJsonResponse.class);

        //Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        validateResult(Collections.singletonList(idFilter), response.getBody(), 2, 10);
    }

    @Test
    @DisplayName("Example Test ok")
    void test_search_ok_date_filter() {
        String url = "http://localhost:" + port + "/test";
        String time = DateTimeFormat.parseLocalDateTimeToString(LocalDateTime.now().minusDays(1));
        FieldFilter<LocalDateTime> dateFilter = FieldFilter.createFilter(">=" + time, "field6", LocalDateTime::now);
        FieldFilter<Float> field3Filter = FieldFilter.createFilter(">=2423430", "field3", () -> 0F);
        FieldFilter<String> field1Filter = FieldFilter.createFilter("lk0f", "field1", String::new);
        url = url.concat("?").concat(field1Filter.toParamUrl());
        url = url.concat("&").concat(dateFilter.toParamUrl());
        url = url.concat("&").concat(field3Filter.toParamUrl());
        ResponseEntity<MongoTableSearchJsonResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET, null, MongoTableSearchJsonResponse.class);

        //Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        validateResult(Arrays.asList(dateFilter,field3Filter,field1Filter), response.getBody(), 0, 3);
    }
    @Test
    @DisplayName("Example Test ok")
    void test_search_ok_mix_filter() {
        String url = "http://localhost:" + port + "/test";
        String time = DateTimeFormat.parseLocalDateTimeToString(LocalDateTime.now().minusDays(1));
        FieldFilter<LocalDateTime> idFilter = FieldFilter.createFilter(">=" + time, "field6", LocalDateTime::now);
        url = url.concat("?").concat(idFilter.toParamUrl());
//        url = url.concat("&").concat("currentPage=2");
        ResponseEntity<MongoTableSearchJsonResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET, null, MongoTableSearchJsonResponse.class);

        //Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        validateResult(Collections.singletonList(idFilter), response.getBody(), 0, 10);
    }

    private void validateResult(
            List<FieldFilter<?>> filters,
            MongoTableSearchJsonResponse body,
            int expectedPage,
            int expectedSize
    ) {
        assertNotNull(body);
        Page<MongoTable> result;
        QMongoTable query = QMongoTable.testTable;
        Pageable page = PageRequest.of(expectedPage, expectedSize);
        Predicate predicate = query.buildPredicates(filters,
                predicateStringBuilder,
                predicateLogicalBuilder
        );
        if (predicate != null) {
            result = jpaRepository.findAll(predicate, page);
        } else {
            result = jpaRepository.findAll(page);
        }
        assertEquals(expectedPage, body.currentPage);
        assertEquals(expectedSize, result.getContent().size());
        result.getContent().forEach(expectedRecord -> {
            if (!body.result.contains(expectedRecord)) {
                fail("Not found: " + expectedRecord);
            }
        });

    }
}

