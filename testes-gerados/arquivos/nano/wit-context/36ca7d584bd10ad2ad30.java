/* @test    @summary Test for ZipInputStream.closeEntry() method to verify behavior when stream is closed.    @run main ZipInputStreamWitupTest */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.ZipInputStream;

public class ZipInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testCloseEntryWhenOpen();
        testCloseEntryWhenAlreadyClosed();
    }

    private static void testCloseEntryWhenOpen() throws IOException {
        byte[] zipData = createDummyZip();
        ByteArrayInputStream bais = new ByteArrayInputStream(zipData);
        ZipInputStream zis = new ZipInputStream(bais);
        try {
            // Should not throw
            zis.closeEntry();
            System.out.println("testCloseEntryWhenOpen passed");
        } catch (IOException e) {
            throw new AssertionError("closeEntry threw unexpectedly", e);
        } finally {
            zis.close();
        }
    }

    private static void testCloseEntryWhenAlreadyClosed() throws IOException {
        byte[] zipData = createDummyZip();
        ByteArrayInputStream bais = new ByteArrayInputStream(zipData);
        ZipInputStream zis = new ZipInputStream(bais);
        zis.close();
        try {
            // Expect IOException because stream is closed
            zis.closeEntry();
            throw new AssertionError("Expected IOException not thrown");
        } catch (IOException e) {
            System.out.println("testCloseEntryWhenAlreadyClosed passed");
        }
    }

    private static byte[] createDummyZip() {
        // Minimal ZIP file with no entries
        return new byte[] {80, 75, 5, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    }
}
