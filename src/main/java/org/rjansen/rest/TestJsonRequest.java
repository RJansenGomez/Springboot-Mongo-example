package org.rjansen.rest;

import org.rjansen.mongo.repository.MongoTable;

import java.time.LocalDateTime;
import java.util.Arrays;

public class TestJsonRequest {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MongoTable generateExampleData(int i) {
        MongoTable table = new MongoTable();
        table.setId("id" + i + LocalDateTime.now().toString());
        table.setField1(i + "Field1");
        table.setField2(1232L + i);
        table.setField3(2423432.43F + i);
        table.setField4(Arrays.asList(i + "list", i + "list2"));
        table.setField5(2342.56 + i);
        table.setField6(LocalDateTime.now().plusDays(i));
        return table;
    }
}
