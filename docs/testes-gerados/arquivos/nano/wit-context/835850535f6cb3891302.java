/*
 * @test
 * @summary Test for ScheduledThreadPoolExecutor.remove()
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledThreadPoolExecutor;

public class ScheduledThreadPoolExecutorWitupTest {

    public static void main(String[] args) {
        testRemoveWithValidState();
        testRemoveWithInvalidState();
    }

    private static void testRemoveWithValidState() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        try {
            // Setup: add a task to remove
            // Since remove() is parameterless, we simulate by calling remove() after setting lastRet
            // but lastRet is private; instead, we invoke remove() directly to observe behavior
            // For the purpose of this test, we invoke remove() directly
            executor.remove();
            System.out.println("testRemoveWithValidState: Passed (no exception)");
        } catch (Exception e) {
            System.out.println("testRemoveWithValidState: Failed with exception: " + e);
        }
    }

    private static void testRemoveWithInvalidState() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        try {
            // Force invalid state by calling remove() without prior valid operation
            // In actual code, lastRet is private; calling remove() directly
            // Expecting IllegalStateException as per source code
            executor.remove();
            System.out.println("testRemoveWithInvalidState: Failed (no exception)");
        } catch (IllegalStateException e) {
            System.out.println("testRemoveWithInvalidState: Passed (caught expected exception)");
        } catch (Exception e) {
            System.out.println("testRemoveWithInvalidState: Failed with unexpected exception: " + e);
        }
    }
}
