/*
 * @test
 * @summary Test DeflaterInputStream(InputStream in) constructor for null input and normal usage
 * @run main DeflaterInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterInputStream;

public class DeflaterInputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        testConstructorWithNullInput();
        testConstructorWithValidInput();
        System.out.println("All tests passed.");
    }

    private static void testConstructorWithNullInput() {
        try {
            new DeflaterInputStream(null);
            throw new AssertionError("Expected NullPointerException for null InputStream");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testConstructorWithValidInput() throws IOException {
        byte[] data = {1, 2, 3, 4, 5};
        InputStream in = new ByteArrayInputStream(data);
        DeflaterInputStream dis = new DeflaterInputStream(in);
        // Read some bytes to verify stream is functional
        int b = dis.read();
        if (b == -1) {
            throw new AssertionError("DeflaterInputStream.read() returned -1 unexpectedly");
        }
        dis.close();
    }
}
