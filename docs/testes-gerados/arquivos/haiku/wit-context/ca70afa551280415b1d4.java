/*
 * @test
 * @summary Witup-generated tests for DerInputStream.getSequence
 * @modules java.base/sun.security.util
 * @run main DerInputStreamWitupTest
 */

import java.io.IOException;
import sun.security.util.DerInputStream;
import sun.security.util.DerValue;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testEmptySequence();
        testSingleIntegerElement();
        testWrongTagThrowsIOException();
        testTruncatedStreamThrowsIOException();
    }

    static void testEmptySequence() throws IOException {
        DerInputStream in = new DerInputStream(new byte[]{0x30, 0x00});
        DerValue[] vals = in.getSequence(4);
        check(vals != null, "result must not be null");
        check(vals.length == 0, "empty sequence must yield zero elements");
        check(in.tag == 0x30, "tag field must be set to SEQUENCE tag");
    }

    static void testSingleIntegerElement() throws IOException {
        DerInputStream in = new DerInputStream(new byte[]{0x30, 0x03, 0x02, 0x01, 0x05});
        DerValue[] vals = in.getSequence(4);
        check(vals.length == 1, "sequence with one INTEGER must yield one element");
        check(vals[0].tag == 0x02, "element tag must be INTEGER");
        check(in.tag == 0x30, "tag field must be set to SEQUENCE tag");
    }

    static void testWrongTagThrowsIOException() {
        expectIOException(new byte[]{0x31, 0x00});
    }

    static void testTruncatedStreamThrowsIOException() {
        expectIOException(new byte[]{0x30});
    }

    static void expectIOException(byte[] data) {
        try {
            new DerInputStream(data).getSequence(4);
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("expected IOException was not thrown");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
