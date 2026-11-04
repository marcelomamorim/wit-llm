/* @test    @summary Test for SubmissionPublisher.onError(Throwable ex) method handling null argument    @run main SubmissionPublisherWitupTest */

import java.util.concurrent.SubmissionPublisher;

public class SubmissionPublisherWitupTest {

    public static void main(String[] args) {
        testOnErrorWithNull();
    }

    public static void testOnErrorWithNull() {
        SubmissionPublisher<Object> publisher = new SubmissionPublisher<>();
        try {
            publisher.onError(null);
            System.out.println("PASS: onError(null) did not throw");
        } catch (NullPointerException npe) {
            System.out.println("FAIL: onError(null) threw NullPointerException");
        } catch (Throwable t) {
            System.out.println("FAIL: onError(null) threw unexpected exception: " + t);
        }
    }
}
