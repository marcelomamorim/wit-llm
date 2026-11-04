/* @test    @summary Test for DESKeySpec constructor with valid and invalid key lengths    @run main DESKeySpecWitupTest */

import javax.crypto.spec.DESKeySpec;
import java.security.InvalidKeyException;

public class DESKeySpecWitupTest {

    public static void main(String[] args) {
        testValidKey();
        testShortKey();
    }

    private static void testValidKey() {
        byte[] validKey = new byte[8];
        try {
            DESKeySpec spec = new DESKeySpec(validKey);
            System.out.println("testValidKey passed");
        } catch (InvalidKeyException e) {
            System.err.println("testValidKey failed: " + e);
        }
    }

    private static void testShortKey() {
        byte[] shortKey = new byte[7];
        try {
            DESKeySpec spec = new DESKeySpec(shortKey);
            System.err.println("testShortKey failed: exception expected but not thrown");
        } catch (InvalidKeyException e) {
            System.out.println("testShortKey passed: caught expected exception");
        }
    }
}
