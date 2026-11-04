/*
 * @test
 * @summary Tests ZipInputStream.closeEntry() on empty input and on a closed stream
 * @run main ZipInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testCloseEntryOnEmptyStreamDoesNotThrow();
        testCloseEntryIsIdempotentOnEmptyStream();
        testCloseEntryAfterCloseThrowsIOException();
    }

    static void testCloseEntryOnEmptyStreamDoesNotThrow() throws IOException {
        ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(new byte[0]));
        zis.closeEntry();
        ZipEntry e = zis.getNextEntry();
        if (e != null) {
            throw new RuntimeException("Expected no entries in empty stream");
        }
        zis.close();
    }

    static void testCloseEntryIsIdempotentOnEmptyStream() throws IOException {
        ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(new byte[0]));
        zis.closeEntry();
        zis.closeEntry();
        if (zis.getNextEntry() != null) {
            throw new RuntimeException("Expected no entries after repeated closeEntry");
        }
        zis.close();
    }

    static void testCloseEntryAfterCloseThrowsIOException() throws IOException {
        ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(new byte[0]));
        zis.close();
        boolean thrown = false;
        try {
            zis.closeEntry();
        } catch (IOException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("closeEntry() on closed stream should throw IOException");
        }
    }
}
