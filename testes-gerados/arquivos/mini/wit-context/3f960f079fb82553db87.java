/*
 * @test
 * @summary Test FutureTask constructor with Callable argument for null and non-null cases
 * @run main FutureTaskWitupTest
 */

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class FutureTaskWitupTest {
    public static void main(String[] args) {
        testConstructorWithNullCallable();
        testConstructorWithValidCallable();
    }

    private static void testConstructorWithNullCallable() {
        try {
            new FutureTask<Object>((Callable<Object>) null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testConstructorWithValidCallable() {
        Callable<String> callable = new Callable<String>() {
            public String call() {
                return "test";
            }
        };
        FutureTask<String> ft = new FutureTask<>(callable);
        if (ft == null) {
            throw new Error("FutureTask constructor returned null");
        }
    }
}
