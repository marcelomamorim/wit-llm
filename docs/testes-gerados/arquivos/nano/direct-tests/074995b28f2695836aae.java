/*
 * @test
 * @summary Test the encode() method of sun.security.pkcs.PKCS8Key.
 * @run main PKCS8KeyWitupTest
 */

import java.io.*;
import sun.security.pkcs.PKCS8Key;

public class PKCS8KeyWitupTest {

    public static void main(String[] args) {
        testEncodeReturnsByteArray();
        testEncodeReturnsClone();
    }

    private static void testEncodeReturnsByteArray() {
        try {
            PKCS8Key key = new PKCS8Key();
            byte[] result = key.encode();
            if (result == null) {
                throw new AssertionError("encode() returned null");
            }
            if (result != null && result.length == 0) {
                // Acceptable if encode produces empty array, but unlikely
                System.out.println("Warning: encode() returned empty array");
            }
            System.out.println("testEncodeReturnsByteArray passed");
        } catch (Exception e) {
            throw new AssertionError("Exception in testEncodeReturnsByteArray: " + e);
        }
    }

    private static void testEncodeReturnsClone() {
        try {
            PKCS8Key key = new PKCS8Key();
            byte[] firstResult = key.encode();
            byte[] secondResult = key.encode();
            if (firstResult == secondResult) {
                throw new AssertionError("encode() did not return a new array");
            }
            if (firstResult.length != secondResult.length) {
                throw new AssertionError("encode() returned arrays of different length");
            }
            for (int i = 0; i < firstResult.length; i++) {
                if (firstResult[i] != secondResult[i]) {
                    throw new AssertionError("encode() returned different data on subsequent calls");
                }
            }
            System.out.println("testEncodeReturnsClone passed");
        } catch (Exception e) {
            throw new AssertionError("Exception in testEncodeReturnsClone: " + e);
        }
    }
}
