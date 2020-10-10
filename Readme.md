# Springboot MongoDB example
In this repository you will see:
 - How can be used testcontainers to execute a MongoDB for test cases.
 - Some custom filters to make more easy the dynamic queries that might come from an endpoint.
 - The dynamic queries are built with custom filters and QueryDsl libraries to integrate it with MongoJpa.
 
#### Hints of where to find the honey
  - Query stuff take a look on the [QMongoTable](https://github.com/RJansenGomez/Springboot-Mongo-example/blob/main/src/main/java/org/rjansen/mongo/repository/QMongoTable.java) and [RepoImpl](https://github.com/RJansenGomez/Springboot-Mongo-example/blob/main/src/main/java/org/rjansen/mongo/repository/MongoTableRepositoryImpl.java)
  - For the Component/Integration/Functional tests take a look on the [MongoComponentTest](https://github.com/RJansenGomez/Springboot-Mongo-example/blob/main/src/test/java/MongoComponentTest.java)
  - Custom url query filters here [QueryFilters](https://github.com/RJansenGomez/Springboot-Mongo-example/tree/main/src/main/java/org/rjansen/common/repository)
## Stack used for the example
 - Springboot 2.2.5
 - MongoDB JPA
 - QueryDsl
 - MongoDB
 - Docker
 - Gradle
 - Lombok
 
## Some relevant dependencies

```
queryDslVersion = '4.3.1'
implementation 'org.springframework.boot:spring-boot-starter-data-mongodb'
compile("com.querydsl:querydsl-mongodb:${queryDslVersion}",
            "com.querydsl:querydsl-apt:${queryDslVersion}",
            "com.querydsl:querydsl-sql:${queryDslVersion}")
testCompile "org.testcontainers:testcontainers:1.14.1"
```

 ## License
 [![License](http://img.shields.io/:license-mit-blue.svg?style=flat-square)](http://badges.mit-license.org)
 - **[MIT license](http://opensource.org/licenses/mit-license.php)**