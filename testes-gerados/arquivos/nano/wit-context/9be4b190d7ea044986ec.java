/*
 * @test
 * @summary Test for java.io.File.getCanonicalFile()
 * @run main FileWitupTest
 */

import java.io.File;
import java.io.IOException;

public class FileWitupTest {

    public static void main(String[] args) throws Exception {
        testGetCanonicalFile_NormalPath();
        testGetCanonicalFile_NullPath();
        testGetCanonicalFile_InvalidPath();
    }

    static void testGetCanonicalFile_NormalPath() throws IOException {
        String path = System.getProperty("user.dir");
        File file = new File(path);
        File canonicalFile = file.getCanonicalFile();
        if (!canonicalFile.getPath().equals(file.getCanonicalPath())) {
            throw new AssertionError("Canonical path mismatch for normal path");
        }
    }

    static void testGetCanonicalFile_NullPath() {
        try {
            File file = new File((String) null);
            file.getCanonicalFile();
            throw new AssertionError("Expected NullPointerException for null path");
        } catch (NullPointerException e) {
            // expected
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException for null path", e);
        }
    }

    static void testGetCanonicalFile_InvalidPath() {
        String invalidPath = "<invalid_path>";
        File file = new File(invalidPath);
        try {
            file.getCanonicalFile();
            // Depending on the system, this may or may not throw; check for exception
        } catch (IOException e) {
            // expected in some cases
        }
    }
}
