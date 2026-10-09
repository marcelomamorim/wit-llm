/* @test    @summary Test subscribe method of SubmissionPublisher handling null subscriber    @run main SubmissionPublisherWitupTest_P190af60bf12f */

import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.Flow;

public class SubmissionPublisherWitupTest_P190af60bf12f {

    public static void main(String[] args) {
        testSubscribeWithNullSubscriber();
    }

    public static void testSubscribeWithNullSubscriber() {
        SubmissionPublisher<Object> publisher = new SubmissionPublisher<>();
        try {
            publisher.subscribe(null);
            // If no exception thrown, test fails
            throw new AssertionError("Expected NullPointerException was not thrown.");
        } catch (NullPointerException e) {
            // Test passes, exception correctly thrown
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t, t);
        } finally {
            // Cleanup
            // No explicit cleanup needed
        }
    }
}
