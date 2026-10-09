/*
 * @test
 * @summary Test for SubmissionPublisher.closeExceptionally(Throwable)
 * @run main SubmissionPublisherWitupTest_Padb09731b35a
 */

import java.util.concurrent.SubmissionPublisher;

public class SubmissionPublisherWitupTest_Padb09731b35a {

    public static void main(String[] args) {
        testCloseExceptionallyWithNullError();
        testCloseExceptionallyWithValidError();
    }

    private static void testCloseExceptionallyWithNullError() {
        SubmissionPublisher<Object> publisher = new SubmissionPublisher<>();
        try {
            publisher.closeExceptionally(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testCloseExceptionallyWithValidError() {
        SubmissionPublisher<Object> publisher = new SubmissionPublisher<>();
        Throwable error = new RuntimeException("Test error");
        try {
            publisher.closeExceptionally(error);
            System.out.println("PASS: closeExceptionally with valid error executed");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
