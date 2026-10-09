/* @test    @summary Test for SubmissionPublisher.consume method handling null consumer    @run main SubmissionPublisherWitupTest_P8fbc1c40a57a */

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class SubmissionPublisherWitupTest_P8fbc1c40a57a {

    public static void main(String[] args) {
        testConsumeWithNullConsumer();
    }

    public static void testConsumeWithNullConsumer() {
        java.util.concurrent.SubmissionPublisher<Object> publisher = new java.util.concurrent.SubmissionPublisher<>();
        try {
            publisher.consume(null);
            throw new AssertionError("Expected NullPointerException was not thrown");
        } catch (NullPointerException e) {
            // Expected exception, test passes
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t, t);
        } finally {
            publisher.close();
        }
    }
}
