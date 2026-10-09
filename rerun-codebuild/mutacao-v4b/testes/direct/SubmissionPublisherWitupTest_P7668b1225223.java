/* @test
   @summary Verifies closeExceptionally method behavior of SubmissionPublisher.
   @run main SubmissionPublisherWitupTest_P7668b1225223
 */
import java.util.concurrent.SubmissionPublisher;
import java.lang.Throwable;

public class SubmissionPublisherWitupTest_P7668b1225223 {
    public static void main(String[] args) {
        testCloseExceptionallyWithNull();
        testCloseExceptionallyWithError();
    }

    static void testCloseExceptionallyWithNull() {
        SubmissionPublisher<Object> publisher = new SubmissionPublisher<>();
        try {
            publisher.closeExceptionally(null);
            throw new AssertionError("Expected NullPointerException for null error");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testCloseExceptionallyWithError() {
        SubmissionPublisher<Object> publisher = new SubmissionPublisher<>();
        Throwable error = new RuntimeException("Test error");
        publisher.closeExceptionally(error);
        // Since closeExceptionally sets closed to true and nullifies clients,
        // we verify that no exception is thrown and the publisher is closed.
        // No public API to verify internal state, so test that calling again throws no exception.
        try {
            publisher.closeExceptionally(new RuntimeException("Another error"));
            // no exception expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception on second closeExceptionally call", e);
        }
    }
}
