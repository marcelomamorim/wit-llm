/*
 * @test
 * @summary Verifies the null-argument checks of the ECPrivateKeySpec constructor and their messages
 * @run main ECPrivateKeySpecWitupTest
 */

import java.math.BigInteger;
import java.security.spec.ECPrivateKeySpec;

public class ECPrivateKeySpecWitupTest {

    public static void main(String[] args) {
        testNullS();
        testNullParams();
        testBothNull();
    }

    static void testNullS() {
        try {
            new ECPrivateKeySpec(null, null);
            throw new RuntimeException("Expected NullPointerException for null s");
        } catch (NullPointerException e) {
            if (!"s is null".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message: " + e.getMessage());
            }
        }
    }

    static void testNullParams() {
        try {
            new ECPrivateKeySpec(BigInteger.ONE, null);
            throw new RuntimeException("Expected NullPointerException for null params");
        } catch (NullPointerException e) {
            if (!"params is null".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message: " + e.getMessage());
            }
        }
    }

    static void testBothNull() {
        try {
            new ECPrivateKeySpec(null, null);
            throw new RuntimeException("Expected NullPointerException when both arguments are null");
        } catch (NullPointerException e) {
            if (!"s is null".equals(e.getMessage())) {
                throw new RuntimeException("s must be checked first, got: " + e.getMessage());
            }
        }
    }
}
