/*
 * @test
 * @summary Tests the public FileInputStream(String name) constructor: successful open, FileNotFoundException for missing files and directories, and NullPointerException for a null name.
 * @run main FileInputStreamWitupTest
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileInputStreamWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testReadsExistingFile() throws IOException {
        File tmp = File.createTempFile("witup", ".bin");
        try {
            try (FileOutputStream out = new FileOutputStream(tmp)) {
                out.write(new byte[] {42, 7, 99});
            }
            try (FileInputStream in = new FileInputStream(tmp.getPath())) {
                check(in.read() == 42, "first byte mismatch");
                check(in.read() == 7, "second byte mismatch");
                check(in.read() == 99, "third byte mismatch");
                check(in.read() == -1, "expected EOF");
            }
        } finally {
            tmp.delete();
        }
    }

    static void testMissingFileThrows() {
        File missing = new File(System.getProperty("java.io.tmpdir"),
                "witup-missing-" + System.nanoTime() + ".bin");
        check(!missing.exists(), "test precondition: file should not exist");
        boolean thrown = false;
        try {
            new FileInputStream(missing.getPath()).close();
        } catch (FileNotFoundException e) {
            thrown = true;
        } catch (IOException e) {
            throw new RuntimeException("unexpected IOException type", e);
        }
        check(thrown, "expected FileNotFoundException for missing file");
    }

    static void testDirectoryThrows() {
        String dir = System.getProperty("java.io.tmpdir");
        boolean thrown = false;
        try {
            new FileInputStream(dir).close();
        } catch (FileNotFoundException e) {
            thrown = true;
        } catch (IOException e) {
            throw new RuntimeException("unexpected IOException type", e);
        }
        check(thrown, "expected FileNotFoundException for directory");
    }

    static void testNullNameThrowsNPE() {
        boolean thrown = false;
        try {
            new FileInputStream((String) null);
        } catch (NullPointerException e) {
            thrown = true;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("null name should not be treated as missing file", e);
        }
        check(thrown, "expected NullPointerException for null name");
    }

    public static void main(String[] args) throws Exception {
        testReadsExistingFile();
        testMissingFileThrows();
        testDirectoryThrows();
        testNullNameThrowsNPE();
    }
}
