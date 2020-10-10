package org.rjansen.rest;

import org.rjansen.common.repository.FieldFilter;
import org.rjansen.mongo.repository.MongoSearch;
import org.rjansen.mongo.repository.MongoTable;
import org.rjansen.mongo.repository.MongoTableJpaRepository;
import org.rjansen.mongo.repository.MongoTableRepositoryImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static org.rjansen.mongo.repository.QMongoTable.*;

@RestController
@RequestMapping(value = "/test")
public class RestTest {

    private final MongoTableJpaRepository repository;
    private final MongoTableRepositoryImpl repositoryImpl;

    public RestTest(final MongoTableJpaRepository repository,
                    final MongoTableRepositoryImpl repositoryImpl) {
        this.repository = repository;
        this.repositoryImpl = repositoryImpl;
    }

    @PostMapping(value = "/create")
    ResponseEntity<?> test_1(@RequestBody TestJsonRequest request) {
        MongoTable record = request.generateExampleData(1);
        repository.save(record);
        return ResponseEntity.accepted().body("OK");
    }

    @GetMapping
    ResponseEntity<MongoTableSearchJsonResponse> searchTest(HttpServletRequest request) {
        MongoSearch search = MongoSearch.convertParamsToSearch(request);
        generateDBInfo();
        return new ResponseEntity<>(mapToJson(repositoryImpl.search(search)),
                HttpStatus.OK
        );
    }

    private MongoTableSearchJsonResponse mapToJson(Page<MongoTable> search) {
        MongoTableSearchJsonResponse response = new MongoTableSearchJsonResponse();
        response.currentPage = search.getNumber();
        response.totalPages = search.getTotalPages();
        response.result = search.getContent();
        return response;

    }

    private void generateDBInfo() {
        for (int i = 0; i < 30; i++) {
            MongoTable record = new TestJsonRequest().generateExampleData(i);
            repository.save(record);
        }
    }
}
