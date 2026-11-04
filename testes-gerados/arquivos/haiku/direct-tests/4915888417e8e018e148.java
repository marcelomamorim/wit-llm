/*
 * @test
 * @summary Tests File.createNewFile() observable results: creating a new file, returning false when the file or directory already exists, and rejecting paths containing a NUL character
 * @run main FileWitupTest
 */

import java.io.File;
import java.io.IOException;

public class FileWitupTest {

    public static void main(String[] args) throws Exception {
        testCreatesNewFileThenReturnsFalse();
        testExistingDirectoryReturnsFalse();
        testPathWithNulCharThrowsIOException();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testCreatesNewFileThenReturnsFalse() throws IOException {
        File f = new File("createNewFileWitupTest.tmp");
        f.delete();
        try {
            check(f.createNewFile(), "first call should create the file and return true");
            check(f.exists(), "file should exist after createNewFile returned true");
            check(f.isFile(), "created path should be a regular file");
            check(!f.createNewFile(), "second call should return false because the file exists");
        } finally {
            f.delete();
        }
    }

    static void testExistingDirectoryReturnsFalse() throws IOException {
        File dir = new File("createNewFileWitupDir.tmp");
        dir.delete();
        check(dir.mkdir(), "setup: directory should be created");
        try {
            check(!dir.createNewFile(), "createNewFile on an existing directory should return false");
            check(dir.isDirectory(), "existing directory should remain a directory");
        } finally {
            dir.delete();
        }
    }

    static void testPathWithNulCharThrowsIOException() {
        File f = new File("bad" + (char) 0 + "name.tmp");
        try {
            f.createNewFile();
            throw new RuntimeException("expected IOException for path containing NUL character");
        } catch (IOException e) {
            check("Invalid file path".equals(e.getMessage()),
                  "unexpected IOException message: " + e.getMessage());
        }
    }
}
