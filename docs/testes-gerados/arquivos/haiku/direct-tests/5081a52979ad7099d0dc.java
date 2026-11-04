/*
 * @test
 * @summary Verifies File.toURL() for invalid paths, regular paths and directories
 * @run main FileWitupTest
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class FileWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    private static void testInvalidPathThrows() {
        File invalid = new File("a\0b");
        boolean thrown = false;
        try {
            invalid.toURL();
        } catch (MalformedURLException e) {
            thrown = true;
        }
        check(thrown, "toURL() should throw MalformedURLException for path containing NUL");
    }

    private static void testRegularFileProtocolAndPath() throws Exception {
        File f = new File("witup_nonexistent_file_12345.txt");
        URL url = f.toURL();
        check("file".equals(url.getProtocol()), "protocol should be file but was " + url.getProtocol());
        check(url.getPath().endsWith("witup_nonexistent_file_12345.txt"),
              "path should end with file name but was " + url.getPath());
        check(!url.getPath().endsWith("/"), "regular file URL should not end with slash");
    }

    private static void testDirectoryHasTrailingSlash() throws Exception {
        File dir = new File(System.getProperty("user.dir"));
        check(dir.isDirectory(), "user.dir should be a directory");
        URL url = dir.toURL();
        check("file".equals(url.getProtocol()), "directory URL protocol should be file");
        check(url.getPath().endsWith("/"), "directory URL should end with slash but was " + url.getPath());
    }

    private static void testRelativePathResolvedToAbsolute() throws Exception {
        URL url = new File("witup_relative_probe.txt").toURL();
        check(url.getPath().startsWith("/") || url.getPath().indexOf(':') == 2,
              "URL path should be absolute but was " + url.getPath());
    }

    public static void main(String[] args) throws Exception {
        testInvalidPathThrows();
        testRegularFileProtocolAndPath();
        testDirectoryHasTrailingSlash();
        testRelativePathResolvedToAbsolute();
    }
}
