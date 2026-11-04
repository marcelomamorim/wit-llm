/*
 * @test
 * @summary WITUP-derived behavioral checks for the public FileInputStream(String name) constructor
 * @run main FileInputStreamWitupTest
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testNullNameThrowsNPE();
        testEmptyNameThrowsFileNotFound();
        testMissingFileThrowsFileNotFound();
        testDirectoryThrowsFileNotFound();
        testExistingFileIsReadable();
    }

    static void testNullNameThrowsNPE() {
        try {
            new FileInputStream((String) null);
        } catch (NullPointerException e) {
            return;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("unexpected FileNotFoundException for null name", e);
        }
        throw new RuntimeException("expected NullPointerException for null name");
    }

    static void testEmptyNameThrowsFileNotFound() {
        try {
            new FileInputStream("");
        } catch (FileNotFoundException e) {
            return;
        }
        throw new RuntimeException("expected FileNotFoundException for empty name");
    }

    static void testMissingFileThrowsFileNotFound() throws IOException {
        File tmp = File.createTempFile("witup_missing", ".tmp");
        String path = tmp.getPath();
        if (!tmp.delete()) {
            throw new RuntimeException("could not prepare missing file");
        }
        try {
            new FileInputStream(path);
        } catch (FileNotFoundException e) {
            return;
        }
        throw new RuntimeException("expected FileNotFoundException for missing file");
    }

    static void testDirectoryThrowsFileNotFound() {
        try {
            new FileInputStream(".");
        } catch (FileNotFoundException e) {
            return;
        }
        throw new RuntimeException("expected FileNotFoundException for directory");
    }

    static void testExistingFileIsReadable() throws IOException {
        File tmp = File.createTempFile("witup_existing", ".bin");
        try {
            try (FileOutputStream out = new FileOutputStream(tmp)) {
                out.write(new byte[] {42, 43});
            }
            try (FileInputStream in = new FileInputStream(tmp.getPath())) {
                int first = in.read();
                int second = in.read();
                int eof = in.read();
                if (first != 42 || second != 43 || eof != -1) {
                    throw new RuntimeException("unexpected content: " + first + "," + second + "," + eof);
                }
            }
        } finally {
            tmp.delete();
        }
    }
}
