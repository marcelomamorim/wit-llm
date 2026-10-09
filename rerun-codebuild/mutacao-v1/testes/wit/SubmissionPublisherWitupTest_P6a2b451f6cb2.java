/* @test    @summary Test for SubmissionPublisher.isSubscribed method handling null subscriber    @run main SubmissionPublisherWitupTest_P6a2b451f6cb2 */

import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.Flow;

public class SubmissionPublisherWitupTest_P6a2b451f6cb2 {

    public static void main(String[] args) {
        testIsSubscribedWithNullSubscriber();
    }

    public static void testIsSubscribedWithNullSubscriber() {
        SubmissionPublisher<Object> publisher = new SubmissionPublisher<>();
        try {
            publisher.isSubscribed(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception, test passes
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception: " + t, t);
        }
    }
}
