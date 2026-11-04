/*
 * @test
 * @summary Test SubmissionPublisher(Executor,int) constructor for null executor and invalid buffer capacity
 * @run main SubmissionPublisherWitupTest
 */

import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SubmissionPublisherWitupTest {
    public static void main(String[] args) {
        testNullExecutor();
        testNonPositiveBufferCapacity();
        testValidConstruction();
    }

    static void testNullExecutor() {
        try {
            new SubmissionPublisher(null, 1);
            throw new Error("Expected NullPointerException for null executor");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testNonPositiveBufferCapacity() {
        Executor exec = Executors.newSingleThreadExecutor();
        try {
            new SubmissionPublisher(exec, 0);
            throw new Error("Expected IllegalArgumentException for non-positive buffer capacity");
        } catch (IllegalArgumentException expected) {
            // expected
        } finally {
            if (exec instanceof java.util.concurrent.ExecutorService) {
                ((java.util.concurrent.ExecutorService) exec).shutdownNow();
            }
        }
    }

    static void testValidConstruction() {
        Executor exec = Executors.newSingleThreadExecutor();
        SubmissionPublisher<Object> pub = null;
        try {
            pub = new SubmissionPublisher<>(exec, 1);
            if (pub == null) {
                throw new Error("SubmissionPublisher constructor returned null");
            }
        } finally {
            if (pub != null) {
                pub.close();
            }
            if (exec instanceof java.util.concurrent.ExecutorService) {
                ((java.util.concurrent.ExecutorService) exec).shutdownNow();
            }
        }
    }
}
