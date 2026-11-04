/*
 * @test
 * @summary Behavior of InputStream.nullInputStream().read(byte[], int, int) for open and closed streams
 * @run main InputStreamWitupTest
 */

import java.io.IOException;
import java.io.InputStream;

public class InputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testOpenStreamReturnsEndOfStream();
        testZeroLengthReturnsZero();
        testOutOfBoundsThrowsIndexOutOfBounds();
        testClosedStreamThrowsIOException();
        testClosedStreamZeroLengthReturnsZero();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testOpenStreamReturnsEndOfStream() throws IOException {
        InputStream in = InputStream.nullInputStream();
        byte[] buf = new byte[10];
        int r = in.read(buf, 0, 5);
        check(r == -1, "expected -1 for open null stream, got " + r);
        in.close();
    }

    static void testZeroLengthReturnsZero() throws IOException {
        InputStream in = InputStream.nullInputStream();
        byte[] buf = new byte[10];
        int r = in.read(buf, 0, 0);
        check(r == 0, "expected 0 for len == 0, got " + r);
        r = in.read(buf, 10, 0);
        check(r == 0, "expected 0 for off == length and len == 0, got " + r);
        in.close();
    }

    static void testOutOfBoundsThrowsIndexOutOfBounds() throws IOException {
        InputStream in = InputStream.nullInputStream();
        byte[] buf = new byte[10];
        boolean thrownNegativeOff = false;
        try {
            in.read(buf, -1, 1);
        } catch (IndexOutOfBoundsException e) {
            thrownNegativeOff = true;
        }
        check(thrownNegativeOff, "expected IndexOutOfBoundsException for negative off");

        boolean thrownTooLong = false;
        try {
            in.read(buf, 0, 11);
        } catch (IndexOutOfBoundsException e) {
            thrownTooLong = true;
        }
        check(thrownTooLong, "expected IndexOutOfBoundsException for len beyond array");

        boolean thrownOffPlusLen = false;
        try {
            in.read(buf, 5, 6);
        } catch (IndexOutOfBoundsException e) {
            thrownOffPlusLen = true;
        }
        check(thrownOffPlusLen, "expected IndexOutOfBoundsException for off + len beyond array");
        in.close();
    }

    static void testClosedStreamThrowsIOException() throws IOException {
        InputStream in = InputStream.nullInputStream();
        in.close();
        byte[] buf = new byte[10];
        boolean thrown = false;
        try {
            in.read(buf, 0, 5);
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "expected IOException reading from closed null stream");
    }

    static void testClosedStreamZeroLengthReturnsZero() throws IOException {
        InputStream in = InputStream.nullInputStream();
        in.close();
        byte[] buf = new byte[10];
        int r = in.read(buf, 0, 0);
        check(r == 0, "expected 0 for len == 0 on closed stream, got " + r);
    }
}
