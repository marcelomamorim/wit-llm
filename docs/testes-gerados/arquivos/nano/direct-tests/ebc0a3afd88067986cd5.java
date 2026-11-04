/* @test    @summary Test DESKeySpec constructor with valid and invalid keys    @run main DESKeySpecWitupTest */

import javax.crypto.spec.DESKeySpec;
import java.security.InvalidKeyException;

public class DESKeySpecWitupTest {

    public static void main(String[] args) throws Exception {
        testValidKey();
        testWeakKey();
        testShortKey();
        testNullKey();
    }

    static void testValidKey() throws Exception {
        byte[] validKey = {0x01, 0x23, 0x45, 0x67, (byte)0x89, (byte)0xAB, (byte)0xCD, (byte)0xEF};
        DESKeySpec spec = new DESKeySpec(validKey);
        assert spec != null : "Constructor with valid key should not be null";
    }

    static void testWeakKey() throws Exception {
        byte[] weakKey = {0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01};
        DESKeySpec spec = new DESKeySpec(weakKey);
        assert spec != null : "Constructor with weak key should succeed";
    }

    static void testShortKey() {
        byte[] shortKey = {0x01, 0x23, 0x45};
        try {
            new DESKeySpec(shortKey);
            assert false : "Constructor with short key should throw InvalidKeyException";
        } catch (InvalidKeyException e) {
            // expected
        }
    }

    static void testNullKey() {
        try {
            new DESKeySpec((byte[]) null);
            assert false : "Constructor with null key should throw NullPointerException or InvalidKeyException";
        } catch (NullPointerException | InvalidKeyException e) {
            // expected
        }
    }
}
