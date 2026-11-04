/*
 * @test
 * @summary Behavioral tests for DataInputStream.read(byte[]) delegation to the underlying stream
 * @run main DataInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class DataInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testReadFillsWholeBuffer();
        testReadFromNonEmptyStreamWithEmptyBufferReturnsZero();
        testReadAtEndOfStreamReturnsMinusOne();
        testShortStreamReturnsAvailableBytesAndLeavesTailUntouched();
        testNullBufferThrowsNullPointerException();
    }

    private static void testReadFillsWholeBuffer() throws IOException {
        byte[] data = {1, 2, 3, 4};
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(data));
        byte[] b = new byte[4];
        int n = in.read(b);
        check(n == 4, "expected 4 bytes read, got " + n);
        for (int i = 0; i < data.length; i++) {
            check(b[i] == data[i], "byte mismatch at " + i);
        }
    }

    private static void testReadFromNonEmptyStreamWithEmptyBufferReturnsZero() throws IOException {
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(new byte[] {5}));
        byte[] b = new byte[0];
        int n = in.read(b);
        check(n == 0, "expected 0 for zero-length buffer, got " + n);
    }

    private static void testReadAtEndOfStreamReturnsMinusOne() throws IOException {
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(new byte[0]));
        byte[] b = new byte[3];
        int n = in.read(b);
        check(n == -1, "expected -1 at end of stream, got " + n);
    }

    private static void testShortStreamReturnsAvailableBytesAndLeavesTailUntouched() throws IOException {
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(new byte[] {9, 8}));
        byte[] b = new byte[5];
        int n = in.read(b);
        check(n == 2, "expected 2 bytes read, got " + n);
        check(b[0] == 9 && b[1] == 8, "unexpected leading bytes");
        for (int i = 2; i < b.length; i++) {
            check(b[i] == 0, "tail byte modified at " + i);
        }
    }

    private static void testNullBufferThrowsNullPointerException() throws IOException {
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(new byte[1]));
        boolean thrown = false;
        try {
            in.read((byte[]) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null buffer");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
