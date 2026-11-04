/*
 * @test
 * @summary Witup-generated checks for ByteArrayOutputStream.toString(int hibyte)
 * @run main ByteArrayOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;

public class ByteArrayOutputStreamWitupTest {

    @SuppressWarnings("deprecation")
    public static void main(String[] args) {
        ByteArrayOutputStream empty = new ByteArrayOutputStream();
        check(empty.toString(0).isEmpty(), "empty stream must yield empty string");
        check(empty.toString(5).isEmpty(), "empty stream with hibyte must yield empty string");

        ByteArrayOutputStream ab = new ByteArrayOutputStream();
        ab.write(0x41);
        ab.write(0x42);
        check("AB".equals(ab.toString(0)), "hibyte 0 must map ASCII bytes");
        check(ab.toString(0).length() == 2, "result length must equal count");

        ByteArrayOutputStream hi = new ByteArrayOutputStream();
        hi.write(0xE9);
        check(hi.toString(0).charAt(0) == (char) 0xE9, "hibyte 0 must keep low byte");
        check(hi.toString(1).charAt(0) == (char) 0x1E9, "hibyte 1 must set high byte");
        check(hi.toString(-1).charAt(0) == (char) 0xFFE9, "negative hibyte must use its low 8 bits");
        check(hi.toString(0x101).equals(hi.toString(1)), "hibyte must be masked to 8 bits");

        ab.reset();
        check(ab.toString(0).isEmpty(), "reset stream must yield empty string");
        ab.write(0x43);
        check("C".equals(ab.toString(0)), "stream must be usable after reset");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
