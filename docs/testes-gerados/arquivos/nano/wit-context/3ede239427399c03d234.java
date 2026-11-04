/*
 * @test
 * @summary Tests for Executors.unconfigurableScheduledExecutorService method.
 * @run main ExecutorsWitupTest
 */

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;

public class ExecutorsWitupTest {

    public static void main(String[] args) {
        testNullExecutor();
        testValidExecutor();
    }

    private static void testNullExecutor() {
        try {
            Executors.unconfigurableScheduledExecutorService(null);
            System.err.println("Failed: Expected NullPointerException for null executor");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown as expected for null executor");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception type: " + t);
        }
    }

    private static void testValidExecutor() {
        ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        try {
            ScheduledExecutorService result = Executors.unconfigurableScheduledExecutorService(scheduledExecutor);
            if (result != null) {
                System.out.println("Passed: Received non-null result for valid executor");
            } else {
                System.err.println("Failed: Result is null for valid executor");
            }
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception for valid executor: " + t);
        } finally {
            scheduledExecutor.shutdown();
        }
    }
}
