/*
 * @test
 * @summary Witup-derived tests for DerInputStream.getInteger
 * @modules java.base/sun.security.util
 * @run main DerInputStreamWitupTest
 */

import java.io.IOException;
import sun.security.util.DerInputStream;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testValidInteger();
        testWrongTagThrowsIOException();
        testEmptyInputThrowsIOException();
    }

    static void testValidInteger() throws IOException {
        DerInputStream in = new DerInputStream(new byte[] {0x02, 0x01, 0x05});
        int v = in.getInteger();
        if (v != 5) {
            throw new RuntimeException("expected 5 but got " + v);
        }
    }

    static void testWrongTagThrowsIOException() {
        expectIOException(new byte[] {0x04, 0x01, 0x05});
    }

    static void testEmptyInputThrowsIOException() {
        expectIOException(new byte[0]);
    }

    static void expectIOException(byte[] data) {
        try {
            DerInputStream in = new DerInputStream(data);
            in.getInteger();
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("expected IOException was not thrown");
    }
}
