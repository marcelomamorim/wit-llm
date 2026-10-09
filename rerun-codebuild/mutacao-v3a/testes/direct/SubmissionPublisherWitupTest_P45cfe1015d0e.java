/* @test    @summary Test the consume method of SubmissionPublisher for correct behavior and exception handling    @run main SubmissionPublisherWitupTest_P45cfe1015d0e */

import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class SubmissionPublisherWitupTest_P45cfe1015d0e {

    public static void main(String[] args) {
        testConsumeWithValidConsumer();
        testConsumeWithNullConsumer();
    }

    private static void testConsumeWithValidConsumer() {
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();
        Consumer<String> consumer = s -> {};
        CompletableFuture<Void> future = publisher.consume(consumer);
        if (future == null) {
            throw new AssertionError("consume() should not return null for valid consumer");
        }
        if (!future.isDone()) {
            // Complete the future to simulate completion
            future.complete(null);
        }
        System.out.println("testConsumeWithValidConsumer passed.");
    }

    private static void testConsumeWithNullConsumer() {
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();
        boolean exceptionThrown = false;
        try {
            publisher.consume(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("consume(null) should throw NullPointerException");
        }
        System.out.println("testConsumeWithNullConsumer passed.");
    }
}
