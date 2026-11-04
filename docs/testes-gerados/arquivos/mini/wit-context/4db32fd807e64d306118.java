/*
 * @test
 * @summary Test Cleaner.create(ThreadFactory) for null argument and normal creation
 * @run main CleanerWitupTest
 */

import java.lang.ref.Cleaner;
import java.util.concurrent.ThreadFactory;

public class CleanerWitupTest {
    public static void main(String[] args) {
        testCreateWithNullThreadFactory();
        testCreateWithValidThreadFactory();
        System.out.println("CleanerWitupTest passed");
    }

    private static void testCreateWithNullThreadFactory() {
        try {
            Cleaner.create(null);
            throw new AssertionError("Expected NullPointerException for null threadFactory");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testCreateWithValidThreadFactory() {
        ThreadFactory tf = new ThreadFactory() {
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setDaemon(true);
                return t;
            }
        };
        Cleaner cleaner = Cleaner.create(tf);
        if (cleaner == null) {
            throw new AssertionError("Cleaner.create(ThreadFactory) returned null");
        }
    }
}
