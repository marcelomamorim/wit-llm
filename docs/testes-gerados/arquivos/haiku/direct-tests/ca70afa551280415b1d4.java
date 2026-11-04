/*
 * @test
 * @summary Tests DerInputStream.getSequence for an empty sequence, a two-element sequence, and a wrong tag
 * @modules java.base/sun.security.util
 * @run main DerInputStreamWitupTest
 */

import java.io.IOException;
import sun.security.util.DerInputStream;
import sun.security.util.DerValue;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testEmptySequence();
        testTwoElementSequence();
        testWrongTagThrowsIOException();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmptySequence() throws IOException {
        DerInputStream dis = new DerInputStream(new byte[] {0x30, 0x00});
        DerValue[] values = dis.getSequence(4);
        check(values != null, "result must not be null");
        check(values.length == 0, "empty sequence must yield zero elements");
        check(dis.tag == 0x30, "tag must be recorded as SEQUENCE (0x30)");
    }

    static void testTwoElementSequence() throws IOException {
        byte[] data = {0x30, 0x06, 0x02, 0x01, 0x01, 0x02, 0x01, 0x02};
        DerInputStream dis = new DerInputStream(data);
        DerValue[] values = dis.getSequence(2);
        check(values != null, "result must not be null");
        check(values.length == 2, "sequence with two integers must yield two elements");
        check(values[0] != null && values[1] != null, "elements must not be null");
        check(dis.tag == 0x30, "tag must be recorded as SEQUENCE (0x30)");
    }

    static void testWrongTagThrowsIOException() throws IOException {
        DerInputStream dis = new DerInputStream(new byte[] {0x02, 0x00});
        try {
            dis.getSequence(1);
            throw new RuntimeException("expected IOException for wrong tag");
        } catch (IOException e) {
            check("Sequence tag error".equals(e.getMessage()),
                  "unexpected message: " + e.getMessage());
        }
    }
}
