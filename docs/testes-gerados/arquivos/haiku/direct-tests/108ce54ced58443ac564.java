/*
 * @test
 * @summary Behavioral checks for Base64.getMimeEncoder(int, byte[]) argument validation, line length rounding and fallback to the basic encoder
 * @run main Base64WitupTest
 */

import java.util.Base64;

public class Base64WitupTest {

    static int failures = 0;

    static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAIL: " + message);
        }
    }

    public static void main(String[] args) {
        byte[] zeros = new byte[6];
        byte[] crlf = {'\r', '\n'};

        try {
            Base64.getMimeEncoder(4, null);
            check(false, "null lineSeparator must throw NullPointerException");
        } catch (NullPointerException expected) {
        }

        try {
            Base64.getMimeEncoder(4, new byte[]{'\n', 'A'});
            check(false, "separator containing base64 alphabet char 'A' must be rejected");
        } catch (IllegalArgumentException expected) {
        }

        try {
            Base64.getMimeEncoder(4, new byte[]{'/'});
            check(false, "separator containing base64 alphabet char '/' must be rejected");
        } catch (IllegalArgumentException expected) {
        }

        check("AAAA\r\nAAAA".equals(Base64.getMimeEncoder(4, crlf).encodeToString(zeros)),
              "lineLength 4 should wrap encoded output with CRLF");

        check("AAAA\r\nAAAA".equals(Base64.getMimeEncoder(5, crlf).encodeToString(zeros)),
              "lineLength 5 should be rounded down to 4");

        check("AAAAAAAA".equals(Base64.getMimeEncoder(3, crlf).encodeToString(zeros)),
              "lineLength 3 rounds to 0 and must produce no line separators");

        check("AAAAAAAA".equals(Base64.getMimeEncoder(0, crlf).encodeToString(zeros)),
              "lineLength 0 must produce no line separators");

        check("AAAAAAAA".equals(Base64.getMimeEncoder(-1, crlf).encodeToString(zeros)),
              "negative lineLength must produce no line separators");

        check(Base64.getMimeEncoder(0, crlf) == Base64.getEncoder(),
              "non-positive lineLength must return the basic encoder instance");

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
