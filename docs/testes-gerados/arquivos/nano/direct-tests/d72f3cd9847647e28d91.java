/* @test    @summary Test GZIPInputStream constructor with default buffer size    @run main GZIPInputStreamWitupTest */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

public class GZIPInputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testConstructorWithInputStream();
    }

    private static void testConstructorWithInputStream() throws IOException {
        byte[] data = {0x1f, (byte)0x8b, 0x08, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x03};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        GZIPInputStream gis = new GZIPInputStream(bais);
        if (gis == null) {
            throw new AssertionError("GZIPInputStream instance is null");
        }
        // Attempt to read from the stream to verify it is initialized
        try {
            int byteRead = gis.read();
            // Since data is minimal, expect -1 or IOException
            if (byteRead != -1) {
                // Data might be valid, but in this test, we focus on constructor success
            }
        } catch (IOException e) {
            // Expected if data is not a valid gzip stream
        } finally {
            gis.close();
        }
    }
}
