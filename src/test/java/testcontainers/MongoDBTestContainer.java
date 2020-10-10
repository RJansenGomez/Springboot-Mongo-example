package testcontainers;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static testcontainers.TestContainerPorts.MONGO_PORT;

//public class MongoDBTestContainer {
//    /**
//     * MongoDB
//     */
//    @ClassRule
//    public static GenericContainer mongo = new GenericContainer("")
//            .withExposedPorts(MONGO_PORT);
public class MongoDBTestContainer extends GenericContainer<MongoDBTestContainer> {
    private static final String IMAGE_VERSION = "mongo:latest";
    private static MongoDBTestContainer container;
    private static int port;
    private static String containerIp;

    private MongoDBTestContainer() {
        super(IMAGE_VERSION);
        this.waitingFor(Wait.forHttp("/test").forPort(27017));
        this.withStartupTimeout(Duration.of(2, ChronoUnit.MINUTES));
        this.addEnv("MONGO_INITDB_DATABASE", "test-mongoDB");

    }

    public static MongoDBTestContainer getInstance() {
        if (container == null) {
            container = new MongoDBTestContainer();
        }
        return container;
    }

    public static class MongoDBTestContainerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            getInstance();
            container.start();
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                    configurableApplicationContext,
                    "spring.data.mongodb.host=" + containerIp,
                    "spring.data.mongodb.port=" + port);
        }
    }

    @Override
    public void start() {
        super.start();
        port = container.getMappedPort(27017);
        containerIp = container.getContainerIpAddress();
    }

    @Override
    public void stop() {
        //do nothing, JVM handles shut down
    }
}
