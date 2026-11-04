/*
 * @test
 * @summary Verify DerInputStream.getT61String decodes T61String DER values as ISO-8859-1 and rejects other tags
 * @modules java.base/sun.security.util
 * @run main DerInputStreamWitupTest
 */

import java.io.IOException;
import sun.security.util.DerInputStream;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        check(new byte[]{0x14, 0x03, 'a', 'b', 'c'}, "abc");
        check(new byte[]{0x14, 0x00}, "");
        check(new byte[]{0x14, 0x02, 'O', 'K'}, "OK");
        check(new byte[]{0x14, 0x01, (byte) 0xE9}, "\u00e9");
        expectFailure(new byte[]{0x13, 0x01, 'a'});
        expectFailure(new byte[]{0x0C, 0x01, 'a'});
    }

    static void check(byte[] der, String expected) throws IOException {
        DerInputStream in = new DerInputStream(der);
        String actual = in.getT61String();
        if (!expected.equals(actual)) {
            throw new RuntimeException("Expected \"" + expected + "\" but got \"" + actual + "\"");
        }
    }

    static void expectFailure(byte[] der) throws IOException {
        DerInputStream in = new DerInputStream(der);
        try {
            in.getT61String();
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("Expected IOException for non-T61String tag");
    }
}
