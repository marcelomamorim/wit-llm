/*
 * @test
 * @summary Test SubmissionPublisher.consume(Consumer) for null argument and normal usage
 * @run main SubmissionPublisherWitupTest
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.function.Consumer;

public class SubmissionPublisherWitupTest {

    public static void main(String[] args) throws Exception {
        testConsumeWithNullConsumer();
        testConsumeWithValidConsumer();
    }

    static void testConsumeWithNullConsumer() {
        SubmissionPublisher<Object> publisher = new SubmissionPublisher<>();
        try {
            publisher.consume(null);
            throw new AssertionError("Expected NullPointerException for null consumer");
        } catch (NullPointerException expected) {
            // expected
        } finally {
            publisher.close();
        }
    }

    static void testConsumeWithValidConsumer() throws Exception {
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();
        final StringBuilder received = new StringBuilder();

        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String s) {
                received.append(s);
            }
        };

        CompletableFuture<Void> future = publisher.consume(consumer);

        // Submit an item and close publisher to complete
        publisher.submit("test");
        publisher.close();

        // Wait for completion
        future.get();

        if (!"test".equals(received.toString())) {
            throw new AssertionError("Consumer did not receive submitted item");
        }
    }
}
