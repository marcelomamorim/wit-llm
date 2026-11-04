/*
 * @test
 * @summary Verifies that the ECPrivateKeySpec constructor rejects null arguments with NullPointerException and the documented messages
 * @run main ECPrivateKeySpecWitupTest
 */

import java.math.BigInteger;
import java.security.spec.ECPrivateKeySpec;

public class ECPrivateKeySpecWitupTest {

    public static void main(String[] args) {
        testNullS();
        testNullParams();
    }

    private static void testNullS() {
        try {
            new ECPrivateKeySpec(null, null);
            throw new AssertionError("Expected NullPointerException when s is null");
        } catch (NullPointerException e) {
            if (!"s is null".equals(e.getMessage())) {
                throw new AssertionError("Unexpected message: " + e.getMessage());
            }
        }
    }

    private static void testNullParams() {
        try {
            new ECPrivateKeySpec(BigInteger.ONE, null);
            throw new AssertionError("Expected NullPointerException when params is null");
        } catch (NullPointerException e) {
            if (!"params is null".equals(e.getMessage())) {
                throw new AssertionError("Unexpected message: " + e.getMessage());
            }
        }
    }
}
