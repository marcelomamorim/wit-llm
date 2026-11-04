/*
 * @test
 * @summary Tests ObjectOutputStream.write(byte[], int, int) argument validation and written bytes
 * @run main ObjectOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ObjectOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testNullBuffer();
        testNegativeOffset();
        testNegativeLength();
        testRangePastEnd();
        testOffsetOverflow();
        testZeroLengthAtEnd();
        testValidWriteContainsBytes();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static ObjectOutputStream newStream(ByteArrayOutputStream bout) throws IOException {
        return new ObjectOutputStream(bout);
    }

    static void testNullBuffer() throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream oos = newStream(bout);
        try {
            oos.write(null, 0, 0);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testNegativeOffset() throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream oos = newStream(bout);
        try {
            oos.write(new byte[4], -1, 1);
            throw new RuntimeException("expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testNegativeLength() throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream oos = newStream(bout);
        try {
            oos.write(new byte[4], 0, -1);
            throw new RuntimeException("expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testRangePastEnd() throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream oos = newStream(bout);
        try {
            oos.write(new byte[4], 2, 3);
            throw new RuntimeException("expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testOffsetOverflow() throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream oos = newStream(bout);
        try {
            oos.write(new byte[4], Integer.MAX_VALUE, 1);
            throw new RuntimeException("expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testZeroLengthAtEnd() throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream oos = newStream(bout);
        oos.write(new byte[4], 4, 0);
        oos.flush();
    }

    static boolean contains(byte[] haystack, byte[] needle) {
        outer:
        for (int i = 0; i + needle.length <= haystack.length; i++) {
            for (int j = 0; j < needle.length; j++) {
                if (haystack[i + j] != needle[j]) {
                    continue outer;
                }
            }
            return true;
        }
        return false;
    }

    static void testValidWriteContainsBytes() throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream oos = newStream(bout);
        byte[] data = {1, 2, 3, (byte) 0xAB};
        oos.write(data, 1, 2);
        oos.flush();
        byte[] out = bout.toByteArray();
        check(contains(out, new byte[] {2, 3}),
              "written block data does not contain the requested bytes");
        check(!contains(out, new byte[] {1, 2, 3, (byte) 0xAB}) || out.length > 0,
              "unexpected output state");
    }
}
