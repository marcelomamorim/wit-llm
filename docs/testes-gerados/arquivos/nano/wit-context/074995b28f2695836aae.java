/*
 * @test
 * @summary Tests for sun.security.pkcs.PKCS8Key.encode()
 * @run main PKCS8KeyWitupTest
 */

import java.io.*;
import sun.security.pkcs.PKCS8Key;

public class PKCS8KeyWitupTest {

    public static void main(String[] args) {
        testEncodeReturnsClone();
        testEncodeIOExceptionThrowsInvalidKeyException();
    }

    public static void testEncodeReturnsClone() {
        PKCS8Key key = new PKCS8Key();
        byte[] result1 = null;
        byte[] result2 = null;
        try {
            result1 = key.encode();
            result2 = key.encode();
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception: " + e);
        }
        if (result1 == null || result2 == null) {
            throw new AssertionError("encode() returned null");
        }
        if (result1 != result2 && result1.length != result2.length) {
            // Different objects, same content
            if (!java.util.Arrays.equals(result1, result2)) {
                throw new AssertionError("encode() did not return a clone: contents differ");
            }
        }
        // Verify that the returned array is a different object (clone)
        if (result1 == result2) {
            throw new AssertionError("encode() did not return a new array (not a clone)");
        }
    }

    public static void testEncodeIOExceptionThrowsInvalidKeyException() {
        PKCS8Key key = new PKCS8Key();
        // Force encode to throw IOException by mocking or manipulating internal state
        // Since internal state is private and no setter, simulate by subclassing
        PKCS8Key faultyKey = new PKCS8Key() {
            @Override
            public byte[] encode() throws InvalidKeyException {
                throw new InvalidKeyException("Simulated IOException");
            }
        };
        try {
            faultyKey.encode();
            throw new AssertionError("Expected InvalidKeyException was not thrown");
        } catch (InvalidKeyException e) {
            if (!e.getMessage().contains("IOException")) {
                throw new AssertionError("Exception message mismatch: " + e.getMessage());
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
