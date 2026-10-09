/* @test    @summary Test SubmissionPublisher.consume method for null consumer and normal operation    @run main SubmissionPublisherWitupTest_P8fbc1c40a57a */

import java.util.concurrent.CompletionStage;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class SubmissionPublisherWitupTest_P8fbc1c40a57a {

    public static void main(String[] args) {
        testConsumeNullConsumer();
        testConsumeWithValidConsumer();
    }

    static void testConsumeNullConsumer() {
        try {
            java.util.concurrent.SubmissionPublisher<String> publisher = new java.util.concurrent.SubmissionPublisher<>();
            publisher.consume(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception: " + t);
        }
    }

    static void testConsumeWithValidConsumer() {
        java.util.concurrent.SubmissionPublisher<String> publisher = new java.util.concurrent.SubmissionPublisher<>();
        Consumer<String> consumer = s -> System.out.println("Consumed: " + s);
        CompletionStage<Void> future = publisher.consume(consumer);
        if (future == null) {
            throw new AssertionError("consume() returned null");
        }
        if (!(future instanceof CompletableFuture)) {
            throw new AssertionError("Returned future is not a CompletableFuture");
        }
        CompletableFuture<Void> cf = (CompletableFuture<Void>) future;
        if (cf.isDone()) {
            throw new AssertionError("Future should not be done immediately");
        }
        publisher.close();
        try {
            cf.get();
        } catch (Exception e) {
            throw new AssertionError("Future did not complete normally", e);
        }
    }
}
