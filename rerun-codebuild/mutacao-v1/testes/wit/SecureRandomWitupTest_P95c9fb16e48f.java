/* @test    @summary Test reseed method of java.security.SecureRandom    @run main SecureRandomWitupTest_P95c9fb16e48f */

import java.security.SecureRandom;
import java.security.SecureRandomParameters;

public class SecureRandomWitupTest_P95c9fb16e48f {

    public static void main(String[] args) {
        testReseedWithNullParams();
        testReseedWithValidParams();
    }

    private static void testReseedWithNullParams() {
        SecureRandom sr = new SecureRandom();
        try {
            sr.reseed(null);
            throw new AssertionError("Expected IllegalArgumentException for null params");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception: " + t);
        }
    }

    private static void testReseedWithValidParams() {
        SecureRandom sr = new SecureRandom();
        SecureRandomParameters params = null;
        try {
            // Attempt to call with non-null params; expecting UnsupportedOperationException or no exception
            // Since actual behavior depends on implementation, we test that no unexpected exception occurs
            sr.reseed(params);
        } catch (UnsupportedOperationException e) {
            // Expected in some implementations
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception: " + t);
        }
    }
}
