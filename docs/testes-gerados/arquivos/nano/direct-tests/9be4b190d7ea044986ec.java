/* @test    @summary Test getCanonicalFile method for File class    @run main FileWitupTest */

import java.io.File;
import java.io.IOException;

public class FileWitupTest {

    public static void main(String[] args) throws IOException {
        testGetCanonicalFileWithAbsolutePath();
        testGetCanonicalFileWithRelativePath();
        testGetCanonicalFileWithNonExistentPath();
    }

    private static void testGetCanonicalFileWithAbsolutePath() throws IOException {
        String absolutePath = System.getProperty("user.dir");
        File file = new File(absolutePath);
        File canonicalFile = file.getCanonicalFile();
        assert canonicalFile != null : "Canonical file should not be null";
        assert canonicalFile.getPath().equals(file.getPath()) : "Paths should be equal for absolute path";
    }

    private static void testGetCanonicalFileWithRelativePath() throws IOException {
        File file = new File(".");
        File canonicalFile = file.getCanonicalFile();
        String expectedPath = new File(System.getProperty("user.dir")).getCanonicalPath();
        assert canonicalFile.getCanonicalPath().equals(expectedPath) : "Canonical paths should match";
    }

    private static void testGetCanonicalFileWithNonExistentPath() throws IOException {
        String nonExistentPath = "nonexistent_directory_12345";
        File file = new File(nonExistentPath);
        File canonicalFile = file.getCanonicalFile();
        String expectedPath = new File(nonExistentPath).getCanonicalPath();
        assert canonicalFile.getPath().equals(expectedPath) : "Paths should match for non-existent path";
    }
}
