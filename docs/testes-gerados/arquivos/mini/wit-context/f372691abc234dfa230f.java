/*
 * @test
 * @summary Test FutureTask(Runnable, V) constructor for null Runnable input
 * @run main FutureTaskWitupTest
 */

import java.util.concurrent.FutureTask;
import java.util.concurrent.Executors;

public class FutureTaskWitupTest {
    public static void main(String[] args) {
        testConstructorWithNullRunnable();
        testConstructorWithValidRunnable();
    }

    private static void testConstructorWithNullRunnable() {
        try {
            new FutureTask<Object>(null, null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception for null runnable
        }
    }

    private static void testConstructorWithValidRunnable() {
        Runnable r = new Runnable() {
            public void run() {}
        };
        Object result = new Object();
        FutureTask<Object> ft = new FutureTask<Object>(r, result);
        if (ft == null) {
            throw new Error("FutureTask constructor returned null");
        }
    }
}
