package org.rjansen.rest;

import org.rjansen.mongo.repository.MongoTable;

import java.util.List;

public class MongoTableSearchJsonResponse {
    public int currentPage;
    public int totalPages;
    public List<MongoTable> result;
}
