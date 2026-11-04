/*
 * @test
 * @summary Test for Cleaner.create(ThreadFactory) method, including null argument handling.
 * @run main CleanerWitupTest
 */

import java.lang.ref.Cleaner;
import java.util.concurrent.ThreadFactory;

public class CleanerWitupTest {

    public static void main(String[] args) {
        testCreateWithValidThreadFactory();
        testCreateWithNullThreadFactory();
    }

    static void testCreateWithValidThreadFactory() {
        ThreadFactory factory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r);
            }
        };
        Cleaner cleaner = Cleaner.create(factory);
        if (cleaner == null) {
            throw new AssertionError("Cleaner should not be null with valid ThreadFactory");
        }
    }

    static void testCreateWithNullThreadFactory() {
        try {
            Cleaner.create(null);
            throw new AssertionError("Expected NullPointerException when threadFactory is null");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
