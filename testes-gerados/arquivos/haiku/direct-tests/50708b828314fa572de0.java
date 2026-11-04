/*
 * @test
 * @summary Verifies String.getBytes(Charset) encoding, null handling, and returned array independence
 * @run main StringWitupTest
 */

import java.nio.charset.Charset;

public class StringWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void checkBytes(byte[] actual, byte... expected) {
        check(actual.length == expected.length,
              "length " + actual.length + " expected " + expected.length);
        for (int i = 0; i < expected.length; i++) {
            check(actual[i] == expected[i],
                  "byte at " + i + ": " + actual[i] + " expected " + expected[i]);
        }
    }

    public static void main(String[] args) throws Exception {
        Charset utf8 = Charset.forName("UTF-8");
        Charset latin1 = Charset.forName("ISO-8859-1");

        boolean npeThrown = false;
        try {
            "abc".getBytes((Charset) null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        check(npeThrown, "null charset must throw NullPointerException");

        checkBytes("abc".getBytes(utf8), (byte) 0x61, (byte) 0x62, (byte) 0x63);
        checkBytes("".getBytes(utf8));
        checkBytes("\u00e9".getBytes(utf8), (byte) 0xC3, (byte) 0xA9);
        checkBytes("\u00e9".getBytes(latin1), (byte) 0xE9);
        checkBytes("\u4e2d".getBytes(utf8), (byte) 0xE4, (byte) 0xB8, (byte) 0xAD);
        checkBytes("\u4e2d".getBytes(latin1), (byte) '?');
        checkBytes("\ud83d\ude00".getBytes(utf8),
                   (byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x80);

        String s = "a\u00e9\u4e2d";
        byte[] encoded = s.getBytes(utf8);
        check(new String(encoded, 0, encoded.length, "UTF-8").equals(s),
              "UTF-8 round trip failed");

        encoded[0] = 0;
        check(s.getBytes(utf8)[0] == (byte) 'a',
              "modifying returned array must not affect later encodings");
    }
}
