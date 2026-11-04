/*
 * @test
 * @summary Verify DerInputStream.getIA5String() decoding and IOException on malformed DER input
 * @modules java.base/sun.security.util
 * @run main DerInputStreamWitupTest
 */

import java.io.IOException;
import sun.security.util.DerInputStream;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testValidIA5String();
        testEmptyIA5String();
        testWrongTagThrowsIOException();
        testEmptyInputThrowsIOException();
        testTruncatedContentThrowsIOException();
    }

    private static void testValidIA5String() throws Exception {
        byte[] data = {0x16, 0x03, 0x61, 0x62, 0x63};
        String result = new DerInputStream(data).getIA5String();
        if (!"abc".equals(result)) {
            throw new RuntimeException("Expected 'abc' but got: " + result);
        }
    }

    private static void testEmptyIA5String() throws Exception {
        byte[] data = {0x16, 0x00};
        String result = new DerInputStream(data).getIA5String();
        if (!"".equals(result)) {
            throw new RuntimeException("Expected empty string but got: " + result);
        }
    }

    private static void testWrongTagThrowsIOException() {
        byte[] data = {0x13, 0x01, 0x41};
        expectIOException(data, "wrong tag");
    }

    private static void testEmptyInputThrowsIOException() {
        byte[] data = new byte[0];
        expectIOException(data, "empty input");
    }

    private static void testTruncatedContentThrowsIOException() {
        byte[] data = {0x16, 0x05, 0x61};
        expectIOException(data, "truncated content");
    }

    private static void expectIOException(byte[] data, String label) {
        try {
            new DerInputStream(data).getIA5String();
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("Expected IOException for " + label);
    }
}
