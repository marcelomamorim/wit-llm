/*
 * @test
 * @summary Test SubmissionPublisher(Executor,int) constructor basic behavior
 * @run main SubmissionPublisherWitupTest
 */

import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SubmissionPublisherWitupTest {
    public static void main(String[] args) throws Exception {
        Executor executor = Executors.newSingleThreadExecutor();

        // Test with a positive buffer capacity
        SubmissionPublisher<Object> pub1 = new SubmissionPublisher<>(executor, 16);
        if (pub1 == null) throw new Error("SubmissionPublisher instance is null");

        // Test with buffer capacity 1 (minimum positive)
        SubmissionPublisher<Object> pub2 = new SubmissionPublisher<>(executor, 1);
        if (pub2 == null) throw new Error("SubmissionPublisher instance is null");

        // Test with a large buffer capacity
        SubmissionPublisher<Object> pub3 = new SubmissionPublisher<>(executor, 1024);
        if (pub3 == null) throw new Error("SubmissionPublisher instance is null");

        // Test with zero buffer capacity (edge case, allowed or not)
        try {
            SubmissionPublisher<Object> pub4 = new SubmissionPublisher<>(executor, 0);
            if (pub4 == null) throw new Error("SubmissionPublisher instance is null");
        } catch (IllegalArgumentException iae) {
            // Expected if zero capacity is disallowed
        } catch (Exception e) {
            throw new Error("Unexpected exception for zero capacity: " + e);
        }

        // Test with negative buffer capacity (should throw IllegalArgumentException or similar)
        try {
            new SubmissionPublisher<>(executor, -1);
            throw new Error("Expected exception for negative capacity not thrown");
        } catch (IllegalArgumentException iae) {
            // Expected
        } catch (Exception e) {
            throw new Error("Unexpected exception for negative capacity: " + e);
        }

        // Shutdown executor to avoid thread leakage
        if (executor instanceof java.util.concurrent.ExecutorService) {
            ((java.util.concurrent.ExecutorService) executor).shutdown();
        }
    }
}
