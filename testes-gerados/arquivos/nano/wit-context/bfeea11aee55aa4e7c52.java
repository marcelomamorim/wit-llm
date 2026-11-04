/* @test    @summary Test for ZipInputStream.available() method to verify exception handling and normal behavior    @run main ZipInputStreamWitupTest */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.ZipInputStream;

public class ZipInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testAvailableWhenOpen();
        testAvailableWhenClosed();
    }

    private static void testAvailableWhenOpen() throws IOException {
        byte[] data = {0x50, 0x4b, 0x03, 0x04}; // minimal ZIP header
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ZipInputStream zis = new ZipInputStream(bais);
        int result = zis.available();
        if (result != 1) {
            throw new AssertionError("Expected available() to return 1 when stream is open, but got " + result);
        }
        zis.close();
    }

    private static void testAvailableWhenClosed() {
        ByteArrayInputStream bais = new ByteArrayInputStream(new byte[0]);
        ZipInputStream zis = new ZipInputStream(bais);
        try {
            // Close the stream to simulate closed state
            zis.close();
            // Call available() after close to trigger exception
            zis.available();
            throw new AssertionError("Expected IOException when calling available() on closed stream");
        } catch (IOException e) {
            // Expected exception
        }
    }
}
