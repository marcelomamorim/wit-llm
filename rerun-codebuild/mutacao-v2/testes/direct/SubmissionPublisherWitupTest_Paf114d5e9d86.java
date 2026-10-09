/*
 * @test
 * @summary Test SubmissionPublisher.consume(Consumer) method behavior
 * @run main SubmissionPublisherWitupTest_Paf114d5e9d86
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class SubmissionPublisherWitupTest_Paf114d5e9d86 {
    public static void main(String[] args) throws Exception {
        testConsumeWithNullConsumer();
        testConsumeWithValidConsumer();
    }

    static void testConsumeWithNullConsumer() {
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();
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
        final AtomicBoolean consumed = new AtomicBoolean(false);
        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                if ("test".equals(s)) {
                    consumed.set(true);
                }
            }
        };

        CompletableFuture<Void> future = publisher.consume(consumer);

        // Submit an item and close publisher to complete
        publisher.submit("test");
        publisher.close();

        // Wait for completion
        future.get();

        if (!consumed.get()) {
            throw new AssertionError("Consumer did not receive submitted item");
        }
    }
}
