/*
 * @test
 * @summary Test javax.crypto.spec.IvParameterSpec constructors for expected behavior and exceptions
 * @run main IvParameterSpecWitupTest
 */

import javax.crypto.spec.IvParameterSpec;

public class IvParameterSpecWitupTest {
    public static void main(String[] args) {
        testConstructorWithValidArray();
        testConstructorWithNullArray();
    }

    private static void testConstructorWithValidArray() {
        byte[] iv = {1, 2, 3, 4, 5, 6, 7, 8};
        IvParameterSpec spec = new IvParameterSpec(iv);
        byte[] returnedIV = spec.getIV();

        if (returnedIV == iv) {
            throw new RuntimeException("getIV() must return a new array, not the original reference");
        }

        if (returnedIV.length != iv.length) {
            throw new RuntimeException("Returned IV length differs from input array length");
        }

        for (int i = 0; i < iv.length; i++) {
            if (returnedIV[i] != iv[i]) {
                throw new RuntimeException("Returned IV content differs from input array at index " + i);
            }
        }
    }

    private static void testConstructorWithNullArray() {
        try {
            new IvParameterSpec((byte[]) null);
            throw new RuntimeException("Expected IllegalArgumentException for null input array");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (NullPointerException e) {
            // Defensive: if implementation throws NPE instead, accept it as well
        }
    }
}
