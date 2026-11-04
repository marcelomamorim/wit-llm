/*
 * @test
 * @summary Checks public observable behavior of ZipFile.getInputStream(ZipEntry): null entry NPE, missing entry returns null, present entry returns its content
 * @run main ZipFileWitupTest
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ZipFileWitupTest {

    private static final String CONTENT = "hello witup";
    private static final String ENTRY_NAME = "a.txt";

    public static void main(String[] args) throws Exception {
        File f = File.createTempFile("witup", ".zip");
        f.deleteOnExit();
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(f))) {
            zos.putNextEntry(new ZipEntry(ENTRY_NAME));
            zos.write(CONTENT.getBytes(StandardCharsets.UTF_8));
            zos.closeEntry();
        }

        testNullEntryThrowsNpe(f);
        testMissingEntryReturnsNull(f);
        testPresentEntryReturnsContent(f);
    }

    private static void testNullEntryThrowsNpe(File f) throws IOException {
        try (ZipFile zf = new ZipFile(f)) {
            try {
                zf.getInputStream(null);
            } catch (NullPointerException expected) {
                return;
            }
            throw new RuntimeException("NullPointerException expected for null entry");
        }
    }

    private static void testMissingEntryReturnsNull(File f) throws IOException {
        try (ZipFile zf = new ZipFile(f)) {
            InputStream is = zf.getInputStream(new ZipEntry("missing.txt"));
            if (is != null) {
                is.close();
                throw new RuntimeException("null expected for missing entry");
            }
        }
    }

    private static void testPresentEntryReturnsContent(File f) throws IOException {
        try (ZipFile zf = new ZipFile(f)) {
            try (InputStream is = zf.getInputStream(new ZipEntry(ENTRY_NAME))) {
                if (is == null) {
                    throw new RuntimeException("stream expected for present entry");
                }
                byte[] buf = new byte[256];
                int total = 0;
                int n;
                while ((n = is.read(buf, total, buf.length - total)) != -1) {
                    total += n;
                    if (total == buf.length) {
                        break;
                    }
                }
                String read = new String(buf, 0, total, StandardCharsets.UTF_8);
                if (!CONTENT.equals(read)) {
                    throw new RuntimeException("unexpected content: " + read);
                }
            }
        }
    }
}
