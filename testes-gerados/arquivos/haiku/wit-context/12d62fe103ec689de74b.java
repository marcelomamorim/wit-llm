/*
 * @test
 * @summary Verify DerInputStream.getOctetString returns the encoded content or throws IOException for malformed input
 * @modules java.base/sun.security.util
 * @run main DerInputStreamWitupTest
 */

import java.io.IOException;
import java.util.Arrays;
import sun.security.util.DerInputStream;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testValidOctetString();
        testEmptyOctetString();
        testWrongTagThrowsIOException();
        testShortReadThrowsIOException();
        testEmptyInputThrowsIOException();
    }

    static void testValidOctetString() throws Exception {
        byte[] data = {0x04, 0x03, 0x01, 0x02, 0x03};
        DerInputStream in = new DerInputStream(data);
        byte[] result = in.getOctetString();
        byte[] expected = {0x01, 0x02, 0x03};
        if (!Arrays.equals(expected, result)) {
            throw new RuntimeException("valid octet string content mismatch");
        }
    }

    static void testEmptyOctetString() throws Exception {
        byte[] data = {0x04, 0x00};
        DerInputStream in = new DerInputStream(data);
        byte[] result = in.getOctetString();
        if (result == null || result.length != 0) {
            throw new RuntimeException("empty octet string should yield zero-length array");
        }
    }

    static void testWrongTagThrowsIOException() {
        byte[] data = {0x02, 0x01, 0x00};
        expectIOException(data, "wrong tag");
    }

    static void testShortReadThrowsIOException() {
        byte[] data = {0x04, 0x05, 0x01, 0x02};
        expectIOException(data, "short read");
    }

    static void testEmptyInputThrowsIOException() {
        expectIOException(new byte[0], "empty input");
    }

    static void expectIOException(byte[] data, String label) {
        try {
            DerInputStream in = new DerInputStream(data);
            in.getOctetString();
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("expected IOException for " + label);
    }
}
