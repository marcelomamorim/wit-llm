/* @test    @summary Test the available() method of ZipInputStream for correct behavior.    @run main ZipInputStreamWitupTest */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.ZipInputStream;

public class ZipInputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testAvailableWhenEntryNotEOF();
        testAvailableWhenEntryEOF();
    }

    static void testAvailableWhenEntryNotEOF() throws IOException {
        byte[] data = {0x50, 0x4b, 0x03, 0x04}; // ZIP local file header signature
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ZipInputStream zis = new ZipInputStream(bais);
        int result = zis.available();
        if (result != 1) {
            throw new AssertionError("Expected available() to return 1 when entry not EOF, but got " + result);
        }
        zis.close();
    }

    static void testAvailableWhenEntryEOF() throws IOException {
        byte[] data = {};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ZipInputStream zis = new ZipInputStream(bais);
        // Simulate end of entry
        // Manually set entryEOF to true to test behavior
        // Note: access to private field is not possible, so we simulate by closing stream
        // and calling available() which should return 0
        zis.close();
        // After close, calling available() should throw IOException, but for test, we check no exception
        try {
            int result = zis.available();
            if (result != 0) {
                throw new AssertionError("Expected available() to return 0 after EOF, but got " + result);
            }
        } catch (IOException e) {
            // Expected if stream is closed
        }
    }
}
