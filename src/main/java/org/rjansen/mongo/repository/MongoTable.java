package org.rjansen.mongo.repository;

import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document("table_test")
@EqualsAndHashCode
public class MongoTable {
    @Id
    private String id;
    private String field1;
    private Long field2;
    private Float field3;
    private List<String> field4;
    private Double field5;
    private LocalDateTime field6;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public Long getField2() {
        return field2;
    }

    public void setField2(Long field2) {
        this.field2 = field2;
    }

    public Float getField3() {
        return field3;
    }

    public void setField3(Float field3) {
        this.field3 = field3;
    }

    public List<String> getField4() {
        return field4;
    }

    public void setField4(List<String> field4) {
        this.field4 = field4;
    }

    public Double getField5() {
        return field5;
    }

    public void setField5(Double field5) {
        this.field5 = field5;
    }

    public LocalDateTime getField6() {
        return field6;
    }

    public void setField6(LocalDateTime field6) {
        this.field6 = field6;
    }

    @Override
    public String toString() {
        return "MongoTable{" +
                "id='" + id + '\'' +
                ", field1='" + field1 + '\'' +
                ", field2=" + field2 +
                ", field3=" + field3 +
                ", field4=" + field4 +
                ", field5=" + field5 +
                ", field6=" + field6 +
                '}';
    }
}
