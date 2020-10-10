package testcontainers;

import org.springframework.stereotype.Component;

public class TestContainerProvider {
    private static MongoDBTestContainer mongoDbContainer;

    public TestContainerProvider() {
        mongoDbContainer = MongoDBTestContainer.getInstance();
        mongoDbContainer.start();
    }
}
