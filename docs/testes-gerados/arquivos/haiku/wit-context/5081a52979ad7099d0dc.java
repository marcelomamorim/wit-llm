/*
 * @test
 * @summary WITUP-derived checks for File.toURL(): invalid path raises MalformedURLException and valid paths yield file URLs
 * @run main FileWitupTest
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class FileWitupTest {

    public static void main(String[] args) throws Exception {
        testPathWithNulThrowsMalformedURLException();
        testRelativeFileYieldsFileUrl();
        testExistingDirectoryUrlEndsWithSlash();
    }

    static void testPathWithNulThrowsMalformedURLException() {
        File f = new File("bad\0name.txt");
        try {
            f.toURL();
            throw new RuntimeException("Expected MalformedURLException for invalid path");
        } catch (MalformedURLException expected) {
            // expected
        }
    }

    static void testRelativeFileYieldsFileUrl() throws Exception {
        File f = new File("witup-nonexistent-file.txt");
        URL url = f.toURL();
        if (!"file".equals(url.getProtocol())) {
            throw new RuntimeException("Unexpected protocol: " + url.getProtocol());
        }
        String path = url.getPath();
        if (!path.endsWith("witup-nonexistent-file.txt")) {
            throw new RuntimeException("Unexpected path: " + path);
        }
    }

    static void testExistingDirectoryUrlEndsWithSlash() throws Exception {
        String userDir = System.getProperty("user.dir");
        File dir = new File(userDir);
        if (!dir.isDirectory()) {
            return;
        }
        URL url = dir.toURL();
        if (!"file".equals(url.getProtocol())) {
            throw new RuntimeException("Unexpected protocol: " + url.getProtocol());
        }
        if (!url.getPath().endsWith("/")) {
            throw new RuntimeException("Directory URL path should end with '/': " + url.getPath());
        }
    }
}
