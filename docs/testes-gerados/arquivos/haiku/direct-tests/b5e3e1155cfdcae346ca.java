/*
 * @test
 * @summary Verifies ZipFile.getInputStream returns content for existing entries, null for missing entries, and NPE for null entry
 * @run main ZipFileWitupTest
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ZipFileWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) throws Exception {
        File f = File.createTempFile("witup", ".zip");
        try {
            try (FileOutputStream fos = new FileOutputStream(f);
                 ZipOutputStream zos = new ZipOutputStream(fos)) {
                zos.putNextEntry(new ZipEntry("a.txt"));
                zos.write("hello".getBytes(StandardCharsets.UTF_8));
                zos.closeEntry();
                zos.putNextEntry(new ZipEntry("dir/b.txt"));
                zos.write("world".getBytes(StandardCharsets.UTF_8));
                zos.closeEntry();
            }

            try (ZipFile zf = new ZipFile(f)) {
                ZipEntry ea = zf.getEntry("a.txt");
                check(ea != null, "entry a.txt not found");
                try (InputStream in = zf.getInputStream(ea)) {
                    String s = new String(in.readAllBytes(), StandardCharsets.UTF_8);
                    check("hello".equals(s), "unexpected content for a.txt: " + s);
                }

                try (InputStream in2 = zf.getInputStream(new ZipEntry("dir/b.txt"))) {
                    check(in2 != null, "stream for dir/b.txt is null");
                    String s2 = new String(in2.readAllBytes(), StandardCharsets.UTF_8);
                    check("world".equals(s2), "unexpected content for dir/b.txt: " + s2);
                }

                check(zf.getInputStream(new ZipEntry("missing.txt")) == null,
                      "missing entry should yield null");

                try {
                    zf.getInputStream(null);
                    throw new RuntimeException("expected NullPointerException");
                } catch (NullPointerException expected) {
                    // expected
                }

                try (InputStream again = zf.getInputStream(ea)) {
                    String s3 = new String(again.readAllBytes(), StandardCharsets.UTF_8);
                    check("hello".equals(s3), "repeated read returned wrong content: " + s3);
                }
            }
        } finally {
            f.delete();
        }
    }
}
