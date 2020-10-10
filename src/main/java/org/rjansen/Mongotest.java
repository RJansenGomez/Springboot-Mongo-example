package org.rjansen;

import org.rjansen.mongo.repository.MongoTable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.time.LocalDateTime;
import java.util.Collections;

@SpringBootApplication
public class Mongotest {
    public static void main(String[] args) {
        SpringApplication.run(Mongotest.class);
    }

    @EventListener
    void onApplicationReadyEvent(ApplicationReadyEvent event) {
        MongoTable table = generateExampleData();
    }

    private MongoTable generateExampleData() {
        MongoTable table = new MongoTable();
        table.setId("id1" + LocalDateTime.now().toString());
        table.setField1("Field1");
        table.setField2(1232L);
        table.setField3(2423432.43F);
        table.setField4(Collections.singletonList("list"));
        table.setField5(2342.56);
        return table;
    }
}
