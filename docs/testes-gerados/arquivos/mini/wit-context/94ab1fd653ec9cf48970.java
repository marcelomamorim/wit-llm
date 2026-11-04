/*
 * @test
 * @summary Test InputStream.readNBytes(int) for argument validation and open state
 * @run main InputStreamWitupTest
 */

import java.io.InputStream;
import java.io.IOException;

public class InputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testReadNBytesNegativeLength();
        testReadNBytesOnClosedStream();
        testReadNBytesZeroLengthReturnsEmptyArray();
    }

    private static void testReadNBytesNegativeLength() {
        InputStream is = new InputStream() {
            @Override
            public int read() throws IOException {
                return -1;
            }
        };
        try {
            is.readNBytes(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative length");
        } catch (IllegalArgumentException iae) {
            // expected
        } catch (IOException ioe) {
            throw new AssertionError("Unexpected IOException", ioe);
        }
    }

    private static void testReadNBytesOnClosedStream() {
        InputStream is = InputStream.nullInputStream();
        try {
            is.close();
            is.readNBytes(0);
            throw new AssertionError("Expected IOException after stream closed");
        } catch (IOException expected) {
            // expected
        }
    }

    private static void testReadNBytesZeroLengthReturnsEmptyArray() throws IOException {
        InputStream is = InputStream.nullInputStream();
        byte[] result = is.readNBytes(0);
        if (result == null) {
            throw new AssertionError("readNBytes(0) returned null instead of empty array");
        }
        if (result.length != 0) {
            throw new AssertionError("readNBytes(0) did not return empty array");
        }
    }

}
