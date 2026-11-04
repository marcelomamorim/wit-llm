/*
 * @test
 * @summary Checks the DER encoding produced by BasicConstraintsExtension.encode(OutputStream) for CA, non-CA and critical variants
 * @modules java.base/sun.security.x509
 * @run main BasicConstraintsExtensionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class BasicConstraintsExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testCaWithPathLen();
        testCaWithoutPathLen();
        testNonCaNotCritical();
        testNonCaCriticalFlag();
        testEncodeIsRepeatable();
    }

    static void testCaWithPathLen() throws Exception {
        BasicConstraintsExtension ext = new BasicConstraintsExtension(Boolean.TRUE, true, 2);
        byte[] out = encode(ext);
        check(out.length > 0 && out[0] == 0x30, "outer SEQUENCE expected");
        byte[] expected = {0x04, 0x08, 0x30, 0x06, 0x01, 0x01, (byte) 0xFF, 0x02, 0x01, 0x02};
        check(endsWith(out, expected), "CA with pathLen 2 encoding mismatch");
    }

    static void testCaWithoutPathLen() throws Exception {
        BasicConstraintsExtension ext = new BasicConstraintsExtension(true, -1);
        byte[] out = encode(ext);
        byte[] expected = {0x04, 0x05, 0x30, 0x03, 0x01, 0x01, (byte) 0xFF};
        check(endsWith(out, expected), "CA without pathLen encoding mismatch");
    }

    static void testNonCaNotCritical() throws Exception {
        BasicConstraintsExtension ext = new BasicConstraintsExtension(false, -1);
        byte[] out = encode(ext);
        byte[] expected = {0x04, 0x02, 0x30, 0x00};
        check(endsWith(out, expected), "non-CA encoding mismatch");
    }

    static void testNonCaCriticalFlag() throws Exception {
        BasicConstraintsExtension ext = new BasicConstraintsExtension(Boolean.TRUE, false, -1);
        byte[] out = encode(ext);
        byte[] expected = {0x04, 0x02, 0x30, 0x00};
        check(endsWith(out, expected), "non-CA critical encoding mismatch");
        check(contains(out, new byte[] {0x01, 0x01, (byte) 0xFF}), "critical BOOLEAN missing");
    }

    static void testEncodeIsRepeatable() throws Exception {
        BasicConstraintsExtension ext = new BasicConstraintsExtension(Boolean.FALSE, true, 0);
        byte[] first = encode(ext);
        byte[] second = encode(ext);
        check(java.util.Arrays.equals(first, second), "repeated encode differs");
    }

    static byte[] encode(BasicConstraintsExtension ext) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ext.encode(bos);
        return bos.toByteArray();
    }

    static boolean endsWith(byte[] data, byte[] suffix) {
        if (data.length < suffix.length) {
            return false;
        }
        int off = data.length - suffix.length;
        for (int i = 0; i < suffix.length; i++) {
            if (data[off + i] != suffix[i]) {
                return false;
            }
        }
        return true;
    }

    static boolean contains(byte[] data, byte[] pattern) {
        for (int i = 0; i + pattern.length <= data.length; i++) {
            boolean match = true;
            for (int j = 0; j < pattern.length; j++) {
                if (data[i + j] != pattern[j]) {
                    match = false;
                    break;
                }
            }
            if (match) {
                return true;
            }
        }
        return false;
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }
}
