/*
 * @test
 * @summary Verifies DerInputStream.getGeneralString decodes GeneralString DER values and rejects a mismatched tag
 * @modules java.base/sun.security.util
 * @run main DerInputStreamWitupTest
 */

import sun.security.util.DerInputStream;
import java.io.IOException;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testSimpleString();
        testEmptyString();
        testWrongTagRejected();
    }

    static void testSimpleString() throws Exception {
        byte[] data = new byte[] {0x1B, 0x03, 'a', 'b', 'c'};
        DerInputStream in = new DerInputStream(data);
        String s = in.getGeneralString();
        if (!"abc".equals(s)) {
            throw new RuntimeException("Expected abc but got " + s);
        }
    }

    static void testEmptyString() throws Exception {
        byte[] data = new byte[] {0x1B, 0x00};
        DerInputStream in = new DerInputStream(data);
        String s = in.getGeneralString();
        if (s == null || !s.isEmpty()) {
            throw new RuntimeException("Expected empty string but got " + s);
        }
    }

    static void testWrongTagRejected() throws Exception {
        byte[] data = new byte[] {0x04, 0x01, 0x41};
        DerInputStream in = new DerInputStream(data);
        try {
            String s = in.getGeneralString();
            throw new RuntimeException("Expected IOException for wrong tag but got " + s);
        } catch (IOException expected) {
        }
    }
}
