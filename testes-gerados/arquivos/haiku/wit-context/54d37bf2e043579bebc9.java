/*
 * @test
 * @summary Verifies ZipFile.getEntry(String) returns entries for existing names, null for missing names, and throws NullPointerException for a null name
 * @run main ZipFileWitupTest
 */
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ZipFileWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        File file = new File("ZipFileWitupTest.zip");
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(file))) {
            zos.putNextEntry(new ZipEntry("a.txt"));
            zos.write("hello".getBytes(StandardCharsets.UTF_8));
            zos.closeEntry();
        }
        try {
            try (ZipFile zf = new ZipFile(file)) {
                ZipEntry entry = zf.getEntry("a.txt");
                check(entry != null, "existing entry should be found");
                check("a.txt".equals(entry.getName()), "entry name should match");

                check(zf.getEntry("missing.txt") == null, "missing entry should yield null");

                boolean thrown = false;
                try {
                    zf.getEntry(null);
                } catch (NullPointerException expected) {
                    thrown = true;
                }
                check(thrown, "null name should throw NullPointerException");
            }
        } finally {
            file.delete();
        }
    }
}
