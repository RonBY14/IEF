import com.eventsystem.components.EventBus;
import com.eventsystem.events.Event;
import com.eventsystem.subscriber.Subscriber;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.MethodName.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EventBusTest implements Subscriber {

    private EventBus eventBus;
    private boolean eventReceivedFlag;

    @BeforeAll
    public void beforeAll() {
        eventBus = new EventBus();
        eventReceivedFlag = false;
    }

    @Test
    public void createTopicTest() {
        eventBus.createTopic("mock");
        Assertions.assertTrue(eventBus.getAvailableTopics().contains("mock"));
    }

    @Test
    public void deleteTopicTest() {
        eventBus.createTopic("mock");
        eventBus.deleteTopic("mock");
        Assertions.assertFalse(eventBus.getAvailableTopics().contains("mock"));
    }

    @Test
    public void subscribeTest() {
        eventBus.createTopic("mock");
        Assertions.assertTrue(eventBus.subscribe(this, "mock"));
        Assertions.assertTrue(eventBus.subscribed(this, "mock"));
    }

    @Test
    public void unsubscribeTest() {
        eventBus.createTopic("mock");
        eventBus.subscribe(this, "mock");
        Assertions.assertTrue(eventBus.unsubscribe(this, "mock"));
        Assertions.assertFalse(eventBus.subscribed(this, "mock"));
    }

    @Test
    public synchronized void publishTest() throws InterruptedException {
        eventBus.createTopic("mock");
        eventBus.subscribe(this, "mock");
        eventBus.publish(new Event() {}, "mock");

        while (!eventReceivedFlag) wait(1000);
    }

    @BeforeEach
    public void afterEach() {
        eventBus.deleteAllTopics();
    }

    @Override
    public synchronized void handle(Event event) {
        if (event != null) {
            eventReceivedFlag = true;
            notify();
        }
    }
}
