/*
 * @test
 * @summary Test Cleaner.register method behavior
 * @run main CleanerWitupTest
 */

import java.lang.ref.Cleaner;
import java.lang.ref.Cleanable;

public class CleanerWitupTest {
    public static void main(String[] args) {
        Cleaner cleaner = Cleaner.create();

        // Test normal registration
        Object obj = new Object();
        Runnable action = new Runnable() {
            public void run() {
                // no-op
            }
        };
        Cleanable cleanable = cleaner.register(obj, action);
        if (cleanable == null) {
            throw new AssertionError("register returned null");
        }

        // Test that passing null obj throws NullPointerException
        try {
            cleaner.register(null, action);
            throw new AssertionError("Expected NullPointerException for null obj");
        } catch (NullPointerException npe) {
            // expected
        }

        // Test that passing null action throws NullPointerException
        try {
            cleaner.register(obj, null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException npe) {
            // expected
        }
    }
}
