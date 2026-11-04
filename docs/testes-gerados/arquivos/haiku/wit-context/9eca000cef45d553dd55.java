/*
 * @test
 * @summary Checks DerInputStream.getT61String() for valid T61String input and for tag, empty and short-length input that must raise IOException
 * @modules java.base/sun.security.util
 * @run main DerInputStreamWitupTest
 */

import java.io.IOException;
import sun.security.util.DerInputStream;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        check("abc", new byte[]{0x14, 0x03, 'a', 'b', 'c'});
        expectIOException(new byte[]{0x13, 0x03, 'a', 'b', 'c'});
        expectIOException(new byte[0]);
        expectIOException(new byte[]{0x14, 0x05, 'a', 'b'});
    }

    static void check(String expected, byte[] data) throws IOException {
        DerInputStream in = new DerInputStream(data);
        String actual = in.getT61String();
        if (!expected.equals(actual)) {
            throw new RuntimeException("expected " + expected + " but got " + actual);
        }
    }

    static void expectIOException(byte[] data) {
        try {
            new DerInputStream(data).getT61String();
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("expected IOException");
    }
}
