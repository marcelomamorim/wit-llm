/*
 * @test
 * @summary Verify PKCS8Key.encode() returns a defensive copy of the cached encoding
 * @modules java.base/sun.security.pkcs
 * @run main PKCS8KeyWitupTest
 */

import java.util.Arrays;
import sun.security.pkcs.PKCS8Key;

public class PKCS8KeyWitupTest {

    static class TestKey extends PKCS8Key {
        TestKey(byte[] enc) {
            this.encodedKey = enc;
        }
    }

    public static void main(String[] args) throws Exception {
        byte[] enc = {1, 2, 3};
        TestKey k = new TestKey(enc.clone());

        byte[] r1 = k.encode();
        if (!Arrays.equals(r1, enc)) {
            throw new RuntimeException("encode() content mismatch");
        }

        r1[0] = 99;
        byte[] r2 = k.encode();
        if (!Arrays.equals(r2, enc)) {
            throw new RuntimeException("cached encoding was modified via returned array");
        }
        if (r1 == r2) {
            throw new RuntimeException("encode() must return a copy, not the same array");
        }
    }
}
