/*
 * @test
 * @summary WITUP-derived tests for ServiceLoader.load(Class)
 * @run main ServiceLoaderWitupTest
 */

import java.util.ServiceLoader;

public class ServiceLoaderWitupTest {

    public static void main(String[] args) {
        testNullServiceThrowsNPE();
        testLoadReturnsNewLoader();
        System.out.println("PASS");
    }

    static void testNullServiceThrowsNPE() {
        boolean thrown = false;
        try {
            ServiceLoader.load((Class<Runnable>) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected NullPointerException for null service");
        }
    }

    static void testLoadReturnsNewLoader() {
        ServiceLoader<Runnable> a = ServiceLoader.load(Runnable.class);
        ServiceLoader<Runnable> b = ServiceLoader.load(Runnable.class);
        if (a == null || b == null) {
            throw new RuntimeException("load must not return null");
        }
        if (a == b) {
            throw new RuntimeException("each load invocation must return a new ServiceLoader");
        }
        if (a.iterator() == null) {
            throw new RuntimeException("iterator must not be null");
        }
    }
}
