/* @test    @summary Test for DESedeKeySpec constructor with byte array and offset, focusing on exception path for invalid key size    @run main DESedeKeySpecWitupTest */

import javax.crypto.spec.DESedeKeySpec;
import java.security.InvalidKeyException;

public class DESedeKeySpecWitupTest {

    public static void main(String[] args) {
        testConstructorWithShortKey();
        testConstructorWithValidKey();
    }

    private static void testConstructorWithShortKey() {
        byte[] shortKey = new byte[23];
        int offset = 0;
        try {
            new DESedeKeySpec(shortKey, offset);
            System.out.println("FAIL: Expected InvalidKeyException for short key");
        } catch (InvalidKeyException e) {
            System.out.println("PASS: Caught expected InvalidKeyException for short key");
        } catch (Exception e) {
            System.out.println("FAIL: Caught unexpected exception: " + e);
        }
    }

    private static void testConstructorWithValidKey() {
        byte[] validKey = new byte[24];
        for (int i = 0; i < 24; i++) {
            validKey[i] = (byte) i;
        }
        int offset = 0;
        try {
            DESedeKeySpec spec = new DESedeKeySpec(validKey, offset);
            byte[] keyReturned = spec.getKey();
            boolean matches = true;
            for (int i = 0; i < 24; i++) {
                if (keyReturned[i] != validKey[i]) {
                    matches = false;
                    break;
                }
            }
            if (matches) {
                System.out.println("PASS: Valid key constructor creates correct key");
            } else {
                System.out.println("FAIL: Key returned does not match input");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
