/*
 * @test
 * @summary Test SubmissionPublisher.closeExceptionally with various inputs
 * @run main SubmissionPublisherWitupTest_P25ce1b83045b
 */

import java.util.concurrent.SubmissionPublisher;

public class SubmissionPublisherWitupTest_P25ce1b83045b {

    public static void main(String[] args) {
        testCloseExceptionallyWithNull();
        testCloseExceptionallyWithError();
    }

    static void testCloseExceptionallyWithNull() {
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();
        try {
            publisher.closeExceptionally(null);
            System.out.println("Failed: Expected NullPointerException for null error");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown as expected")
        } catch (Throwable t) {
            System.out.println("Failed: Unexpected exception type: " + t);
        }
    }

    static void testCloseExceptionallyWithError() {
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();
        Throwable error = new RuntimeException("test error");
        publisher.closeExceptionally(error);
        // No direct observable effect, but ensure that subsequent calls do not throw
        try {
            publisher.closeExceptionally(error);
            System.out.println("Passed: closeExceptionally called again without exception")
        } catch (Throwable t) {
            System.out.println("Failed: Exception on second call: " + t);
        }
    }
}
