/*
 * @test
 * @summary Verifies ZipInputStream.closeEntry() throws IOException on a closed stream and completes normally on an open stream with no entries
 * @run main ZipInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.ZipInputStream;

public class ZipInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testClosedStreamThrowsIOException();
        testCloseEntryOnEmptyArchiveCompletes();
        testRepeatedCloseEntryOnEmptyArchiveCompletes();
    }

    static void testClosedStreamThrowsIOException() throws Exception {
        ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(new byte[0]));
        zis.close();
        try {
            zis.closeEntry();
        } catch (IOException expected) {
            return;
        }
        throw new RuntimeException("closeEntry() on closed stream did not throw IOException");
    }

    static void testCloseEntryOnEmptyArchiveCompletes() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(new byte[0]))) {
            zis.closeEntry();
        }
    }

    static void testRepeatedCloseEntryOnEmptyArchiveCompletes() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(new byte[0]))) {
            zis.closeEntry();
            zis.closeEntry();
        }
    }
}
