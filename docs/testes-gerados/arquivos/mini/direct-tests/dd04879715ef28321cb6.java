/*
 * @test
 * @summary Test InflaterInputStream(InputStream, Inflater) constructor
 * @run main InflaterInputStreamWitupTest
 */

import java.util.zip.InflaterInputStream;
import java.util.zip.Inflater;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class InflaterInputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        // Test with valid InputStream and Inflater
        byte[] dummyData = new byte[] {0x78, 0x9c, 0x03, 0x00, 0x00, 0x00, 0x00, 0x01}; // valid zlib header + empty deflate
        InputStream in = new ByteArrayInputStream(dummyData);
        Inflater inf = new Inflater();
        InflaterInputStream iis = new InflaterInputStream(in, inf);
        // The constructor should create an InflaterInputStream with default buffer size 512
        // We test that reading does not throw and returns -1 (empty stream)
        int readByte = iis.read();
        if (readByte != -1) {
            throw new AssertionError("Expected -1 on reading empty compressed stream, got " + readByte);
        }
        iis.close();

        // Test that constructor throws NullPointerException if InputStream is null
        try {
            new InflaterInputStream(null, inf);
            throw new AssertionError("Expected NullPointerException for null InputStream");
        } catch (NullPointerException e) {
            // expected
        }

        // Test that constructor throws NullPointerException if Inflater is null
        try {
            new InflaterInputStream(in, null);
            throw new AssertionError("Expected NullPointerException for null Inflater");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
