/*
 * @test
 * @summary Observable behavior of DerInputStream.getBytes(byte[]) for exact reads, zero-length reads, short reads and null input
 * @modules java.base/sun.security.util
 * @run main DerInputStreamWitupTest
 */

import sun.security.util.DerInputStream;
import java.io.IOException;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testReadsExactBytes();
        testSequentialReads();
        testZeroLengthDoesNotConsume();
        testShortReadThrowsIOException();
        testNullArrayThrowsNPE();
    }

    static void testReadsExactBytes() throws Exception {
        DerInputStream in = new DerInputStream(new byte[] {1, 2, 3});
        byte[] out = new byte[3];
        in.getBytes(out);
        check(out[0] == 1 && out[1] == 2 && out[2] == 3, "exact read content");
    }

    static void testSequentialReads() throws Exception {
        DerInputStream in = new DerInputStream(new byte[] {1, 2, 3, 4, 5});
        byte[] first = new byte[2];
        in.getBytes(first);
        check(first[0] == 1 && first[1] == 2, "first chunk");
        byte[] second = new byte[3];
        in.getBytes(second);
        check(second[0] == 3 && second[1] == 4 && second[2] == 5, "second chunk");
    }

    static void testZeroLengthDoesNotConsume() throws Exception {
        DerInputStream in = new DerInputStream(new byte[] {9});
        in.getBytes(new byte[0]);
        byte[] out = new byte[1];
        in.getBytes(out);
        check(out[0] == 9, "zero-length read must not consume data");
    }

    static void testShortReadThrowsIOException() throws Exception {
        DerInputStream in = new DerInputStream(new byte[] {1, 2});
        boolean thrown = false;
        try {
            in.getBytes(new byte[3]);
        } catch (IOException e) {
            thrown = true;
            check(e.getMessage() != null && e.getMessage().contains("Short read"),
                  "short read message");
        }
        check(thrown, "short read must throw IOException");
    }

    static void testNullArrayThrowsNPE() throws Exception {
        DerInputStream in = new DerInputStream(new byte[] {1});
        boolean thrown = false;
        try {
            in.getBytes(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null array must throw NullPointerException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Failed: " + message);
        }
    }
}
