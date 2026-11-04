/*
 * @test
 * @summary Test ZipInputStream.closeEntry() behavior including IOException on closed stream
 * @run main ZipInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.ZipInputStream;

public class ZipInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testCloseEntryOnOpenStream();
        testCloseEntryOnClosedStreamThrowsIOException();
    }

    private static void testCloseEntryOnOpenStream() throws IOException {
        byte[] emptyZip = new byte[0];
        ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(emptyZip));
        // closeEntry should not throw if stream is open, even if no entries
        try {
            zis.closeEntry();
        } catch (IOException e) {
            throw new AssertionError("closeEntry threw IOException unexpectedly", e);
        } finally {
            zis.close();
        }
    }

    private static void testCloseEntryOnClosedStreamThrowsIOException() throws IOException {
        byte[] emptyZip = new byte[0];
        ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(emptyZip));
        zis.close();
        boolean threw = false;
        try {
            zis.closeEntry();
        } catch (IOException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IOException when calling closeEntry on closed ZipInputStream");
        }
    }
}
