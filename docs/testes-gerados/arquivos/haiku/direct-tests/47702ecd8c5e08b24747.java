/*
 * @test
 * @summary Verifies DerOutputStream.putSequence encoding for empty sequences, append behavior, and null input
 * @modules java.base/sun.security.util
 * @run main DerOutputStreamWitupTest
 */

import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;

public class DerOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testEmptySequenceEncoding();
        testSequenceIsAppendedToExistingContent();
        testNullArrayThrowsNullPointerException();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmptySequenceEncoding() throws Exception {
        DerOutputStream out = new DerOutputStream();
        out.putSequence(new DerValue[0]);
        byte[] data = out.toByteArray();
        check(data.length == 2, "expected 2 bytes for empty sequence, got " + data.length);
        check(data[0] == (byte) 0x30, "expected SEQUENCE tag 0x30, got " + data[0]);
        check(data[1] == 0, "expected length 0, got " + data[1]);
    }

    static void testSequenceIsAppendedToExistingContent() throws Exception {
        DerOutputStream out = new DerOutputStream();
        out.write(0x05);
        out.putSequence(new DerValue[0]);
        byte[] data = out.toByteArray();
        check(data.length == 3, "expected 3 bytes, got " + data.length);
        check(data[0] == 0x05, "prefix byte not preserved");
        check(data[1] == (byte) 0x30, "expected SEQUENCE tag after prefix");
        check(data[2] == 0, "expected length 0 after prefix");
    }

    static void testNullArrayThrowsNullPointerException() throws Exception {
        DerOutputStream out = new DerOutputStream();
        boolean thrown = false;
        try {
            out.putSequence(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null sequence array");
    }
}
