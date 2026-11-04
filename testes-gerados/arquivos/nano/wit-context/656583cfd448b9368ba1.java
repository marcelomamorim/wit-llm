/* @test    @summary Test for DESKeySpec constructor with byte[] and offset, focusing on exception path for invalid key size    @run main DESKeySpecWitupTest */

import javax.crypto.spec.DESKeySpec;
import java.security.InvalidKeyException;

public class DESKeySpecWitupTest {

    public static void main(String[] args) {
        testConstructorWithShortKey();
        testConstructorWithValidKey();
    }

    private static void testConstructorWithShortKey() {
        byte[] shortKey = new byte[7]; // less than 8 bytes
        try {
            new DESKeySpec(shortKey, 0);
            System.err.println("Failed: Expected InvalidKeyException for short key");
        } catch (InvalidKeyException e) {
            System.out.println("Passed: Caught expected InvalidKeyException for short key");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception type: " + e);
        }
    }

    private static void testConstructorWithValidKey() {
        byte[] validKey = new byte[8];
        try {
            DESKeySpec spec = new DESKeySpec(validKey, 0);
            if (spec != null) {
                System.out.println("Passed: DESKeySpec created successfully with valid key");
            } else {
                System.err.println("Failed: DESKeySpec is null with valid key");
            }
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception with valid key: " + e);
        }
    }
}
