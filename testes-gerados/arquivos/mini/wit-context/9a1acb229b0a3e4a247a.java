/*
 * @test
 * @summary Test JarInputStream(InputStream in) constructor for basic behavior and IOException on closed stream
 * @run main JarInputStreamWitupTest
 */

import java.util.jar.JarInputStream;
import java.io.*;

public class JarInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testConstructorWithValidStream();
        testConstructorWithClosedStream();
    }

    private static void testConstructorWithValidStream() throws IOException {
        // Create a minimal valid JAR stream with no entries
        byte[] jarData = new byte[] {
            // ZIP local file header signature
            0x50, 0x4b, 0x03, 0x04,
            // Version needed to extract
            0x00, 0x00,
            // General purpose bit flag
            0x00, 0x00,
            // Compression method (store)
            0x00, 0x00,
            // Last mod file time
            0x00, 0x00,
            // Last mod file date
            0x00, 0x00,
            // CRC-32
            0x00, 0x00, 0x00, 0x00,
            // Compressed size
            0x00, 0x00, 0x00, 0x00,
            // Uncompressed size
            0x00, 0x00, 0x00, 0x00,
            // File name length
            0x00, 0x00,
            // Extra field length
            0x00, 0x00
        };

        ByteArrayInputStream bais = new ByteArrayInputStream(jarData);
        JarInputStream jis = new JarInputStream(bais);
        // The first entry should be null because no entries
        if (jis.getNextEntry() != null) {
            throw new AssertionError("Expected no entries in JarInputStream");
        }
        jis.close();
    }

    private static void testConstructorWithClosedStream() {
        ByteArrayInputStream bais = new ByteArrayInputStream(new byte[0]);
        try {
            bais.close();
            new JarInputStream(bais);
            throw new AssertionError("Expected IOException when constructing JarInputStream with closed stream");
        } catch (IOException e) {
            // Expected IOException due to closed stream
        }
    }
}
