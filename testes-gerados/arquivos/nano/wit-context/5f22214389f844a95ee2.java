/* @test    @summary Test for setRejectedExecutionHandler method in ThreadPoolExecutor    @run main ThreadPoolExecutorWitupTest */

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.RejectedExecutionHandler;

public class ThreadPoolExecutorWitupTest {

    public static void main(String[] args) {
        testSetRejectedExecutionHandler_NullHandler();
        testSetRejectedExecutionHandler_ValidHandler();
    }

    private static void testSetRejectedExecutionHandler_NullHandler() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0L, null, null);
        try {
            executor.setRejectedExecutionHandler(null);
            // Expecting NullPointerException
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testSetRejectedExecutionHandler_ValidHandler() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0L, null, null);
        RejectedExecutionHandler handler = new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                // no-op
            }
        };
        try {
            executor.setRejectedExecutionHandler(handler);
            System.out.println("PASS: Valid handler set without exception");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
