/*
 * @test
 * @summary Checks ZipFile(File, int) mode validation and null file handling
 * @run main ZipFileWitupTest
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ZipFileWitupTest {

    private static void createEmptyZip(File f) throws IOException {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(f))) {
            zos.flush();
        }
    }

    private static void expectIllegalArgument(File f, int mode) {
        try {
            ZipFile zf = new ZipFile(f, mode);
            zf.close();
            throw new AssertionError("IllegalArgumentException expected for mode " + mode);
        } catch (IllegalArgumentException expected) {
            // expected
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException for mode " + mode, e);
        }
    }

    public static void main(String[] args) throws Exception {
        File f = File.createTempFile("witup", ".zip");
        try {
            createEmptyZip(f);

            ZipFile zf = new ZipFile(f, ZipFile.OPEN_READ);
            try {
                if (zf.size() != 0) {
                    throw new AssertionError("Expected empty zip");
                }
            } finally {
                zf.close();
            }

            ZipFile zf2 = new ZipFile(f, ZipFile.OPEN_READ | ZipFile.OPEN_DELETE);
            zf2.close();
            createEmptyZip(f);

            expectIllegalArgument(f, 0);
            expectIllegalArgument(f, ZipFile.OPEN_DELETE);
            expectIllegalArgument(f, 2);
            expectIllegalArgument(f, 8);
            expectIllegalArgument(f, -1);

            try {
                new ZipFile((File) null, ZipFile.OPEN_READ);
                throw new AssertionError("NullPointerException expected for null file");
            } catch (NullPointerException expected) {
                // expected
            }
        } finally {
            f.delete();
        }
    }
}
