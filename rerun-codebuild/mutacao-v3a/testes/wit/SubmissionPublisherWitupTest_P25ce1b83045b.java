/*
 * @test
 * @summary Test for closeExceptionally(Throwable) method in SubmissionPublisher.
 * @run main SubmissionPublisherWitupTest_P25ce1b83045b
 */

import java.util.concurrent.SubmissionPublisher;

public class SubmissionPublisherWitupTest_P25ce1b83045b {

    public static void main(String[] args) {
        testCloseExceptionallyWithNullError();
        testCloseExceptionallyWithError();
    }

    private static void testCloseExceptionallyWithNullError() {
        SubmissionPublisher<Object> publisher = new SubmissionPublisher<>();
        boolean exceptionThrown = false;
        try {
            publisher.closeExceptionally(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when passing null error");
        }
    }

    private static void testCloseExceptionallyWithError() {
        SubmissionPublisher<Object> publisher = new SubmissionPublisher<>();
        Throwable error = new RuntimeException("Test error");
        publisher.closeExceptionally(error);
        // Verify state after closing
        if (!publisher.closed) {
            throw new AssertionError("Publisher should be closed after closeExceptionally");
        }
    }
}
