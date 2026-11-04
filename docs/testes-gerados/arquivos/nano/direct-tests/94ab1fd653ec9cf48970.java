/* @test    @summary Test InputStream.readNBytes with various inputs    @run main InputStreamWitupTest */

import java.io.InputStream;
import java.io.IOException;

public class InputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testReadNBytesNegative();
        testReadNBytesZero();
        testReadNBytesPositive();
    }

    static void testReadNBytesNegative() {
        InputStream is = InputStream.nullInputStream();
        try {
            is.readNBytes(-1);
            System.err.println("Failed: Expected IllegalArgumentException for negative length");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (IOException e) {
            System.err.println("Failed: Unexpected IOException for negative length");
        }
    }

    static void testReadNBytesZero() throws IOException {
        InputStream is = InputStream.nullInputStream();
        byte[] result = is.readNBytes(0);
        if (result.length != 0) {
            System.err.println("Failed: Expected empty byte array for zero length");
        }
    }

    static void testReadNBytesPositive() throws IOException {
        InputStream is = InputStream.nullInputStream();
        byte[] result = is.readNBytes(10);
        if (result.length != 0) {
            System.err.println("Failed: Expected empty byte array from nullInputStream");
        }
    }
}
