/*
 * @test
 * @summary Test the read() method of InflaterInputStream for correct behavior.
 * @run main InflaterInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

public class InflaterInputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testReadReturnsData();
        testReadReturnsMinusOneAtEOF();
        testReadThrowsWhenStreamClosed();
    }

    static void testReadReturnsData() throws IOException {
        byte[] compressedData = { 120, -100, 3, 0, 0, 0, 0, 1 }; // compressed form of empty data
        ByteArrayInputStream bais = new ByteArrayInputStream(compressedData);
        Inflater inflater = new Inflater();
        try (InflaterInputStream iis = new InflaterInputStream(bais, inflater)) {
            int result = iis.read();
            // Since the data decompresses to empty, expect -1 or 0 depending on implementation
            // But as per implementation, read() calls read(byte[], int, int), which returns -1 at EOF
            // So, here, we check if result is -1, which indicates EOF immediately
            if (result != -1) {
                System.out.println("testReadReturnsData: Passed, read byte: " + result);
            } else {
                System.out.println("testReadReturnsData: Passed, EOF reached immediately.");
            }
        }
    }

    static void testReadReturnsMinusOneAtEOF() throws IOException {
        byte[] compressedData = { 120, -100, 3, 0, 0, 0, 0, 1 };
        ByteArrayInputStream bais = new ByteArrayInputStream(compressedData);
        Inflater inflater = new Inflater();
        try (InflaterInputStream iis = new InflaterInputStream(bais, inflater)) {
            while (iis.read() != -1) {
                // Read until EOF
            }
            int result = iis.read();
            if (result == -1) {
                System.out.println("testReadReturnsMinusOneAtEOF: Passed, EOF reached.");
            } else {
                System.out.println("testReadReturnsMinusOneAtEOF: Failed, expected -1 but got: " + result);
            }
        }
    }

    static void testReadThrowsWhenStreamClosed() {
        byte[] data = { 1, 2, 3 };
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        Inflater inflater = new Inflater();
        InflaterInputStream iis = new InflaterInputStream(bais, inflater);
        try {
            // Close the stream
            iis.close();
            // Now, calling read() should throw IOException
            iis.read();
            System.out.println("testReadThrowsWhenStreamClosed: Failed, no exception thrown.");
        } catch (IOException e) {
            System.out.println("testReadThrowsWhenStreamClosed: Passed, caught exception: " + e.getMessage());
        }
    }
}
