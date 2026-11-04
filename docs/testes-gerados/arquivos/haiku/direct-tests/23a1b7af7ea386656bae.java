/*
 * @test
 * @summary Checks public String.getBytes(ISO_8859_1) behavior on UTF16-coded strings, which reaches StringUTF16.compress
 * @run main StringUTF16WitupTest
 */

import java.nio.charset.StandardCharsets;

public class StringUTF16WitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) {
        String empty = "";
        byte[] e = empty.getBytes(StandardCharsets.ISO_8859_1);
        check(e.length == 0, "empty string must yield empty byte array");

        String latinOnly = "a\u00e9b\u00ff";
        byte[] l = latinOnly.getBytes(StandardCharsets.ISO_8859_1);
        check(l.length == 4, "latin1 length mismatch");
        check(l[0] == (byte) 'a', "byte 0 mismatch");
        check(l[1] == (byte) 0xE9, "byte 1 mismatch");
        check(l[2] == (byte) 'b', "byte 2 mismatch");
        check(l[3] == (byte) 0xFF, "byte 3 mismatch");

        String wide = "a\u4e2db";
        byte[] w = wide.getBytes(StandardCharsets.ISO_8859_1);
        check(w.length == 3, "wide string length mismatch");
        check(w[0] == (byte) 'a', "wide byte 0 mismatch");
        check(w[1] == (byte) '?', "unmappable char must become '?'");
        check(w[2] == (byte) 'b', "wide byte 2 mismatch");

        String allWide = "\u4e2d\u6587";
        byte[] aw = allWide.getBytes(StandardCharsets.ISO_8859_1);
        check(aw.length == 2, "all-wide length mismatch");
        check(aw[0] == (byte) '?' && aw[1] == (byte) '?', "all-wide must map to '?'");
    }
}
