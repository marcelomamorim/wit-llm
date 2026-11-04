/*
 * @test
 * @summary Test Cleaner.register(Object, Runnable) for null argument checks and normal behavior
 * @run main CleanerWitupTest
 */

import java.lang.ref.Cleaner;
import java.lang.ref.Cleanable;

public class CleanerWitupTest {
    public static void main(String[] args) {
        Cleaner cleaner = Cleaner.create();

        // Test null obj argument triggers NullPointerException
        try {
            cleaner.register(null, new Runnable() {
                public void run() {}
            });
            throw new Error("Expected NullPointerException for null obj");
        } catch (NullPointerException npe) {
            if (!"obj".equals(npe.getMessage())) {
                throw new Error("NullPointerException message mismatch for null obj");
            }
        }

        // Test null action argument triggers NullPointerException
        try {
            cleaner.register(new Object(), null);
            throw new Error("Expected NullPointerException for null action");
        } catch (NullPointerException npe) {
            if (!"action".equals(npe.getMessage())) {
                throw new Error("NullPointerException message mismatch for null action");
            }
        }

        // Test normal registration returns non-null Cleanable
        Object obj = new Object();
        Runnable action = new Runnable() {
            public void run() {}
        };
        Cleanable cleanable = cleaner.register(obj, action);
        if (cleanable == null) {
            throw new Error("register returned null Cleanable");
        }

        // Test that calling clean() on the returned Cleanable does not throw
        cleanable.clean();
    }
}
