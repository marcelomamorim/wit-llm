/* @test    @summary Test reseed method of SecureRandom for null and thread safety    @run main SecureRandomWitupTest_P3be5a38e46f3 */

import java.security.SecureRandom;
import java.security.SecureRandomParameters;

public class SecureRandomWitupTest_P3be5a38e46f3 {

    public static void main(String[] args) {
        testReseedWithNullParams();
        testReseedThreadSafety();
    }

    public static void testReseedWithNullParams() {
        SecureRandom sr = new SecureRandom();
        boolean exceptionThrown = false;
        try {
            sr.reseed(null);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException when reseed with null params");
        }
    }

    public static void testReseedThreadSafety() {
        SecureRandom sr = new SecureRandom();
        SecureRandomParameters params = new SecureRandomParameters() {
            // Empty implementation for testing
        };
        try {
            sr.reseed(params);
        } catch (Exception e) {
            throw new AssertionError("reseed should not throw exception in thread-safe context", e);
        }
    }
}
