/*
 * @test
 * @summary Witup-style checks for DerOutputStream.putSet
 * @modules java.base/sun.security.util
 * @run main DerOutputStreamWitupTest
 */
import java.io.IOException;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;

public class DerOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testEmptySetEncodesTagAndZeroLength();
        testAppendsAfterExistingContent();
        testNullSetThrowsNullPointerException();
    }

    static void testEmptySetEncodesTagAndZeroLength() throws IOException {
        DerOutputStream out = new DerOutputStream();
        out.putSet(new DerValue[0]);
        byte[] b = out.toByteArray();
        if (b.length != 2) {
            throw new RuntimeException("expected 2 bytes, got " + b.length);
        }
        if (b[0] != DerValue.tag_Set) {
            throw new RuntimeException("unexpected tag: " + b[0]);
        }
        if (b[1] != 0) {
            throw new RuntimeException("unexpected length: " + b[1]);
        }
    }

    static void testAppendsAfterExistingContent() throws IOException {
        DerOutputStream out = new DerOutputStream();
        out.write(1);
        out.putSet(new DerValue[0]);
        byte[] b = out.toByteArray();
        if (b.length != 3) {
            throw new RuntimeException("expected 3 bytes, got " + b.length);
        }
        if (b[0] != 1) {
            throw new RuntimeException("existing content was altered");
        }
        if (b[1] != DerValue.tag_Set) {
            throw new RuntimeException("unexpected tag: " + b[1]);
        }
        if (b[2] != 0) {
            throw new RuntimeException("unexpected length: " + b[2]);
        }
    }

    static void testNullSetThrowsNullPointerException() throws IOException {
        DerOutputStream out = new DerOutputStream();
        try {
            out.putSet(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
        if (out.size() != 0) {
            throw new RuntimeException("stream modified on failure");
        }
    }
}
