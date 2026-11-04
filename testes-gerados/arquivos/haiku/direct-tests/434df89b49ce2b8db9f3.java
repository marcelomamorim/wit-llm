/*
 * @test
 * @summary Tests argument validation and basic block-data reads of ObjectInputStream.read(byte[], int, int)
 * @run main ObjectInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectInputStreamWitupTest {

    static void fail(String msg) {
        throw new RuntimeException(msg);
    }

    static ObjectInputStream newStream(byte[] payload) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.write(payload);
        }
        return new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
    }

    static void readFully(ObjectInputStream ois, byte[] buf, int off, int len) throws IOException {
        int total = 0;
        while (total < len) {
            int r = ois.read(buf, off + total, len - total);
            if (r <= 0) {
                fail("unexpected read result: " + r);
            }
            total += r;
        }
    }

    static void testNullBuffer() throws IOException {
        ObjectInputStream ois = newStream(new byte[] {1});
        try {
            ois.read(null, 0, 1);
            fail("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testNegativeOffset() throws IOException {
        ObjectInputStream ois = newStream(new byte[] {1, 2});
        try {
            ois.read(new byte[4], -1, 2);
            fail("expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }

    static void testNegativeLength() throws IOException {
        ObjectInputStream ois = newStream(new byte[] {1, 2});
        try {
            ois.read(new byte[4], 0, -1);
            fail("expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }

    static void testRangeExceedsBuffer() throws IOException {
        ObjectInputStream ois = newStream(new byte[] {1, 2});
        try {
            ois.read(new byte[4], 2, 3);
            fail("expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }

    static void testReadFullBuffer() throws IOException {
        byte[] payload = {1, 2, 3, 4, 5};
        ObjectInputStream ois = newStream(payload);
        byte[] buf = new byte[5];
        readFully(ois, buf, 0, 5);
        for (int i = 0; i < payload.length; i++) {
            if (buf[i] != payload[i]) {
                fail("mismatch at " + i + ": " + buf[i]);
            }
        }
    }

    static void testReadWithOffset() throws IOException {
        byte[] payload = {'a', 'b', 'c'};
        ObjectInputStream ois = newStream(payload);
        byte[] buf = new byte[8];
        readFully(ois, buf, 2, 3);
        if (buf[0] != 0 || buf[1] != 0) {
            fail("bytes before offset were modified");
        }
        if (buf[2] != 'a' || buf[3] != 'b' || buf[4] != 'c') {
            fail("unexpected content at offset");
        }
        if (buf[5] != 0) {
            fail("bytes after range were modified");
        }
    }

    public static void main(String[] args) throws IOException {
        testNullBuffer();
        testNegativeOffset();
        testNegativeLength();
        testRangeExceedsBuffer();
        testReadFullBuffer();
        testReadWithOffset();
    }
}
