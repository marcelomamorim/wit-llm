/*
 * @test
 * @summary Checks DerValue.getBitString(boolean) tag validation and bit string decoding through public APIs
 * @modules java.base/sun.security.util
 * @run main DerValueWitupTest
 */

import sun.security.util.DerValue;
import java.io.IOException;
import java.util.Arrays;

public class DerValueWitupTest {

    public static void main(String[] args) throws Exception {
        testValidBitStringNotImplicit();
        testWrongTagNotImplicitThrows();
        testImplicitTagSkipsTagCheck();
    }

    static void testValidBitStringNotImplicit() throws Exception {
        DerValue v = new DerValue(new byte[]{0x03, 0x02, 0x00, (byte) 0xAB});
        byte[] bits = v.getBitString(false);
        check(Arrays.equals(bits, new byte[]{(byte) 0xAB}),
              "expected bit string payload {0xAB}");
    }

    static void testWrongTagNotImplicitThrows() throws Exception {
        DerValue v = new DerValue(new byte[]{0x02, 0x01, 0x05});
        boolean thrown = false;
        try {
            v.getBitString(false);
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "expected IOException for non bit string tag when tagImplicit is false");
    }

    static void testImplicitTagSkipsTagCheck() throws Exception {
        DerValue v = new DerValue(new byte[]{0x02, 0x02, 0x00, (byte) 0xAB});
        byte[] bits = v.getBitString(true);
        check(Arrays.equals(bits, new byte[]{(byte) 0xAB}),
              "expected implicit-tagged payload {0xAB}");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
