/*
 * @test
 * @summary ZipFile(String) opens a valid zip by name, fails for a missing file and rejects a null name
 * @run main ZipFileWitupTest
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ZipFileWitupTest {

    public static void main(String[] args) throws Exception {
        File f = File.createTempFile("witup", ".zip");
        try {
            try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(f))) {
                zos.putNextEntry(new ZipEntry("a.txt"));
                zos.write(new byte[] {'h', 'i'});
                zos.closeEntry();
            }
            try (ZipFile zf = new ZipFile(f.getPath())) {
                if (zf.size() != 1) {
                    throw new RuntimeException("unexpected size");
                }
                if (zf.getEntry("a.txt") == null) {
                    throw new RuntimeException("entry a.txt not found");
                }
                if (!f.getPath().equals(zf.getName())) {
                    throw new RuntimeException("unexpected name");
                }
            }
        } finally {
            f.delete();
        }

        File missing = new File("witup-missing-" + System.nanoTime() + ".zip");
        boolean ioException = false;
        try {
            new ZipFile(missing.getPath()).close();
        } catch (IOException e) {
            ioException = true;
        }
        if (!ioException) {
            throw new RuntimeException("missing file did not raise IOException");
        }

        boolean npe = false;
        try {
            new ZipFile((String) null);
        } catch (NullPointerException e) {
            npe = true;
        }
        if (!npe) {
            throw new RuntimeException("null name did not raise NullPointerException");
        }
    }
}
