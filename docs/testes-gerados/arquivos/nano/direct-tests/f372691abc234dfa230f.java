/*
 * @test
 * @summary Test constructor FutureTask(Runnable, Object) for correct initialization
 * @run main FutureTaskWitupTest
 */

import java.util.concurrent.FutureTask;
import java.util.concurrent.Callable;

public class FutureTaskWitupTest {

    public static void main(String[] args) {
        testConstructorWithRunnableAndResult();
        testConstructorWithNullRunnable();
        testConstructorWithNullResult();
    }

    static void testConstructorWithRunnableAndResult() {
        Runnable r = new Runnable() {
            public void run() {
                // do nothing
            }
        };
        String result = "testResult";
        FutureTask<String> ft = new FutureTask<>(r, result);
        assert ft != null : "FutureTask instance should not be null";
        // Since no public getter for callable or state, test that get() returns the result after run
        Thread t = new Thread(ft);
        t.start();
        try {
            String res = ft.get();
            assert res.equals(result) : "Expected result to be 'testResult'";
        } catch (Exception e) {
            assert false : "Exception during get(): " + e;
        }
    }

    static void testConstructorWithNullRunnable() {
        Runnable r = null;
        String result = "nullRunnable";
        FutureTask<String> ft = new FutureTask<>(r, result);
        assert ft != null : "FutureTask instance should not be null";
        Thread t = new Thread(ft);
        t.start();
        try {
            String res = ft.get();
            assert res.equals(result) : "Expected result to be 'nullRunnable'";
        } catch (Exception e) {
            assert false : "Exception during get(): " + e;
        }
    }

    static void testConstructorWithNullResult() {
        Runnable r = new Runnable() {
            public void run() {
                // do nothing
            }
        };
        String result = null;
        FutureTask<String> ft = new FutureTask<>(r, result);
        assert ft != null : "FutureTask instance should not be null";
        Thread t = new Thread(ft);
        t.start();
        try {
            String res = ft.get();
            assert res == null : "Expected result to be null";
        } catch (Exception e) {
            assert false : "Exception during get(): " + e;
        }
    }
}
