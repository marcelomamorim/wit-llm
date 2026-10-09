/* @test
   @summary Test the consume method of SubmissionPublisher for null consumer.
   @run main SubmissionPublisherWitupTest_P78159e57311d
 */
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class SubmissionPublisherWitupTest_P78159e57311d {
    public static void main(String[] args) {
        testConsumeWithNullConsumer();
        testConsumeWithValidConsumer();
    }

    static void testConsumeWithNullConsumer() {
        try {
            java.util.concurrent.SubmissionPublisher<String> publisher = new java.util.concurrent.SubmissionPublisher<>();
            publisher.consume(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testConsumeWithValidConsumer() {
        java.util.concurrent.SubmissionPublisher<String> publisher = new java.util.concurrent.SubmissionPublisher<>();
        Consumer<String> consumer = s -> { /* no-op */ };
        CompletionStage<Void> future = publisher.consume(consumer);
        if (!(future instanceof CompletableFuture)) {
            throw new AssertionError("Returned future is not a CompletableFuture");
        }
        CompletableFuture<Void> cf = (CompletableFuture<Void>) future;
        if (cf.isDone()) {
            throw new AssertionError("Future should not be done immediately");
        }
        publisher.close();
        cf.join();
    }
}
