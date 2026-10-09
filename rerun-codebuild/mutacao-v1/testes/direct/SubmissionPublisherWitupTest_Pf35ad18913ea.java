/* @test    @summary Test closeExceptionally method of SubmissionPublisher for null and non-null error handling    @run main SubmissionPublisherWitupTest_Pf35ad18913ea */

import java.util.concurrent.SubmissionPublisher;

public class SubmissionPublisherWitupTest_Pf35ad18913ea {

    public static void main(String[] args) {
        testCloseExceptionallyWithNullError();
        testCloseExceptionallyWithValidError();
    }

    static void testCloseExceptionallyWithNullError() {
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

    static void testCloseExceptionallyWithValidError() {
        SubmissionPublisher<Object> publisher = new SubmissionPublisher<>();
        Throwable error = new RuntimeException("Test error");
        publisher.closeExceptionally(error);
        // Since closeExceptionally sets closed to true, verify that the publisher is closed
        // and that no further submissions are possible (not directly accessible, so test indirectly)
        boolean exceptionThrown = false;
        try {
            publisher.submit("test");
        } catch (IllegalStateException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalStateException after closeExceptionally");
        }
    }
}
