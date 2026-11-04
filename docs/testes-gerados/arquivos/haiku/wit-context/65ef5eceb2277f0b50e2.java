/*
 * @test
 * @summary Verify DerInputStream.getUTF8String() decodes UTF8String values and rejects wrong tags, truncated content and empty input
 * @modules java.base/sun.security.util
 * @run main DerInputStreamWitupTest
 */

import sun.security.util.DerInputStream;
import java.io.IOException;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        checkValue(new byte[]{0x0C, 0x03, 0x61, 0x62, 0x63}, "abc");
        checkValue(new byte[]{0x0C, 0x00}, "");
        checkValue(new byte[]{0x0C, 0x02, (byte) 0xC3, (byte) 0xA9}, "\u00e9");
        expectIOException(new byte[]{0x04, 0x01, 0x41});
        expectIOException(new byte[]{0x0C, 0x05, 0x61});
        expectIOException(new byte[0]);
    }

    private static void checkValue(byte[] data, String expected) throws Exception {
        DerInputStream in = new DerInputStream(data);
        String actual = in.getUTF8String();
        if (!expected.equals(actual)) {
            throw new RuntimeException("Expected \"" + expected + "\" but got \"" + actual + "\"");
        }
    }

    private static void expectIOException(byte[] data) throws Exception {
        try {
            DerInputStream in = new DerInputStream(data);
            String value = in.getUTF8String();
            throw new RuntimeException("Expected IOException but got value: " + value);
        } catch (IOException expected) {
            // expected
        }
    }
}
