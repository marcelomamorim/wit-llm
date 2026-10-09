/*
 * @test
 * @summary Test for SubmissionPublisher.consume(Consumer<? super T> consumer) handling null input
 * @run main SubmissionPublisherWitupTest_P45cfe1015d0e
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Flow;
import java.util.function.Consumer;

public class SubmissionPublisherWitupTest_P45cfe1015d0e {

    public static void main(String[] args) throws Exception {
        testConsumeNullConsumer();
    }

    public static void testConsumeNullConsumer() {
        java.util.concurrent.SubmissionPublisher<Object> publisher = new java.util.concurrent.SubmissionPublisher<>();
        try {
            publisher.consume(null);
            // If no exception thrown, fail the test
            throw new AssertionError("Expected NullPointerException was not thrown");
        } catch (NullPointerException e) {
            // Expected exception, test passes
        }
        // Clean up
        publisher.close();
    }
}
