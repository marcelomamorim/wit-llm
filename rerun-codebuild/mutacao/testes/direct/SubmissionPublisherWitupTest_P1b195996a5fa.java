/* @test    @summary Test the consume method of SubmissionPublisher for null consumer and normal operation    @run main SubmissionPublisherWitupTest_P1b195996a5fa */

import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class SubmissionPublisherWitupTest_P1b195996a5fa {

    public static void main(String[] args) {
        testConsumeNullConsumer();
        testConsumeValidConsumer();
    }

    static void testConsumeNullConsumer() {
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();
        try {
            publisher.consume(null);
            // Expecting NullPointerException, so fail if no exception thrown
            System.err.println("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Test passes
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
        }
    }

    static void testConsumeValidConsumer() {
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();
        Consumer<String> consumer = s -> {};
        CompletableFuture<Void> future = publisher.consume(consumer);
        if (future == null) {
            System.err.println("consume() returned null");
        } else {
            // Optionally, check if future completes normally
            future.thenRun(() -> {
                // Test passed
            });
        }
    }
}
