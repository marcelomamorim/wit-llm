/*
 * @test
 * @summary Tests for java.io.InputStream.readNBytes(int)
 * @run main InputStreamWitupTest
 */

import java.io.IOException;
import java.io.InputStream;

public class InputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testReadNBytesNegative();
        testReadNBytesClosedStream();
    }

    private static void testReadNBytesNegative() {
        InputStream stream = InputStream.nullInputStream();
        try {
            stream.readNBytes(-1);
            System.err.println("Failed: Expected IllegalArgumentException for negative len");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (IOException e) {
            System.err.println("Failed: Unexpected IOException");
        }
    }

    private static void testReadNBytesClosedStream() throws IOException {
        InputStream stream = InputStream.nullInputStream();
        stream.close();
        try {
            stream.readNBytes(10);
            System.err.println("Failed: Expected IOException after stream closed");
        } catch (IOException e) {
            // Expected exception
        }
    }
}
