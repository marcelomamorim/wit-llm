/*
 * @test
 * @summary Basic observable behavior of String(byte[], int, int, Charset) constructor
 * @run main StringWitupTest
 */

import java.nio.charset.Charset;

public class StringWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) {
        Charset utf8 = Charset.forName("UTF-8");

        byte[] abc = {'a', 'b', 'c'};
        check("abc".equals(new String(abc, 0, 3, utf8)), "full range decode");

        byte[] sliced = {'x', 'a', 'b', 'y'};
        check("ab".equals(new String(sliced, 1, 2, utf8)), "offset/length decode");

        check(new String(abc, 3, 0, utf8).isEmpty(), "zero length at end yields empty string");

        byte[] multi = {(byte) 0xC3, (byte) 0xA9};
        check("\u00e9".equals(new String(multi, 0, 2, utf8)), "multi-byte UTF-8 decode");

        try {
            new String(abc, 0, 3, (Charset) null);
            check(false, "null charset must throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }

        try {
            new String(abc, -1, 1, utf8);
            check(false, "negative offset must throw IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }

        try {
            new String(abc, 2, 5, utf8);
            check(false, "out-of-range count must throw IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }
}
