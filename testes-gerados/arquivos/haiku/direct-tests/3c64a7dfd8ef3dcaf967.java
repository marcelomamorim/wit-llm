/*
 * @test
 * @summary Verify DerValue.getIA5String decodes IA5String values as ASCII and rejects non-IA5 tags
 * @modules java.base/sun.security.util
 * @run main DerValueWitupTest
 */

import java.io.IOException;
import sun.security.util.DerValue;

public class DerValueWitupTest {
    public static void main(String[] args) throws Exception {
        DerValue ascii = new DerValue((byte)0x16, new byte[]{0x61, 0x62, 0x63});
        check("abc".equals(ascii.getIA5String()), "ASCII bytes must decode to abc");

        DerValue empty = new DerValue((byte)0x16, new byte[0]);
        check("".equals(empty.getIA5String()), "empty IA5String must decode to empty string");

        DerValue nonAscii = new DerValue((byte)0x16, new byte[]{(byte)0xC3});
        check("\uFFFD".equals(nonAscii.getIA5String()), "non-ASCII byte must be replaced by U+FFFD");

        DerValue integer = new DerValue((byte)0x02, new byte[]{0x41});
        boolean thrown = false;
        try {
            integer.getIA5String();
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "non-IA5 tag must throw IOException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
