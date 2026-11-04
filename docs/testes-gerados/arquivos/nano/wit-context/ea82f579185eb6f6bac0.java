/*
 * @test
 * @summary Tests for SubmissionPublisher constructor with Executor and maxBufferCapacity
 * @run main SubmissionPublisherWitupTest
 */

import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SubmissionPublisherWitupTest {

    public static void main(String[] args) {
        testConstructorWithNullExecutor();
        testConstructorWithInvalidBufferCapacity();
    }

    private static void testConstructorWithNullExecutor() {
        try {
            new SubmissionPublisher(null, 10);
            System.out.println("Failed: Expected NullPointerException for null executor");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("Failed: Unexpected exception: " + t);
        }
    }

    private static void testConstructorWithInvalidBufferCapacity() {
        Executor executor = Executors.newSingleThreadExecutor();
        try {
            new SubmissionPublisher(executor, 0);
            System.out.println("Failed: Expected IllegalArgumentException for buffer capacity 0");
        } catch (IllegalArgumentException e) {
            System.out.println("Passed: IllegalArgumentException thrown as expected");
        } catch (Throwable t) {
            System.out.println("Failed: Unexpected exception: " + t);
        } finally {
            executor.shutdown();
        }
    }
}
