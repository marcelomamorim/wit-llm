/*
 * @test
 * @summary Checks String(byte[], int, int, Charset) null-charset and bounds exception behavior
 * @run main StringWitupTest
 */

import java.nio.charset.Charset;

public class StringWitupTest {

    public static void main(String[] args) throws Exception {
        Charset utf8 = Charset.forName("UTF-8");
        byte[] data = "abc".getBytes(utf8);

        check(new String(data, 0, data.length, utf8).equals("abc"));
        check(new String(data, 1, 2, utf8).equals("bc"));
        check(new String(data, 3, 0, utf8).isEmpty());

        expectNPE(data, 0, 3, null);
        expectNPE(data, -1, 1, null);

        expectSIOOBE(data, -1, 1, utf8);
        expectSIOOBE(data, 0, -1, utf8);
        expectSIOOBE(data, 2, 2, utf8);
        expectSIOOBE(data, 0, 4, utf8);
    }

    private static void expectNPE(byte[] data, int offset, int length, Charset charset) {
        try {
            new String(data, offset, length, charset);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException");
    }

    private static void expectSIOOBE(byte[] data, int offset, int length, Charset charset) {
        try {
            new String(data, offset, length, charset);
        } catch (StringIndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("Expected StringIndexOutOfBoundsException for offset="
                + offset + ", length=" + length);
    }

    private static void check(boolean condition) {
        if (!condition) {
            throw new RuntimeException("Check failed");
        }
    }
}
