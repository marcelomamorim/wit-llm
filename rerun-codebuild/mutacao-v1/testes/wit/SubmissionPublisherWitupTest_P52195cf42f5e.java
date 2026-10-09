/* @test    @summary Test subscribe method of SubmissionPublisher for null argument handling    @run main SubmissionPublisherWitupTest_P52195cf42f5e */

import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.Flow.Subscriber;

public class SubmissionPublisherWitupTest_P52195cf42f5e {

    public static void main(String[] args) {
        testSubscribeWithNull();
    }

    public static void testSubscribeWithNull() {
        SubmissionPublisher<Object> publisher = new SubmissionPublisher<>();
        try {
            publisher.subscribe(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception, test passes
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t, t);
        }
    }
}
