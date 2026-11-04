/* @test    @summary Basic tests for SubmissionPublisher constructor    @run main SubmissionPublisherWitupTest */

import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SubmissionPublisherWitupTest {

    public static void main(String[] args) {
        testDefaultConstructor();
        testConstructorWithNullExecutor();
        testConstructorWithInvalidBufferCapacity();
    }

    static void testDefaultConstructor() {
        try {
            SubmissionPublisher<?> publisher = new SubmissionPublisher<>();
            if (publisher != null) {
                System.out.println("testDefaultConstructor: PASSED");
            } else {
                System.out.println("testDefaultConstructor: FAILED - null returned");
            }
        } catch (Throwable t) {
            System.out.println("testDefaultConstructor: FAILED with exception: " + t);
        }
    }

    static void testConstructorWithNullExecutor() {
        try {
            SubmissionPublisher<?> publisher = new SubmissionPublisher<>(null, 16, null);
            System.out.println("testConstructorWithNullExecutor: FAILED - no exception thrown");
        } catch (NullPointerException e) {
            System.out.println("testConstructorWithNullExecutor: PASSED");
        } catch (Throwable t) {
            System.out.println("testConstructorWithNullExecutor: FAILED with unexpected exception: " + t);
        }
    }

    static void testConstructorWithInvalidBufferCapacity() {
        Executor executor = Executors.newSingleThreadExecutor();
        try {
            SubmissionPublisher<?> publisher = new SubmissionPublisher<>(executor, 0, null);
            System.out.println("testConstructorWithInvalidBufferCapacity: FAILED - no exception thrown");
        } catch (IllegalArgumentException e) {
            System.out.println("testConstructorWithInvalidBufferCapacity: PASSED");
        } catch (Throwable t) {
            System.out.println("testConstructorWithInvalidBufferCapacity: FAILED with unexpected exception: " + t);
        } finally {
            // No need to shutdown executor here as it was not used
        }
    }
}
