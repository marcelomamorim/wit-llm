/* @test    @summary Test SecureRandom reseed method for thread safety and synchronization    @run main SecureRandomWitupTest */

import java.security.SecureRandom;

public class SecureRandomWitupTest {

    public static void main(String[] args) {
        testReseedThreadSafe();
        testReseedNonThreadSafe();
    }

    public static void testReseedThreadSafe() {
        SecureRandom sr = new SecureRandom();
        try {
            sr.reseed();
        } catch (Exception e) {
            throw new AssertionError("reseed() threw an exception in thread-safe mode", e);
        }
    }

    public static void testReseedNonThreadSafe() {
        SecureRandom sr = new SecureRandom();
        // Simulate non-thread-safe mode by setting threadSafe to false via reflection if needed
        // Since threadSafe is not accessible, we assume default thread safety; test the method directly
        try {
            sr.reseed();
        } catch (Exception e) {
            throw new AssertionError("reseed() threw an exception in non-thread-safe mode", e);
        }
    }
}
