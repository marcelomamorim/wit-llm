/*
 * @test
 * @summary Verify String(byte[], Charset) decoding and null argument handling
 * @run main StringWitupTest
 */

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class StringWitupTest {

    public static void main(String[] args) {
        byte[] utf8 = "h\u00e9llo".getBytes(StandardCharsets.UTF_8);
        check("h\u00e9llo".equals(new String(utf8, StandardCharsets.UTF_8)), "utf8 round trip");

        check("".equals(new String(new byte[0], StandardCharsets.UTF_8)), "empty array yields empty string");

        byte[] latin1 = {(byte) 0xE9};
        check("\u00e9".equals(new String(latin1, StandardCharsets.ISO_8859_1)), "latin1 decoding");

        byte[] ascii = {'a', 'b', 'c'};
        check("abc".equals(new String(ascii, StandardCharsets.US_ASCII)), "ascii decoding");

        expectNpe(() -> new String((byte[]) null, StandardCharsets.UTF_8), "null bytes");
        expectNpe(() -> new String(utf8, (Charset) null), "null charset");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Failed: " + message);
        }
    }

    private static void expectNpe(Runnable r, String message) {
        try {
            r.run();
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException: " + message);
    }
}
