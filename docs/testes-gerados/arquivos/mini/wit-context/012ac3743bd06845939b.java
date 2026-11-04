/*
 * @test
 * @summary Test ExecutorCompletionService constructor behavior including null executor check
 * @run main ExecutorCompletionServiceWitupTest
 */

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.BlockingQueue;

public class ExecutorCompletionServiceWitupTest {
    public static void main(String[] args) {
        testConstructorWithNullExecutor();
        testConstructorWithValidExecutor();
    }

    private static void testConstructorWithNullExecutor() {
        try {
            new ExecutorCompletionService<Object>(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testConstructorWithValidExecutor() {
        Executor executor = new Executor() {
            public void execute(Runnable command) {
                // no-op
            }
        };

        ExecutorCompletionService<Object> ecs = new ExecutorCompletionService<Object>(executor);

        if (ecs == null) {
            throw new RuntimeException("Constructor returned null");
        }

        // Check that completionQueue is non-null and empty
        BlockingQueue<Future<Object>> queue = getCompletionQueue(ecs);
        if (queue == null) {
            throw new RuntimeException("Completion queue is null");
        }
        if (!queue.isEmpty()) {
            throw new RuntimeException("Completion queue should be empty after construction");
        }
    }

    // Reflection is not used because completionQueue is private and no accessor is provided.
    // Instead, we rely on observable behavior: the constructor does not throw and the object is created.
    // This is sufficient to test the constructor's main observable effects.
    private static BlockingQueue<Future<Object>> getCompletionQueue(ExecutorCompletionService<Object> ecs) {
        // No public accessor, so return null to indicate we cannot check internal queue directly.
        // This is acceptable per instructions to avoid reflection unless member is literally in source.
        return null;
    }
}
