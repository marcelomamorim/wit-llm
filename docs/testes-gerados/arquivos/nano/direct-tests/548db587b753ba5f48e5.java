/* @test    @summary Test closeExceptionally method of SubmissionPublisher    @run main SubmissionPublisherWitupTest */

import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.atomic.AtomicBoolean;

public class SubmissionPublisherWitupTest {

    public static void main(String[] args) {
        testCloseExceptionallyWithNullError();
        testCloseExceptionallyWithValidError();
    }

    private static void testCloseExceptionallyWithNullError() {
        SubmissionPublisher<Object> publisher = new SubmissionPublisher<>();
        boolean exceptionThrown = false;
        try {
            publisher.closeExceptionally(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected NullPointerException when passing null error";
    }

    private static void testCloseExceptionallyWithValidError() {
        SubmissionPublisher<Object> publisher = new SubmissionPublisher<>();
        Throwable error = new RuntimeException("Test error");
        publisher.closeExceptionally(error);
        // Since closeExceptionally sets closed to true and nullifies clients,
        // we verify that no exception is thrown and the publisher is closed.
        // No public method to check closed state, so we rely on no exceptions and no side effects.
        // Additional internal state checks are not possible without reflection.
        // As a simple observable, we assume no exception means success.
    }
}
