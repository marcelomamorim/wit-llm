/*
 * @test
 * @summary Test for SubmissionPublisher.consume method handling null consumer
 * @run main SubmissionPublisherWitupTest
 */

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class SubmissionPublisherWitupTest {

    public static void main(String[] args) {
        testConsumeWithNullConsumer();
        testConsumeWithValidConsumer();
    }

    public static void testConsumeWithNullConsumer() {
        java.util.concurrent.SubmissionPublisher<String> publisher = new java.util.concurrent.SubmissionPublisher<>();
        try {
            publisher.consume(null);
            System.err.println("Expected NullPointerException not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            System.out.println("NullPointerException correctly thrown for null consumer");
        }
    }

    public static void testConsumeWithValidConsumer() {
        java.util.concurrent.SubmissionPublisher<String> publisher = new java.util.concurrent.SubmissionPublisher<>();
        Consumer<String> consumer = s -> {};
        CompletableFuture<Void> future = publisher.consume(consumer);
        if (future == null) {
            System.err.println("consume() returned null instead of CompletableFuture");
            System.exit(1);
        } else {
            System.out.println("consume() returned a valid CompletableFuture");
        }
        publisher.close();
    }
}
