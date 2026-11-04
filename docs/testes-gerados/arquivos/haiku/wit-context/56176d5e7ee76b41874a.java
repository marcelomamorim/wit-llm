/*
 * @test
 * @summary Witup-generated tests for DerInputStream.getGeneralString exception and normal paths
 * @modules java.base/sun.security.util
 * @run main DerInputStreamWitupTest
 */

import java.io.IOException;
import sun.security.util.DerInputStream;

public class DerInputStreamWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testValidGeneralString() throws IOException {
        byte[] data = new byte[] {0x1B, 0x03, 'a', 'b', 'c'};
        DerInputStream in = new DerInputStream(data);
        String s = in.getGeneralString();
        check("abc".equals(s), "expected abc but got " + s);
    }

    static void testWrongTagThrowsIOException() throws IOException {
        byte[] data = new byte[] {0x04, 0x01, 'a'};
        DerInputStream in = new DerInputStream(data);
        boolean thrown = false;
        try {
            in.getGeneralString();
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "expected IOException for wrong tag");
    }

    static void testTruncatedContentThrowsIOException() throws IOException {
        byte[] data = new byte[] {0x1B, 0x05, 'a'};
        DerInputStream in = new DerInputStream(data);
        boolean thrown = false;
        try {
            in.getGeneralString();
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "expected IOException for truncated content");
    }

    public static void main(String[] args) throws Exception {
        testValidGeneralString();
        testWrongTagThrowsIOException();
        testTruncatedContentThrowsIOException();
    }
}
