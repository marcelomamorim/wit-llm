/*
 * @test
 * @summary Test java.io.File.getCanonicalFile() basic behavior
 * @run main FileWitupTest
 */

import java.io.File;
import java.io.IOException;

public class FileWitupTest {
    public static void main(String[] args) throws IOException {
        // Test 1: getCanonicalFile on a simple relative path
        File f1 = new File(".");
        File cf1 = f1.getCanonicalFile();
        if (cf1 == null) {
            throw new AssertionError("getCanonicalFile() returned null");
        }
        // The canonical file path should be absolute
        if (!cf1.isAbsolute()) {
            throw new AssertionError("Canonical file is not absolute");
        }

        // Test 2: getCanonicalFile on an absolute path
        File f2 = new File(System.getProperty("user.dir"));
        File cf2 = f2.getCanonicalFile();
        if (cf2 == null) {
            throw new AssertionError("getCanonicalFile() returned null for absolute path");
        }
        if (!cf2.isAbsolute()) {
            throw new AssertionError("Canonical file is not absolute for absolute input");
        }

        // Test 3: getCanonicalFile on a path with parent references
        File f3 = new File(".." + File.separator + ".");
        File cf3 = f3.getCanonicalFile();
        if (cf3 == null) {
            throw new AssertionError("getCanonicalFile() returned null for parent path");
        }
        if (!cf3.isAbsolute()) {
            throw new AssertionError("Canonical file is not absolute for parent path");
        }

        // Test 4: getCanonicalFile on a file that does not exist (should still return canonical file)
        File f4 = new File("nonexistent_file_123456789.tmp");
        File cf4 = f4.getCanonicalFile();
        if (cf4 == null) {
            throw new AssertionError("getCanonicalFile() returned null for nonexistent file");
        }
        if (!cf4.isAbsolute()) {
            throw new AssertionError("Canonical file is not absolute for nonexistent file");
        }

        System.out.println("All getCanonicalFile() tests passed.");
    }
}
