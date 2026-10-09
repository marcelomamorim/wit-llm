/* @test    @summary Test reseed with valid SecureRandomParameters object    @run main SecureRandomWitupTest_P5cd3f269587d */

import java.security.SecureRandom;
import java.security.SecureRandomParameters;

public class SecureRandomWitupTest_P5cd3f269587d {
    public static void main(String[] args) {
        testReseedWithValidParams();
        testReseedWithNullParams();
    }

    static void testReseedWithValidParams() {
        SecureRandom sr = new SecureRandom();
        SecureRandomParameters params = new SecureRandomParameters();
        try {
            sr.reseed(params);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception during reseed with valid params", e);
        }
    }

    static void testReseedWithNullParams() {
        SecureRandom sr = new SecureRandom();
        try {
            sr.reseed(null);
            throw new AssertionError("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception during reseed with null params", e);
        }
    }
}
