/*
 * @test
 * @summary Test ZipInputStream.available() behavior including closed stream and entryEOF
 * @run main ZipInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.ZipInputStream;

public class ZipInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testAvailableOnOpenStream();
        testAvailableOnEntryEOF();
        testAvailableOnClosedStream();
    }

    private static void testAvailableOnOpenStream() throws IOException {
        byte[] emptyZip = new byte[0];
        ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(emptyZip));
        // Initially, entryEOF is false, so available() should return 1
        int avail = zis.available();
        if (avail != 1) {
            throw new AssertionError("Expected available() to return 1 on open stream but got " + avail);
        }
        zis.close();
    }

    private static void testAvailableOnEntryEOF() throws IOException {
        byte[] emptyZip = new byte[0];
        ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(emptyZip));
        // Use reflection to set entryEOF to true since no public setter
        try {
            java.lang.reflect.Field entryEOFField = ZipInputStream.class.getDeclaredField("entryEOF");
            entryEOFField.setAccessible(true);
            entryEOFField.setBoolean(zis, true);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Reflection failed", e);
        }
        int avail = zis.available();
        if (avail != 0) {
            throw new AssertionError("Expected available() to return 0 when entryEOF is true but got " + avail);
        }
        zis.close();
    }

    private static void testAvailableOnClosedStream() {
        byte[] emptyZip = new byte[0];
        ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(emptyZip));
        try {
            zis.close();
            zis.available();
            throw new AssertionError("Expected IOException when calling available() on closed stream");
        } catch (IOException e) {
            // Expected IOException with message "Stream closed"
            if (!"Stream closed".equals(e.getMessage())) {
                throw new AssertionError("Expected IOException with message 'Stream closed' but got: " + e.getMessage());
            }
        }
    }
}
