/*
 * @test
 * @summary Verify DerInputStream.getPrintableString decoding and tag/EOF error reporting
 * @modules java.base/sun.security.util
 * @run main DerInputStreamWitupTest
 */

import sun.security.util.DerInputStream;
import java.io.IOException;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        byte[] valid = {0x13, 0x03, 'a', 'b', 'c'};
        String s = new DerInputStream(valid).getPrintableString();
        check("abc".equals(s), "valid PrintableString should decode to abc, got: " + s);

        byte[] emptyValue = {0x13, 0x00};
        String e = new DerInputStream(emptyValue).getPrintableString();
        check("".equals(e), "empty PrintableString should decode to empty string, got: " + e);

        expectIOException(new byte[]{0x0C, 0x03, 'a', 'b', 'c'});
        expectIOException(new byte[0]);
    }

    private static void expectIOException(byte[] data) throws Exception {
        try {
            new DerInputStream(data).getPrintableString();
        } catch (IOException expected) {
            return;
        }
        throw new RuntimeException("expected IOException for input length " + data.length);
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
