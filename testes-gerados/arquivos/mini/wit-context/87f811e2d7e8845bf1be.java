/*
 * @test
 * @summary Test InputStream.read(byte[], int, int) for bounds checks and closed stream
 * @run main InputStreamWitupTest
 */

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class InputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testReadThrowsIndexOutOfBoundsException();
        testReadReturnsZeroForZeroLength();
        testReadThrowsIOExceptionWhenClosed();
        testReadReturnsMinusOneWhenOpenAndLenPositive();
    }

    static void testReadThrowsIndexOutOfBoundsException() {
        InputStream is = InputStream.nullInputStream();
        byte[] buffer = new byte[10];
        // off < 0
        try {
            is.read(buffer, -1, 5);
            throw new AssertionError("Expected IndexOutOfBoundsException for off < 0");
        } catch (IndexOutOfBoundsException expected) {
        }
        // len < 0
        try {
            is.read(buffer, 0, -1);
            throw new AssertionError("Expected IndexOutOfBoundsException for len < 0");
        } catch (IndexOutOfBoundsException expected) {
        }
        // off + len > buffer.length
        try {
            is.read(buffer, 5, 6);
            throw new AssertionError("Expected IndexOutOfBoundsException for off + len > buffer.length");
        } catch (IndexOutOfBoundsException expected) {
        }
    }

    static void testReadReturnsZeroForZeroLength() throws IOException {
        InputStream is = InputStream.nullInputStream();
        byte[] buffer = new byte[10];
        int result = is.read(buffer, 0, 0);
        if (result != 0) {
            throw new AssertionError("Expected read to return 0 when len is 0, got " + result);
        }
    }

    static void testReadThrowsIOExceptionWhenClosed() throws IOException {
        InputStream is = InputStream.nullInputStream();
        is.close();
        byte[] buffer = new byte[10];
        try {
            is.read(buffer, 0, 1);
            throw new AssertionError("Expected IOException when reading from closed stream");
        } catch (IOException expected) {
        }
    }

    static void testReadReturnsMinusOneWhenOpenAndLenPositive() throws IOException {
        InputStream is = InputStream.nullInputStream();
        byte[] buffer = new byte[10];
        int result = is.read(buffer, 0, 1);
        if (result != -1) {
            throw new AssertionError("Expected read to return -1 when stream is open and len > 0, got " + result);
        }
    }

}
