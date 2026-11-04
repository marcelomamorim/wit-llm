/*
 * @test
 * @summary Test java.util.zip.ZipInputStream.available() method behavior
 * @run main ZipInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.ZipInputStream;

public class ZipInputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        testAvailableBeforeClose();
        testAvailableAfterClose();
        testAvailableAfterEntryEOF();
    }

    private static void testAvailableBeforeClose() throws IOException {
        byte[] emptyZip = new byte[0];
        ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(emptyZip));
        // Initially, entryEOF is false, so available() should return 1
        int avail = zis.available();
        if (avail != 1) {
            throw new AssertionError("Expected available() to return 1 before EOF, got " + avail);
        }
        zis.close();
    }

    private static void testAvailableAfterClose() {
        byte[] emptyZip = new byte[0];
        ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(emptyZip));
        try {
            zis.close();
            // After close, available() should throw IOException("Stream closed")
            zis.available();
            throw new AssertionError("Expected IOException after close when calling available()");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.equals("Stream closed")) {
                throw new AssertionError("Expected IOException with message 'Stream closed', got: " + e);
            }
        }
    }

    private static void testAvailableAfterEntryEOF() throws IOException {
        byte[] emptyZip = new byte[0];
        ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(emptyZip));
        // Use reflection to set entryEOF to true since no public setter
        try {
            java.lang.reflect.Field entryEOFField = ZipInputStream.class.getDeclaredField("entryEOF");
            entryEOFField.setAccessible(true);
            entryEOFField.setBoolean(zis, true);
        } catch (ReflectiveOperationException e) {
            throw new AssertionError("Reflection failed to set entryEOF", e);
        }
        int avail = zis.available();
        if (avail != 0) {
            throw new AssertionError("Expected available() to return 0 when entryEOF is true, got " + avail);
        }
        zis.close();
    }
}
