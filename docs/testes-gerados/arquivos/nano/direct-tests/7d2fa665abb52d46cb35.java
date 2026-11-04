/* @test    @summary Test InputStream.read(byte[]) method for correct behavior and exception handling.    @run main InputStreamWitupTest */

import java.io.IOException;
import java.io.InputStream;

public class InputStreamWitupTest {

    public static void main(String[] args) {
        testReadWithNullBuffer();
        testReadWithZeroLength();
        testReadNormal();
    }

    static void testReadWithNullBuffer() {
        InputStream is = InputStream.nullInputStream();
        try {
            is.read(null);
            // Expecting NullPointerException as per InputStream.read(byte[])
            // but since the method calls read(byte[], int, int), which may throw NPE
            // if b is null, we check for that.
            // If no exception, test fails.
            System.err.println("testReadWithNullBuffer: Failed - no exception thrown");
        } catch (NullPointerException expected) {
            // Expected
        } catch (IOException e) {
            System.err.println("testReadWithNullBuffer: Failed - unexpected IOException: " + e);
        } catch (Exception e) {
            System.err.println("testReadWithNullBuffer: Failed - unexpected exception: " + e);
        }
    }

    static void testReadWithZeroLength() {
        InputStream is = InputStream.nullInputStream();
        try {
            int result = is.read(new byte[10], 0, 0);
            if (result != 0) {
                System.err.println("testReadWithZeroLength: Failed - expected 0, got " + result);
            }
        } catch (IOException e) {
            System.err.println("testReadWithZeroLength: Failed - IOException: " + e);
        }
    }

    static void testReadNormal() {
        InputStream is = InputStream.nullInputStream();
        try {
            byte[] buffer = new byte[5];
            int result = is.read(buffer);
            if (result != -1) {
                System.err.println("testReadNormal: Failed - expected -1, got " + result);
            }
        } catch (IOException e) {
            System.err.println("testReadNormal: Failed - IOException: " + e);
        }
    }
}
