/*
 * @test
 * @summary Tests decoding of PKCS10Attributes from a DerInputStream
 * @modules java.base/sun.security.pkcs10 java.base/sun.security.util
 * @run main PKCS10AttributesWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import sun.security.pkcs10.PKCS10Attributes;
import sun.security.util.DerInputStream;

public class PKCS10AttributesWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void expectIOException(byte[] data, String msg) {
        boolean thrown = false;
        try {
            new PKCS10Attributes(new DerInputStream(data));
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, msg);
    }

    static void testEmptySetDecodes() throws IOException {
        byte[] empty = {0x31, 0x00};
        PKCS10Attributes attrs = new PKCS10Attributes(new DerInputStream(empty));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        attrs.encode(out);
        check(out.size() > 0, "encoding of decoded empty set produced no bytes");

        PKCS10Attributes again = new PKCS10Attributes(
                new DerInputStream(out.toByteArray()));
        ByteArrayOutputStream out2 = new ByteArrayOutputStream();
        again.encode(out2);
        check(out.toByteArray().length == out2.toByteArray().length,
                "re-encoding changed length");
    }

    static void testNonSetTagRejected() {
        byte[] seq = {0x30, 0x00};
        expectIOException(seq, "SEQUENCE tag accepted where SET is required");
    }

    static void testTruncatedInputRejected() {
        byte[] truncated = {0x31, 0x05, 0x01};
        expectIOException(truncated, "truncated SET accepted");
    }

    public static void main(String[] args) throws Exception {
        testEmptySetDecodes();
        testNonSetTagRejected();
        testTruncatedInputRejected();
    }
}
