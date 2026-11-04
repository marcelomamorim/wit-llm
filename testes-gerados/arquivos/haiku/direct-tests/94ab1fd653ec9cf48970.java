/*
 * @test
 * @summary Checks observable behavior of InputStream.readNBytes(int) for negative, zero, partial and exhausted reads
 * @run main InputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class InputStreamWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static class OneByteStream extends InputStream {
        private final byte[] data;
        private int pos;

        OneByteStream(byte[] data) {
            this.data = data;
        }

        @Override
        public int read() {
            return pos < data.length ? (data[pos++] & 0xff) : -1;
        }
    }

    public static void main(String[] args) throws IOException {
        // Negative length must be rejected with IllegalArgumentException
        boolean iae = false;
        try {
            new OneByteStream(new byte[] {1, 2, 3}).readNBytes(-1);
        } catch (IllegalArgumentException e) {
            iae = true;
        }
        check(iae, "readNBytes(-1) should throw IllegalArgumentException");

        iae = false;
        try {
            InputStream.nullInputStream().readNBytes(-1);
        } catch (IllegalArgumentException e) {
            iae = true;
        }
        check(iae, "nullInputStream().readNBytes(-1) should throw IllegalArgumentException");

        // Zero length returns an empty array
        byte[] r = new OneByteStream(new byte[] {1, 2, 3}).readNBytes(0);
        check(r.length == 0, "readNBytes(0) should return empty array");

        // Partial read returns exactly the requested prefix
        r = new OneByteStream(new byte[] {1, 2, 3, 4, 5}).readNBytes(3);
        check(r.length == 3, "expected 3 bytes, got " + r.length);
        check(r[0] == 1 && r[1] == 2 && r[2] == 3, "unexpected bytes for partial read");

        // Request larger than available returns all remaining bytes
        OneByteStream s = new OneByteStream(new byte[] {1, 2, 3, 4, 5});
        r = s.readNBytes(10);
        check(r.length == 5, "expected 5 bytes, got " + r.length);
        check(r[4] == 5, "last byte mismatch");

        // Exhausted stream returns empty array
        r = s.readNBytes(3);
        check(r.length == 0, "exhausted stream should return empty array");

        // ByteArrayInputStream behaves consistently
        r = new ByteArrayInputStream(new byte[] {7, 8, 9}).readNBytes(2);
        check(r.length == 2 && r[0] == 7 && r[1] == 8, "ByteArrayInputStream partial read mismatch");

        // nullInputStream yields no bytes
        r = InputStream.nullInputStream().readNBytes(5);
        check(r.length == 0, "nullInputStream should yield no bytes");
    }
}
